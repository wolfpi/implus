package com.baidu.im.frame.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;

public class OsUtil {
    public static String getProcessNameBy(Context context) {
    	
    	if(context == null)
    		return null;

        int pid = android.os.Process.myPid();

        ActivityManager actvityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> procInfos = actvityManager.getRunningAppProcesses();

        if (procInfos != null) {
            for (RunningAppProcessInfo procInfo : procInfos) {
                if (pid == procInfo.pid) {
                    return context.getPackageName() + ":" + procInfo.processName;
                }
            }
        }
        return null;
    }
}
