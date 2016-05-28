///**
// * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
// */
//package com.baidu.im.inapp.transaction.session;
//
//import junit.framework.Assert;
//import android.test.InstrumentationTestCase;
//import android.test.suitebuilder.annotation.LargeTest;
//import android.test.suitebuilder.annotation.MediumTest;
//import android.test.suitebuilder.annotation.SmallTest;
//import android.text.TextUtils;
//
//import com.baidu.im.frame.inapp.InAppApplication;
//import com.baidu.im.frame.pb.EnumAppStatus.EAppStatus;
//import com.baidu.im.sdk.LoginMessage;
//import com.baidu.im.testutil.MockUser.UserEnum;
//import com.baidu.im.testutil.SetUpUtil;
//
///**
// * @author zhaowei10
// * 
// */
//public class UserLoginTransactionTest extends InstrumentationTestCase {
//
//	UserEnum user = UserEnum.imrd_333;
//
//	public UserLoginTransactionTest() {
//	}
//
//	public UserLoginTransactionTest(UserEnum userEnum) {
//		this.user = userEnum;
//	}
//
//	@Override
//	public void setUp() throws Exception {
//		SetUpUtil.initialize(this.getInstrumentation());
//		SetUpUtil.getRegAppReady();
//	}
//
//	/**
//	 * login with account and password
//	 */
//	@SmallTest
//	public void testTransaction() {
//
//		// reset user info
//		InAppApplication.getInstance().getSession().removeSession();
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		InAppApplication.getInstance().getSession().removeUid();
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//
//		// login
//		LoginMessage loginMessage = new LoginMessage();
//		loginMessage.setAccount(user.name());
//		loginMessage.setPassword(user.getPassword());
//		UserLoginTransaction userLoginTransaction = new UserLoginTransaction(
//				loginMessage);
//		userLoginTransaction.run();
//
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() != 0);
//
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppStatus() == EAppStatus.APP_ONLINE);
//	}
//
//	/**
//	 * login with duss
//	 */
//	@MediumTest
//	public void testLoginWithBduss() {
//
//		// reset user info
//		InAppApplication.getInstance().getSession().removeSession();
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		InAppApplication.getInstance().getSession().removeUid();
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//
//		String bduss = SetUpUtil.getBduss();
//		Assert.assertTrue(!TextUtils.isEmpty(bduss));
//
//		// login
//		LoginMessage loginMessage = new LoginMessage();
//		loginMessage.setBduss(bduss);
//		UserLoginTransaction userLoginTransaction = new UserLoginTransaction(
//				loginMessage);
//		userLoginTransaction.run();
//
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() != 0);
//
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppStatus() == EAppStatus.APP_ONLINE);
//	}
//
//	@LargeTest
//	public void testTransaction2() {
//		// un reg app first
//		SetUpUtil.getUnRegReady();
//		// try to app login
//		testTransaction();
//	}
//
//	@Override
//	protected void tearDown() throws Exception {
//		SetUpUtil.destroy();
//	}
//}
