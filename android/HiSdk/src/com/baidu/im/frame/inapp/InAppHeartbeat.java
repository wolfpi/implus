package com.baidu.im.frame.inapp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.NetworkUtil;
import com.baidu.im.sdk.IMessageResultCallback;

public class InAppHeartbeat {

	public static final String TAG = "InAppHeartbeat";
    // 心跳间隔 30秒。
    public static final long HEARTBEAT_INTERVAL = 5 * 60 * 1000;

    // 延迟更新配置文件
    private static final long UPDATE_CONFIG_DELAY = 6 * 60 * 1000;

    private ScheduledExecutorService heartbeatThreadPool;

    private IMessageResultCallback regAppCallback;
    private IMessageResultCallback heartbeatCallback;

    private class HeartbeatThreadFactory implements ThreadFactory {

        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "heartbeatFromInApp");
            t.setDaemon(true);
            return t;
        }
    }

    public void start(IMessageResultCallback regAppCallback, IMessageResultCallback heartbeatCallback) {
        this.regAppCallback = regAppCallback;
        this.heartbeatCallback = heartbeatCallback;
        if (heartbeatThreadPool != null) {
            heartbeatThreadPool.shutdown();
        }
        heartbeatThreadPool = Executors.newSingleThreadScheduledExecutor(new HeartbeatThreadFactory());
        //heartbeatThreadPool.scheduleAtFixedRate(new HeartbeatThread(), 1000, HEARTBEAT_INTERVAL, TimeUnit.MILLISECONDS);
    }

    private class HeartbeatThread implements Runnable {
        public void run() {
            if (NetworkUtil.isNetworkConnected(InAppApplication.getInstance().getContext())) {
                if (!InAppApplication.getInstance().isConnected()) {
                    LogUtil.printMainProcess("心跳发起regApp");
                    //InAppApplication.getInstance().getTransactionFlow().regApp(regAppCallback);
                }else { 
                LogUtil.i(TAG, "inapp heartbeat");
                InAppApplication.getInstance().getTransactionFlow().heartbeat(heartbeatCallback);
                InAppApplication.getInstance().setLastInappHearbeat();
            	}
                if (InAppApplication.getInstance().isUpdateConfigRequired()) {
                    LogUtil.printMainProcess("心跳延迟发起UpdateConfig");
                    heartbeatThreadPool.schedule(new UpdateConfigThread(), UPDATE_CONFIG_DELAY, TimeUnit.MILLISECONDS);
                }
                
            }
        }
    }

    private class UpdateConfigThread implements Runnable {

        @Override
        public void run() {
            if (NetworkUtil.isNetworkConnected(InAppApplication.getInstance().getContext())) {
                if (InAppApplication.getInstance().isUpdateConfigRequired()) {
                    LogUtil.printMainProcess("心跳发起updateConfig");
                    InAppApplication.getInstance().getTransactionFlow().updateConfig();
                    InAppApplication.getInstance().setUpdateConfigRequired(false);
                }
            }
        }

    }

    public void stop() {
        if (heartbeatThreadPool != null) {
            heartbeatThreadPool.shutdown();
            heartbeatThreadPool = null;
        }
    }

    public IMessageResultCallback getRegAppCallback() {
        return regAppCallback;
    }

    public IMessageResultCallback getHeartbeatCallback() {
        return heartbeatCallback;
    }
}
