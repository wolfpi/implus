package com.baidu.imc.impl.im.util;

import java.nio.charset.Charset;

public class StringUtil {

    private static final String UTF_8 = "UTF-8";

    public static byte[] stringToBytes(String str) {
        if (null != str && str.length() > 0) {
            return str.getBytes(Charset.forName(UTF_8));
        } else {
            return null;
        }
    }

    public static String bytesToString(byte[] bytes) {
        if (null != bytes && bytes.length > 0) {
            return new String(bytes, Charset.forName(UTF_8));
        } else {
            return null;
        }
    }
}
