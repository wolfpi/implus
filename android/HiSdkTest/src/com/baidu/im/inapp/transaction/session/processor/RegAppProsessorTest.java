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
public class RegAppProsessorTest extends InstrumentationTestCase {

	@Override
	public void setUp() throws Exception {
		//SetUpUtil.getRegAppReady();
	}

//    public void testProcess() throws Exception {
//
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getApiKey()));
//
//		// 删除appId
//		InAppApplication.getInstance().getSession().removeAppId();
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppId() == 0);
//
//		// processor
//		RegAppProsessor registerAppProsessor = new RegAppProsessor();
//        Assert.assertTrue(registerAppProsessor.process() == ProcessorCode.SUCCESS);
//
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getApiKey()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppId() != 0);
//	}
//    
//    /**
//     * NO_API_KEY（4）注册App时无API_KEY
//     * @throws Exception
//     */
//	public void testProcessNoApiKey() throws Exception {
//		// 删除API Key
//		InAppApplication.getInstance().getSession().setApiKey("");
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getApiKey()));
//
//		// 删除appId
//		InAppApplication.getInstance().getSession().removeAppId();
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppId() == 0);
//
//		// processor
//		RegAppProsessor registerAppProsessor = new RegAppProsessor();
//		Assert.assertTrue(registerAppProsessor.process() == ProcessorCode.NO_API_KEY);
//
//		Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getApiKey()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppId() == 0);
//	}
//
//	/**
//	 * PARAM_ERROR（110）参数错误
//	 * @throws Exception
//	 */
//	public void testProcessParamError() throws Exception {
//		// 删除appId
//		InAppApplication.getInstance().getSession().removeAppId();
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppId() == 0);
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_PARAM_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.RegApp);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
//
//		// processor
//		RegAppProsessor registerAppProsessor = new RegAppProsessor();
//		Assert.assertTrue(registerAppProsessor.process() == ProcessorCode.PARAM_ERROR);
//	}
//	
//	/**
//	 * SESSION_ERROR（120）SESSION过期
//	 * @throws Exception
//	 */
//	public void testProcessSessionError() throws Exception {
//		// 删除appId
//		InAppApplication.getInstance().getSession().removeAppId();
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppId() == 0);
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_SESSION_NOT_EXIST);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.RegApp);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
//
//		// processor
//		RegAppProsessor registerAppProsessor = new RegAppProsessor();
//		Assert.assertTrue(registerAppProsessor.process() == ProcessorCode.SESSION_ERROR);
//	}
//	
//	/**
//	 * SEND_TIME_OUT（11）发送协议超时
//	 * @throws Exception
//	 */
//	public void testProcessSendTimeOut() throws Exception {
//		// 删除appId
//		InAppApplication.getInstance().getSession().removeAppId();
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppId() == 0);
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_SUCCESS);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.RegApp);
//		mockPara.setNoResponsePacketFlag(true);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
//
//		// processor
//		RegAppProsessor registerAppProsessor = new RegAppProsessor();
//		Assert.assertTrue(registerAppProsessor.process() == ProcessorCode.SEND_TIME_OUT);
//	}
//	
//	/**
//	 * SERVER_ERROR（190）服务器错误
//	 * @throws Exception
//	 */
//	public void testProcessServerError() throws Exception {
//		// 删除appId
//		InAppApplication.getInstance().getSession().removeAppId();
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppId() == 0);
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_UNKNOWN_SERVER_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.RegApp);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
//
//		// processor
//		RegAppProsessor registerAppProsessor = new RegAppProsessor();
//		Assert.assertTrue(registerAppProsessor.process() == ProcessorCode.SERVER_ERROR);
//	}
//	
//	/**
//	 * UNKNOWN_ERROR（999）未知错误
//	 * @throws Exception
//	 */
//	public void testProcessUnknownError() throws Exception {
//		// 删除appId
//		InAppApplication.getInstance().getSession().removeAppId();
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppId() == 0);
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_UNKNOWN_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.RegApp);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
//
//		// processor
//		RegAppProsessor registerAppProsessor = new RegAppProsessor();
//		Assert.assertTrue(registerAppProsessor.process() == ProcessorCode.UNKNOWN_ERROR);
//	}

	@Override
	protected void tearDown() throws Exception {
	}
}
