package com.baidu.imc.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.testutil.TestFacade;
import com.baidu.imc.IMPChannelSDK;

public class IMChannelSDKTest extends InstrumentationTestCase {

    public static final String TAG = "IMChannelSDK";
    
    private TestFacade mFacade = new TestFacade();
    
    @Override
    protected void setUp() {
      
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testNormalSDK() {
	   IMPChannelSDK.init(mFacade.getAppkey(), this.getInstrumentation().getContext());
	   IMPChannelSDK.init(mFacade.getAppkey(), this.getInstrumentation().getContext());
	   IMPChannelSDK.init(mFacade.getAppkey(), this.getInstrumentation().getContext());
	   Assert.assertTrue( IMPChannelSDK.getPushClient() != null);
   }
   
   public void testAbNormalSDKInvalid() {
	   
	   IMPChannelSDK.init("", this.getInstrumentation().getContext());
	   Assert.assertTrue( IMPChannelSDK.getPushClient() == null);
	   
	   IMPChannelSDK.init("", null);
	   Assert.assertTrue( IMPChannelSDK.getPushClient() == null);
	   
	   IMPChannelSDK.init(null, null);
	   Assert.assertTrue( IMPChannelSDK.getPushClient() == null);
   }
   
   public void testAbNormalSDK() {
	   Assert.assertTrue( IMPChannelSDK.getPushClient() == null);
   }
}
