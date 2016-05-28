package com.baidu.im.testutil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

public class AssertUtil {

    public static byte[] readFile(Context context, String fileName) {
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
}
