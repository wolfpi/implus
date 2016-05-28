package com.baidu.im.frame.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class DeviceInfoUtil {

    public static final String TAG = "DeviceInfoUtil";
    private static String imeistring = null;
    private static String imeiPrefix = "";

    public static void setDeviceToken(String token)
    {
    	imeistring = token;
    }
    
    public static void setDeviceTokenPrefix(String prefix)
    {
    	imeiPrefix = prefix;
    }
    
    public static void printBuild() {
        Class<Build> cls = Build.class;
        Field[] fieldlist = cls.getDeclaredFields();
        for (int i = 0; i < fieldlist.length; i++) {
            Field fld = fieldlist[i];
            try {
                LogUtil.e(TAG, fld.getName() + "           " + fld.get(null));
            } catch (IllegalAccessException e) {
                LogUtil.e(TAG, e);
            } catch (IllegalArgumentException e) {
                LogUtil.e(TAG, e);
            }
        }
    }

    public static void printTelephonyInfo(Context context) {
    	if(context != null)
    	{
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        LogUtil.e(TAG, "DeviceId(IMEI) = " + tm.getDeviceId());
        LogUtil.e(TAG, "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion());
        LogUtil.e(TAG, "Line1Number = " + tm.getLine1Number());
        LogUtil.e(TAG, "NetworkCountryIso = " + tm.getNetworkCountryIso());
        LogUtil.e(TAG, "NetworkOperator = " + tm.getNetworkOperator());
        LogUtil.e(TAG, "NetworkOperatorName = " + tm.getNetworkOperatorName());
        LogUtil.e(TAG, "NetworkType = " + tm.getNetworkType());
        LogUtil.e(TAG, "PhoneType = " + tm.getPhoneType());
        LogUtil.e(TAG, "SimCountryIso = " + tm.getSimCountryIso());
        LogUtil.e(TAG, "SimOperator = " + tm.getSimOperator());
        LogUtil.e(TAG, "SimOperatorName = " + tm.getSimOperatorName());
        LogUtil.e(TAG, "SimSerialNumber = " + tm.getSimSerialNumber());
        LogUtil.e(TAG, "SimState = " + tm.getSimState());
        LogUtil.e(TAG, "SubscriberId(IMSI) = " + tm.getSubscriberId());
        LogUtil.e(TAG, "VoiceMailNumber = " + tm.getVoiceMailNumber());
    	}
    }

    public static String getDeviceToken(Context context) {

    	if(context == null)
    		return null;
    	if(!TextUtils.isEmpty(imeistring))
    		return imeistring;
    	
        if (TextUtils.isEmpty(imeistring)) {

            try {
                // 优先采用设备ID的md5值作为固定的设备ID
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null) {
                    imeistring = tm.getDeviceId();
                }
            } catch (Exception e) {
                LogUtil.e(TAG, "Error in TelephonyManager.getDeviceId(): " + e.getMessage());
            }

            if (TextUtils.isEmpty(imeistring)) {

                try {
                    // 如果设备ID取不到采用wifi mac地址的md5值作为固定的设备ID
                    WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo info = wifi.getConnectionInfo();
                    String wifiMac = info.getMacAddress();
                    if (TextUtils.isEmpty(wifiMac)) {
                        // 如果Wifi Mac取不到采用设备型号加序列号的md5值作为固定的设备ID
                        // 例： Build.MODEL: GT-I9500 Build.SERIAL: 4d00568756b46091
                        if (Build.VERSION.SDK_INT >= 9) {
                            imeistring = Build.MODEL + getModelSerial();
                        } else {
                            imeistring = Build.MODEL + getDeviceSerialByReflect();
                        }
                    }
                } catch (Exception e) {
                    LogUtil.e(TAG, "Error in TelephonyManager.getDeviceId(): " + e.getMessage());
                }
            }
            if (TextUtils.isEmpty(imeistring)) {
                LogUtil.e(TAG, "Can not get deviceId.");
                imeistring = System.currentTimeMillis() + "@@@";
            }

        }

        imeistring = imeistring + imeiPrefix;
        return imeistring;
    }

    /**
     * 通过Build.SERIAL获取设备序列号
     * 
     * @return
     */
    @TargetApi(9)
    public static String getModelSerial() {
        return Build.SERIAL;
    }

    /**
     * 通过反射获取设备序列号，适配API level 9以下
     * 
     * @return
     */
    public static String getDeviceSerialByReflect() {
        String serial = "unknown";
        try {
            Class<?> clazz = Class.forName("android.os.Build");
            Class<?> paraTypes = Class.forName("java.lang.String");
            Method method = clazz.getDeclaredMethod("getString", paraTypes);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            serial = (String) method.invoke(new Build(), "ro.serialno");
        } catch (Throwable e) {

        }
        return serial;
    }
}
