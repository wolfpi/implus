package com.baidu.im.frame.outapp;

import java.util.Collection;
import java.util.Iterator;

import com.baidu.im.frame.MessageDataEnum;
import com.baidu.im.frame.utils.LogUtil;

import junit.framework.Assert;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.test.AndroidTestCase;

public class AppMapTest extends AndroidTestCase {

public static final String TAG = "AppMapTest";
    
    //private TestFacade mTestFacade = new TestFacade();
    //private final String mTextMsg = "InboxKeyTest";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   final static Handler handler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message message) {
			LogUtil.printMainProcess("Dummy message="
					+ message);
		}
	};
   
   public void testInvalidaddSeq2AppKey() {
	   AppMap appmap = new AppMap(null);
	   appmap.addSeq2AppKey(null, "appkey", null);
	   Assert.assertTrue(appmap.getAllMessengers() == null);
	   Assert.assertTrue(appmap.getPreferenceByID(null) == null);
   }
   
   public void testvalidaddSeq2AppKey() {
	   AppMap appmap = new AppMap(this.getContext());
	   Messenger msger = new Messenger(handler);
	   appmap.addSeq2AppKey("123", "appkey",msger);
       Collection<Messenger> msgersUnRegs = appmap.getAllMessengers();
       if (msgersUnRegs != null) {
           Iterator it = msgersUnRegs.iterator(); // 获得一个迭代子
           while (it.hasNext()) {
               Messenger clientMsger = (Messenger) it.next(); // 得到下一个元素
               Assert.assertTrue(clientMsger == msger);
           }
       } else
       {
    	   Assert.assertTrue(false);
       }
       
       appmap.addAppId("appkey", 123);
	   Assert.assertTrue(appmap.getPreferenceByID("123") != null);
	   
	   Assert.assertTrue(appmap.getSeqPreference() != null);
	   appmap.removeAppid("123"); 
   }
}
