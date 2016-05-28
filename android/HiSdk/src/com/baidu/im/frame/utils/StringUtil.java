package com.baidu.im.frame.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {

    /**
     * 将字符串转成MD5值
     * 
     * @param string
     * @return
     */
    public static String StringToMD5(String string) {
    	if(string == null)
    		return null;
    	
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }
    
    public static  boolean isStringInValid(String str)
    {
    	if(str == null || str.isEmpty())
    		return true;
    	return false;
    }
}
