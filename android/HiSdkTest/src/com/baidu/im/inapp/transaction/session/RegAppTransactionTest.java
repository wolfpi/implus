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
//import com.baidu.im.frame.utils.LogUtil;
//import com.baidu.im.testutil.SetUpUtil;
//
///**
// * @author zhaowei10
// * 
// */
//public class RegAppTransactionTest extends InstrumentationTestCase {
//
//    @Override
//    public void setUp() {
//        SetUpUtil.initialize(this.getInstrumentation());
//    }
//
//    @SmallTest
//    public void testTransaction() throws InterruptedException {
//
//        String apiKey = InAppApplication.getInstance().getSession().getApiKey();
//        Assert.assertTrue(!TextUtils.isEmpty(apiKey));
//
//        // 重置App在sdk内保存的信息
//        InAppApplication.getInstance().getSession().clear();
//
//        Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance().getSession().getSessionId()));
//
//        Assert.assertTrue(InAppApplication.getInstance().getSession().getAppId() == 0);
//
//        Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//        // 保留apiKey。
//        InAppApplication.getInstance().getSession().setApiKey(apiKey);
//
//        InAppApplication.getInstance().setConnected(false);
//        RegAppTransaction regAppTransaction = new RegAppTransaction();
//        regAppTransaction.run();
//
//        LogUtil.printMainProcess("getChannelKey   " + InAppApplication.getInstance().getSession().getChannelKey());
//        // 确保取到channelKey。
//        Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance().getSession().getChannelKey()));
//        // 确保取到了appId。
//        Assert.assertTrue(InAppApplication.getInstance().getSession().getAppId() != 0);
//
//    }
//
//    @Override
//    protected void tearDown() throws Exception {
//        SetUpUtil.destroy();
//    }
//}
