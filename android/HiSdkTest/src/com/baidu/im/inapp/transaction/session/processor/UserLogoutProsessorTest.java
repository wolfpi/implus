/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.inapp.transaction.session.processor;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.text.TextUtils;

import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.utils.BizCodeProcessUtil;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
import com.baidu.im.testutil.MockUser.UserEnum;
import com.baidu.im.testutil.MockObj;

/**
 * @author zhaowei10
 * 
 */
public class UserLogoutProsessorTest extends InstrumentationTestCase {

	@Override
	public void setUp() throws Exception {
	}

//	@SmallTest
//	public void testProcess() throws Exception {
//
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getBduss()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() != 0);
//
//		UserLogoutProsessor userLogoutProsessor = new UserLogoutProsessor();
//		Assert.assertTrue(userLogoutProsessor.process() == ProcessorCode.SUCCESS);
//
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getBduss()));
//	}
//
//	/**
//	 * NO_SESSION_ID 用户登出没有SESSION_ID
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessNoSessionId() throws Exception {
//
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getBduss()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() != 0);
//
//		// 删除sessionID
//		InAppApplication.getInstance().getSession().removeSession();
//
//		UserLogoutProsessor userLogoutProsessor = new UserLogoutProsessor();
//		Assert.assertTrue(userLogoutProsessor.process() == ProcessorCode.NO_SESSION_ID);
//
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() != 0);
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getBduss()));
//	}
//
//	/**
//	 * PARAM_ERROR（110）参数错误
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessParamError() throws Exception {
//
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getBduss()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() != 0);
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_PARAM_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.Logout);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		UserLogoutProsessor userLogoutProsessor = new UserLogoutProsessor();
//		Assert.assertTrue(userLogoutProsessor.process() == ProcessorCode.PARAM_ERROR);
//
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() != 0);
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getBduss()));
//	}
//
//	/**
//	 * SESSION_ERROR（120）SESSION过期
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessSessionError() throws Exception {
//
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getBduss()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() != 0);
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_SESSION_NOT_EXIST);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.Logout);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		UserLogoutProsessor userLogoutProsessor = new UserLogoutProsessor();
//		Assert.assertTrue(userLogoutProsessor.process() == ProcessorCode.SESSION_ERROR);
//
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() != 0);
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getBduss()));
//	}
//
//	/**
//	 * SEND_TIME_OUT（11）发送协议超时
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessSendTimeOut() throws Exception {
//
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getBduss()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() != 0);
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_SUCCESS);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.Logout);
//		mockPara.setNoResponsePacketFlag(true);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		UserLogoutProsessor userLogoutProsessor = new UserLogoutProsessor();
//		Assert.assertTrue(userLogoutProsessor.process() == ProcessorCode.SEND_TIME_OUT);
//
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() != 0);
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getBduss()));
//	}
//
//	/**
//	 * UNKNOWN_ERROR（999）未知错误
//	 * 
//	 * @throws Exception
//	 */
//	public void testProcessUnknownError() throws Exception {
//
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getBduss()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() != 0);
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_UNKNOWN_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.Logout);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//		UserLogoutProsessor userLogoutProsessor = new UserLogoutProsessor();
//		Assert.assertTrue(userLogoutProsessor.process() == ProcessorCode.UNKNOWN_ERROR);
//
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getSessionId()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() != 0);
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getBduss()));
//	}

	@Override
	protected void tearDown() throws Exception {
	}
}
