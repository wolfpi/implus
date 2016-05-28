package com.baidu.im.frame.test;

import junit.framework.Assert;
import android.os.Handler;
import android.os.Looper;
import android.os.Messenger;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.SeqDispatcher2InApp;

public class SeqDispatcher2InAppTest extends InstrumentationTestCase {

    public static final String TAG = "SeqDispatcher2InAppTest";
    
    @Override
    protected void setUp() {
      
    }
   @Override
   public void tearDown() {
     
   }
   
   public void testNormalSeqDispatcher2() {
	   SeqDispatcher2InApp dispatch = new SeqDispatcher2InApp();
	   Messenger msger = new Messenger(new Handler(Looper.getMainLooper()) {});
	   dispatch.addSeqDispatch(0, msger);
	   Assert.assertTrue(dispatch.getMessenger(0) != null);
   }
   
   public void testAbNormalSeqDispatcher2() {
	   SeqDispatcher2InApp dispatch = new SeqDispatcher2InApp();
	   dispatch.addSeqDispatch(0, null);
	   Assert.assertTrue(dispatch.getMessenger(0) == null);
   }
   public void testAbNormalIntergerSeqDispatcher2() {
	   SeqDispatcher2InApp dispatch = new SeqDispatcher2InApp();
	   Messenger msger = new Messenger(new Handler(Looper.getMainLooper()) {});
	   dispatch.addSeqDispatch(Integer.MAX_VALUE, msger);
	   Assert.assertTrue(dispatch.getMessenger(Integer.MAX_VALUE) == msger);
	   
	   dispatch.addSeqDispatch(Integer.MIN_VALUE, msger);
	   Assert.assertTrue(dispatch.getMessenger(Integer.MIN_VALUE) == msger);
   }

}
