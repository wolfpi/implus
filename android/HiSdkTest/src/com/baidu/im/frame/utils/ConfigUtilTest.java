package com.baidu.im.frame.utils;

import junit.framework.Assert;
import android.test.AndroidTestCase;

public class ConfigUtilTest extends AndroidTestCase {

    public void testLoad() {
        ConfigUtil.load();
    }

    public void testGetChannelType() {
        ConfigUtil.load();
        Assert.assertNotNull(ConfigUtil.getChannelType());
    }

    public void testGetHostIp() {
        ConfigUtil.load();
        Assert.assertNotNull(ConfigUtil.getHostIp());
    }

    public void testGetPort() {
        ConfigUtil.load();
        Assert.assertNotNull(ConfigUtil.getPort());
    }
}
