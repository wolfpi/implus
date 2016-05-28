package com.baidu.im.frame.utils;

import junit.framework.Assert;
import android.test.AndroidTestCase;

public class DeviceInfoMapUtilTest extends AndroidTestCase {

    public void testDeviceInfo() {
        com.baidu.im.frame.pb.ObjDeviceInfo.DeviceInfo builder = new
                com.baidu.im.frame.pb.ObjDeviceInfo.DeviceInfo();
        DeviceInfoMapUtil.getDeviceInfo(this.getContext(), builder);
        com.baidu.im.frame.pb.ObjDeviceInfo.DeviceInfo deviceInfo = builder;
        Assert.assertNotNull(deviceInfo.getDeviceToken());
       // Assert.assertNotNull(deviceInfo.getDeviceId());
        Assert.assertNotNull(deviceInfo.getPlatform());
        Assert.assertNotNull(deviceInfo.getDeviceName());
        Assert.assertNotNull(deviceInfo.getImei());
        Assert.assertNotNull(deviceInfo.getSdCard());
    }

    public void testDeviceTypeInfo() {
        com.baidu.im.frame.pb.ObjDeviceTypeInfo.DeviceTypeInfo builder = new com.baidu.im.frame.pb.ObjDeviceTypeInfo.DeviceTypeInfo();
        DeviceInfoMapUtil.getDeviceTypeInfo(getContext(), builder);
        com.baidu.im.frame.pb.ObjDeviceTypeInfo.DeviceTypeInfo deviceTypeInfo = builder;
        // Assert.assertNotNull(deviceTypeInfo.getDeviceId());
        Assert.assertNotNull(deviceTypeInfo.getPlatform());
        Assert.assertNotNull(deviceTypeInfo.getModel());
        Assert.assertNotNull(deviceTypeInfo.getOs());
        Assert.assertNotNull(deviceTypeInfo.getNetwork());
        Assert.assertNotNull(deviceTypeInfo.getDisplay());
        Assert.assertNotNull(deviceTypeInfo.getManu());
        Assert.assertNotNull(deviceTypeInfo.getScreenSize());
    }

    public void testBUA() {
        com.baidu.im.frame.pb.ObjBua.BUA builder = new com.baidu.im.frame.pb.ObjBua.BUA();
        DeviceInfoMapUtil.getBUA(getContext(), builder);
        com.baidu.im.frame.pb.ObjBua.BUA bua = builder;
        Assert.assertNotNull(bua.getAppVer());
        Assert.assertNotNull(bua.getMem());
        Assert.assertNotNull(bua.getSdCard());
    }

    public void testBUAInfo() {
        com.baidu.im.frame.pb.ObjBua.BUAInfo builder = new com.baidu.im.frame.pb.ObjBua.BUAInfo();
        DeviceInfoMapUtil.getBUAInfo(getContext(), builder);
        com.baidu.im.frame.pb.ObjBua.BUAInfo buaInfo = builder;
        Assert.assertNotNull(buaInfo.getDeviceToken());
        Assert.assertNotNull(buaInfo.getNetwork());
        Assert.assertNotNull(buaInfo.getApn());
        Assert.assertNotNull(buaInfo.getOsVer());
        Assert.assertNotNull(buaInfo.getSdkVer());
    }
}
