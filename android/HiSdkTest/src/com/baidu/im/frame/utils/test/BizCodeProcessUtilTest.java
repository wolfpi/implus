package com.baidu.im.frame.utils.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.utils.BizCodeProcessUtil;
import com.baidu.im.frame.utils.BizCodeProcessUtil.ChannelCode;
import com.baidu.im.mock.pb.DownPacketAppLogout;

public class BizCodeProcessUtilTest extends InstrumentationTestCase {

    public static final String TAG = "BizCodeProcessUtilTest";
    
    //private TestFacade mTestFacade = new TestFacade();
    //private final String mTextMsg = "InboxKeyTest";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testprocChannelCode() {
	   Assert.assertTrue(BizCodeProcessUtil.procChannelCode(null) == ProcessorCode.CHANNEL_SERVER_ERROR);
	   Assert.assertTrue(BizCodeProcessUtil.procChannelCode(ChannelCode.CHANNEL_AUTHENTICATION_ERROR) == ProcessorCode.SESSION_ERROR);
	   Assert.assertTrue(BizCodeProcessUtil.procChannelCode(ChannelCode.CHANNEL_DISPATCH_ERROR) == ProcessorCode.CHANNEL_DISPATCH_ERROR);
	   Assert.assertTrue(BizCodeProcessUtil.procChannelCode(ChannelCode.CHANNEL_SUCCESS) == ProcessorCode.SUCCESS);
   }
   
   public void testprocBizCode() {
	  BizCodeProcessUtil.processBizCode(0);
   }
   
   public void testprocProcessorCode() {
	   
	   DownPacket dp = DownPacketAppLogout.getSuccess(this.getInstrumentation().getContext());
	   Assert.assertTrue(BizCodeProcessUtil.procProcessorCode(null,null) == ProcessorCode.UNKNOWN_ERROR );
	   Assert.assertTrue(BizCodeProcessUtil.procProcessorCode(null,dp) == ProcessorCode.SUCCESS);
	   Assert.assertTrue(BizCodeProcessUtil.procProcessorCode("test",dp) == ProcessorCode.SUCCESS);
	}
}