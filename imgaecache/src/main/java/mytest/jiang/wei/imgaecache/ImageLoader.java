package com.irex.utils.imagecache;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.irex.R;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wei.jiang on 2015/12/18.
 */
public class ImageLoader {
    private static final String TAG = "ImageLoader";
    private static final int TAG_KEY_URL = R.id.ivIcon;
    private static final int MESSAGE_POST_RESULT = 1;
    private Context mContext;
    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskCache;

    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 10; // 指定DiskLruCache缓存空间大小  10M的空间
    private static final String CACHE_DIR_NAME = "bitmap"; // 缓存文件夹名称
    private static final int MEMORY_CACHE_SIZE = (int) Runtime.getRuntime().maxMemory() / 8; //指定LruCache缓存大小 内存的八分之一

    /****************线程池相关参数********************/
    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() + 1;
    private static final int MAX_POOL_SIZE = CORE_POOL_SIZE * 2;
    private static final int KEEP_ALIVE = 10;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger();
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "ImageLoader#" + mCount.getAndIncrement());
        }
    };

    //private static ImageLoader imageLoader;

    // 创建线程池
    private static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), sThreadFactory);

    // 实现运行在主线程的handler
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            LoadResult result = (LoadResult) msg.obj;
            ImageView imageView = result.imageView;
            String url = (String) imageView.getTag(TAG_KEY_URL);
            if (url.equals(result.url)) { //解决加载出来后，ImageView已经滑过的问题
                imageView.setImageBitmap(result.bitmap);
            } else {
                Log.w(TAG, "set image bitmap ,but url has changed , ignored");
            }
        }
    };
    private boolean isDiskCacheCreate = false;


    private ImageLoader(Context context) {
        mContext = context;
        mMemoryCache = ImageUtil.openLruCache(MEMORY_CACHE_SIZE);
        mDiskCache = ImageUtil.openDiskLruCache(mContext, DISK_CACHE_SIZE, CACHE_DIR_NAME);
        isDiskCacheCreate = true;
    }

    public static ImageLoader build(Context context) {
        /*if (imageLoader == null) {
            imageLoader = new ImageLoader(context);
        }*/
        return new ImageLoader(context);
    }


    /**
     * 把bitmap放入内存中
     * @param url
     * @param bitmap
     */
    private void addBitmap2MemoryCache(String url, Bitmap bitmap) {
        if (loadBitmMapFromMemoryCache(url) == null) {
            mMemoryCache.put(url, bitmap);
        }
    }

    /**
     * 从内存中加载
     * @param url
     * @return
     */
    private Bitmap loadBitmMapFromMemoryCache(String url) {
        return mMemoryCache.get(url);
    }

    /**
     * 从磁盘中加载
     * @param url
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private Bitmap loadBitmapFromDiskCache(String url, int reqWidth, int reqHeight) {
        Bitmap bitmap =  ImageUtil.getFromDiskLruCache(url, mDiskCache, reqWidth, reqHeight);
        if (bitmap != null) { //放入到内存中
            addBitmap2MemoryCache(url, bitmap);
        }
        return bitmap;
    }

    /**
     * 从网络中加载
     * @param url
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private Bitmap loadBitmapFromHttp(String url, int reqWidth, int reqHeight) {
        if (Looper.myLooper() == Looper.getMainLooper()) { //判断是否在主线程
            throw new RuntimeException("can not visit net work from UI Thread");
        }
        if (mDiskCache == null) {
            return null;
        }
        ImageUtil.put2DisLruCache(url, mDiskCache);
        return loadBitmapFromDiskCache(url, reqWidth, reqHeight);
    }

    /**
     * 同步加载
     * @return
     */
    public Bitmap loadBitmap(String url, int reqWidth, int reqHeight) {
        Bitmap bitmap = loadBitmMapFromMemoryCache(url);
        if (bitmap != null) {
            Log.d(TAG, "loadBitmMapFromMemoryCache, url:" + url);
            return bitmap;
        }

        bitmap = loadBitmapFromDiskCache(url, reqWidth, reqHeight);
        if (bitmap != null) {
            Log.d(TAG, "loadBitmapFromDiskCache, url:" + url);
            return bitmap;
        }

        bitmap = loadBitmapFromHttp(url, reqWidth, reqHeight);
        Log.d(TAG, "loadBitmapFromHttp, url:" + url);
        if (bitmap == null && !isDiskCacheCreate) {
            Log.d(TAG, "DiskLrucache is not created");
            bitmap = ImageUtil.downLoadImage(url);
        }
        return bitmap;
    }

    /**
     * 异步加载
     * @param url
     * @param imageView
     * @param reqWidth
     * @param reqHeight
     */
    public void bindBitmap(final String url, final ImageView imageView, final int reqWidth, final int reqHeight) {
        imageView.setTag(TAG_KEY_URL, url);
        Bitmap bitmap = loadBitmMapFromMemoryCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(url, reqWidth, reqHeight);
                if (bitmap != null) {
                    LoadResult result = new LoadResult(imageView, url, bitmap);
                    Message msg = mHandler.obtainMessage(MESSAGE_POST_RESULT, result);
                    mHandler.sendMessage(msg);
                }
            }
        };

        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }

    /**
     * 消息的封装实体
     */
    private static class LoadResult {
        public ImageView imageView;
        public String url;
        public Bitmap bitmap;

        public LoadResult(ImageView imageView, String url, Bitmap bitmap) {
            this.imageView = imageView;
            this.url = url;
            this.bitmap = bitmap;
        }
    }
}
