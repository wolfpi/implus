package com.baidu.im.frame.outapp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.baidu.im.outapp.OutAppApplication;

/**
 * 网络进程的心跳, 监听业务进程的心跳包,如果超时则开启网络进程心跳以保护通道.
 * 
 * @author zhaowei10
 * 
 */
public class OutAppDaemonThread implements Runnable {

    public static final String TAG = "OutAppDaemonThread";

    public static final long INTERVAL = 3000;

    private ScheduledExecutorService threadPool;

    private class HeartbeatThreadFactory implements ThreadFactory {

        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "OutAppDaemonThread");
            t.setDaemon(true);
            return t;
        }
    }

    public void start() {

        if (threadPool != null) {
            threadPool.shutdown();
        }
        threadPool = Executors.newSingleThreadScheduledExecutor(new HeartbeatThreadFactory());
        threadPool.scheduleAtFixedRate(this, INTERVAL, INTERVAL, TimeUnit.MILLISECONDS);
    }

    void checkAppStatus() {
    	/*
        int sessionStatus = PreferenceUtil.getInt(PreferenceKey.sessionStatus);
        if (IAppSession.STATUS_USER_LOGIN == sessionStatus || IAppSession.STATUS_APP_LOGIN == sessionStatus
                || IAppSession.STATUS_APP_ONLINE == sessionStatus) {
            OutAppApplication.getInstance().getTransactionFlow().outAppSetAppOffline();
        }*/
    }

    void checkHeartbeat() {
    	/*

        if (Math.abs(SystemClock.elapsedRealtime() - PreferenceUtil.getLong(PreferenceKey.lastHeartbeatTime)) > InAppHeartbeat.HEARTBEAT_INTERVAL) {
            OutAppApplication.getInstance().getNetworkLayer().heartbeat();
        }*/
    }

    public void run() {

        // 如果app没有绑定，则离线。
        if (!OutAppApplication.getInstance().getOutAppConnection().isBind()) {
            checkAppStatus();
            checkHeartbeat();
        }
    }

    public void stop() {
        if (threadPool != null) {
            threadPool.shutdown();
            threadPool = null;
        }
    }

}
