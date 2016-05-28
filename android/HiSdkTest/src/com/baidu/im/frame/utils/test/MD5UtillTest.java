package com.baidu.im.frame.utils.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.utils.MD5Util;

public class MD5UtillTest extends InstrumentationTestCase {

    public static final String TAG = "MD5UtillTest";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testMarshUnmarsh() {
	 
       Assert.assertTrue(MD5Util.getMD5(null) == null);
       Assert.assertTrue(MD5Util.getMD5("test").equalsIgnoreCase("098f6bcd4621d373cade4e832627b4f6"));
       Assert.assertTrue(MD5Util.getMD5("") == null);
   }
   
}