package com.baidu.imc.impl.push.message.test;

import android.content.Intent;
import android.test.InstrumentationTestCase;

//import com.baidu.imc.impl.push.message.PushMessageBroadcastReceiver;

public class PushMessageBroadcastReceiverTest extends InstrumentationTestCase {

    public static final String TAG = "PushMessageImplTest";
    
    //private TestFacade mTestFacade = new TestFacade();
    //private final String mTextMsg = "test";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testInvalidBroadcase() {
	   
	   Intent in = new Intent();
	   in.putExtra("messageId", "12");
	   /*
	   //PushMessageBroadcastReceiver pushMsgReceiver = new PushMessageBroadcastReceiver();
	   pushMsgReceiver.setListener(null);
	   
	   pushMsgReceiver.onReceive(null, null);
	   pushMsgReceiver.onReceive(this.getInstrumentation().getContext(), null);
	   pushMsgReceiver.onReceive(null, in);
	   pushMsgReceiver.onReceive(this.getInstrumentation().getContext(), in);*/
   }
}