package com.baidu.hi.sdk.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

public class FileUtil {

    /***
     * 文件复制
     * 
     * @param srcFile
     * @param destFile
     */
    @SuppressWarnings("deprecation")
    public static boolean copy(File srcFile, File destFile) {
        if (!srcFile.exists()) {
            return false;
        }

        String state = android.os.Environment.getExternalStorageState();
        if (state == null || !state.equals(android.os.Environment.MEDIA_MOUNTED)) {
            return false;
        }

        StatFs sfs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long availableCount = sfs.getAvailableBlocks();
        long blockSizea = sfs.getBlockSize();
        if (availableCount * blockSizea < srcFile.length()) {
            return false;
        }

        ensureParentExists(destFile);

        InputStream is = null;
        OutputStream os = null;

        try {
            is = new BufferedInputStream(new FileInputStream(srcFile));
            os = new BufferedOutputStream(new FileOutputStream(destFile));

            copyStream(is, os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeQuietly(os);
            closeQuietly(is);
        }

        return true;
    }

    private static void closeQuietly(Closeable os){
        try {
            if (os != null) {
                os.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void copyStream(InputStream is, OutputStream os) throws IOException {
        byte[] b = new byte[1024 * 4];
        int len = 0;
        while ((len = is.read(b)) != -1) {
            os.write(b, 0, len);
        }
    }

    public static boolean delete(File file) {
        if (null != file && file.exists()) {
            return file.delete();
        } else {
            return false;
        }
    }


    public static boolean move(File from, File to) {
        try {
            ensureParentExists(to);
            return null != from && to != null && from.renameTo(to);
        } catch (Exception e) {
            e.printStackTrace();
            // try copy byte by byte
            try {
                copy(from, to);
                delete(from);
                return true;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获得文件扩展名
     * 
     * @param fileName :带文件类型的文件全名
     * @return：文件类型
     */
    public static String getFileType(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            return fileName.substring(index + 1);
        } else {
            return null;
        }
    }

    /**
     * 获得文件名
     * 
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        int index = filePath.lastIndexOf(".");
        if (index != -1) {
            return filePath.substring(0, index);
        } else {
            return null;
        }
    }

    public static boolean writeTo(final InputStream inputStream, final File toFile){

        FileOutputStream outputStream = null;
        try {
            ensureParentExists(toFile);
            outputStream = new FileOutputStream(toFile);
            copyStream(inputStream, outputStream);
            outputStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(outputStream);
            closeQuietly(inputStream);
        }
        return false;
    }

    private static void ensureParentExists(File toFile) {
        File parentFile = toFile.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
    }
}
