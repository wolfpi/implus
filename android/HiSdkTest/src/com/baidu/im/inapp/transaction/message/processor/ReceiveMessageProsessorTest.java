///**
// * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
// */
//package com.baidu.im.inapp.transaction.message.processor;
//
//import junit.framework.Assert;
//import android.test.InstrumentationTestCase;
//
//import com.baidu.im.frame.ProcessorCode;
//import com.baidu.im.frame.inapp.InAppApplication;
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
//public class ReceiveMessageProsessorTest extends InstrumentationTestCase {
//
//	public static final String MOCK_MESSAGE = "This is a mock message.";
//
//	private long messageId;
//
//	@Override
//	public void setUp() throws Exception {
//		SetUpUtil.initialize(this.getInstrumentation());
//		SetUpUtil.getUserLoginReady(UserEnum.imrd_333);
//	}
//
//	public void testNormalMsgProcess() throws Exception {
//
//		String MOCK_MESSAGE = "This is a mock message.";
//		long messageId = System.currentTimeMillis();
//		DownPacket downPacket = MockDownPacket.mockOneOnlineNormalDownPacket(
//				messageId, MOCK_MESSAGE.getBytes());
//
//		ReceiveMessageProsessor receiveMessageProsessor = new ReceiveMessageProsessor(
//				downPacket);
//
//		Assert.assertTrue(receiveMessageProsessor.process() == ProcessorCode.SUCCESS);
//
//		BinaryMessage message = (BinaryMessage) ChannelSdk
//				.getMessage(messageId + "");
//
//		Assert.assertTrue(message != null);
//		Assert.assertEquals(MOCK_MESSAGE, new String(message.getData()));
//
//	}
//
//	public void testSysNotifyMsgProcess() throws Exception {
//
//		DownPacket downPacket = MockDownPacket
//				.mockOneOnlineSysNotifyDownPacket();
//
//		ReceiveMessageProsessor receiveMessageProsessor = new ReceiveMessageProsessor(
//				downPacket);
//
//		Assert.assertTrue(receiveMessageProsessor.process() == ProcessorCode.SUCCESS);
//
//	}
//
//	/**
//	 * NO_SESSION_ID_FAILURE（3）发送推送确认时没有SESSION_ID
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessNoSessionId() throws Exception {
//
//		InAppApplication.getInstance().getSession().removeSession();
//
//		String MOCK_MESSAGE = "This is a mock message.";
//		long messageId = System.currentTimeMillis();
//		DownPacket downPacket = MockDownPacket.mockOneOnlineNormalDownPacket(
//				messageId, MOCK_MESSAGE.getBytes());
//
//		ReceiveMessageProsessor receiveMessageProsessor = new ReceiveMessageProsessor(
//				downPacket);
//
//		Assert.assertTrue(receiveMessageProsessor.process() == ProcessorCode.NO_SESSION_ID_FAILURE);
//
//		BinaryMessage message = (BinaryMessage) ChannelSdk
//				.getMessage(messageId + "");
//
//		Assert.assertTrue(message == null);
//
//	}
//
//	/**
//	 * EMPTY_PUSH（21）收到空的推送 如果外面的包是empty 目前业务没有处理 有问题
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessEmptyPush() throws Exception {
//
//		ReceiveMessageProsessor receiveMessageProsessor = new ReceiveMessageProsessor(
//				MockDownPacket.mockOneOnlineEmptyNormalDownPacket());
//
//		Assert.assertTrue(receiveMessageProsessor.process() == ProcessorCode.EMPTY_PUSH);
//
//		BinaryMessage message = (BinaryMessage) ChannelSdk
//				.getMessage(messageId + "");
//
//		Assert.assertTrue(message == null);
//
//	}
//
//	@Override
//	protected void tearDown() throws Exception {
//		SetUpUtil.destroy();
//	}
//}
