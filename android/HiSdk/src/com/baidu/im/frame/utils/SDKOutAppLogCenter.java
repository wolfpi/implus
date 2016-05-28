package com.baidu.im.frame.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.hi.plugin.logcenter.ILogCenter;
import com.baidu.hi.plugin.logcenter.LogCenter;
import com.baidu.hi.plugin.logcenter.LogCenterConfiguration;
import com.baidu.hi.plugin.logcenter.log.FileLog;
import com.baidu.hi.plugin.logcenter.log.LogcatLog;
import com.baidu.im.constant.Constant;

public class SDKOutAppLogCenter {

    public LogCenter logCenter;

    private FileLog statLog;
    private FileLog imLog;
    private FileLog msgLog;
    private FileLog protocolLog;

    public SDKOutAppLogCenter(LogLevel defaultLogcatLevel, LogLevel defaultFileLogLevel) {
        if (defaultLogcatLevel == null || defaultFileLogLevel == null) {
            throw new RuntimeException("defaultLogcatLevel or defaultFileLogLevel is null");
        }
        LogCenterConfiguration configuration = new LogCenterConfiguration();
        configuration.logFilePath = Constant.sdkExternalLogDir;
        configuration.logName = ".mainprocess.txt";
        configuration.logClearTime = 24 * 10;
        configuration.logNeedFormat = false;
        configuration.defaultLogcatLevel = defaultLogcatLevel.getLogCenterLevel();
        configuration.defaultFileLogLevel = defaultFileLogLevel.getLogCenterLevel();
        SDKLogCenterListener listener = new SDKLogCenterListener();
        logCenter = new LogCenter(configuration, listener);
    }

    public SDKOutAppLogCenter(LogCenterConfiguration configuration) {
        SDKLogCenterListener listener = new SDKLogCenterListener();
        logCenter = new LogCenter(configuration, listener);
    }

    public void initialize(Context context) {
        if (null != context) {
            logCenter.initialize(context, this.getClass());
            logCenter.enablePeriodicUpload(true, 24,
                    "http://rest.implus.baidu.com/proxy_monitor_api/request_log_upload.php", statLog);
            logCenter.enableSpecificUpload(true, "");
        } else {
            throw new RuntimeException("SDKOutAppLogCenter Context is null");
        }

    }

    private class SDKLogCenterListener implements ILogCenter {

        @Override
        public List<FileLog> addFileLogs(String logFilePath, int logClearTime, boolean logNeedFormat) {
            if (TextUtils.isEmpty(logFilePath)) {
                return null;
            }
            statLog = new FileLog(logFilePath, ".stat.txt", logClearTime, false);
            imLog = new FileLog(logFilePath, ".im.txt", logClearTime, true);
            msgLog = new FileLog(logFilePath, ".msg.txt", logClearTime, false);
            protocolLog = new FileLog(logFilePath, ".protocol.txt", logClearTime, false);
            List<FileLog> list = new ArrayList<FileLog>();
            list.add(statLog);
            list.add(imLog);
            list.add(msgLog);
            list.add(protocolLog);
            return list;
        }

        @Override
        public List<LogcatLog> addLogcats() {
            return null;
        }

    }

    public void stat(String tag, String msg, Throwable tr) {
    	if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg))
    		return;
        if (null != logCenter.getLogcatLog()) {
            logCenter.getLogcatLog().i(tag, msg, tr);
        }
        if (null != statLog) {
            statLog.e(tag, msg, tr);
        }
    }

    public void stat(String tag, String msg) {
    	if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg))
    		return;
        if (null != logCenter.getLogcatLog()) {
            logCenter.getLogcatLog().i(tag, msg);
        }
        if (null != statLog) {
            statLog.e(tag, msg);
        }
    }

    public void im(String tag, String msg) {
    	if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg))
    		return;
        if (null != logCenter.getLogcatLog()) {
            logCenter.getLogcatLog().i(tag, msg);
        }
        if (null != imLog) {
            imLog.i(tag, msg);
        }
    }

    public void im(String tag, String msg, Throwable tr) {
    	if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg))
    		return;
        if (null != logCenter.getLogcatLog()) {
            logCenter.getLogcatLog().e(tag, msg, tr);
        }
        if (null != imLog) {
            imLog.e(tag, msg, tr);
        }
    }

    public void msg(String tag, String msg) {
    	if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg))
    		return;
        if (null != logCenter.getLogcatLog()) {
            logCenter.getLogcatLog().i(tag, msg, null);
        }
        if (null != msgLog) {
            msgLog.i(tag, msg);
        }
    }

    public void protocol(String tag, String msg) {
    	if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg))
    		return;
        if (null != logCenter.getLogcatLog()) {
            logCenter.getLogcatLog().w(tag, msg, null);
        }
        if (null != msgLog) {
            protocolLog.w(tag, msg);
        }
    }

    public void e(String tag, String msg, Throwable tr) {
    	if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg))
    		return;
        logCenter.e(tag, msg, tr);
    }

    public void e(String tag, String msg) {
    	if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg))
    		return;
        logCenter.e(tag, msg);
    }

    public void w(String tag, String msg, Throwable tr) {
    	if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg))
    		return;
        logCenter.w(tag, msg, tr);
    }

    public void w(String tag, String msg) {
    	if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg))
    		return;
        logCenter.w(tag, msg);
    }

    public void i(String tag, String msg, Throwable tr) {
    	if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg))
    		return;
        logCenter.i(tag, msg, tr);
    }

    public void i(String tag, String msg) {
    	if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg))
    		return;
        logCenter.i(tag, msg);
    }

    public void d(String tag, String msg, Throwable tr) {
    	if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg))
    		return;
        logCenter.d(tag, msg, tr);
    }

    public void d(String tag, String msg) {
    	if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg))
    		return;
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
