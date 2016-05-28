/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.inapp.transaction.session.processor;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;
import android.text.TextUtils;

import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.utils.BizCodeProcessUtil;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
import com.baidu.im.sdk.LoginMessage;
import com.baidu.im.testutil.MockObj;
import com.baidu.im.testutil.MockUser;

/**
 * @author zhaowei10
 * 
 */
public class UserLoginProsessorTest extends InstrumentationTestCase {

	@Override
	public void setUp() throws Exception {
	}

//	public void testProcess() throws Exception {
//
//		InAppApplication.getInstance().getSession().removeSession();
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		InAppApplication.getInstance().getSession().removeUid();
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//
//		LoginMessage loginMessage = new LoginMessage();
//		loginMessage.setAccount(MockUser.UserEnum.imrd_333.name());
//		loginMessage.setPassword(MockUser.UserEnum.imrd_333.getPassword());
//		UserLoginProsessor userLoginProsessor = new UserLoginProsessor(
//				loginMessage);
//
//		Assert.assertTrue(userLoginProsessor.process() == ProcessorCode.SUCCESS);
//
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() != 0);
//	}
//
//	/**
//	 * NO_BDUSS（1）用户登录没有BDUSS 错误的用户名 密码登陆就可以获取到
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessNobduss() throws Exception {
//
//		InAppApplication.getInstance().getSession().removeSession();
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		InAppApplication.getInstance().getSession().removeUid();
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//
//		LoginMessage loginMessage = new LoginMessage();
//		loginMessage.setAccount(MockUser.UserEnum.imrd_333.name());
//		loginMessage.setPassword("wrongpass");
//
//		UserLoginProsessor userLoginProsessor = new UserLoginProsessor(
//				loginMessage);
//
//		Assert.assertTrue(userLoginProsessor.process() == ProcessorCode.NO_BDUSS);
//
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//	}
//
//	/**
//	 * PARAM_ERROR（110）参数错误
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessParamError() throws Exception {
//
//		InAppApplication.getInstance().getSession().removeSession();
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		InAppApplication.getInstance().getSession().removeUid();
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//
//		LoginMessage loginMessage = new LoginMessage();
//		loginMessage.setAccount(MockUser.UserEnum.imrd_333.name());
//		loginMessage.setPassword(MockUser.UserEnum.imrd_333.getPassword());
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_PARAM_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.Logout);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		UserLoginProsessor userLoginProsessor = new UserLoginProsessor(
//				loginMessage);
//
//		Assert.assertTrue(userLoginProsessor.process() == ProcessorCode.PARAM_ERROR);
//
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//	}
//
//	/**
//	 * SESSION_ERROR（120）SESSION过期
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessSessionError() throws Exception {
//
//		InAppApplication.getInstance().getSession().removeSession();
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		InAppApplication.getInstance().getSession().removeUid();
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//
//		LoginMessage loginMessage = new LoginMessage();
//		loginMessage.setAccount(MockUser.UserEnum.imrd_333.name());
//		loginMessage.setPassword(MockUser.UserEnum.imrd_333.getPassword());
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_SESSION_NOT_EXIST);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.Logout);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		UserLoginProsessor userLoginProsessor = new UserLoginProsessor(
//				loginMessage);
//
//		Assert.assertTrue(userLoginProsessor.process() == ProcessorCode.SESSION_ERROR);
//
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//	}
//
//	/**
//	 * SEND_TIME_OUT（11）发送协议超时
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessSendTimeOut() throws Exception {
//
//		InAppApplication.getInstance().getSession().removeSession();
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		InAppApplication.getInstance().getSession().removeUid();
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//
//		LoginMessage loginMessage = new LoginMessage();
//		loginMessage.setAccount(MockUser.UserEnum.imrd_333.name());
//		loginMessage.setPassword(MockUser.UserEnum.imrd_333.getPassword());
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_SUCCESS);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.Logout);
//		mockPara.setNoResponsePacketFlag(true);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		UserLoginProsessor userLoginProsessor = new UserLoginProsessor(
//				loginMessage);
//
//		Assert.assertTrue(userLoginProsessor.process() == ProcessorCode.SEND_TIME_OUT);
//
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//	}
//
//	/**
//	 * SERVER_ERROR（190）服务器错误
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessServerError() throws Exception {
//
//		InAppApplication.getInstance().getSession().removeSession();
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		InAppApplication.getInstance().getSession().removeUid();
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//
//		LoginMessage loginMessage = new LoginMessage();
//		loginMessage.setAccount(MockUser.UserEnum.imrd_333.name());
//		loginMessage.setPassword(MockUser.UserEnum.imrd_333.getPassword());
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_UNKNOWN_SERVER_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.Logout);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		UserLoginProsessor userLoginProsessor = new UserLoginProsessor(
//				loginMessage);
//
//		Assert.assertTrue(userLoginProsessor.process() == ProcessorCode.SERVER_ERROR);
//
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//	}
//
//	/**
//	 * UNKNOWN_ERROR（999）未知错误
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessUnknownError() throws Exception {
//
//		InAppApplication.getInstance().getSession().removeSession();
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		InAppApplication.getInstance().getSession().removeUid();
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//
//		LoginMessage loginMessage = new LoginMessage();
//		loginMessage.setAccount(MockUser.UserEnum.imrd_333.name());
//		loginMessage.setPassword(MockUser.UserEnum.imrd_333.getPassword());
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_UNKNOWN_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.Logout);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		UserLoginProsessor userLoginProsessor = new UserLoginProsessor(
//				loginMessage);
//
//		Assert.assertTrue(userLoginProsessor.process() == ProcessorCode.UNKNOWN_ERROR);
//
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//	}

	@Override
	protected void tearDown() throws Exception {
	}
}
