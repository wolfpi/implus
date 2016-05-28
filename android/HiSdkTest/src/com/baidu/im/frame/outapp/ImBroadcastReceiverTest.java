package com.baidu.im.frame.outapp;

import android.test.AndroidTestCase;

public class ImBroadcastReceiverTest extends AndroidTestCase {

public static final String TAG = "ImBroadcastReceiverTest";
    
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testReceive() {
	   
	   ImBroadcastReceiver recv = new ImBroadcastReceiver();
	   
	   recv.onReceive(null, null);
	   recv.onReceive(this.getContext(), null);
   }
}
