package com.baidu.imc.impl.im.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.baidu.imc.impl.im.client.BDHiIMConstant;

public class FileUtil {

    public static File getSDRootFile() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory();
        } else {
            return null;
        }
    }

    public static String getTmpFileAbsolutePath(String tmpFileName) {
        File rootFile = FileUtil.getSDRootFile();
        if (rootFile != null) {
            return rootFile + BDHiIMConstant.LOCAL_RES_PATH + "/" + tmpFileName;
        } else {
            return null;
        }
    }

    public static boolean isInTmpFileDir(String filePathAndName) {
        File rootFile = FileUtil.getSDRootFile();
        if (rootFile != null) {
            String tmpDirPath = rootFile + BDHiIMConstant.LOCAL_RES_PATH;
            if (!TextUtils.isEmpty(filePathAndName)) {
                File file = new File(filePathAndName);
                if (file.exists() && file.getParent().equals(tmpDirPath)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean copyIntoTmpFileDir(String srcFilePathAndName, String targetFileName) {
        if (TextUtils.isEmpty(srcFilePathAndName) || TextUtils.isEmpty(targetFileName)) {
            return false;
        }

        File srcFile = new File(srcFilePathAndName);
        if (!srcFile.exists()) {
            return false;
        }

        File rootFile = FileUtil.getSDRootFile();
        if (rootFile == null) {
            return false;
        }
        File destFile = new File(rootFile + BDHiIMConstant.LOCAL_RES_PATH + "/" + targetFileName);

        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }

        String state = android.os.Environment.getExternalStorageState();
        if (state == null || !state.equals(android.os.Environment.MEDIA_MOUNTED)) {
            return false;
        }

        StatFs sfs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        @SuppressWarnings("deprecation")
        long availableCount = sfs.getAvailableBlocks();
        @SuppressWarnings("deprecation")
        long blockSizea = sfs.getBlockSize();
        if (availableCount * blockSizea < srcFile.length()) {
            return false;
        }

        InputStream is = null;
        OutputStream os = null;

        try {
            is = new BufferedInputStream(new FileInputStream(srcFile));
            os = new BufferedOutputStream(new FileOutputStream(destFile));

            byte[] b = new byte[256];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static boolean rename(String filePathAndName, String fileName) {
        if (!TextUtils.isEmpty(filePathAndName)) {
            File srcFile = new File(filePathAndName);
            if (srcFile.exists()) {
                File rootFile = FileUtil.getSDRootFile();
                if (rootFile != null) {
                    String targetFilePathAndName = rootFile + BDHiIMConstant.LOCAL_RES_PATH + "/" + fileName;
                    File destFile = new File(targetFilePathAndName);
                    if (!destFile.getParentFile().exists()) {
                        destFile.getParentFile().mkdirs();
                    }
                    return srcFile.renameTo(destFile);
                }
            }
        }
        return false;

    }
}
