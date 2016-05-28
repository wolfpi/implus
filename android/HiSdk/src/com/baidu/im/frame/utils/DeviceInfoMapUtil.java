package com.baidu.im.frame.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.baidu.im.frame.inapp.ChannelSdkImpl;
import com.baidu.im.outapp.network.hichannel.Channelinfo;

public class DeviceInfoMapUtil {

    public static final String TAG = "DeviceInfoMapUtil";

    private static Map<String, String> deviceInfoMap = new ConcurrentHashMap<String, String>();

    private static long deviceInfoTimestamp = 0l;
    private static boolean initialized = false;
    private final static long MAX_TIMESTAMP_INTERVAL = 60000;

    // For device information map
    private final static String IMEI = "imei";
    private final static String DEVICE_NAME = "device";
    private final static String SD_TOTAL = "sd_total";
    private final static String SD_PERC_STR = "sd_perc_str";
    private final static String SD_PERC_INT = "sd_perc_int";
    private final static String MEM_TOTAL = "mem_total";
    private final static String MEM_USED_PERC = "mem_perc";
    private final static String MODEL = "model";
    private final static String OS = "OS";
    private final static String NETWORK = "network";
    private final static String APN = "apn";
    // private final static String SDDOUBLE = "sddouble";
    private final static String DISPLAY = "display";
    private final static String MANU = "manu";
    private final static String SDK_VER = "sdk_ver";
    private final static String SCREEN_SIZE = "screen_size";
    private final static String APP_VER = "app_ver";
    private final static String APP_ID = "app_id";
    private final static String DEVICE_TYPE_ID = "device_type_id";

    // For transaction log
    private final static String T_TOKEN = "token";
    private final static String T_TYPE = "type";
    private final static String T_SDKVER = "sdkver";
    private final static String T_APPVER = "appver";
    private final static String T_APPID = "appid";
    private final static String T_NETWORK = "network";
    private final static String T_APN = "apn";
    private final static String T_OSVER = "osver";
    private final static String T_MEM = "mem";
    private final static String T_SDCARD = "sdcard";

    /**
     * deviceToken, deviceId, deviceName, imei, sdCard, platform
     */
    public static com.baidu.im.frame.pb.ObjDeviceInfo.DeviceInfo getDeviceInfo(Context context,
            com.baidu.im.frame.pb.ObjDeviceInfo.DeviceInfo builder) {
        if (context != null && builder != null) {
            refreshDeviceInfoMap(context);
            builder.setDeviceToken(DeviceInfoUtil.getDeviceToken(context));
            builder.setDeviceTypeId(Integer.parseInt(deviceInfoMap.get(DEVICE_TYPE_ID)));
            builder.setDeviceName(deviceInfoMap.get(DEVICE_NAME));
            builder.setImei(deviceInfoMap.get(IMEI));
            builder.setSdCard(deviceInfoMap.get(SD_TOTAL));
            builder.setPlatform(com.baidu.im.frame.pb.EnumPlatformType.ANDROID);
        }
        return builder;
    }

    /**
     * deviceToken, deviceId, deviceName, imei, sdCard, platform
     */
    public static com.baidu.im.frame.pb.ObjDeviceInfo.DeviceInfo getDeviceInfoWithEmptyTypeId(Context context,
                                                                               com.baidu.im.frame.pb.ObjDeviceInfo.DeviceInfo builder) {
        if (context != null && builder != null) {
            refreshDeviceInfoMap(context);
            builder.setDeviceToken(DeviceInfoUtil.getDeviceToken(context));
            builder.setDeviceTypeId(0); // regapp 时不再传入以前的值
            builder.setDeviceName(deviceInfoMap.get(DEVICE_NAME));
            builder.setImei(deviceInfoMap.get(IMEI));
            builder.setSdCard(deviceInfoMap.get(SD_TOTAL));
            builder.setPlatform(com.baidu.im.frame.pb.EnumPlatformType.ANDROID);
        }
        return builder;
    }

