package com.baidu.hi.sdk.utils;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.widget.ImageView;

import com.baidu.hi.sdk.Constant;
import com.baidu.hi.sdk.IMSDKApplication;
import com.baidu.imc.listener.ProgressListener;
import com.baidu.imc.message.content.FileMessageContent;

public class ImageLoader {
    private LruCache<String, Bitmap> imageCache;
    private static final int SOFT_IMAGE_CACHE_CAPACITY = 100;
    private LinkedHashMap<String, SoftReference<Bitmap>> softImageCache;
    private static volatile ImageLoader mLoader;
    private Map<ImageView, String> imageViewMap;
    private static Handler handler = null;
    private static final int IMAGE = 1;
    private static final int THUMBNAIL = 2;
    private ExecutorService imageThreadPool;

    private ImageLoader() {
        // 软引用
        softImageCache = new LinkedHashMap<String, SoftReference<Bitmap>>(SOFT_IMAGE_CACHE_CAPACITY, 0.75f, true) {

            private static final long serialVersionUID = -3658395524135628408L;

            @Override
            public SoftReference<Bitmap> put(String key, SoftReference<Bitmap> value) {
                return super.put(key, value);
            }

            @Override
            protected boolean removeEldestEntry(Map.Entry<String, SoftReference<Bitmap>> eldest) {
                if (size() > SOFT_IMAGE_CACHE_CAPACITY) {
                    return true;
                } else {
                    return false;
                }
            }

        };

        // LruCache通过构造函数传入缓存值，以KB为单位。
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 使用最大可用内存值的1/8作为缓存的大小。
        int cacheSize = maxMemory / 8;
        imageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                softImageCache.put(key, new SoftReference<Bitmap>(oldValue));
            }

