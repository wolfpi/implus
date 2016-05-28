/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.inapp.transaction.session.processor;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.utils.BizCodeProcessUtil;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
import com.baidu.im.testutil.MockObj;

/**
 * @author zhaowei10
 * 
 */
public class HeartbeatProsessorTest extends InstrumentationTestCase {

	@Override
	public void setUp() throws Exception {
	}

//    public void testProcess() throws Exception {
//
//		HeartbeatProsessor heartbeatProsessor = new HeartbeatProsessor();
//
//        Assert.assertTrue(heartbeatProsessor.process() == ProcessorCode.SUCCESS);
//
//	}
//    
//    /**
//     * PARAM_ERROR（110）参数错误    
//     * @throws Exception
//     */
//    public void testProcessParamError() throws Exception {
//    	MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_PARAM_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.Heartbeat);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
//		
//		HeartbeatProsessor heartbeatProsessor = new HeartbeatProsessor();
//        Assert.assertTrue(heartbeatProsessor.process() == ProcessorCode.PARAM_ERROR);
//	}
//    
//    /**
//     * SESSION_ERROR（120）SESSION过期
//     * @throws Exception
//     */
//    public void testProcessSessionError() throws Exception {
//    	MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_SESSION_NOT_EXIST);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.Heartbeat);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
//		
//		HeartbeatProsessor heartbeatProsessor = new HeartbeatProsessor();
//        Assert.assertTrue(heartbeatProsessor.process() == ProcessorCode.SESSION_ERROR);
//	}
//    
//    /**
//     * SEND_TIME_OUT（11）发送协议超时
//     * @throws Exception
//     */
//    public void testProcessSendTimeOut() throws Exception {
//    	MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_SUCCESS);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setNoResponsePacketFlag(true);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.Heartbeat);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
//		
//		HeartbeatProsessor heartbeatProsessor = new HeartbeatProsessor();
//        Assert.assertTrue(heartbeatProsessor.process() == ProcessorCode.SEND_TIME_OUT);
//	}
//    
//    /**
//     * SERVER_ERROR（190）服务器错误
//     * @throws Exception
//     */
//    public void testProcessServerError() throws Exception {
//    	MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_UNKNOWN_SERVER_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.Heartbeat);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
//		
//		HeartbeatProsessor heartbeatProsessor = new HeartbeatProsessor();
//        Assert.assertTrue(heartbeatProsessor.process() == ProcessorCode.SERVER_ERROR);
//	}
//    
//    /**
//     * UNKNOWN_ERROR（999）未知错误
//     * @throws Exception
//     */
//    public void testProcessUnknownError() throws Exception {
//    	MockObj mockPara = new MockObj();
//		mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_UNKNOWN_ERROR);
//		mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS);
//		mockPara.setServiceName(ServiceNameEnum.CoreSession);
//		mockPara.setMethodName(MethodNameEnum.Heartbeat);
//		SetUpUtil.setExceptionChannel(this.getInstrumentation(),mockPara);
//		
//		HeartbeatProsessor heartbeatProsessor = new HeartbeatProsessor();
//        Assert.assertTrue(heartbeatProsessor.process() == ProcessorCode.UNKNOWN_ERROR);
//	}

	@Override
	protected void tearDown() throws Exception {
	}
}
