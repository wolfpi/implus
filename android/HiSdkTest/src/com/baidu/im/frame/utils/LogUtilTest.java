package com.baidu.im.frame.utils;

import android.test.AndroidTestCase;

public class LogUtilTest extends AndroidTestCase {
    private final static String TESTLOG = "TESTLOG";

    public void testFError() {
       // LogUtil.fError(Thread.currentThread(), new Exception(TESTLOG + " Test log for exception printing."));
    }

    public void testFProtocol() {
       // LogUtil.printIm(msg);(TESTLOG + " Test log for protocol printing.");
    }

    public void testPrintMainprocess() {
        LogUtil.printMainProcess(TESTLOG + " Test log for mainprogress log printing.");
        LogUtil.printMainProcess(TESTLOG, "Test log with tag for mainprogress log printing.");
    }

    public void testDebug() {
        LogUtil.printDebug(TESTLOG, "Test log for debug log printing.");
    }

    public void testE() {
        LogUtil.e(TESTLOG, "Test log for e text log printing.");
        LogUtil.e(TESTLOG, new Exception("Test log for e exception log printing."));
        LogUtil.e(TESTLOG, "Test log for e text log with exception printing.", new Exception(
                "Test log for e exception with text log printing."));
    }

    public void testW() {
        LogUtil.w(TESTLOG, "Test log for w text log printing.");
    }

    public void testI() {
        LogUtil.i(TESTLOG, "Test log for i text log printing.");
    }

    public void testD() {
        LogUtil.d(TESTLOG, "Test log for d text log printing.");
    }

    public void testStat() {
        LogUtil.printStat("{\"rcode\":0,\"action\":\""
                + TESTLOG
                + "\",\"protocol\":[{\"rcode\":0,\"id\":1101430888,\"time\":"
                + "1427959208776,\"cost\":114,\"name\":\"Heartbeat\"}],\"time\":1427959208776"
                + ",\"client\":{\"platform\":\"android\",\"token\":\"866022020747850\",\"sdkver\":\"20\",\"mem\":\"65\",\"appid\":"
                + "\"1\",\"osver\":\"4.4.4\",\"apn\":\"4\",\"type\":\"Lenovo K30-T\",\"sdcard\":\"72\",\"network\":\"2\",\"appver\":"
                + "\"20\"},\"cost\":114,\"key\":1100809192}");
    }
}
