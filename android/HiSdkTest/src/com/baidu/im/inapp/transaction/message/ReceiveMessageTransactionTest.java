///**
// * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
// */
//package com.baidu.im.inapp.transaction.message;
//
//import junit.framework.Assert;
//import android.test.InstrumentationTestCase;
//
//import com.baidu.im.frame.ProcessorCode;
//import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
//import com.baidu.im.sdk.BinaryMessage;
//import com.baidu.im.sdk.ChannelSdk;
//import com.baidu.im.testutil.MockDownPacket;
//import com.baidu.im.testutil.MockUser.UserEnum;
//import com.baidu.im.testutil.SetUpUtil;
//
///**
// * @author zhaowei10
// * 
// */
//public class ReceiveMessageTransactionTest extends InstrumentationTestCase {
//
//    @Override
//    public void setUp() throws Exception {
//        SetUpUtil.initialize(this.getInstrumentation());
//        SetUpUtil.getUserLoginReady(UserEnum.imrd_333);
//    }
//
//    public void testSysNotifyMsgTransaction() throws Exception {
//
//        DownPacket downPacket = MockDownPacket.mockOneOnlineSysNotifyDownPacket();
//
//        ReceiveMessageTransaction transaction = new ReceiveMessageTransaction(downPacket);
//
//        Assert.assertTrue(transaction.startWorkFlow() == ProcessorCode.SUCCESS);
//
//    }
//
//    public void testNormalMsgTransaction() throws Exception {
//
//        String MOCK_MESSAGE = "This is a mock message.";
//        long messageId = System.currentTimeMillis();
//        DownPacket downPacket = MockDownPacket.mockOneOnlineNormalDownPacket(messageId, MOCK_MESSAGE.getBytes());
//
//        ReceiveMessageTransaction transaction = new ReceiveMessageTransaction(downPacket);
//
//        Assert.assertTrue(transaction.startWorkFlow() == ProcessorCode.SUCCESS);
//
//        BinaryMessage message = (BinaryMessage) ChannelSdk.getMessage(Long.toString(messageId));
//
//        Assert.assertEquals(MOCK_MESSAGE, new String(message.getData()));
//
//    }
//
//    @Override
//    protected void tearDown() throws Exception {
//        SetUpUtil.destroy();
//    }
//}
