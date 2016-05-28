package com.baidu.im.frame.outapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.baidu.im.frame.ServiceControlUtil;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.outapp.OutAppApplication;
import com.baidu.im.sdk.OutAppService;

public class ImBroadcastReceiver extends BroadcastReceiver {
	
	
	private final static String TAG = "IMBroadcastReceiver";

    private static final String CONNECTIVITY_ACTION = ConnectivityManager.CONNECTIVITY_ACTION;
    private static final String BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
    private static final String MEDIA_MOUNTED = "android.intent.action.MEDIA_MOUNTED";
    private static final String USER_PRESENT = "android.intent.action.USER_PRESENT";
    private static final String ACTION_POWER_CONNECTED = "android.intent.action.ACTION_POWER_CONNECTED";
    private static final String ACTION_POWER_DISCONNECTED = "android.intent.action.ACTION_POWER_DISCONNECTED";
    public final static String ACTION_DEBUG_DUMP_SERVICE = "com.baidu.im.sdk.d.r"; // dump service data

    @Override
    public void onReceive(Context context, Intent intent) {
    	
    	if(context == null || intent == null)
    		return ;
    	if(OutAppApplication.getInstance() != null && OutAppApplication.getInstance().getNetworkLayer() != null ) {
    		OutAppApplication.getInstance().getNetworkLayer().networkChanged();
    	}

        final String action = intent.getAction();
        LogUtil.i(TAG, "broadcast to start" + action);
        try {
        	
        	if(ServiceControlUtil.showInSeperateProcess(context)) {
        		Intent startintent = new Intent(context, OutAppService.class);
        		startintent.setAction("com.baidu.im.sdk.service");
                try {
                    context.startService(startintent);
                } catch (Exception e) {
                    LogUtil.e("ImBroadcastReceiver: fail to startService", e);
                }
            }

            if (CONNECTIVITY_ACTION.equals(action)) {
            	
            	if( InAppApplication.getInstance() != null)
            	{
            		InAppApplication.getInstance().setInAppNetworkChanged(true);
            	}
            } else if (BOOT_COMPLETED.equals(action)) {

            } else if (MEDIA_MOUNTED.equals(action)) {
            	
            } else if (USER_PRESENT.equals(action)) {
            	// OutAppApplication.getInstance().getNetworkLayer().networkChanged();

            } else if (ACTION_POWER_CONNECTED.equals(action)) {
       
            } else if (ACTION_POWER_DISCONNECTED.equals(action)) {

            } else if (ACTION_DEBUG_DUMP_SERVICE.equals(action)) {
                // adb shell am broadcast -a com.baidu.hi.r.service
                OutAppApplication.getInstance().getNetworkLayer().dumpChannel();
            }
        } catch (RuntimeException e) {
            LogUtil.e("ImBroadcastReceiver", e);
        }

    }
}