    public static void fetchChannelInfo(Context context, JSONObject jsonobj) throws Exception
    {
    	if(context == null || jsonobj == null)
    		return;
    	refreshDeviceInfoMap(context);
    	jsonobj.put("network",deviceInfoMap.get(NETWORK));
    	jsonobj.put("apn",deviceInfoMap.get(APN));
    	jsonobj.put("osver",deviceInfoMap.get(OS));
    }
    /**
     * deviceId, platform, model, os, network, sdDouble, display, manu, screensize
     */
    public static com.baidu.im.frame.pb.ObjDeviceTypeInfo.DeviceTypeInfo getDeviceTypeInfo(Context context,
            com.baidu.im.frame.pb.ObjDeviceTypeInfo.DeviceTypeInfo builder) {
        if (context != null && builder != null) {
            refreshDeviceInfoMap(context);
            // builder.setDeviceId(0);
            builder.setPlatform(com.baidu.im.frame.pb.EnumPlatformType.ANDROID);
            builder.setModel(truncateString(deviceInfoMap.get(MODEL), 32)); // NOTE: 服务端DB字段要求32字符以内, 所以进行截断
            builder.setOs(deviceInfoMap.get(OS));
            builder.setNetwork(deviceInfoMap.get(NETWORK));
            builder.setDisplay(deviceInfoMap.get(DISPLAY));
            builder.setManu(deviceInfoMap.get(MANU));
            builder.setScreenSize(deviceInfoMap.get(SCREEN_SIZE));
        }
        return builder;
    }

    /**
     * 截断字符串, 使满足最大长度要求, 如果长度 c > length, 则返回原字符串不作处理
     *
     * @param s
     * @param c 字符串最大长度
     * @return 截断后的字符串
     */
    private static String truncateString(String s, int c) {
        if(c < s.length() && c >= 0) {
            return s.substring(0, c);
        }
        return s;
    }

    /**
     * deviceToken, network, apn, osVer, sdkVer
     */
    public static com.baidu.im.frame.pb.ObjBua.BUAInfo getBUAInfo(Context context,
            com.baidu.im.frame.pb.ObjBua.BUAInfo builder) {
        if (context != null && builder != null) {
            refreshDeviceInfoMap(context);
            builder.setDeviceToken(deviceInfoMap.get(IMEI));
            builder.setNetwork(Integer
                    .parseInt(deviceInfoMap.get(NETWORK)));
            builder.setApn(Integer.parseInt(deviceInfoMap.get(APN)));
            builder.setOsVer(deviceInfoMap.get(OS));
            builder.setSdkVer("AND_1.1.1.0");
        }
        return builder;
    }

    /**
     * appVer, mem, sdCard, buaInfo
     */
    public static com.baidu.im.frame.pb.ObjBua.BUA getBUA(Context context,
            com.baidu.im.frame.pb.ObjBua.BUA builder) {
        if (context != null && builder != null) {
            refreshDeviceInfoMap(context);
            builder.setAppVer(deviceInfoMap.get(APP_VER));
            builder.setMem(Integer.parseInt(deviceInfoMap.get(MEM_USED_PERC)));
            builder.setSdCard(Integer.parseInt(deviceInfoMap.get(SD_TOTAL)));
            // com.baidu.im.frame.pb.ObjBua.BUAInfo infoBuilder =
            // com.baidu.im.frame.pb.ObjBua.BUAInfo.newBuilder();
            // getBUAInfo(context, infoBuilder);
            // builder.setBuaInfo(infoBuilder);
        }
        return builder;
    }

