package com.baidu.im.frame.utils;

public class HexUtil {
    private static char hexDigits[] =
            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public final static String hex(byte[] data) {
    	if(data == null)
    		return null;
        char str[] = new char[data.length * 2];

        for (int i = 0; i < data.length; i++) {
            byte byte0 = data[i];
            str[i * 2] = hexDigits[byte0 >>> 4 & 0xf];
            str[i * 2 + 1] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
}
