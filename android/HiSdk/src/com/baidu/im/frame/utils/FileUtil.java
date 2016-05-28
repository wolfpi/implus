package com.baidu.im.frame.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import com.baidu.im.constant.Constant;

public class FileUtil {
    public static final String TAG = "FileUtil";

    // 写数据到SD中的文件
    public static void writeStringToFileInSdkFolder(String fileName, String write_str) {
    	
    	if(StringUtil.isStringInValid(fileName) ||write_str == null)
    		return ;
        try {
            File dir = new File(Constant.sdkExternalDir);
            dir.mkdirs();
            File file = new File(dir, fileName);
            FileOutputStream fout = new FileOutputStream(file, true);
            byte[] bytes = write_str.getBytes(Charset.forName("utf-8"));

            fout.write(bytes);
            fout.close();
        }

        catch (Exception e) {
            LogUtil.e(TAG, e);
        }
    }

    // 读SD中的文件
    public static String readStringFromFileInSdkFolder(String fileName) {
    	if(StringUtil.isStringInValid(fileName))
    		return null;
    	
        String res = "";
        try {

            File dir = new File(Constant.sdkExternalDir);
            dir.mkdirs();
            File file = new File(dir, fileName);
            if (file.exists()) {
                FileInputStream fin = new FileInputStream(file);

                int length = fin.available();

                byte[] buffer = new byte[length];
                fin.read(buffer);

                res = new String(buffer, "UTF-8");

                fin.close();
            }
        }

        catch (Exception e) {
            LogUtil.e(TAG, e);
        }
        return res;
    }

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] readBytesFromFileInSdkFolder(String fileName) {
    	if(StringUtil.isStringInValid(fileName))
    		return null;
        byte[] buffer = null;
        try {
            File dir = new File(Constant.sdkExternalDir);
            FileInputStream fin = new FileInputStream(new File(dir, fileName));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[50*1024];
            int n;
            while ((n = fin.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fin.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            LogUtil.e(TAG, e);
        } catch (IOException e) {
            LogUtil.e(TAG, e);
        }
        return buffer;
    }

    /**
     * 根据byte数组，生成文件
     */
    public static void saveBytesToFileInSdkFolder(String fileName, byte[] bfile) {
    	
    	if(StringUtil.isStringInValid(fileName) || bfile == null)
    		return ;
    	
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(Constant.sdkExternalDir);
            if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(dir, fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            LogUtil.e(TAG, e);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
