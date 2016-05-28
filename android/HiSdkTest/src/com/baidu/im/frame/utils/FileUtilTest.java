package com.baidu.im.frame.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import junit.framework.Assert;

import org.apache.http.util.EncodingUtils;

import android.os.Environment;
import android.test.AndroidTestCase;

public class FileUtilTest extends AndroidTestCase {

    public void testWriteFileInSdkFolder() {
        FileUtil.writeStringToFileInSdkFolder("test", "test" + System.currentTimeMillis());
    }

    public void testReadFileInSdkFolder() {
        FileUtil.writeStringToFileInSdkFolder("test", "test" + System.currentTimeMillis());
        String text = FileUtil.readStringFromFileInSdkFolder("test");
        Assert.assertNotNull(text);
    }

    // 写数据到SD中的文件
    public static void writeFileInSdkFolder(String fileName, String write_str) {
        try {

            File dir = new File(Environment.getExternalStorageDirectory() + "/baidu/sdk");
            dir.mkdirs();
            FileOutputStream fout = new FileOutputStream(new File(dir, fileName), true);
            byte[] bytes = write_str.getBytes();

            fout.write(bytes);
            fout.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 读SD中的文件
    public static String readFileInSdkFolder(String fileName) {
        String res = "";
        try {

            File dir = new File(Environment.getExternalStorageDirectory() + "/baidu/sdk");
            dir.mkdirs();
            FileInputStream fin = new FileInputStream(new File(dir, fileName));

            int length = fin.available();

            byte[] buffer = new byte[length];
            fin.read(buffer);

            res = EncodingUtils.getString(buffer, "UTF-8");

            fin.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