    public static JSONObject getStatClient(Context context, JSONObject clientObj) {
        if (context != null && clientObj != null) {
            refreshDeviceInfoMap(context);
            try {
                clientObj.put(T_TOKEN, deviceInfoMap.get(IMEI));
                clientObj.put(T_TYPE, "Android");
                clientObj.put(T_SDKVER, deviceInfoMap.get(SDK_VER));
                clientObj.put(T_APPVER, deviceInfoMap.get(APP_VER));
                clientObj.put(T_APPID, deviceInfoMap.get(APP_ID));
                clientObj.put(T_NETWORK, deviceInfoMap.get(NETWORK));
                clientObj.put(T_APN, deviceInfoMap.get(APN));
                clientObj.put(T_OSVER, deviceInfoMap.get(OS));
                // clientObj.put(T_PLATFORM, "android");
                clientObj.put(T_MEM, deviceInfoMap.get(MEM_USED_PERC));
                clientObj.put(T_SDCARD, deviceInfoMap.get(SD_PERC_INT));
            } catch (JSONException e) {
                LogUtil.printError(e);
            }
        }
        return clientObj;
    }
    
    public static void getChannelinfo(Context context, Channelinfo cinfo)
    {
    	 if (context != null && cinfo != null) {
             refreshDeviceInfoMap(context);
             cinfo.setApn(Integer.parseInt(deviceInfoMap.get(APN)));
             cinfo.setNetwork(Integer.parseInt(deviceInfoMap.get(NETWORK)));
             cinfo.setOsversion(deviceInfoMap.get(OS));
    	 }
    	
    }

    private synchronized static void refreshDeviceInfoMap(Context context/* , boolean inAppDeviceInfo */) {
        if (context != null) {
            long nowTime = System.currentTimeMillis();
            if (nowTime - deviceInfoTimestamp > MAX_TIMESTAMP_INTERVAL) {
                if (initialized) {
                    LogUtil.printDebug(TAG, "Asynchronized refresh deviceInfo");
                    deviceInfoTimestamp = nowTime;
                    startRefreshThread(context);
                } else {
                    LogUtil.printDebug(TAG, "Synchronized refresh deviceInfo");
                    refreshingDeviceInfo(context/* , inAppDeviceInfo */);
                    deviceInfoTimestamp = nowTime;
                    initialized = true;
                }
            } else {
                LogUtil.printDebug(TAG, "Do not need to refresh deviceInfo");
            }
        }
    }

    private static ScheduledExecutorService deviceInfoRefreshExecutorService;

