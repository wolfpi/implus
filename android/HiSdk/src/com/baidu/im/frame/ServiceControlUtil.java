package com.baidu.im.frame;

import com.baidu.im.frame.utils.LogUtil;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class ServiceControlUtil {

	public static boolean showInSeperateProcess(Context context)
	{
		boolean inSeperateProcess = true;
    	if(context != null) {
    	String metainProcess;
    	PackageManager pm = context.getPackageManager();
          if (null != pm) {
        	try {
				ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA);
				
				if((appInfo != null) && (appInfo.metaData != null) ) {
					inSeperateProcess=appInfo.metaData.getBoolean("serviceinseparateprocess",true);
				}
			} catch (NameNotFoundException e) {
				LogUtil.printError("read metadata seperate process", e);
			}
          }
    	}
		
		return inSeperateProcess;
	}
}
