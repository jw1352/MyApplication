package mytest.jiang.wei.imgaecache;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.OutputStream;

import libcore.io.DiskLruCache;

public class MainActivity extends Activity {

    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 10; // 指定DiskLruCache缓存空间大小  10M的空间
    private static final String CACHE_DIR_NAME = "bitmap"; // 缓存文件夹名称
    private static final int MEMORY_CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory() / 1024) / 8; //指定LruCache缓存大小 内存的八分之一

    private ImageView imageView;
    private DiskLruCache disLruCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        Bitmap bitmap = ImageUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.image, 200, 200);
        imageView.setImageDrawable(new BitmapDrawable(bitmap));

        LruCache<String, Bitmap> imageCache = ImageUtil.openLruCache(MEMORY_CACHE_SIZE);
        imageCache.put("image1", bitmap);

        disLruCache = ImageUtil.openDiskLruCache(MainActivity.this, DISK_CACHE_SIZE, CACHE_DIR_NAME);

    }

    public void download(View v) {
        //下载下个图片到DiskLruCache中
        new Thread(new Runnable() {
            @Override
            public void run() {
                String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
                ImageUtil.put2DisLruCache(imageUrl, disLruCache);
            }
        }).start();
    }

    public void readImage(View v) {
        String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
        Bitmap bitmap = ImageUtil.getFromDiskLruCache(imageUrl, disLruCache, 200, 200);
        imageView.setImageBitmap(bitmap);
    }

    public void delete(View v) {
        String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
        String key = ImageUtil.encode(imageUrl);
        try {
            disLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
