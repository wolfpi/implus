package com.baidu.im.frame.utils;

import junit.framework.Assert;
import android.test.AndroidTestCase;

import com.baidu.im.sdk.BinaryMessage;

public class ParcelableUtilTest extends AndroidTestCase {

    public void testParcelable() {
        BinaryMessage binaryMessage = new BinaryMessage();
        binaryMessage.setData(new byte[] { '1', '2', '3' });
        binaryMessage.setMethodName("methodName");
        binaryMessage.setServiceName("serviceName");
        byte[] in = ParcelableUtil.marshall(binaryMessage);

        BinaryMessage out = ParcelableUtil.unmarshall(in, BinaryMessage.CREATOR);

        Assert.assertTrue(binaryMessage.getMethodName().equals(out.getMethodName()));
        Assert.assertTrue(binaryMessage.getServiceName().equals(out.getServiceName()));
        Assert.assertTrue(new String(binaryMessage.getData()).equals(new String(out.getData())));
    }
}
