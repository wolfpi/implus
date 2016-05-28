package com.baidu.imc.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;
import com.baidu.im.testutil.TestFacade;
import com.baidu.imc.IMPlusSDK;

public class IMPlusSDKTest extends InstrumentationTestCase {

    public static final String TAG = "IMPlusSDKTest";
    
    private TestFacade mFacade = new TestFacade();
    
    @Override
    protected void setUp() {
      
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testNormalSDK() {
	   IMPlusSDK.init(mFacade.getAppkey(), this.getInstrumentation().getContext());
	   IMPlusSDK.init(mFacade.getAppkey(), this.getInstrumentation().getContext());
	   IMPlusSDK.init(mFacade.getAppkey(), this.getInstrumentation().getContext());
	   Assert.assertTrue( IMPlusSDK.getImpClient() != null);
   }
   
   public void testAbNormalSDKInvalid() {
	   
	   IMPlusSDK.init("", this.getInstrumentation().getContext());
	   Assert.assertTrue( IMPlusSDK.getImpClient() == null);
	   
	   IMPlusSDK.init("", null);
	   Assert.assertTrue( IMPlusSDK.getImpClient() == null);
	   
	   IMPlusSDK.init(null, null);
	   Assert.assertTrue( IMPlusSDK.getImpClient() == null);
   }
   
   public void testAbNormalSDK() {
	   Assert.assertTrue( IMPlusSDK.getImpClient() == null);
   }
}
