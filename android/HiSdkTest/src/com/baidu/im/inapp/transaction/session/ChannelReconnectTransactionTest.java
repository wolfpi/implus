///**
// * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
// */
//package com.baidu.im.inapp.transaction.session;
//
//import junit.framework.Assert;
//import android.test.InstrumentationTestCase;
//import android.text.TextUtils;
//
//import com.baidu.im.frame.inapp.InAppApplication;
//import com.baidu.im.inapp.transaction.session.ChannelReconnectTransaction;
//import com.baidu.im.testutil.SetUpUtil;
//
///**
// * @author zhaowei10
// * 
// */
//public class ChannelReconnectTransactionTest extends InstrumentationTestCase {
//
//	@Override
//	public void setUp() throws Exception {
//		SetUpUtil.initialize(this.getInstrumentation());
//	}
//
//	public void testTransaction() throws InterruptedException {
//
//		// channelKey不为空
//		String channelKey = InAppApplication.getInstance().getSession()
//				.getChannelKey();
//		Assert.assertTrue(!TextUtils.isEmpty(channelKey));
//
//		ChannelReconnectTransaction channelReconnectTransaction = new ChannelReconnectTransaction();
//
//		channelReconnectTransaction.run();
//
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getChannelKey()));
//
//		Assert.assertEquals(channelKey, InAppApplication.getInstance()
//				.getSession().getChannelKey());
//	}
//
//	@Override
//	protected void tearDown() throws Exception {
//		SetUpUtil.destroy();
//	}
//}
