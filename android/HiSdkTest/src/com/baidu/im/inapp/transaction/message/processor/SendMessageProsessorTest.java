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
//import com.baidu.im.frame.utils.BizCodeProcessUtil;
//import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
//import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
//import com.baidu.im.sdk.BinaryMessage;
//import com.baidu.im.testutil.MockObj;
//import com.baidu.im.testutil.MockUser.UserEnum;
//import com.baidu.im.testutil.SetUpUtil;
//
///**
// * @author zhaowei10
// * 
// */
//public class SendMessageProsessorTest extends InstrumentationTestCase {
//
//	@Override
//	public void setUp() throws Exception {
//		SetUpUtil.initialize(this.getInstrumentation());
//		SetUpUtil.getUserLoginReady(UserEnum.imrd_333);
//	}
//
//	public void testProcess() throws Exception {
//
//		String message = "This is a message from Unit Test. timestamp="
//				+ System.currentTimeMillis();
//
//		String appMessage = new String("msg 1.3 R 1\r\nfrom:" + 40000333
//				+ "\r\nto:" + 40000333 + "\r\nmethod:msg_request\r\n\r\n<msg>"
//				+ message + "</msg>");
//
//		BinaryMessage binaryMessage = new BinaryMessage();
//		binaryMessage.setData(appMessage.getBytes());
//		SendMessageProsessor sendMessageProsessor = new SendMessageProsessor(
//				binaryMessage, InAppApplication.getInstance().getSession()
//						.getSessionId());
//
//		Assert.assertTrue(sendMessageProsessor.process() == ProcessorCode.SUCCESS);
//
//	}
//
//	/**
//	 * NO_SESSION_ID_FAILURE（3）发送消息没有SESSION_ID
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessNoSessionId() throws Exception {
//
//		String message = "This is a message from Unit Test. timestamp="
//				+ System.currentTimeMillis();
//
//		String appMessage = new String("msg 1.3 R 1\r\nfrom:" + 40000333
//				+ "\r\nto:" + 40000333 + "\r\nmethod:msg_request\r\n\r\n<msg>"
//				+ message + "</msg>");
//
//		BinaryMessage binaryMessage = new BinaryMessage();
//		binaryMessage.setData(appMessage.getBytes());
//
//		// 删除sessionID
//		InAppApplication.getInstance().getSession().removeSession();
//
//		SendMessageProsessor sendMessageProsessor = new SendMessageProsessor(
//				binaryMessage, InAppApplication.getInstance().getSession()
//						.getSessionId());
//		Assert.assertTrue(sendMessageProsessor.process() == ProcessorCode.NO_SESSION_ID_FAILURE);
//	}
//
//	/**
//	 * PARAM_ERROR（110）参数错误
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessParamError() throws Exception {
//
//		String message = "This is a message from Unit Test. timestamp="
//				+ System.currentTimeMillis();
//
//		String appMessage = new String("msg 1.3 R 1\r\nfrom:" + 40000333
//				+ "\r\nto:" + 40000333 + "\r\nmethod:msg_request\r\n\r\n<msg>"
//				+ message + "</msg>");
//
//		BinaryMessage binaryMessage = new BinaryMessage();
//		binaryMessage.setData(appMessage.getBytes());
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_PARAM_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreMsg);
//		mockPara.setMethodName(MethodNameEnum.SendData);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		SendMessageProsessor sendMessageProsessor = new SendMessageProsessor(
//				binaryMessage, InAppApplication.getInstance().getSession()
//						.getSessionId());
//		Assert.assertTrue(sendMessageProsessor.process() == ProcessorCode.PARAM_ERROR);
//	}
//
//	/**
//	 * SESSION_ERROR（120）SESSION过期
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessSessionError() throws Exception {
//
//		String message = "This is a message from Unit Test. timestamp="
//				+ System.currentTimeMillis();
//
//		String appMessage = new String("msg 1.3 R 1\r\nfrom:" + 40000333
//				+ "\r\nto:" + 40000333 + "\r\nmethod:msg_request\r\n\r\n<msg>"
//				+ message + "</msg>");
//
//		BinaryMessage binaryMessage = new BinaryMessage();
//		binaryMessage.setData(appMessage.getBytes());
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_SESSION_NOT_EXIST);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreMsg);
//		mockPara.setMethodName(MethodNameEnum.SendData);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		SendMessageProsessor sendMessageProsessor = new SendMessageProsessor(
//				binaryMessage, InAppApplication.getInstance().getSession()
//						.getSessionId());
//		Assert.assertTrue(sendMessageProsessor.process() == ProcessorCode.SESSION_ERROR);
//	}
//
//	/**
//	 * SEND_TIME_OUT（11）发送协议超时
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessSendTimeOut() throws Exception {
//
//		String message = "This is a message from Unit Test. timestamp="
//				+ System.currentTimeMillis();
//
//		String appMessage = new String("msg 1.3 R 1\r\nfrom:" + 40000333
//				+ "\r\nto:" + 40000333 + "\r\nmethod:msg_request\r\n\r\n<msg>"
//				+ message + "</msg>");
//
//		BinaryMessage binaryMessage = new BinaryMessage();
//		binaryMessage.setData(appMessage.getBytes());
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_SUCCESS);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreMsg);
//		mockPara.setMethodName(MethodNameEnum.SendData);
//		mockPara.setNoResponsePacketFlag(true);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		SendMessageProsessor sendMessageProsessor = new SendMessageProsessor(
//				binaryMessage, InAppApplication.getInstance().getSession()
//						.getSessionId());
//		Assert.assertTrue(sendMessageProsessor.process() == ProcessorCode.SEND_TIME_OUT);
//	}
//
//	/**
//	 * SERVER_ERROR（190）服务器错误
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessServerError() throws Exception {
//
//		String message = "This is a message from Unit Test. timestamp="
//				+ System.currentTimeMillis();
//
//		String appMessage = new String("msg 1.3 R 1\r\nfrom:" + 40000333
//				+ "\r\nto:" + 40000333 + "\r\nmethod:msg_request\r\n\r\n<msg>"
//				+ message + "</msg>");
//
//		BinaryMessage binaryMessage = new BinaryMessage();
//		binaryMessage.setData(appMessage.getBytes());
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_UNKNOWN_SERVER_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreMsg);
//		mockPara.setMethodName(MethodNameEnum.SendData);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		SendMessageProsessor sendMessageProsessor = new SendMessageProsessor(
//				binaryMessage, InAppApplication.getInstance().getSession()
//						.getSessionId());
//		Assert.assertTrue(sendMessageProsessor.process() == ProcessorCode.SERVER_ERROR);
//	}
//
//	/**
//	 * UNKNOWN_ERROR（999）未知错误
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessUnknownError() throws Exception {
//
//		String message = "This is a message from Unit Test. timestamp="
//				+ System.currentTimeMillis();
//
//		String appMessage = new String("msg 1.3 R 1\r\nfrom:" + 40000333
//				+ "\r\nto:" + 40000333 + "\r\nmethod:msg_request\r\n\r\n<msg>"
//				+ message + "</msg>");
//
//		BinaryMessage binaryMessage = new BinaryMessage();
//		binaryMessage.setData(appMessage.getBytes());
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_UNKNOWN_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreMsg);
//		mockPara.setMethodName(MethodNameEnum.SendData);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		SendMessageProsessor sendMessageProsessor = new SendMessageProsessor(
//				binaryMessage, InAppApplication.getInstance().getSession()
//						.getSessionId());
//		Assert.assertTrue(sendMessageProsessor.process() == ProcessorCode.UNKNOWN_ERROR);
//	}
//
//	/**
//	 * ChannelDispatchError（520）未知错误
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessChannelDispatchError() throws Exception {
//
//		String message = "This is a message from Unit Test. timestamp="
//				+ System.currentTimeMillis();
//
//		String appMessage = new String("msg 1.3 R 1\r\nfrom:" + 40000333
//				+ "\r\nto:" + 40000333 + "\r\nmethod:msg_request\r\n\r\n<msg>"
//				+ message + "</msg>");
//
//		BinaryMessage binaryMessage = new BinaryMessage();
//		binaryMessage.setData(appMessage.getBytes());
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_UNKNOWN_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_DISPATCH_ERROR);
//		mockPara.setServiceName(ServiceNameEnum.CoreMsg);
//		mockPara.setMethodName(MethodNameEnum.SendData);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		SendMessageProsessor sendMessageProsessor = new SendMessageProsessor(
//				binaryMessage, InAppApplication.getInstance().getSession()
//						.getSessionId());
//		Assert.assertTrue(sendMessageProsessor.process() == ProcessorCode.CHANNEL_DISPATCH_ERROR);
//	}
//
//	@Override
//	protected void tearDown() throws Exception {
//		SetUpUtil.destroy();
//	}
//}
