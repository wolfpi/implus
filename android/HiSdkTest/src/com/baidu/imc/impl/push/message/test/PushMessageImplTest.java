package com.baidu.imc.impl.push.message.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.imc.impl.push.message.PushMessageImpl;
public class PushMessageImplTest extends InstrumentationTestCase {

    public static final String TAG = "PushMessageImplTest";
    
    //private TestFacade mTestFacade = new TestFacade();
    private final String mTextMsg = "test";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testPushMsg() {
	   PushMessageImpl pushMsg = new PushMessageImpl();
	   Assert.assertTrue(pushMsg.getMessage() == null);
	   Assert.assertTrue(pushMsg.getNotification() == null);
	   pushMsg.setMessage(mTextMsg);
	   Assert.assertTrue(pushMsg.getMessage().equals(mTextMsg));
   }
}