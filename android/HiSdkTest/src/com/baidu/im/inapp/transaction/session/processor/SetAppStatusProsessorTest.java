/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.inapp.transaction.session.processor;

import android.test.InstrumentationTestCase;

/**
 * @author zhaowei10
 */
public class SetAppStatusProsessorTest extends InstrumentationTestCase {

    @Override
    public void setUp() throws Exception {
    }

    //	@SmallTest
    //    public void testProcess() throws Exception {
    //
    //		// Set offline
    //		Assert.assertTrue(InAppApplication.getInstance().getSession()
    //				.getAppStatus() == EAppStatus.APP_ONLINE);
    //
    //		SetAppStatusProsessor setAppStatusProsessor = new SetAppStatusProsessor(
    //				EAppStatus.APP_OFFLINE);
    //        Assert.assertEquals(setAppStatusProsessor.process(), ProcessorCode.SUCCESS);
    //
    //		Assert.assertTrue(InAppApplication.getInstance().getSession()
    //				.getAppStatus() == EAppStatus.APP_OFFLINE);
    //
    //		// Set online
    //		setAppStatusProsessor = new SetAppStatusProsessor(EAppStatus.APP_ONLINE);
    //        Assert.assertEquals(setAppStatusProsessor.process(), ProcessorCode.SUCCESS);
    //
    //		Assert.assertTrue(InAppApplication.getInstance().getSession()
    //				.getAppStatus() == EAppStatus.APP_ONLINE);
    //
    //	}
    //
    //	/**
    //	 * 设置App状态没有APP_STATUS
    //	 * @throws Exception
    //	 */
    //	public void testProcessNoAppStatus() throws Exception {
    //		Assert.assertTrue(InAppApplication.getInstance().getSession()
    //				.getAppStatus() == EAppStatus.APP_ONLINE);
    //
    //		SetAppStatusProsessor setAppStatusProsessor = new SetAppStatusProsessor(null);
    //        Assert.assertEquals(setAppStatusProsessor.process(), ProcessorCode.NO_APP_STATUS);
    //	}
    //
    //	/**
    //	 * PARAM_ERROR（110）参数错误
    //	 * @throws Exception
    //	 */
    //	public void testProcessParamError() throws Exception {
    //		Assert.assertTrue(InAppApplication.getInstance().getSession()
    //				.getAppStatus() == EAppStatus.APP_ONLINE);
    //
    //		MockObj mockPara = new MockObj();
    //		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_PARAM_ERROR);
    //		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
    //		mockPara.setServiceName(ServiceNameEnum.CoreSession);
    //		mockPara.setMethodName(MethodNameEnum.SetAppStatus);
    //		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
    //
    //		SetAppStatusProsessor setAppStatusProsessor = new SetAppStatusProsessor(EAppStatus.APP_OFFLINE);
    //        Assert.assertEquals(setAppStatusProsessor.process(), ProcessorCode.PARAM_ERROR);
    //        Assert.assertTrue(InAppApplication.getInstance().getSession()
    //				.getAppStatus() == EAppStatus.APP_ONLINE);
    //	}
    //
    //	/**
    //	 * SESSION_ERROR（120）SESSION过期
    //	 * @throws Exception
    //	 */
    //	public void testProcessSessionError() throws Exception {
    //		Assert.assertTrue(InAppApplication.getInstance().getSession()
    //				.getAppStatus() == EAppStatus.APP_ONLINE);
    //
    //		MockObj mockPara = new MockObj();
    //		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_SESSION_NOT_EXIST);
    //		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
    //		mockPara.setServiceName(ServiceNameEnum.CoreSession);
    //		mockPara.setMethodName(MethodNameEnum.SetAppStatus);
    //		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
    //
    //		SetAppStatusProsessor setAppStatusProsessor = new SetAppStatusProsessor(EAppStatus.APP_OFFLINE);
    //        Assert.assertEquals(setAppStatusProsessor.process(), ProcessorCode.SESSION_ERROR);
    //        Assert.assertTrue(InAppApplication.getInstance().getSession()
    //				.getAppStatus() == EAppStatus.APP_ONLINE);
    //	}
    //
    //	/**
    //	 * SEND_TIME_OUT（11）发送协议超时
    //	 * @throws Exception
    //	 */
    //	public void testProcessSendTimeOut() throws Exception {
    //		Assert.assertTrue(InAppApplication.getInstance().getSession()
    //				.getAppStatus() == EAppStatus.APP_ONLINE);
    //
    //		MockObj mockPara = new MockObj();
    //		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_SUCCESS);
    //		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
    //		mockPara.setNoResponsePacketFlag(true);
    //		mockPara.setServiceName(ServiceNameEnum.CoreSession);
    //		mockPara.setMethodName(MethodNameEnum.SetAppStatus);
    //		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
    //
    //		SetAppStatusProsessor setAppStatusProsessor = new SetAppStatusProsessor(EAppStatus.APP_OFFLINE);
    //        Assert.assertEquals(setAppStatusProsessor.process(), ProcessorCode.SEND_TIME_OUT);
    //        Assert.assertTrue(InAppApplication.getInstance().getSession()
    //				.getAppStatus() == EAppStatus.APP_ONLINE);
    //	}
    //
    //	/**
    //	 * SERVER_ERROR（190）服务器错误
    //	 * @throws Exception
    //	 */
    //	public void testProcessServerError() throws Exception {
    //		Assert.assertTrue(InAppApplication.getInstance().getSession()
    //				.getAppStatus() == EAppStatus.APP_ONLINE);
    //
    //		MockObj mockPara = new MockObj();
    //		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_UNKNOWN_SERVER_ERROR);
    //		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
    //		mockPara.setServiceName(ServiceNameEnum.CoreSession);
    //		mockPara.setMethodName(MethodNameEnum.SetAppStatus);
    //		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
    //
    //		SetAppStatusProsessor setAppStatusProsessor = new SetAppStatusProsessor(EAppStatus.APP_OFFLINE);
    //        Assert.assertEquals(setAppStatusProsessor.process(), ProcessorCode.SERVER_ERROR);
    //        Assert.assertTrue(InAppApplication.getInstance().getSession()
    //				.getAppStatus() == EAppStatus.APP_ONLINE);
    //	}
    //
    //	/**
    //	 * UNKNOWN_ERROR（999）未知错误
    //	 * @throws Exception
    //	 */
    //	public void testProcessUnknownError() throws Exception {
    //		Assert.assertTrue(InAppApplication.getInstance().getSession()
    //				.getAppStatus() == EAppStatus.APP_ONLINE);
    //
    //		MockObj mockPara = new MockObj();
    //		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_UNKNOWN_ERROR);
    //		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
    //		mockPara.setServiceName(ServiceNameEnum.CoreSession);
    //		mockPara.setMethodName(MethodNameEnum.SetAppStatus);
    //		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
    //
    //		SetAppStatusProsessor setAppStatusProsessor = new SetAppStatusProsessor(EAppStatus.APP_OFFLINE);
    //        Assert.assertEquals(setAppStatusProsessor.process(), ProcessorCode.UNKNOWN_ERROR);
    //        Assert.assertTrue(InAppApplication.getInstance().getSession()
    //				.getAppStatus() == EAppStatus.APP_ONLINE);
    //	}

    @Override
    protected void tearDown() throws Exception {
    }
}
