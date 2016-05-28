///**
// * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
// */
//package com.baidu.im.inapp.transaction.session;
//
//import android.test.InstrumentationTestCase;
//
//import com.baidu.im.testutil.SetUpUtil;
//
///**
// * @author zhaowei10
// * 
// */
//public class HeartbeatTransactionTest extends InstrumentationTestCase {
//
//	@Override
//	public void setUp() throws Exception {
//		SetUpUtil.initialize(this.getInstrumentation());
//		SetUpUtil.getRegAppReady();
//	}
//
//	public void testTransaction() {
//
//		HeartbeatTransaction appLoginTransaction = new HeartbeatTransaction();
//
//		appLoginTransaction.run();
//
//	}
//
//	@Override
//	protected void tearDown() throws Exception {
//		SetUpUtil.destroy();
//	}
//}
