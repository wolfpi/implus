package com.baidu.im.frame.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.baidu.hi.plugin.logcenter.ILogCenter;
import com.baidu.hi.plugin.logcenter.LogCenter;
import com.baidu.hi.plugin.logcenter.LogCenterConfiguration;
import com.baidu.hi.plugin.logcenter.log.FileLog;
import com.baidu.hi.plugin.logcenter.log.LogcatLog;

public class SDKInAppLogCenter {

    public LogCenter logCenter;

    private FileLog statLog;

    public SDKInAppLogCenter(LogLevel defaultLogcatLevel, LogLevel defaultFileLogLevel) {
    	if(defaultLogcatLevel == null || defaultFileLogLevel == null)
    		throw new RuntimeException("defaultLogcatLevel or defaultFileLogLevel is null");
    	
        LogCenterConfiguration configuration = new LogCenterConfiguration();
        configuration.logFilePath = "/BaiduIm/log/InApp/";
        configuration.logName = ".mainprocess.log";
        configuration.logClearTime = 24 * 10;
        configuration.logNeedFormat = false;
        configuration.defaultLogcatLevel = defaultLogcatLevel.getLogCenterLevel();
        configuration.defaultFileLogLevel = defaultFileLogLevel.getLogCenterLevel();
        SDKLogCenterListener listener = new SDKLogCenterListener();
        logCenter = new LogCenter(configuration, listener);
    }

    public SDKInAppLogCenter(LogCenterConfiguration configuration) {
        SDKLogCenterListener listener = new SDKLogCenterListener();
        logCenter = new LogCenter(configuration, listener);
    }

    public void initialize(Context context) {
        if (null != context) {
            logCenter.initialize(context, this.getClass());
            logCenter.enablePeriodicUpload(true, 24, "", statLog);
            logCenter.enableSpecificUpload(true, "");
        } else {
            throw new RuntimeException("SDKInAppLogCenter Context is null");
        }

    }

    private class SDKLogCenterListener implements ILogCenter {

        @Override
        public List<FileLog> addFileLogs(String logFilePath, int logClearTime, boolean logNeedFormat) {
            statLog = new FileLog(logFilePath, ".stat", logClearTime, false);
            List<FileLog> list = new ArrayList<FileLog>();
            list.add(statLog);
            return list;
        }

        @Override
        public List<LogcatLog> addLogcats() {
            return null;
        }

    }

    public void stat(String tag, String msg, Throwable tr) {
        if (null != logCenter.getLogcatLog()) {
            logCenter.getLogcatLog().e(tag, msg, tr);
        }
        if (null != statLog) {
            statLog.e(tag, msg, tr);
        }
    }

    public void stat(String tag, String msg) {
        if (null != logCenter.getLogcatLog()) {
            logCenter.getLogcatLog().e(tag, msg);
        }
        if (null != statLog) {
            statLog.e(tag, msg);
        }
    }

    public void e(String tag, String msg, Throwable tr) {
        logCenter.e(tag, msg, tr);
    }

    public void e(String tag, String msg) {
        logCenter.e(tag, msg);
    }

    public void w(String tag, String msg, Throwable tr) {
        logCenter.w(tag, msg, tr);
    }

    public void w(String tag, String msg) {
        logCenter.w(tag, msg);
    }

    public void i(String tag, String msg, Throwable tr) {
        logCenter.i(tag, msg, tr);
    }

    public void i(String tag, String msg) {
        logCenter.i(tag, msg);
    }

    public void d(String tag, String msg, Throwable tr) {
        logCenter.d(tag, msg, tr);
    }

    public void d(String tag, String msg) {
        logCenter.d(tag, msg);
    }

    public void setFileLogLevel(LogLevel level) {
        logCenter.setFileLogLevel(level.getLogCenterLevel());
    }

    public void changeFileLogLevel(LogLevel level, int duration, String url) {
        logCenter.changeFileLogLevel(level.getLogCenterLevel(), duration, url);
    }

    public void setLogcatLevel(LogLevel level) {
        logCenter.setLogcatLevel(level.getLogCenterLevel());
    }

    public void changeLogcatLogLevel(LogLevel level, int duration) {
        logCenter.changeLogcatLevel(level.getLogCenterLevel(), duration);
    }

    public void changeTime() {
        logCenter.changeTime();
    }
}
