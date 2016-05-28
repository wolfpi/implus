package com.baidu.im.frame.utils;

import java.io.UnsupportedEncodingException;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BdimussUtil {
    public static final String TAG = "BdimussUtil";

    public static String EncodeBdimuss(String password, byte[] seed, int timeValue) throws NoSuchAlgorithmException,
            DigestException {
        if (password == null || password.length() == 0)
            return "";
        if (seed == null || seed.length == 0)
            return "";
        MessageDigest messageDigest;
        messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        byte[] passwdMd5Byte = messageDigest.digest();
        String passwordMd5 = bytesToHexString(passwdMd5Byte);// 生成MD5字符串

        byte[] timeByte = intToBytes(timeValue);
        byte[] binTimeSeed = byteJoin(timeByte, seed);

        messageDigest.update(binTimeSeed);
        byte[] timeSeedMd5 = messageDigest.digest();

        byte[] newSeed = new byte[8];
        for (int i = 0; i < newSeed.length; ++i) {
            newSeed[i] = doByteXor(timeSeedMd5[i], timeSeedMd5[(16 - 1) - i]);
        }
        String newSeedString = bytesToHexString(newSeed);
        passwordMd5 = passwordMd5 + newSeedString;

        messageDigest.update(passwordMd5.getBytes());
        byte[] pwdSeedMd5 = messageDigest.digest();
        String hexBdimuss = bytesToHexString(pwdSeedMd5);

        hexBdimuss = hexBdimuss + bytesToHexString(binTimeSeed);
        return hexBdimuss;

    }

    public static HashMap<String, String> GetPassportParams(String account, String password) {
    	
    	if(StringUtil.isStringInValid(account) || StringUtil.isStringInValid(password))
    		return null;
    	
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("username", account);
        paramMap.put("isphone", "0");
        MessageDigest messageDigestPass;
        try {
            messageDigestPass = MessageDigest.getInstance("MD5");
            messageDigestPass.update(password.getBytes("utf-8"));
            byte[] passMd5ByteTemp = messageDigestPass.digest();

            messageDigestPass.update(bytesToHexString(passMd5ByteTemp).getBytes("utf-8"));
            byte[] passMd5Byte = messageDigestPass.digest();
            String passMd5 = bytesToHexString(passMd5Byte);// 生成MD5字符串
            paramMap.put("password", passMd5);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LogUtil.e(TAG, e);
        }

        paramMap.put("crypttype", "4");
        paramMap.put("login_type", "3");
        paramMap.put("logictype", "0");
        paramMap.put("ie", "utf-8");
        paramMap.put("tpl", "hi");
        paramMap.put("appid", "1");

        MessageDigest messageDigest1;
        try {
            messageDigest1 = MessageDigest.getInstance("MD5");
            messageDigest1.update(account.getBytes());
            byte[] idMd5Byte = messageDigest1.digest();
            String idMd5 = bytesToHexString(idMd5Byte);// 生成MD5字符串
            paramMap.put("clientid", idMd5);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }

        paramMap.put("clientip", "");

        Object[] keys = paramMap.keySet().toArray();
        Arrays.sort(keys);
        String queryString = "";
        for (int i = 0; i < keys.length; i++) {
            queryString = queryString + keys[i] + "=" + paramMap.get(keys[i]) + "&";
        }
        queryString = queryString + "sign_key=bc3bc92a4b98594cf63327c53484c9e1";

        String sig = "";
        try {
            MessageDigest messageDigest = null;
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(queryString.getBytes());
            byte[] sigByte = messageDigest.digest();
            sig = bytesToHexString(sigByte);// 生成MD5字符串
        } catch (NoSuchAlgorithmException e) {
            LogUtil.e(TAG, e);
        }
        paramMap.put("sig", sig);
        return paramMap;
    }

    static HashMap<String, Object> parsePassportResponse(String response) {
    	if(StringUtil.isStringInValid(response))
    		return null;
    	
        HashMap<String, Object> result = new HashMap<String, Object>();
        if (response == null || response.length() == 0) {
            return result;
        }
        // JSONTokener jsonTokener = new JSONTokener(response);
        // Object ob;
        try {
            // ob = jsonTokener.nextValue();
            // JSONObject responseObject =JSONObject.fromObject(response);
            JSONArray array = new JSONArray("[" + response + "]");
            for (int i = 0; i < array.length(); i++) {
                JSONObject responseObject = array.getJSONObject(i);
                Iterator<String> keyIter = responseObject.keys();
                while (keyIter.hasNext()) {
                    String key = (String) keyIter.next();
                    Object value = responseObject.get(key);
                    result.put(key, value);
                }
            }
        } catch (JSONException e) {
            LogUtil.e(TAG, e);
        }
        return result;
    }

    private static byte doByteXor(byte a, byte b) {
        a ^= b;
        return a;
    }

    private static byte[] byteJoin(byte[] a, byte[] b) {
    	if(a == null || b == null)
    		return null;
    	
        byte[] c = new byte[a.length + b.length];
        for (int i = 0; i < a.length; i++)
            c[i] = a[i];
        for (int j = a.length; j < (a.length + b.length); j++)
            c[j] = b[j - a.length];
        return c;
    }

    /**
     * 把字节数组转换成16进制字符串
     * 
     * @param bArray
     * @return
     */
    private static final String bytesToHexString(byte[] bArray) {
    	if(bArray == null)
    		return null;
    	
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase(Locale.US));
        }
        return sb.toString().toLowerCase(Locale.US);
    }

    private static byte[] intToBytes(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }
}
