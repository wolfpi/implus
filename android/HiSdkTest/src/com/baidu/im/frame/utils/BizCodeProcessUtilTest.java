package com.baidu.im.frame.utils;

import junit.framework.Assert;
import android.test.AndroidTestCase;

import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.utils.BizCodeProcessUtil.ChannelCode;

public class BizCodeProcessUtilTest extends AndroidTestCase {
	public void testProcBizCode() {
		//Assert.assertEquals(ProcessorCode.SESSION_ERROR,
				//BizCodeProcessUtil.procBizCode(10120));
	}

	public void testProcessChannelCode() {
		Assert.assertEquals(ProcessorCode.CHANNEL_DISPATCH_ERROR,
				BizCodeProcessUtil.procChannelCode(ChannelCode.CHANNEL_DISPATCH_ERROR));
	}

	public void testProcessBizCode() {
		Assert.assertEquals(ProcessorCode.UNKNOWN_ERROR.getCode(),
				BizCodeProcessUtil.processBizCode(Integer.MAX_VALUE));
	}
}
