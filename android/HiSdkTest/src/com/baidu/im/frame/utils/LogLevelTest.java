package com.baidu.im.frame.utils;

import com.baidu.hi.plugin.logcenter.LogCenterLevel;

import junit.framework.Assert;
import android.test.AndroidTestCase;

public class LogLevelTest extends AndroidTestCase {

    public void test() {
        Assert.assertEquals(16, LogLevel.ERROR.getLevel());
        Assert.assertEquals(8, LogLevel.PROTOCOL.getLevel());
        Assert.assertEquals(4, LogLevel.MAINPROGRESS.getLevel());
        Assert.assertEquals(2, LogLevel.DEBUG.getLevel());
        Assert.assertEquals(8, LogLevel.WARN.getLevel());
        Assert.assertEquals(4, LogLevel.INFO.getLevel());

        Assert.assertEquals("error", LogLevel.ERROR.getName());
        Assert.assertEquals("protocol", LogLevel.PROTOCOL.getName());
        Assert.assertEquals("mainprogress", LogLevel.MAINPROGRESS.getName());
        Assert.assertEquals("debug", LogLevel.DEBUG.getName());
        Assert.assertEquals("warn", LogLevel.WARN.getName());
        Assert.assertEquals("info", LogLevel.INFO.getName());

        Assert.assertEquals("E", LogLevel.ERROR.getTag());
        Assert.assertEquals("W", LogLevel.PROTOCOL.getTag());
        Assert.assertEquals("I", LogLevel.MAINPROGRESS.getTag());
        Assert.assertEquals("D", LogLevel.DEBUG.getTag());
        Assert.assertEquals("W", LogLevel.WARN.getTag());
        Assert.assertEquals("I", LogLevel.INFO.getTag());

        Assert.assertEquals(LogCenterLevel.ERROR, LogLevel.ERROR.getLogCenterLevel());
        Assert.assertEquals(LogCenterLevel.WARN, LogLevel.PROTOCOL.getLogCenterLevel());
        Assert.assertEquals(LogCenterLevel.INFO, LogLevel.MAINPROGRESS.getLogCenterLevel());
        Assert.assertEquals(LogCenterLevel.DEBUG, LogLevel.DEBUG.getLogCenterLevel());
        Assert.assertEquals(LogCenterLevel.WARN, LogLevel.WARN.getLogCenterLevel());
        Assert.assertEquals(LogCenterLevel.INFO, LogLevel.INFO.getLogCenterLevel());
    }

}
