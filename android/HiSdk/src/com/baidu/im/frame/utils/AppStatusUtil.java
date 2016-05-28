package com.baidu.im.frame.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;

public class AppStatusUtil {

    /**
     * Whether the process is running on background or not.
     * 
     * @param context
     * @return result
     */
    public static boolean isBackground(Context context) {
        if (context != null) {
            String packageNameString = context.getPackageName();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (null != appProcesses) {
                for (RunningAppProcessInfo appProcess : appProcesses) {
                    if (appProcess.processName.equals(packageNameString)) {
                        if (appProcess.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                                && appProcess.importance != RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Whether the process is running in foreground or not.
     * 
     * @param context
     * @return result
     */
    public static boolean isVisible(Context context) {
        if (null != context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            String packageName = context.getPackageName();
            List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses == null) {
                return false;
            }
            for (RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.processName.equals(packageName)) {
                    if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                            || appProcess.importance == RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Whether the process is running in foreground or not.
     * 
     * @param context
     * @return result
     */
    public static boolean isRunning(Context context) {
        if (null != context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            String packageName = context.getPackageName();
            List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses == null) {
            	
                return false;
            }
            for (RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.processName.equals(packageName)) { 
                        return true;
                    } else {
                 
                        return false;
                    }
                }
            }
        return false;
    }

    /**
     * Whether the activity is on the top of task or not.
     * 
     * @param context
     * @return result
     */
    public static boolean isTopActivity(Context context) {
        if (null != context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            try {
                List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
                if (tasksInfo.size() > 0) {
                    if (context.getPackageName().equals(tasksInfo.get(0).topActivity.getPackageName())) {
                        return true;
                    }
                }
            } catch (SecurityException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Whether the screen is locked or not.
     * 
     * @param context
     * @return result
     */
    public static boolean isScreenNotLocked(Context context) {
        if (null != context) {
            boolean isScreenLocked = false;
            boolean isScreenOn = false;
            try {
                // isKeyguardRestricted
                KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                isScreenLocked = keyguardManager.inKeyguardRestrictedInputMode();
                // isScreenOn
                PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                isScreenOn = powerManager.isScreenOn();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!isScreenLocked || isScreenOn) {
                return true;
            }
        }
        return false;
    }
}
