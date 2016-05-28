/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.inapp.transaction.session.processor;

import android.test.InstrumentationTestCase;

/**
 * @author zhaowei10
 */
public class AppLogoutProsessorTest extends InstrumentationTestCase {

    @Override
    public void setUp() throws Exception {
    }

    //	public void testProcess() throws Exception {
    //
    //		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
    //				.getSession().getSessionId()));
    //
    //		AppLogoutProsessor appLogoutProsessor = new AppLogoutProsessor();
    //		Assert.assertTrue(appLogoutProsessor.process() == ProcessorCode.SUCCESS);
    //
    //		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
    //				.getSession().getSessionId()));
    //		Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
    //		Assert.assertTrue(InAppApplication.getInstance().getSession()
    //				.getAppStatus() == EAppStatus.APP_NULL);
    //
    //	}
    //
    //	/**
    //	 * no SessionId
    //	 *
    //	 * @throws Exception
    //	 */
    //	public void testProcessNoSessionId() throws Exception {
    //		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
    //				.getSession().getSessionId()));
    //		// 删除sessionID
    //		InAppApplication.getInstance().getSession().removeSession();
    //
    //		AppLogoutProsessor appLogoutProsessor = new AppLogoutProsessor();
    //		Assert.assertTrue(appLogoutProsessor.process() == ProcessorCode.NO_SESSION_ID);
    //	}
    //
    //	/**
    //	 * PARAM_ERROR（110）参数错误
    //	 *
    //	 * @throws Exception
    //	 */
    //	public void testProcessParamError() throws Exception {
    //		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
    //				.getSession().getSessionId()));
    //
    //		MockObj mockPara = new MockObj();
    //		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_PARAM_ERROR);
    //		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
    //		mockPara.setServiceName(ServiceNameEnum.CoreSession);
    //		mockPara.setMethodName(MethodNameEnum.AppLogout);
    //		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
    //
    //		AppLogoutProsessor appLogoutProsessor = new AppLogoutProsessor();
    //		Assert.assertTrue(appLogoutProsessor.process() == ProcessorCode.PARAM_ERROR);
    //	}
    //
    //	/**
    //	 * SESSION_ERROR（120）SESSION过期
    //	 *
    //	 * @throws Exception
    //	 */
    //	public void testProcessSessionError() throws Exception {
    //		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
    //				.getSession().getSessionId()));
    //
    //		MockObj mockPara = new MockObj();
    //		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_SESSION_NOT_EXIST);
    //		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
    //		mockPara.setServiceName(ServiceNameEnum.CoreSession);
    //		mockPara.setMethodName(MethodNameEnum.AppLogout);
    //		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
    //
    //		AppLogoutProsessor appLogoutProsessor = new AppLogoutProsessor();
    //		Assert.assertTrue(appLogoutProsessor.process() == ProcessorCode.SESSION_ERROR);
    //	}
    //
    //	/**
    //	 * SEND_TIME_OUT（11）发送协议超时
    //	 *
    //	 * @throws Exception
    //	 */
    //	public void testProcessSendTimeOut() throws Exception {
    //		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
    //				.getSession().getSessionId()));
    //
    //		MockObj mockPara = new MockObj();
    //		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_SUCCESS);
    //		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
    //		mockPara.setServiceName(ServiceNameEnum.CoreSession);
    //		mockPara.setMethodName(MethodNameEnum.AppLogout);
    //		mockPara.setNoResponsePacketFlag(true);
    //		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
    //
    //		AppLogoutProsessor appLogoutProsessor = new AppLogoutProsessor();
    //		Assert.assertTrue(appLogoutProsessor.process() == ProcessorCode.SEND_TIME_OUT);
    //	}
    //
    //	/**
    //	 * SERVER_ERROR（190）服务器错误
    //	 *
    //	 * @throws Exception
    //	 */
    //	public void testProcessServerError() throws Exception {
    //		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
    //				.getSession().getSessionId()));
    //
    //		MockObj mockPara = new MockObj();
    //		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_UNKNOWN_SERVER_ERROR);
    //		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
    //		mockPara.setServiceName(ServiceNameEnum.CoreSession);
    //		mockPara.setMethodName(MethodNameEnum.AppLogout);
    //		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
    //
    //		AppLogoutProsessor appLogoutProsessor = new AppLogoutProsessor();
    //		Assert.assertTrue(appLogoutProsessor.process() == ProcessorCode.SERVER_ERROR);
    //	}
    //
    //	/**
    //	 * UNKNOWN_ERROR（999）未知错误
    //	 *
    //	 * @throws Exception
    //	 */
    //	public void testProcessUnknownError() throws Exception {
    //		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
    //				.getSession().getSessionId()));
    //
    //		MockObj mockPara = new MockObj();
    //		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_UNKNOWN_ERROR);
    //		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
    //		mockPara.setServiceName(ServiceNameEnum.CoreSession);
    //		mockPara.setMethodName(MethodNameEnum.AppLogout);
    //		SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
    //
    //		AppLogoutProsessor appLogoutProsessor = new AppLogoutProsessor();
    //		Assert.assertTrue(appLogoutProsessor.process() == ProcessorCode.UNKNOWN_ERROR);
    //	}

    @Override
    protected void tearDown() throws Exception {
    }
}
