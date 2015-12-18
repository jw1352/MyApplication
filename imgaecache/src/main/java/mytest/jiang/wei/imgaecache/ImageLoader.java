package mytest.jiang.wei.imgaecache;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import libcore.io.DiskLruCache;

/**
 * Created by wei.jiang on 2015/12/18.
 */
public class ImageLoader {
    private static final String TAG = "ImageLoader";
    private static final int TAG_KEY_URL = R.id.imageView;
    private static final int MESSAGE_POST_RESULT = 1;
    private Context mContext;
    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskCache;

    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 10; // 指定DiskLruCache缓存空间大小  10M的空间
    private static final String CACHE_DIR_NAME = "bitmap"; // 缓存文件夹名称
    private static final int MEMORY_CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory() / 1024) / 8; //指定LruCache缓存大小 内存的八分之一

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

    // 创建线程池
    private static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), sThreadFactory);


    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            LoadResult result = (LoadResult) msg.obj;
            ImageView imageView = result.imageView;
            String url = (String) imageView.getTag(TAG_KEY_URL);
            if (url.equals(result.url)) {
                imageView.setImageBitmap(result.bitmap);
            } else {
                Log.w(TAG, "set image bitmap ,but url has changed , ignored");
            }
        }
    };


    private ImageLoader(Context context) {
        mContext = context;
        mMemoryCache = ImageUtil.openLruCache(MEMORY_CACHE_SIZE);
        mDiskCache = ImageUtil.openDiskLruCache(mContext, DISK_CACHE_SIZE, CACHE_DIR_NAME);
    }

    public static ImageLoader build(Context context) {
        return new ImageLoader(context);
    }

    private void addBitmap2MemoryCache(String key, Bitmap bitmap) {
        if (loadBitmMapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * 内存中加载
     * @param key
     * @return
     */
    private Bitmap loadBitmMapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 从磁盘中加载
     * @param url
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private Bitmap loadBitmapFromDiskCache(String url, int reqWidth, int reqHeight) {
        return ImageUtil.getFromDiskLruCache(url, mDiskCache, reqWidth, reqHeight);
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
        String key = ImageUtil.encode(url);
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
        if (bitmap != null) {
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
