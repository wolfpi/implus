package com.baidu.im.frame.utils;

import android.test.AndroidTestCase;

/**
 * 网络状态获取
 * 
 * @author zhaowei10
 * 
 */
public class Md5UtilTest extends AndroidTestCase {

    public void testNetworkUtil() {
        LogUtil.e("Md5UtilTest",
                MD5Util.getMD5("B5:06:69:28:8C:1D:B7:FB:4B:A3:8F:1F:47:91:36:22:A9:93:B9:E1;com.baidu.durobot.mate"));

    }
}
