package com.baidu.im.frame.utils.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.utils.NetworkUtil;
import com.baidu.im.frame.utils.NetworkUtil.NetworkType;

public class NetworkUtilTest extends InstrumentationTestCase {

    public static final String TAG = "NetworkUtilTest";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testMarshUnmarsh() {
	 
       Assert.assertTrue(NetworkUtil.getNetworkType(null) == NetworkType.unknown);
       Assert.assertTrue(NetworkUtil.isNetworkConnected(null) == false);
   }
   
}