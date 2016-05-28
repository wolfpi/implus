///**
// * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
// */
//package com.baidu.im.inapp.transaction.message;
//
//import junit.framework.Assert;
//import android.test.InstrumentationTestCase;
//
//import com.baidu.im.frame.ProcessorCode;
//import com.baidu.im.sdk.BinaryMessage;
//import com.baidu.im.testutil.MockUser.UserEnum;
//import com.baidu.im.testutil.SetUpUtil;
//import com.baidu.im.testutil.Sleeper;
//
///**
// * @author zhaowei10
// * 
// */
//public class SendMessageTransactionTest extends InstrumentationTestCase {
//
//    @Override
//    public void setUp() throws Exception {
//        SetUpUtil.initialize(this.getInstrumentation());
//        SetUpUtil.getUserLoginReady(UserEnum.imrd_333);
//        Sleeper.waitWithoutBlock(2000);
//    }
//
//    public void testTransaction() throws Exception {
//
//        String message = "This is a message from Unit Test. timestamp=" + System.currentTimeMillis();
//
//        String appMessage =
//                new String("msg 1.3 R 1\r\nfrom:" + 40000333 + "\r\nto:" + 40000333
//                        + "\r\nmethod:msg_request\r\n\r\n<msg>" + message + "</msg>");
//
//        BinaryMessage binaryMessage = new BinaryMessage();
//        binaryMessage.setData(appMessage.getBytes());
//
//        SendMessageTransaction channelReconnectTransaction = new SendMessageTransaction(binaryMessage);
//
//        Assert.assertTrue(channelReconnectTransaction.startWorkFlow() == ProcessorCode.SUCCESS);
//
//    }
//
//    @Override
//    protected void tearDown() throws Exception {
//        SetUpUtil.destroy();
//    }
//}