    private static class HeartbeatThreadFactory implements ThreadFactory {

        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "heartbeatFromInApp");
            t.setDaemon(true);
            return t;
        }

    }

    public static void startRefreshThread(Context context) {
        if (null == deviceInfoRefreshExecutorService || deviceInfoRefreshExecutorService.isShutdown()
                || deviceInfoRefreshExecutorService.isTerminated()) {
            deviceInfoRefreshExecutorService = Executors.newSingleThreadScheduledExecutor(new HeartbeatThreadFactory());
        }
        deviceInfoRefreshExecutorService.schedule(new RefreshDeviceInfoMapThread(context), 0, TimeUnit.MILLISECONDS);
    }

    private static class RefreshDeviceInfoMapThread extends Thread {

        private Context context;

        // private boolean inAppDeviceInfo;

        public RefreshDeviceInfoMapThread(Context context/* , boolean inAppDeviceInfo */) {
            this.context = context;
            // this.inAppDeviceInfo = inAppDeviceInfo;
        }

        @Override
        public void run() {
            refreshingDeviceInfo(context/* , inAppDeviceInfo */);
        }

    }

    private static void refreshingDeviceInfo(Context context/* , boolean inAppDeviceInfo */) {
        if (null != context) {
            LogUtil.printDebug(TAG, "Start refreshing device information map...");
            // from telephonyManager
            if (!deviceInfoMap.containsKey(IMEI)) {
                String deviceId = DeviceInfoUtil.getDeviceToken(context);
                deviceInfoMap.put(IMEI, deviceId);
                LogUtil.printDebug(TAG, "Device Id : " + deviceId);
            }
            if (!deviceInfoMap.containsKey(APN)) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String simOperatorStr = tm.getSimOperator();
                if ("46000".equals(simOperatorStr) || "46002".equals(simOperatorStr)) {
                    deviceInfoMap.put(APN,
                            Integer.toString(com.baidu.im.frame.pb.EnumApnInfo.CHINA_MOBILE));
                } else if ("46001".equals(simOperatorStr)) {
                    deviceInfoMap.put(APN,
                            Integer.toString(com.baidu.im.frame.pb.EnumApnInfo.CHINA_UNICOM));
                } else if ("46003".equals(simOperatorStr)) {
                    deviceInfoMap.put(APN,
                            Integer.toString(com.baidu.im.frame.pb.EnumApnInfo.CHINA_TELECOM));
                } else {
                    deviceInfoMap.put(APN, Integer.toString(com.baidu.im.frame.pb.EnumApnInfo.OTHER));
                }
                LogUtil.printDebug(TAG, "SIM Operator : " + simOperatorStr);
            }

            // from connectivityManager
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (null != info) {
                int type = info.getType();
                if (type == ConnectivityManager.TYPE_MOBILE) {
                    int subType = info.getSubtype();
                    if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
                            || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                        deviceInfoMap.put(NETWORK,
                                Integer.toString(com.baidu.im.frame.pb.EnumNetworkType.APN_2G));
                        LogUtil.printDebug(TAG, "APN : 2G");
                    } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS
                            || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                            || subType == TelephonyManager.NETWORK_TYPE_EVDO_A
                            || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                            || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                        deviceInfoMap.put(NETWORK,
                                Integer.toString(com.baidu.im.frame.pb.EnumNetworkType.APN_3G));
                        LogUtil.printDebug(TAG, "APN : 3G");
                    } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {
                        deviceInfoMap.put(NETWORK,
                                Integer.toString(com.baidu.im.frame.pb.EnumNetworkType.APN_4G));
                        LogUtil.printDebug(TAG, "APN : 4G");
                    } else {
                        deviceInfoMap.put(NETWORK,
                                Integer.toString(com.baidu.im.frame.pb.EnumNetworkType.APN_UNKNOWN));
                        LogUtil.printDebug(TAG, "APN : UNKNOWN");
                    }
                } else if (type == ConnectivityManager.TYPE_WIFI) {
                    deviceInfoMap.put(NETWORK,
                            Integer.toString(com.baidu.im.frame.pb.EnumNetworkType.APN_WIFI));
                    LogUtil.printDebug(TAG, "APN : WIFI");
                } else {
                    deviceInfoMap.put(NETWORK,
                            Integer.toString(com.baidu.im.frame.pb.EnumNetworkType.APN_UNKNOWN));
                }
            } else {
                deviceInfoMap.put(NETWORK,
                        Integer.toString(com.baidu.im.frame.pb.EnumNetworkType.APN_UNKNOWN));
            }

            // from packageManager
            if (!deviceInfoMap.containsKey(APP_VER)) {
                PackageManager pm = context.getPackageManager();
                if (null != pm) {
                    try {
                        PackageInfo pmInfo = pm.getPackageInfo(context.getPackageName(), 0);
                        int appVer = pmInfo.versionCode;
                        deviceInfoMap.put(APP_VER, Integer.toString(appVer));
                        LogUtil.printDebug(TAG, "APP_VER : " + appVer);
                    } catch (NameNotFoundException e) {
                        LogUtil.printError(e);
                    }
                }
            }

            // from resource
            if (!deviceInfoMap.containsKey(DISPLAY) || !deviceInfoMap.containsKey(SCREEN_SIZE)) {
                DisplayMetrics dm = context.getResources().getDisplayMetrics();
                deviceInfoMap.put(DISPLAY, Integer.toString(dm.densityDpi));
                LogUtil.printDebug(TAG, "DISPLAY : " + dm.densityDpi);
                String screenSize = Integer.toString(dm.heightPixels) + "*" + Integer.toString(dm.widthPixels);
                deviceInfoMap.put(SCREEN_SIZE, screenSize);
                LogUtil.printDebug(TAG, "SCREEN_SIZE : " + screenSize);
            }

            // from ActivityManager and /proc/meminfo
            long totalMemory = 0;
            if (deviceInfoMap.containsKey(MEM_TOTAL)) {
                totalMemory = Long.parseLong(deviceInfoMap.get(MEM_TOTAL));
            } else {
                totalMemory = getTotalMemery();
                deviceInfoMap.put(MEM_TOTAL, Long.toString(totalMemory));
            }
            if (totalMemory > 0) {
                long availMem = getAvailableMemory(context);
                int usedMemPerc = (int) (availMem * 100 / totalMemory);
                if (usedMemPerc > 0) {
                    deviceInfoMap.put(MEM_USED_PERC, Integer.toString(usedMemPerc));
                    LogUtil.printDebug(TAG, "MEM_USED_PERC : " + usedMemPerc);
                }
            }
        }

        // from Build
        if (!deviceInfoMap.containsKey(DEVICE_NAME)) {
            deviceInfoMap.put(DEVICE_NAME, Build.DEVICE);
            LogUtil.printDebug(TAG, "DEVICE_NAME : " + Build.DEVICE);
        }
        if (!deviceInfoMap.containsKey(MODEL)) {
            deviceInfoMap.put(MODEL, Build.MODEL);
            LogUtil.printDebug(TAG, "MODEL : " + Build.MODEL);
        }
        if (!deviceInfoMap.containsKey(OS)) {
            deviceInfoMap.put(OS, Build.VERSION.RELEASE);
            LogUtil.printDebug(TAG, "OS : " + Build.VERSION.RELEASE);
        }
        if (!deviceInfoMap.containsKey(MANU)) {
            deviceInfoMap.put(MANU, Build.MANUFACTURER);
            LogUtil.printDebug(TAG, "MANU : " + Build.MANUFACTURER);
        }

        // from sdk
        if (!deviceInfoMap.containsKey(SDK_VER)) {
            deviceInfoMap.put(SDK_VER, Integer.toString(ChannelSdkImpl.getVersionCode()));
            LogUtil.printDebug(TAG, "SDK_VER : " + ChannelSdkImpl.getVersionCode());
        }

        // from session
        if (!deviceInfoMap.containsKey(APP_ID)) {
            deviceInfoMap.put(APP_ID,
                    Integer.toString(GlobalInstance.Instance().preferenceInstace().getInt(PreferenceKey.appId)));
            LogUtil.printDebug(TAG, "APP_ID : " + ChannelSdkImpl.getVersionCode());
        }

        // from Environment
        int usedSDPerc = getUsedSDPerc();
        int usedROMPerc = getUsedRomPerc();
        String sdPerc = "SD: " + usedSDPerc + " ROM: " + usedROMPerc;
        deviceInfoMap.put(SD_PERC_STR, sdPerc);
        LogUtil.printDebug(TAG, "SD_PERC_STR : " + sdPerc);
        if (usedSDPerc > 0) {
            deviceInfoMap.put(SD_PERC_INT, Integer.toString(usedSDPerc));
            LogUtil.printDebug(TAG, "SD_PERC_INT : " + usedSDPerc);
        } else {
            deviceInfoMap.put(SD_PERC_INT, Integer.toString(usedROMPerc));
            LogUtil.printDebug(TAG, "SD_PERC_INT : " + usedROMPerc);
        }
        if (!deviceInfoMap.containsKey(SD_TOTAL)) {
            int sdSize = getSDSize();
            deviceInfoMap.put(SD_TOTAL, Integer.toString(sdSize));
            LogUtil.printDebug(TAG, "SD_TOTAL : " + sdSize);
        }

        // SDDOUBLE ?

        // DEVICE_TYPE_ID
        if (!deviceInfoMap.containsKey(DEVICE_TYPE_ID)) {
            int deviceTypeId = GlobalInstance.Instance().preferenceInstace().getInt(PreferenceKey.deviceTypeId);
            deviceInfoMap.put(DEVICE_TYPE_ID, Integer.toString(deviceTypeId));
            LogUtil.printDebug(TAG, "DEVICE_TYPE_ID : " + deviceTypeId);
        }
    }

    /**
     * 获取已使用内存的百分比
     */
    @SuppressWarnings("unused")
    private static int getUsedMemoryPerc(Context context) {
        long totalMemory = getTotalMemery();
        if (totalMemory > 0) {
            long availMem = getAvailableMemory(context);
            long totalMem = totalMemory;
            return (int) (availMem / totalMem);
        } else {
            return 0;
        }
    }

    /**
     * 获取总内存大小
     */
    private static long getTotalMemery() {
        // 系统内存信息文件
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;
        long totalMemory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            // 读取meminfo第一行，系统总内存大小
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            // for (String num : arrayOfString) {
            // Log.i(str2, num + "\t");
            // }
            // 获得系统总内存，单位是KB
            totalMemory = Long.valueOf(arrayOfString[1]).longValue();
            localBufferedReader.close();
        } catch (IOException e) {

        }
        return totalMemory * 1000;
    }

    /**
     * 获取可用内存大小
     */
    private static long getAvailableMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memInfo);
        long availMem = memInfo.availMem;
        return availMem;
    }

    /**
     * 获取Android Data路径下已使用容量百分比
     */
    private static int getUsedRomPerc() {
        String path = Environment.getDataDirectory().getPath();
        return getUsedSizePerc(path);
    }

    /**
     * 获取SD卡已使用容量百分比
     */
    private static int getUsedSDPerc() {
        // 先判断有无SD卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getPath();
            return getUsedSizePerc(path);
        }
        return 0;
    }

    /**
     * 获取指定路径下已使用的容量占总数的百分比
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private static int getUsedSizePerc(String path) {
        StatFs statFs = new StatFs(path);
        // long blockSize;
        long totalBlocks;
        long availableBlocks;
        if (Build.VERSION.SDK_INT >= 18) {
            // blockSize=statFs.getBlockSizeLong();
            totalBlocks = statFs.getBlockCountLong();
            availableBlocks = statFs.getAvailableBlocksLong();
        } else {
            // blockSize=statFs.getBlockSize();
            totalBlocks = statFs.getBlockCount();
            availableBlocks = statFs.getAvailableBlocks();
        }
        long usedBlocks = totalBlocks - availableBlocks;

        // 处理存储容量格式
        // long total = totalBlocks*blockSize;
        // long available = availableBlocks * blockSize;
        // long used = usedBlocks * blockSize;

        return (int) (usedBlocks * 100 / totalBlocks);
    }

    /**
     * 获取SD卡已使用容量百分比
     */
    private static int getSDSize() {
        // 先判断有无SD卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getPath();
            return getTotalSize(path);
        }
        return 0;
    }

    /**
     * 获取指定路径下总容量 M
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private static int getTotalSize(String path) {
        StatFs statFs = new StatFs(path);
        long blockSize;
        long totalBlocks;
        // long availableBlocks;
        if (Build.VERSION.SDK_INT >= 18) {
            blockSize = statFs.getBlockSizeLong();
            totalBlocks = statFs.getBlockCountLong();
            // availableBlocks = statFs.getAvailableBlocksLong();
        } else {
            blockSize = statFs.getBlockSize();
            totalBlocks = statFs.getBlockCount();
            // availableBlocks = statFs.getAvailableBlocks();
        }
        return (int) ((blockSize * totalBlocks) / (1000 * 1000));
    }
}
