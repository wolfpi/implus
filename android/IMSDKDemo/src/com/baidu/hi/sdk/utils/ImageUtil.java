package com.baidu.hi.sdk.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

public class ImageUtil {

    public static String generatePhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyy-MM-dd_HHmmss", Locale.getDefault());
        return dateFormat.format(date) + ".jpg";
    }

    public static String copyToFile(final Context context, Uri data, File toFile, boolean renameToItsMd5){
        try {
            String dirPath = toFile.getParent();
            InputStream inputStream = context.getContentResolver().openInputStream(data);
            if(!FileUtil.writeTo(inputStream, toFile)){
                return null;
            }
            if (renameToItsMd5) {
                String md5 = MD5Util.getFileMD5(toFile);
                File targetFile = new File(dirPath, md5);
                FileUtil.move(toFile, targetFile);
                if (targetFile.exists()) {
                    final String targetFilePath = targetFile.getAbsolutePath();
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "上传路径为：" + targetFilePath, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return targetFile.getName();
                } else {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "无法保存图片", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return null;
                }
            }else{
                return toFile.exists() ? toFile.getName() : null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // failed to null
        return null;
    }

    public static String doSavePhoto(final Context context, Uri data, int quality, String dirPath, String fileName) {
        if (null != data) {
            // 读取uri所在的图片
            Bitmap saveBitmap = null;
            try {
                saveBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (null != saveBitmap) {
                if (!new File(dirPath).exists()) {
                    new File(dirPath).mkdirs();
                }
                File imageFile = new File(dirPath, fileName);
                saveBitmap(context, saveBitmap, quality, imageFile.getAbsolutePath());
                String md5 = MD5Util.getFileMD5(imageFile);
                File targetFile = new File(dirPath, md5);
                FileUtil.move(imageFile, targetFile);
                if (targetFile.exists()) {
                    final String targetFilePath = targetFile.getAbsolutePath();
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "上传路径为：" + targetFilePath, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return targetFile.getName();
                } else {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "无法保存图片", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return null;
                }
            }
        }
        ((Activity) context).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(context, "无法获取相片2", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    public static String doSavePhoto(final Context context, Bitmap bitmap, int quality, String dirPath, String fileName) {
        if (null != bitmap) {
            if (!new File(dirPath).exists()) {
                new File(dirPath).mkdirs();
            }
            File imageFile = new File(dirPath, fileName);
            saveBitmap(context, bitmap, quality, imageFile.getAbsolutePath());
            String md5 = MD5Util.getFileMD5(imageFile);
            File targetFile = new File(dirPath, md5);
            FileUtil.copy(imageFile, targetFile);
            FileUtil.delete(imageFile);
            if (targetFile.exists()) {
                final String targetFilePath = targetFile.getAbsolutePath();
                ((Activity) context).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(context, "上传路径为：" + targetFilePath, Toast.LENGTH_SHORT).show();
                    }
                });
                return targetFile.getName();
            } else {
                ((Activity) context).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(context, "无法保存图片", Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            }
        }
        ((Activity) context).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(context, "无法获取相片3", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    public static void saveBitmap(final Context context, Bitmap bitmap, int quality, String path) {
        // Bitmap图片保存
        OutputStream ostream = null;
        try {
            File file = new File(path);
            if (!file.exists()) {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }
            } else {
                file.delete();
            }
            file.createNewFile();
            ostream = new FileOutputStream(file);
            bitmap.compress(CompressFormat.JPEG, quality, ostream);
        } catch (FileNotFoundException e) {
            ((Activity) context).runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(context, "无法找到图片", Toast.LENGTH_SHORT).show();
                }

            });
        } catch (IOException e) {
            ((Activity) context).runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(context, "无法保存图片", Toast.LENGTH_SHORT).show();
                }

            });
        } finally {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            if (null != ostream) {
                try {
                    ostream.flush();
                    ostream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static BitmapFactory.Options getBitmapOptions(InputStream in) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        return options;
    }

    public static boolean doCopyImage(String srcFilePath, String targetFilePath) {
        return FileUtil.copy(new File(srcFilePath), new File(targetFilePath));
    }

    /**
     * 按路径创建Bitmap, 压缩至size * size的矩形区域内
     * 
     * @param original path
     * @param target size
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Bitmap getCompressBitmap(String path, int size) {
        Bitmap bitmap = null;
        InputStream in = null;
        File fileTmp = new File(path);
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        if (fileTmp.isDirectory()) {
            return null;
        }
        if (size <= 0) {
            return null;
        }
        if (fileTmp.exists()) {
            try {
                in = new FileInputStream(fileTmp);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(in, null, options);
                in.close();
                int image_width = options.outWidth;
                int image_height = options.outHeight;
                in = new FileInputStream(fileTmp);
                if (Math.max(image_width, image_height) > size) {
                    int scale = 1;
                    while (image_width * image_height * (1 / Math.pow(scale, 2)) > size * size) {
                        scale++;
                    }
                    if (scale > 1) {
                        scale--;
                        // scale to max possible inSampleSize that still yields
                        // an image
                        // larger than target
                        options.inJustDecodeBounds = false;
                        options.inPreferredConfig = Bitmap.Config.RGB_565;
                        options.inPurgeable = true;
                        options.inInputShareable = true;
                        options.inSampleSize = scale;
                        bitmap = BitmapFactory.decodeStream(in, null, options);
                    } else {
                        // scale == 1
                        bitmap = BitmapFactory.decodeStream(in);
                    }
                    // resize to desired dimensions
                    image_width = bitmap.getWidth();
                    image_height = bitmap.getHeight();
                    double y = 1;
                    double x = 1;
                    if (image_height > image_width) {
                        y = size;
                        x = (y / image_height) * image_width;
                    } else {
                        x = size;
                        y = (x / image_width) * image_height;
                    }

                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) x, (int) y, true);
                    return scaledBitmap;
                } else {
                    return BitmapFactory.decodeStream(in);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != in) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != bitmap && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        }
        return null;
    }
}
