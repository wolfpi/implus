package com.baidu.im.frame.utils.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.constant.Constant.EChannelType;
import com.baidu.im.frame.utils.ConfigUtil;

public class ConfigUtilTest extends InstrumentationTestCase {

    public static final String TAG = "ConfigUtilTest";
    
    //private TestFacade mTestFacade = new TestFacade();
    //private final String mTextMsg = "InboxKeyTest";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testGenerateConfig() {
	   ConfigUtil.load();
	   
	   Assert.assertTrue(ConfigUtil.getChannelType() == EChannelType.imPlatform);
   }
   
   public void testGetPassportParams() {
   }
   
}