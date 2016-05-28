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
public class UnRegAppProsessorTest extends InstrumentationTestCase {

	@Override
	public void setUp() throws Exception {
	}

//    public void testProcess() throws Exception {
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getApiKey()));
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getChannelKey()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppId() != 0);
//
//		UnRegAppProsessor unRegAppProsessor = new UnRegAppProsessor();
//        Assert.assertTrue(unRegAppProsessor.process() == ProcessorCode.SUCCESS);
//
//		// 删除本地的appid
//		// TODO 删除密钥
//        Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance().getSession().getSessionId()));
//        Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance().getSession().getBduss()));
//        Assert.assertTrue(InAppApplication.getInstance().getSession().getAppId() == 0);
//        // channelkey 不为空 考虑通道一对多 
//        // TODO 考虑加密存储channelkey
//        Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance().getSession().getChannelKey()));
//        Assert.assertTrue(InAppApplication.getInstance().getSession().getUid() == 0);
//
//	}
//    
//    /**
//     * NO_APP_ID（6）注销App没有APP_ID
//     * @throws Exception
//     */
//    public void testProcessNoAppId() throws Exception {
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getApiKey()));
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getChannelKey()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppId() != 0);
//		//删除APPID
//		InAppApplication.getInstance().getSession().setAppId(0);
//		UnRegAppProsessor unRegAppProsessor = new UnRegAppProsessor();
//        Assert.assertTrue(unRegAppProsessor.process() == ProcessorCode.NO_APP_ID);
//	}
//    
//    /**
//     * PARAM_ERROR（110）参数错误
//     * @throws Exception
//     */
//    public void testProcessParamError() throws Exception {
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getApiKey()));
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getChannelKey()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppId() != 0);
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_PARAM_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.UnRegApp);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
//		
//		UnRegAppProsessor unRegAppProsessor = new UnRegAppProsessor();
//        Assert.assertTrue(unRegAppProsessor.process() == ProcessorCode.PARAM_ERROR);
//	}
//    
//    /**
//	 * SESSION_ERROR（120）SESSION过期
//	 * @throws Exception
//	 */
//    public void testProcessSessionError() throws Exception {
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getApiKey()));
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getChannelKey()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppId() != 0);
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_SESSION_NOT_EXIST);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.UnRegApp);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
//		
//		UnRegAppProsessor unRegAppProsessor = new UnRegAppProsessor();
//        Assert.assertTrue(unRegAppProsessor.process() == ProcessorCode.SESSION_ERROR);
//	}
//    
//    /**
//     * SEND_TIME_OUT（11）发送协议超时
//     * @throws Exception
//     */
//    public void testProcessSendTimeOut() throws Exception {
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getApiKey()));
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getChannelKey()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppId() != 0);
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_SUCCESS);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.UnRegApp);
//		mockPara.setNoResponsePacketFlag(true);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
//		
//		UnRegAppProsessor unRegAppProsessor = new UnRegAppProsessor();
//        Assert.assertTrue(unRegAppProsessor.process() == ProcessorCode.SEND_TIME_OUT);
//	}
//    
//    /**
//     * SERVER_ERROR（190）服务器错误
//     * @throws Exception
//     */
//    public void testProcessServerError() throws Exception {
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getApiKey()));
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getChannelKey()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppId() != 0);
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_UNKNOWN_SERVER_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.UnRegApp);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
//		
//		UnRegAppProsessor unRegAppProsessor = new UnRegAppProsessor();
//        Assert.assertTrue(unRegAppProsessor.process() == ProcessorCode.SERVER_ERROR);
//	}
//    
//    /**
//     * UNKNOWN_ERROR（999）未知错误
//     * @throws Exception
//     */
//    public void testProcessUnknownError() throws Exception {
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getApiKey()));
//		Assert.assertTrue(!TextUtils.isEmpty(InAppApplication.getInstance()
//				.getSession().getChannelKey()));
//		Assert.assertTrue(InAppApplication.getInstance().getSession()
//				.getAppId() != 0);
//
//		MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_UNKNOWN_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.UnRegApp);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
//		
//		UnRegAppProsessor unRegAppProsessor = new UnRegAppProsessor();
//        Assert.assertTrue(unRegAppProsessor.process() == ProcessorCode.UNKNOWN_ERROR);
//	}

	@Override
	protected void tearDown() throws Exception {
	}

}
