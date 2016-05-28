package com.baidu.imc.impl.im.client.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.imc.impl.im.client.ResourceManager;


public class ResourceManagerTest extends InstrumentationTestCase {

    public static final String TAG = "IMPlusSDKTest";
    
   // private TestFacade mFacade = new TestFacade();
    
    @Override
    protected void setUp() {
      
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testNormalDefault() {
	  Assert.assertTrue(ResourceManager.getInstance().getLocalResourceManager() == null);
	  Assert.assertTrue(ResourceManager.getInstance().getRemoteResourceManager() == null);
	  
	  ResourceManager.getInstance().setLocalResourceManager(null);
	  ResourceManager.getInstance().setRemoteResourceManager(null);
	  Assert.assertTrue(ResourceManager.getInstance().getLocalResourceManager() == null);
	  Assert.assertTrue(ResourceManager.getInstance().getRemoteResourceManager() == null);
   }
}
