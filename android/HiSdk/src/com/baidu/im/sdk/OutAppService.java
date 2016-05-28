package com.baidu.im.sdk;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.baidu.im.dlinterface.OutAppServiceFacade;
import com.baidu.im.frame.ServiceControlUtil;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.outapp.OutAppApplication;

/**
 * Out-app的service，out-app进程的承载着。
 * 
 * @author zhaowei10
 * 
 */
public class OutAppService extends Service {

    public static final String TAG = "OutAppService";

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate() {
    	
    	LogUtil.e(TAG, "service onCreated");
        super.onCreate();
        
        Notification notification =
                new Notification(android.R.drawable.star_on, "outAppservice running", System.currentTimeMillis());

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,new Intent(this, OutAppService.class), 0);
        notification.setLatestEventInfo(this, "outApp Service", "outAppservice running", contentIntent);
        startForeground(0, notification);
        OutAppServiceFacade.onCreate(this);
    }

    private void startService()
    {
    	if(ServiceControlUtil.showInSeperateProcess(OutAppApplication.getInstance().getContext()) ) {
    		if(OutAppApplication.getInstance().getContext() != null && OutAppService.class != null)
    		{
    			Intent startintent = new Intent(OutAppApplication.getInstance().getContext(), OutAppService.class);
    			startintent.setAction("com.baidu.im.sdk.service");
                try {
                    OutAppApplication.getInstance().getContext().startService(startintent);
                } catch (Exception e) {
                    LogUtil.e(TAG, "failed to start service", e);
                }
            }
    	}
     	
    }
    @Override
    public void onDestroy() {
        OutAppServiceFacade.onDestroy();
        super.onDestroy();
        
        //startService();
        LogUtil.e(TAG, "service onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
    	 LogUtil.e(TAG, "service onBind");
        return OutAppServiceFacade.onBind(intent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Service#onUnbind(android.content.Intent)
     */
    @Override
    public boolean onUnbind(Intent intent) {
    	 LogUtil.e(TAG, "service Unbind");
        OutAppServiceFacade.onUnbind(intent);
        
        startService();
        return true;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        OutAppServiceFacade.onTaskRemoved(rootIntent);
        startService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // LogUtil.e(TAG, "Service OnstartCmd is called");
        return super.onStartCommand(intent, START_STICKY, startId);
    }
}
