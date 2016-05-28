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
import com.baidu.im.testutil.MockObj;

/**
 * @author zhaowei10
 * 
 */
public class AppLoginProsessorTest extends InstrumentationTestCase {

	@Override
	public void setUp() throws Exception {

	}

	/**
	 * 正常返回200
	 * 
	 * @throws Exception
	 */
	
//	public void testProcess() throws Exception {
//		InAppApplication.getInstance().getSession().removeSession();
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		AppLoginProsessor appLoginProsessor = new AppLoginProsessor();
//		Assert.assertTrue(appLoginProsessor.process() == ProcessorCode.SUCCESS);
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//	}
//
//	/**
//	 * 如果channelKey正确且应用未注册，则登录失败，返回错误
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessWithUnregisteredApp() throws Exception {
//		InAppApplication.getInstance().getSession().removeSession();
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getChannelKey()));
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_APP_NOT_EXIST);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.AppLogin);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		AppLoginProsessor appLoginProsessor = new AppLoginProsessor();
//		Assert.assertTrue(appLoginProsessor.process() == ProcessorCode.UNREGISTERED_APP);
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//	}
//
//	/**
//	 * 如果channelKey不正确，则登录失败，返回错误 目前server端没有校验channelKey!!!
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessWithWrongChannelKey() throws Exception {
//		InAppApplication.getInstance().getSession().removeSession();
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getChannelKey()));
//		InAppApplication.getInstance().getSession()
//				.setChannelKey("###WrongChannelKey###");
//
//		AppLoginProsessor appLoginProsessor = new AppLoginProsessor();
//		Assert.assertTrue(appLoginProsessor.process() == ProcessorCode.SUCCESS);
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//	}
//
//	/**
//	 * PARAM_ERROR（110）参数错误
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessParamError() throws Exception {
//		InAppApplication.getInstance().getSession().removeSession();
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_PARAM_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.AppLogin);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		AppLoginProsessor appLoginProsessor = new AppLoginProsessor();
//		Assert.assertTrue(appLoginProsessor.process() == ProcessorCode.PARAM_ERROR);
//	}
//
//	/**
//	 * SESSION_ERROR（120）SESSION过期
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessSessionError() throws Exception {
//		InAppApplication.getInstance().getSession().removeSession();
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_SESSION_NOT_EXIST);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.AppLogin);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		AppLoginProsessor appLoginProsessor = new AppLoginProsessor();
//		Assert.assertTrue(appLoginProsessor.process() == ProcessorCode.SESSION_ERROR);
//	}
//
//	/**
//	 * SEND_TIME_OUT（11）发送协议超时
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessSendTimeOut() throws Exception {
//		InAppApplication.getInstance().getSession().removeSession();
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_SUCCESS);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.AppLogin);
//		mockPara.setNoResponsePacketFlag(true);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		AppLoginProsessor appLoginProsessor = new AppLoginProsessor();
//		Assert.assertTrue(appLoginProsessor.process() == ProcessorCode.SEND_TIME_OUT);
//	}
//
//	/**
//	 * SERVER_ERROR（190）服务器错误
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessServerError() throws Exception {
//		InAppApplication.getInstance().getSession().removeSession();
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_UNKNOWN_SERVER_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.AppLogin);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		AppLoginProsessor appLoginProsessor = new AppLoginProsessor();
//		Assert.assertTrue(appLoginProsessor.process() == ProcessorCode.SERVER_ERROR);
//	}
//
//	/**
//	 * UNKNOWN_ERROR（999）未知错误
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessUnknownError() throws Exception {
//		InAppApplication.getInstance().getSession().removeSession();
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_UNKNOWN_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.AppLogin);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		AppLoginProsessor appLoginProsessor = new AppLoginProsessor();
//		Assert.assertTrue(appLoginProsessor.process() == ProcessorCode.UNKNOWN_ERROR);
//	}

	@Override
	protected void tearDown() throws Exception {
	}
}
