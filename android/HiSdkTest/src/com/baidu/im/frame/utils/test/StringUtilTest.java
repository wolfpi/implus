package com.baidu.im.frame.utils.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.utils.StringUtil;

public class StringUtilTest extends InstrumentationTestCase {

    public static final String TAG = "StringUtilTest";
    
    //private TestFacade mTestFacade = new TestFacade();
    //private final String mTextMsg = "InboxKeyTest";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testisStringInValid() {
	   Assert.assertTrue(StringUtil.isStringInValid(null));
	   Assert.assertTrue(StringUtil.isStringInValid(""));
	   Assert.assertFalse(StringUtil.isStringInValid("test"));
	   
   }
   
   public void testStringToMD5()
   {
	   Assert.assertTrue(StringUtil.StringToMD5(null) == null);
	   Assert.assertTrue(StringUtil.StringToMD5("test").equalsIgnoreCase("098f6bcd4621d373cade4e832627b4f6"));
   }
   
}