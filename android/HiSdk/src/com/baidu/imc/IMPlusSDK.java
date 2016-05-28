package com.baidu.imc;

import android.content.Context;
import android.util.Log;

import com.baidu.im.frame.utils.DeviceInfoUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.client.IMClient;
import com.baidu.imc.impl.im.client.IMPClientImpl;

public class IMPlusSDK extends IMPChannelSDK {
	private static IMPClientImpl impClient = null;
	private final static String TAG = "IMPlusSDK";
	private static String mAppKey = null;
	
	public static void init(String appKey, Context context){
		
		if(appKey == null || appKey.isEmpty() || context == null)
			return;
		
		if(mAppKey != null)
			return;
		mAppKey = appKey;
		IMPChannelSDK.init(appKey, context);
		
		try{
			if(impClient==null){
				synchronized(IMPlusSDK.class){
					if(impClient==null){
						impClient = new IMPClientImpl();
						impClient.init(context, appKey);
					}
				}
			}
		}catch(Throwable t){
			t.printStackTrace();
			Log.e("IMPlusSDK", t.getMessage(), t);
		}
	}
	
public static void init(String appKey, Context context,String deviceToken){
	    if(deviceToken == null || deviceToken.length() == 0)
	    	return;
	    
		DeviceInfoUtil.setDeviceToken(deviceToken);
		init(appKey,context);
	}

public static void init2(String appKey, Context context,String deviceTokenPrefix){
    if(deviceTokenPrefix == null || deviceTokenPrefix.length() == 0)
    	return;
    
	DeviceInfoUtil.setDeviceTokenPrefix(deviceTokenPrefix);
	init(appKey,context);
}

	
	public static IMClient getImpClient(){

		try {
			if(impClient == null) {
                LogUtil.e(TAG, "IMPlusSDK did not initialized correctly");
            }
			return impClient;
		} catch (Throwable e) {
			// FIXME 未初始化时会报 NoClassDefFoundError, 需要找到真实原因
			e.printStackTrace();
			return null;
		}
	}
	
}
