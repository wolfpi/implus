package com.baidu.im.inapp.model;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import android.test.MoreAsserts;

import com.baidu.im.frame.utils.ParcelableUtil;
import com.baidu.im.sdk.BinaryMessage;

public class BinaryMessageTest extends AndroidTestCase {

    public void testBinaryMessage() {

        BinaryMessage message = new BinaryMessage();
        message.setData(new String("data").getBytes());
        message.setMethodName("methodname");
        message.setServiceName("servicename");

        byte[] data = ParcelableUtil.marshall(message);
        BinaryMessage newModel = ParcelableUtil.unmarshall(data, BinaryMessage.CREATOR);

        Assert.assertNotNull(newModel);
        MoreAsserts.assertEquals(newModel.getData(), message.getData());
        Assert.assertEquals(newModel.getServiceName(), message.getServiceName());
        Assert.assertEquals(newModel.getMethodName(), message.getMethodName());

    }
}
