package com.baidu.im.frame.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.text.TextUtils;

public class MD5Util {
    private static char hexDigits[] =
            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    public final static String getMD5(String s) {

        if (TextUtils.isEmpty(s)) {
            return null;
        }
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFileMD5(File file) {
        InputStream fin = null;
        String md5 = null;
        try {
            // Prepare fileInputStream
            fin = new FileInputStream(file);
            // Prepare md5
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");

            byte[] buffer = new byte[50*1024];
            int readBytes;
            while ((readBytes = fin.read(buffer)) >= 0) {
                if (readBytes > 0) {
                    md5Digest.update(buffer, 0, readBytes);
                }
            }
            md5 = HexUtil.hex(md5Digest.digest());
        } catch (FileNotFoundException e) {
            LogUtil.printImE("Can not find the file.", e);
        } catch (NoSuchAlgorithmException e) {
            LogUtil.printImE("Can not get md5 or sha256.", e);
        } catch (IOException e) {
            LogUtil.printImE("Can not read file.", e);
        } finally {
            if (null != fin) {
                try {
                    fin.close();
                } catch (IOException e) {
                    LogUtil.printImE("Can not close fileInputStream.", e);
                }
            }
        }
        return md5;
    }
}
