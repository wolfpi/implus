package com.baidu.im.frame.outapp;

import java.io.IOException;

import junit.framework.Assert;
import android.test.AndroidTestCase;

import com.baidu.im.frame.utils.PreferenceUtil;

public class NetworkLayerTest extends AndroidTestCase {

public static final String TAG = "NetworkLayerTest";
    
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   
   public void testNetworkLayer() {
	   try
	   {
		   NetworkLayer nw = new NetworkLayer(null,null);
	   } catch(Exception e)
	   {
		   Assert.assertTrue(true);
		   return;
	   }
	   Assert.assertTrue(false);
   }
   
   public void testWholeNetwork() {
	   
	   PreferenceUtil pref = new PreferenceUtil();
	   pref.initialize(this.getContext(), "apiKey");
	   
	   try {
		NetworkLayer nw = new NetworkLayer(null,null);
		nw.send(null);
		nw.sendReconnect();
		nw.getSeq();
		nw.getNetworkChannel();
		nw.heartbeat();
		nw.dumpChannel();
		nw.onReceive(null);
		nw.saveProtocolFile(null);
		
	   } catch (Exception e) {
		//Assert.assertTrue(false);
		e.printStackTrace();
	}
	  
   }
}
