package com.baidu.im.frame.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

public class AssertUtil {

    public static byte[] readByte(Context context, String fileName) {
    	
    	if(context == null || StringUtil.isStringInValid(fileName))
    		return null;
    	
        InputStream inputStream;
        try {
            inputStream = context.getAssets().open(fileName);
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[2024];

        int len;

        try {

            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }

            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();

            throw new RuntimeException(fileName);
        }

        return outputStream.toByteArray();
    }

    /**
     * 从assets 文件夹中获取文件并读取数据
     */
    public static String readString(Context context, String fileName) {
        String result = "";
    	if(context == null || StringUtil.isStringInValid(fileName))
    		return result;
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            // 获取文件的字节数
            int lenght = in.available();
            // 创建byte数组
            byte[] buffer = new byte[lenght];
            // 将文件中的数据读到byte数组中
            in.read(buffer);
            result = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
