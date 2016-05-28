package com.baidu.im.dlinterface;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.outapp.OutAppApplication;

public class OutAppServiceFacade {

    public static final String TAG = "OutAppServiceFacade";

    public static synchronized void onCreate(Context context) {

        try {
            LogUtil.printMainProcess(TAG, "service onCreate.");
            OutAppApplication.getInstance().initialize(context);

            // service杀不死
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, context.getClass());
            PendingIntent pendingIntent =
                    PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            long triggerAtTime = SystemClock.elapsedRealtime();
            int interval = 300 * 1000;
            manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, interval, pendingIntent);

        } catch (RuntimeException e) {
            LogUtil.e(TAG, e);
        }
    }

    public static synchronized void onDestroy() {
        LogUtil.printMainProcess(TAG, "service onDestroy.");
        try {
            OutAppApplication.getInstance().destroy();
        } catch (RuntimeException e) {
            LogUtil.e(TAG, e);
        }
    }

    public static synchronized IBinder onBind(Intent intent) {
        LogUtil.printMainProcess(TAG, "service onBind.");
        try {
            if (OutAppApplication.getInstance().getOutAppConnection() != null) {

                LogUtil.printMainProcess("dynamicLoader0 intent=" + intent);
                LogUtil.printMainProcess("dynamicLoader0 Binder=" + null);
                return OutAppApplication.getInstance().getOutAppConnection().getBinder();
            }
        } catch (RuntimeException e) {
            LogUtil.e(TAG, e);
        }
        return null;
    }

    public static synchronized void onTaskRemoved(Intent intent) {
        LogUtil.printMainProcess(TAG, "service onTaskRemoved.");
        try {
            if (OutAppApplication.getInstance().getOutAppConnection() != null) {
                OutAppApplication.getInstance().getOutAppConnection().onUnbind();
            }
        } catch (RuntimeException e) {
            LogUtil.e(TAG, e);
        }
    }

    public static synchronized void onUnbind(Intent intent) {
        LogUtil.printMainProcess(TAG, "service onUnbind.");
        try {
            if (OutAppApplication.getInstance().getOutAppConnection() != null) {
                OutAppApplication.getInstance().getOutAppConnection().onUnbind();
            }
        } catch (RuntimeException e) {
            LogUtil.e(TAG, e);
        }
    }
}
