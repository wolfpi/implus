package com.baidu.im.frame.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.baidu.im.frame.SequenceDispatcher;
import com.baidu.im.frame.SequenceListener;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.mock.pb.DownPacketCenter;

public class SequenceDispatcherTest extends InstrumentationTestCase {

    public static final String TAG = "SequenceDispatcherTest";
    private DownPacketCenter mDpc = null;
    
    @Override
    protected void setUp() {
      mDpc = new DownPacketCenter(this.getInstrumentation().getContext());
    }
   @Override
   public void tearDown() {
     
   }
   
   public void testNormalSeqDispatcher() {
	 SequenceDispatcher sd= new SequenceDispatcher();
	 dummySequenceListener ds = new dummySequenceListener();
	 sd.register(ds);
	 DownPacket dp = mDpc.getDownPacketAppLogin();
	 Log.e("testNornal", String.valueOf(dp.getSeq()));
	 boolean dr = sd.dispatch(dp);
	 Assert.assertTrue(dr);
	 sd.unRegister(ds);
	  
	 Assert.assertFalse(sd.dispatch(dp));
   }
   
   public void testAbNormalSeqDispatcher() {
		 SequenceDispatcher sd= new SequenceDispatcher();
		 sd.register(null);
		 Assert.assertFalse(sd.dispatch(null));
		 sd.unRegister(null);
		 Assert.assertTrue(true);
	}

}

class dummySequenceListener implements SequenceListener
{
	@Override
	public boolean onReceive(DownPacket downPacket) {
		Assert.assertTrue(true);
		return true;
	}

	@Override
	public int getListenSeq() {
		return 1146;
	}
	}