package com.baidu.hi.sdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.baidu.hi.sdk.IMSDKApplication;

public class NetworkUtil {
    /**
     * 当前是否已经联网
     * 
     * @return
     */
    public static boolean isConnected() {
        final NetworkInfo info = getNetworkInfo();
        return (info != null && info.isConnected());
    }

    /**
     * 获得Android 的网络信息
     * 
     * @return
     */
    private static NetworkInfo getNetworkInfo() {
        return ((ConnectivityManager) IMSDKApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
    }
}
