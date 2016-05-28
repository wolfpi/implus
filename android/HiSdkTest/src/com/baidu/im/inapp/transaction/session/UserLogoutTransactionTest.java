///**
// * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
// */
//package com.baidu.im.inapp.transaction.session;
//
//import junit.framework.Assert;
//import android.test.InstrumentationTestCase;
//import android.test.suitebuilder.annotation.SmallTest;
//import android.text.TextUtils;
//
//import com.baidu.im.frame.inapp.InAppApplication;
//import com.baidu.im.testutil.MockUser.UserEnum;
//import com.baidu.im.testutil.SetUpUtil;
//
///**
// * @author zhaowei10
// * 
// */
//public class UserLogoutTransactionTest extends InstrumentationTestCase {
//
//	private static final UserEnum user = UserEnum.imrd_333;
//
//	@Override
//	public void setUp() throws Exception {
//		SetUpUtil.initialize(this.getInstrumentation());
//		SetUpUtil.getUserLoginReady(user);
//	}
//
//	/**
//	 * login with account and password
//	 */
//	@SmallTest
//	public void testTransaction() {
//
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getBduss()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() != 0);
//
//		// login
//		UserLogoutTransaction userLogoutTransaction = new UserLogoutTransaction();
//		userLogoutTransaction.run();
//
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getBduss()));
//	}
//
//	@Override
//	protected void tearDown() throws Exception {
//		SetUpUtil.destroy();
//	}
//}
