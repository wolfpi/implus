package com.baidu.im.frame.utils;

import org.json.JSONObject;

import junit.framework.Assert;
import android.test.AndroidTestCase;

public class DynamicConfigUtilTest extends AndroidTestCase {
    public void test() throws Exception {
        String LOG_MD5 = "123";
        String LOG_CONFIG = "{logcatLevel:4, fileLogLevel:8}";
        String SERVER_MD5 = "456";
        String SERVER_CONFIG = "{ip:4, port:8}";
       // PreferenceUtil.initialize(this.getContext());
        DynamicConfigUtil.clearConfig();
        DynamicConfigUtil.setConfig(DynamicConfigUtil.CONFIG_LOG, LOG_MD5, LOG_CONFIG);
        DynamicConfigUtil.setConfig(DynamicConfigUtil.CONFIG_SERVER, SERVER_MD5, SERVER_CONFIG);
        Assert.assertEquals(LOG_MD5, DynamicConfigUtil.getConfigMD5(DynamicConfigUtil.CONFIG_LOG));
        Assert.assertEquals(SERVER_MD5, DynamicConfigUtil.getConfigMD5(DynamicConfigUtil.CONFIG_SERVER));
        Assert.assertEquals(new JSONObject(LOG_CONFIG).toString(),
                DynamicConfigUtil.getConfig(DynamicConfigUtil.CONFIG_LOG));
        Assert.assertEquals(new JSONObject(SERVER_CONFIG).toString(),
                DynamicConfigUtil.getConfig(DynamicConfigUtil.CONFIG_SERVER));
        long timestamp = System.currentTimeMillis();
        DynamicConfigUtil.setConfigTimestamp(timestamp);
        Assert.assertEquals(timestamp, DynamicConfigUtil.getConfigTimestamp());
    }

}
