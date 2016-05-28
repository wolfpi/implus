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
//public class UnRegAppTransactionTest extends InstrumentationTestCase {
//
//    @Override
//    public void setUp() throws Exception {
//        SetUpUtil.initialize(this.getInstrumentation());
//        SetUpUtil.getUserLoginReady(UserEnum.imrd_333);
//    }
//
//    @SmallTest
//    public void testTransaction() {
//
//        Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance().getSession().getApiKey()));
//
//        Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance().getSession().getChannelKey()));
//
//        Assert.assertTrue(InAppApplication.getInstance().getSession().getAppId() != 0);
//
//        UnRegAppTransaction unRegAppTransaction = new UnRegAppTransaction();
//        unRegAppTransaction.run();
//
//        Assert.assertTrue(InAppApplication.getInstance().getSession().getAppId() == 0);
//
//    }
//
//    @Override
//    protected void tearDown() throws Exception {
//        SetUpUtil.destroy();
//    }
//}
