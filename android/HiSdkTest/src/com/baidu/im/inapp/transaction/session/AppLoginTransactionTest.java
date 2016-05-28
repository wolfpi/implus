///**
// * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
// */
//package com.baidu.im.inapp.transaction.session;
//
//import junit.framework.Assert;
//import android.test.InstrumentationTestCase;
//import android.test.suitebuilder.annotation.LargeTest;
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
//public class AppLoginTransactionTest extends InstrumentationTestCase {
//
//    @Override
//    public void setUp() throws Exception {
//        SetUpUtil.initialize(this.getInstrumentation());
//        SetUpUtil.getRegAppReady();
//    }
//
//    public void testTransaction() {
//
//        // reset user info
//        InAppApplication.getInstance().getSession().removeUid();
//        Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//
//        InAppApplication.getInstance().getSession().removeSession();
//        Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance().getSession().getSessionId()));
//
//        AppLoginTransaction appLoginTransaction = new AppLoginTransaction();
//
//        appLoginTransaction.run();
//
//        Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance().getSession().getSessionId()));
//
//        // appLog 不会改变uid
//        Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//
//        Assert.assertTrue(InAppApplication.getInstance().getSession().getAppStatus() == EAppStatus.APP_ONLINE);
//
//    }
//
//    @LargeTest
//    public void testTransaction2() {
//        // un reg app first
//        SetUpUtil.getUnRegReady();
//        // try to app login
//        testTransaction();
//    }
//
//    @Override
//    protected void tearDown() throws Exception {
//        SetUpUtil.destroy();
//    }
//}
