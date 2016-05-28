package com.baidu.im.frame.utils.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.utils.ParcelableUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.imc.impl.im.util.StringUtil;


public class ParcelableUtilTest extends InstrumentationTestCase {

    public static final String TAG = "SignatureUtillTest";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testMarshUnmarsh() {
	   BinaryMessage message = new BinaryMessage();
       message.setData(new String("data").getBytes());
       message.setMethodName("methodname");
       message.setServiceName("servicename");

       Assert.assertTrue(ParcelableUtil.marshall(null) == null);
       byte[] data = ParcelableUtil.marshall(message);
       Assert.assertTrue(data != null);
       
       Assert.assertTrue(ParcelableUtil.unmarshall(null,null) == null);
       Assert.assertTrue(ParcelableUtil.unmarshall(null,BinaryMessage.CREATOR) == null);
       Assert.assertTrue(ParcelableUtil.unmarshall(data, null) == null);
       
       BinaryMessage newModel = ParcelableUtil.unmarshall(data, BinaryMessage.CREATOR);
       
       Assert.assertTrue(newModel.getServiceName().equalsIgnoreCase("servicename"));
       Assert.assertTrue(newModel.getMethodName().equalsIgnoreCase("methodname"));
       Assert.assertTrue(StringUtil.bytesToString(newModel.getData()).equalsIgnoreCase("data"));
       
   }
   
}