            @Override
            protected int sizeOf(String key, Bitmap value) {
                final int bitmapSize = getBitmapSize(value) / 1024;
                return bitmapSize == 0 ? 1 : bitmapSize;
            }

        };
        imageViewMap = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
        imageThreadPool = Executors.newFixedThreadPool(4);

    }

    private class ImageDownloadProgressListener implements ProgressListener<String> {

        private String key;
        private ImageView imageView;
        private String targetFilePath;

        public ImageDownloadProgressListener(String key, ImageView imageView, String targetFilePath) {
            this.key = key;
            this.imageView = imageView;
            this.targetFilePath = targetFilePath;
        }

        @Override
        public void onError(int errCode, String errMsg) {
            System.out.println("onError " + errCode + errMsg);
        }

        @Override
        public void onProgress(float percent) {
            System.out.println("onProgress " + percent);
        }

        @Override
        public void onSuccess(String filePath) {
            System.out.println("onSuccess " + filePath);
            boolean result = FileUtil.copy(new File(filePath), new File(targetFilePath));
            FileUtil.delete(new File(filePath));
            if (result) {
                System.out.println("copy success " + targetFilePath);
                BitmapLoader bitmapLoader = new BitmapLoader(imageView, key, null);
                imageThreadPool.execute(bitmapLoader);
            }
        }

    }

    @TargetApi(VERSION_CODES.KITKAT)
    private static int getBitmapSize(Bitmap bitmap) {
        // From KitKat onward use getAllocationByteCount() as allocated bytes can potentially be
        // larger than bitmap byte count.
        if (Build.VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        }

        if (Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }

        // Pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    public static synchronized ImageLoader getInstance() {
        if (mLoader == null) {
            mLoader = new ImageLoader();
        }
        validateHandler();
        return mLoader;
    }

    private static void validateHandler() {
        if (null == handler) {
            Looper looper = Looper.myLooper();
            if (looper == null) {
                Looper.prepare();
            }
            // handler = handler==null?new Handler():handler;
            handler = IMSDKApplication.getInstance().getHandler();
        }
    }

    private Bitmap getBitmapFromCache(String key) {
        synchronized (imageCache) {
            Bitmap bitmap = imageCache.get(key);
            if (null != bitmap) {
                return bitmap;
            }
        }

        synchronized (softImageCache) {
            SoftReference<Bitmap> bitmapReference = softImageCache.get(key);
            if (null != bitmapReference) {
                Bitmap bitmap = bitmapReference.get();
                if (null != bitmap) {
                    return bitmap;
                }
            }
        }
        return null;
    }

    public void displayImage(String fileName, ImageView imageView, FileMessageContent fileMessageContent) {
        if (!TextUtils.isEmpty(fileName) && null != imageView) {
            String key = fileName + "@" + IMAGE;
            System.out.println(key);
            imageViewMap.put(imageView, key);
            Bitmap bitmap = getBitmapFromCache(key);
            if (bitmap != null && !bitmap.isRecycled()) {
                imageView.setImageBitmap(bitmap);
            } else {
                BitmapLoader bitmapLoader = new BitmapLoader(imageView, key, fileMessageContent);
                imageThreadPool.execute(bitmapLoader);
            }
        }
    }

    public void displayThumbnail(String fileName, ImageView imageView, FileMessageContent fileMessageContent) {
        if (!TextUtils.isEmpty(fileName) && null != imageView) {
            String key = fileName + "@" + THUMBNAIL;
            System.out.println(key);
            imageViewMap.put(imageView, key);
            Bitmap bitmap = getBitmapFromCache(key);
            if (bitmap != null && !bitmap.isRecycled()) {
                imageView.setImageBitmap(bitmap);
            } else {
                BitmapLoader bitmapLoader = new BitmapLoader(imageView, key, fileMessageContent);
                imageThreadPool.execute(bitmapLoader);
            }
        }
    }

    private class BitmapLoader implements Runnable {

        private ImageView imageView;
        private String key;
        private FileMessageContent fileMessageContent;

        public BitmapLoader(ImageView imageView, String key, FileMessageContent fileMessageContent) {
            this.imageView = imageView;
            this.key = key;
            this.fileMessageContent = fileMessageContent;
        }

        @Override
        public void run() {
            if (imageView == null || TextUtils.isEmpty(key)) {
                return;
            }
            if (imageReused(imageView, key)) {
                return;
            }
            Bitmap bitmap = getBitmap(key, imageView, fileMessageContent);
            if (bitmap == null) {
                return;
            } else {
                imageCache.put(key, bitmap);
            }
            if (imageReused(imageView, key)) {
                return;
            }
            BitmapDisplayer bitmapDisplayer = new BitmapDisplayer(bitmap, imageView, key);
            handler.post(bitmapDisplayer);
        }

    }

    private class BitmapDisplayer implements Runnable {

        private Bitmap bitmap;
        private ImageView imageView;
        private String key;

        public BitmapDisplayer(Bitmap bitmap, ImageView imageView, String key) {
            this.bitmap = bitmap;
            this.imageView = imageView;
            this.key = key;
        }

        @Override
        public void run() {
            if (bitmap == null || bitmap.isRecycled()) {
                return;
            }
            if (imageView == null || TextUtils.isEmpty(key)) {
                return;
            }
            if (imageReused(imageView, key)) {
                return;
            }
            imageView.setImageBitmap(bitmap);
        }

    }

    private boolean imageReused(ImageView imageView, String key) {
        String tmpTag = imageViewMap.get(imageView);
        if (tmpTag == null || !tmpTag.equals(key)) {
            return true;
        } else {
            return false;
        }
    }

    private Bitmap getBitmap(String key, ImageView imageView, FileMessageContent fileMessageContent) {
        String[] keys = key.split("@");
        String fileName = keys[0];
        int type = Integer.parseInt(keys[1]);
        Bitmap bitmap = null;
        switch (type) {
            case THUMBNAIL:
            case IMAGE:
                // is SD card mounted?
                if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                    System.out.println("SD card is not existed. " + key);
                    break;
                }
                // get file path
                String thumbnailFilePath = getFilePath(type, fileName);
                if (TextUtils.isEmpty(thumbnailFilePath)) {
                    System.out.println("Can not get file path. " + key);
                    break;
                }
                // get compress bitmap
                if (new File(thumbnailFilePath).exists()) {
                    if (type == THUMBNAIL) {
                        bitmap = ImageUtil.getCompressBitmap(thumbnailFilePath, 200);
                    } else {
                        bitmap = ImageUtil.getCompressBitmap(thumbnailFilePath, 800);
                    }
                    break;
                }
                // is network connected?
                if (!NetworkUtil.isConnected()) {
                    System.out.println("Network is not connected. " + key);
                    break;
                }
                // download image
                if (null == fileMessageContent) {
                    System.out.println("Message content is not existed. " + key);
                    break;
                }
                fileMessageContent.loadResource(new ImageDownloadProgressListener(key, imageView, thumbnailFilePath));
                break;
            default:
                break;
        }
        return bitmap;
    }

    private String getFilePath(int type, String fileName) {
        String filePath;
        switch (type) {
            case THUMBNAIL:
                filePath = Constant.THUMBNAIL_DIR + "/" + fileName;
                break;
            case IMAGE:
                filePath = Constant.IMAGE_DIR + "/" + fileName;
                break;
            default:
                filePath = null;
                break;
        }
        return filePath;
    }
}
