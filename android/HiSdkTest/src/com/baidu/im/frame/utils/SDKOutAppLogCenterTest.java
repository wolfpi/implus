//package com.baidu.im.frame.utils;
//
//import java.io.File;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//import junit.framework.Assert;
//import android.test.InstrumentationTestCase;
//
//import com.baidu.hi.plugin.logcenter.LogCenterConfiguration;
//import com.baidu.hi.plugin.logcenter.log.FileLog;
//import com.baidu.hi.plugin.logcenter.utils.FileUtil;
//import com.baidu.im.testutil.SetUpUtil;
//
//public class SDKOutAppLogCenterTest extends InstrumentationTestCase {
//
//    @Override
//    public void setUp() throws Exception {
//        SetUpUtil.initialize(this.getInstrumentation());
//    }
//
//    private final static String TESTLOG = "TESTLOG";
//
//    private class TestSDKOutAppLogCenter extends SDKOutAppLogCenter {
//
//        public TestSDKOutAppLogCenter(LogCenterConfiguration configuration) {
//            super(configuration);
//        }
//
//    }
//
//    public void testD() {
//        // 初始化为DEBUG
//        LogCenterConfiguration configuration = new LogCenterConfiguration();
//        configuration.defaultFileLogLevel = LogLevel.DEBUG.getLogCenterLevel();
//        configuration.defaultLogcatLevel = LogLevel.DEBUG.getLogCenterLevel();
//        configuration.logFilePath = "/BaiduIm/log/test/";
//        SDKOutAppLogCenter logCenter = new TestSDKOutAppLogCenter(configuration);
//        File sdRoot = FileUtil.getSDRootFile();
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.d(TESTLOG, "Test log for d text log printing.");
//        logCenter.d(TESTLOG, "Test log for d text log with exception printing.", new Exception(
//                "Test log for d exception with text log printing."));
//        if (sdRoot != null) {
//            // 可打出DEBUG日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() > 0);
//        }
//        // 切换级别为INFO
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.setFileLogLevel(LogLevel.INFO);
//        logCenter.d(TESTLOG, "Test log for d text log printing.");
//        logCenter.d(TESTLOG, "Test log for d text log with exception printing.", new Exception(
//                "Test log for d exception with text log printing."));
//        if (sdRoot != null) {
//            // 不可打出INFO日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() == 0);
//        }
//        // 切换级别为WARN
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.setFileLogLevel(LogLevel.WARN);
//        logCenter.d(TESTLOG, "Test log for d text log printing.");
//        logCenter.d(TESTLOG, "Test log for d text log with exception printing.", new Exception(
//                "Test log for d exception with text log printing."));
//        if (sdRoot != null) {
//            // 不可打出WARN日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() == 0);
//        }
//        // 切换级别为ERROR
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.setFileLogLevel(LogLevel.ERROR);
//        logCenter.d(TESTLOG, "Test log for d text log printing.");
//        logCenter.d(TESTLOG, "Test log for d text log with exception printing.", new Exception(
//                "Test log for d exception with text log printing."));
//        if (sdRoot != null) {
//            // 不可打出ERROR日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() == 0);
//        }
//    }
//
//    public void testDWithBroadcast() throws Exception {
//        // 初始化为DEBUG
//        LogCenterConfiguration configuration = new LogCenterConfiguration();
//        configuration.defaultFileLogLevel = LogLevel.DEBUG.getLogCenterLevel();
//        configuration.defaultLogcatLevel = LogLevel.DEBUG.getLogCenterLevel();
//        configuration.logFilePath = "/BaiduIm/log/test/";
//        SDKOutAppLogCenter logCenter = new TestSDKOutAppLogCenter(configuration);
//        logCenter.initialize(this.getInstrumentation().getTargetContext());
//        File sdRoot = FileUtil.getSDRootFile();
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.d(TESTLOG, "Test log for d text log printing.");
//        logCenter.d(TESTLOG, "Test log for d text log with exception printing.", new Exception(
//                "Test log for d exception with text log printing."));
//        if (sdRoot != null) {
//            // 可打出DEBUG日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() > 0);
//        }
//        // 切换级别为INFO
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.changeFileLogLevel(LogLevel.INFO, 1, null);
//        CountDownLatch countdownLatch = new CountDownLatch(1);
//        countdownLatch.await(3000, TimeUnit.MILLISECONDS);
//        logCenter.d(TESTLOG, "Test log for d text log printing.");
//        logCenter.d(TESTLOG, "Test log for d text log with exception printing.", new Exception(
//                "Test log for d exception with text log printing."));
//        if (sdRoot != null) {
//            // 不可打出INFO日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() == 0);
//        }
//        // 切换级别为WARN
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.changeFileLogLevel(LogLevel.WARN, 1, null);
//        CountDownLatch countdownLatchW = new CountDownLatch(1);
//        countdownLatchW.await(3000, TimeUnit.MILLISECONDS);
//        logCenter.d(TESTLOG, "Test log for d text log printing.");
//        logCenter.d(TESTLOG, "Test log for d text log with exception printing.", new Exception(
//                "Test log for d exception with text log printing."));
//        if (sdRoot != null) {
//            // 不可打出WARN日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() == 0);
//        }
//        // 切换级别为ERROR
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.changeFileLogLevel(LogLevel.ERROR, 1, null);
//        CountDownLatch countdownLatchE = new CountDownLatch(1);
//        countdownLatchE.await(3000, TimeUnit.MILLISECONDS);
//        logCenter.d(TESTLOG, "Test log for d text log printing.");
//        logCenter.d(TESTLOG, "Test log for d text log with exception printing.", new Exception(
//                "Test log for d exception with text log printing."));
//        if (sdRoot != null) {
//            // 不可打出ERROR日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() == 0);
//        }
//    }
//
//    public void testI() {
//        // 初始化为INFO
//        LogCenterConfiguration configuration = new LogCenterConfiguration();
//        configuration.defaultFileLogLevel = LogLevel.INFO.getLogCenterLevel();
//        configuration.defaultLogcatLevel = LogLevel.INFO.getLogCenterLevel();
//        configuration.logFilePath = "/BaiduIm/log/test/";
//        SDKOutAppLogCenter logCenter = new TestSDKOutAppLogCenter(configuration);
//        File sdRoot = FileUtil.getSDRootFile();
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.d(TESTLOG, "Test log for d text log printing.");
//        logCenter.d(TESTLOG, "Test log for d text log with exception printing.", new Exception(
//                "Test log for d exception with text log printing."));
//        if (sdRoot != null) {
//            // 不可打出DEBUG日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() == 0);
//        }
//        // 切换级别为INFO
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.i(TESTLOG, "Test log for i text log printing.");
//        logCenter.i(TESTLOG, "Test log for i text log with exception printing.", new Exception(
//                "Test log for i exception with text log printing."));
//        if (sdRoot != null) {
//            // 可打出INFO日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() > 0);
//        }
//        // 切换级别为WARN
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.setFileLogLevel(LogLevel.WARN);
//        logCenter.i(TESTLOG, "Test log for i text log printing.");
//        logCenter.i(TESTLOG, "Test log for i text log with exception printing.", new Exception(
//                "Test log for i exception with text log printing."));
//        if (sdRoot != null) {
//            // 不可打出WARN日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() == 0);
//        }
//        // 切换级别为ERROR
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.setFileLogLevel(LogLevel.ERROR);
//        logCenter.i(TESTLOG, "Test log for i text log printing.");
//        logCenter.i(TESTLOG, "Test log for i text log with exception printing.", new Exception(
//                "Test log for i exception with text log printing."));
//        if (sdRoot != null) {
//            // 不可打出ERROR日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() == 0);
//        }
//    }
//
//    public void testIWithBroadCast() throws Exception {
//        // 初始化为INFO
//        LogCenterConfiguration configuration = new LogCenterConfiguration();
//        configuration.defaultFileLogLevel = LogLevel.INFO.getLogCenterLevel();
//        configuration.defaultLogcatLevel = LogLevel.INFO.getLogCenterLevel();
//        configuration.logFilePath = "/BaiduIm/log/test/";
//        SDKOutAppLogCenter logCenter = new TestSDKOutAppLogCenter(configuration);
//        logCenter.initialize(this.getInstrumentation().getTargetContext());
//        File sdRoot = FileUtil.getSDRootFile();
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.d(TESTLOG, "Test log for d text log printing.");
//        logCenter.d(TESTLOG, "Test log for d text log with exception printing.", new Exception(
//                "Test log for d exception with text log printing."));
//        if (sdRoot != null) {
//            // 不可打出DEBUG日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() == 0);
//        }
//        // 切换级别为INFO
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.i(TESTLOG, "Test log for i text log printing.");
//        logCenter.i(TESTLOG, "Test log for i text log with exception printing.", new Exception(
//                "Test log for i exception with text log printing."));
//        if (sdRoot != null) {
//            // 可打出INFO日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() > 0);
//        }
//        // 切换级别为WARN
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.changeFileLogLevel(LogLevel.WARN, 1, null);
//        CountDownLatch countdownLatch = new CountDownLatch(1);
//        countdownLatch.await(3000, TimeUnit.MILLISECONDS);
//        logCenter.i(TESTLOG, "Test log for i text log printing.");
//        logCenter.i(TESTLOG, "Test log for i text log with exception printing.", new Exception(
//                "Test log for i exception with text log printing."));
//        if (sdRoot != null) {
//            // 不可打出WARN日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() == 0);
//        }
//        // 切换级别为ERROR
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.changeFileLogLevel(LogLevel.ERROR, 1, null);
//        CountDownLatch countdownLatchE = new CountDownLatch(1);
//        countdownLatchE.await(3000, TimeUnit.MILLISECONDS);
//        logCenter.i(TESTLOG, "Test log for i text log printing.");
//        logCenter.i(TESTLOG, "Test log for i text log with exception printing.", new Exception(
//                "Test log for i exception with text log printing."));
//        if (sdRoot != null) {
//            // 不可打出ERROR日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() == 0);
//        }
//    }
//
//    public void testW() {
//        // 初始化为WARN
//        LogCenterConfiguration configuration = new LogCenterConfiguration();
//        configuration.defaultFileLogLevel = LogLevel.WARN.getLogCenterLevel();
//        configuration.defaultLogcatLevel = LogLevel.WARN.getLogCenterLevel();
//        configuration.logFilePath = "/BaiduIm/log/test/";
//        SDKOutAppLogCenter logCenter = new TestSDKOutAppLogCenter(configuration);
//        File sdRoot = FileUtil.getSDRootFile();
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.d(TESTLOG, "Test log for d text log printing.");
//        logCenter.d(TESTLOG, "Test log for d text log with exception printing.", new Exception(
//                "Test log for d exception with text log printing."));
//        logCenter.i(TESTLOG, "Test log for i text log printing.");
//        logCenter.i(TESTLOG, "Test log for i text log with exception printing.", new Exception(
//                "Test log for i exception with text log printing."));
//        if (sdRoot != null) {
//            // 不可打出DEBUG、INFO日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() == 0);
//        }
//        // 切换级别为WARN
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.w(TESTLOG, "Test log for w text log printing.");
//        logCenter.w(TESTLOG, "Test log for w text log with exception printing.", new Exception(
//                "Test log for w exception with text log printing."));
//        if (sdRoot != null) {
//            // 可打出WARN日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() > 0);
//        }
//        // 切换级别为ERROR
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.setFileLogLevel(LogLevel.ERROR);
//        logCenter.w(TESTLOG, "Test log for w text log printing.");
//        logCenter.w(TESTLOG, "Test log for w text log with exception printing.", new Exception(
//                "Test log for w exception with text log printing."));
//        if (sdRoot != null) {
//            // 不可打出ERROR日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() == 0);
//        }
//    }
//
//    public void testWWithBroadcast() throws Exception {
//        // 初始化为WARN
//        LogCenterConfiguration configuration = new LogCenterConfiguration();
//        configuration.defaultFileLogLevel = LogLevel.WARN.getLogCenterLevel();
//        configuration.defaultLogcatLevel = LogLevel.WARN.getLogCenterLevel();
//        configuration.logFilePath = "/BaiduIm/log/test/";
//        SDKOutAppLogCenter logCenter = new TestSDKOutAppLogCenter(configuration);
//        logCenter.initialize(this.getInstrumentation().getTargetContext());
//        File sdRoot = FileUtil.getSDRootFile();
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.d(TESTLOG, "Test log for d text log printing.");
//        logCenter.d(TESTLOG, "Test log for d text log with exception printing.", new Exception(
//                "Test log for d exception with text log printing."));
//        logCenter.i(TESTLOG, "Test log for i text log printing.");
//        logCenter.i(TESTLOG, "Test log for i text log with exception printing.", new Exception(
//                "Test log for i exception with text log printing."));
//        if (sdRoot != null) {
//            // 不可打出DEBUG、INFO日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() == 0);
//        }
//        // 切换级别为WARN
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.w(TESTLOG, "Test log for w text log printing.");
//        logCenter.w(TESTLOG, "Test log for w text log with exception printing.", new Exception(
//                "Test log for w exception with text log printing."));
//        if (sdRoot != null) {
//            // 可打出WARN日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() > 0);
//        }
//        // 切换级别为ERROR
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.changeFileLogLevel(LogLevel.ERROR, 1, null);
//        CountDownLatch countdownLatch = new CountDownLatch(1);
//        countdownLatch.await(3000, TimeUnit.MILLISECONDS);
//        logCenter.w(TESTLOG, "Test log for w text log printing.");
//        logCenter.w(TESTLOG, "Test log for w text log with exception printing.", new Exception(
//                "Test log for w exception with text log printing."));
//        if (sdRoot != null) {
//            // 不可打出ERROR日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() == 0);
//        }
//    }
//
//    public void testE() {
//        // 初始化为ERROR
//        LogCenterConfiguration configuration = new LogCenterConfiguration();
//        configuration.defaultFileLogLevel = LogLevel.ERROR.getLogCenterLevel();
//        configuration.defaultLogcatLevel = LogLevel.ERROR.getLogCenterLevel();
//        configuration.logFilePath = "/BaiduIm/log/test/";
//        SDKOutAppLogCenter logCenter = new TestSDKOutAppLogCenter(configuration);
//        File sdRoot = FileUtil.getSDRootFile();
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.d(TESTLOG, "Test log for d text log printing.");
//        logCenter.d(TESTLOG, "Test log for d text log with exception printing.", new Exception(
//                "Test log for d exception with text log printing."));
//        logCenter.i(TESTLOG, "Test log for i text log printing.");
//        logCenter.i(TESTLOG, "Test log for i text log with exception printing.", new Exception(
//                "Test log for i exception with text log printing."));
//        logCenter.w(TESTLOG, "Test log for w text log printing.");
//        logCenter.w(TESTLOG, "Test log for w text log with exception printing.", new Exception(
//                "Test log for w exception with text log printing."));
//        if (sdRoot != null) {
//            // 不可打出DEBUG、INFO、WARN日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() == 0);
//        }
//        // 切换级别为ERROR
//        logCenter.logCenter.getLogClient().removeFileLogs();
//        logCenter.logCenter.getLogClient().rescheduleFileLog();
//        logCenter.e(TESTLOG, "Test log for e text log printing.");
//        logCenter.e(TESTLOG, "Test log for e text log with exception printing.", new Exception(
//                "Test log for e exception with text log printing."));
//        if (sdRoot != null) {
//            // 可打出ERROR日志
//            FileLog fileLog = (FileLog) (logCenter.logCenter.getFileLog());
//            File file = new File(sdRoot + fileLog.getFilePath() + fileLog.getFileName());
//            Assert.assertTrue(file.exists());
//            Assert.assertTrue(file.length() > 0);
//        }
//    }
//
//    public void testRescheduleFileLogLevel() throws Exception {
//        // 初始化为ERROR
//        LogCenterConfiguration configuration = new LogCenterConfiguration();
//        configuration.defaultFileLogLevel = LogLevel.ERROR.getLogCenterLevel();
//        configuration.defaultLogcatLevel = LogLevel.ERROR.getLogCenterLevel();
//        configuration.logFilePath = "/BaiduIm/log/test/";
//        SDKOutAppLogCenter logCenter = new TestSDKOutAppLogCenter(configuration);
//        logCenter.initialize(this.getInstrumentation().getTargetContext());
//        Assert.assertEquals(LogLevel.ERROR.getLogCenterLevel(), logCenter.logCenter.getFileLogLevel());
//        logCenter.changeFileLogLevel(LogLevel.INFO, 3, null);
//        CountDownLatch countdownLatch1 = new CountDownLatch(1);
//        countdownLatch1.await(3000, TimeUnit.MILLISECONDS);
//        Assert.assertEquals(LogLevel.INFO.getLogCenterLevel(), logCenter.logCenter.getFileLogLevel());
//        logCenter.logCenter.changeTime();
//        logCenter.logCenter.changeTime();
//        logCenter.logCenter.changeTime();
//        CountDownLatch countdownLatch2 = new CountDownLatch(1);
//        countdownLatch2.await(3000, TimeUnit.MILLISECONDS);
//        Assert.assertEquals(LogLevel.ERROR.getLogCenterLevel(), logCenter.logCenter.getFileLogLevel());
//    }
//}
