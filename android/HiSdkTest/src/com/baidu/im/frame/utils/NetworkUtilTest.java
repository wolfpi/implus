package com.baidu.im.frame.utils;

import android.test.AndroidTestCase;

/**
 * 网络状态获取
 * 
 * @author zhaowei10
 * 
 */
public class NetworkUtilTest extends AndroidTestCase {

    public void testNetworkUtil() {
        NetworkUtil.getNetworkType(getContext());
        NetworkUtil.isNetworkConnected(getContext());
    }
}
