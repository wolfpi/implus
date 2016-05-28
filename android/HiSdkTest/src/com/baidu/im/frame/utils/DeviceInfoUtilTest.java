package com.baidu.im.frame.utils;

import android.test.AndroidTestCase;

public class DeviceInfoUtilTest extends AndroidTestCase {

    public void testPrintBuild() {
        DeviceInfoUtil.printBuild();
    }

    public void testPrintTelephonyInfo() {
        DeviceInfoUtil.printTelephonyInfo(getContext());
    }
}
