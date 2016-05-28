package com.baidu.im.frame.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

/**
 * 网络状态获取
 * 
 * @author zhaowei10
 * 
 */
public class NetworkUtil {

	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	public static NetworkType getNetworkType(Context context) {
		if(context == null)
			return NetworkType.unknown;
		
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return NetworkType.broken;
		} else {
			int nType = networkInfo.getType();
			if (nType == ConnectivityManager.TYPE_MOBILE) {
				String extraInfo = networkInfo.getExtraInfo();
				if (!TextUtils.isEmpty(extraInfo)) {
					if (extraInfo.equalsIgnoreCase("cmnet")) {
						return NetworkType.cmnet;
					} else {
						return NetworkType.cmwap;
					}
				}
			} else if (nType == ConnectivityManager.TYPE_WIFI) {
				return NetworkType.wifi;
			}
			return NetworkType.unknown;
		}
	}

	public enum NetworkType {

		broken,

		unknown,

		cmnet,

		cmwap,

		wifi,
	}
}
