package com.baidu.im.frame.inapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.baidu.im.constant.Constant;
import com.baidu.im.frame.ServiceControlUtil;
import com.baidu.im.frame.pb.ObjInformMessage.InformMessage;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.NotificationUtil;
import com.baidu.im.frame.utils.PreferenceKey;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

public class PushReceiver extends BroadcastReceiver {
	
	final static private String TAG = "push"; 

	@Override
	public void onReceive(Context context, Intent intent) {
		
		LogUtil.i(TAG, "on Receive in context");
		if(intent.getAction().equals("com.baidu.im.sdk.push")) {
			
	    InAppApplication.getInstance().getPreference().initialize(context, null);
	    int appId = InAppApplication.getInstance().getPreference().getInt(PreferenceKey.appId);
	    int appIdInIntent = intent.getIntExtra(Constant.sdkBDAppId, -1);
	    
	    LogUtil.i(TAG, String.valueOf(appId) + String.valueOf(appIdInIntent));
	    if(appIdInIntent == appId)
	    {
	    	int chattype = intent.getIntExtra(Constant.sdkBDChatType, -1);
	    	String toID     = intent.getStringExtra(Constant.sdkBDTOUID);
	    	String fromID   = intent.getStringExtra(Constant.sdkBDFORMUID);
	    	if(toID == null)
	    	{
	    		toID = "";
	    		fromID = "";
	    	}
	    	
	    try {
			InformMessage informMessage =
			InformMessage.parseFrom(intent.getByteArrayExtra(Constant.sdkBDData));
			if(ServiceControlUtil.showInSeperateProcess(context) && NotificationUtil.isNotificationEnable()) 
			{
			     //NotificationUtil.showNormal(context, informMessage,chattype,toID,fromID);
				NotificationUtil.showNormal(context, informMessage,appId);
			     LogUtil.i("push", "show OK....");
			}
			
		} catch (InvalidProtocolBufferMicroException e) {
			//e.printStackTrace();
		}
	    }
		}
	    
	}
}
