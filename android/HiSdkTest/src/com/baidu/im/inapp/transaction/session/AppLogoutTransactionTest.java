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
//import com.baidu.im.frame.pb.EnumAppStatus.EAppStatus;
//import com.baidu.im.testutil.SetUpUtil;
//
///**
// * @author zhaowei10
// * 
// */
//public class AppLogoutTransactionTest extends InstrumentationTestCase {
//
//	@Override
//	public void setUp() throws Exception {
//		SetUpUtil.initialize(this.getInstrumentation());
//		SetUpUtil.getAppLoginReady();
//	}
//
//	public void testTransaction() {
//
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppStatus() == EAppStatus.APP_ONLINE);
//
//		AppLogoutTransaction appLogoutTransaction = new AppLogoutTransaction();
//
//		appLogoutTransaction.run();
//
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppStatus() == EAppStatus.APP_NULL);
//	}
//
//	@Override
//	protected void tearDown() throws Exception {
//		SetUpUtil.destroy();
//	}
//}
