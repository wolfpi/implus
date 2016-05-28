/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.inapp.transaction.session.processor;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.text.TextUtils;

import com.baidu.im.constant.Constant.EChannelType;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.utils.BizCodeProcessUtil;
import com.baidu.im.frame.utils.ConfigUtil;
import com.baidu.im.mock.MockNetworkFramework;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
import com.baidu.im.testutil.MockObj;

/**
 * @author zhaowei10
 * 
 */
public class RegChannelProsessorTest extends InstrumentationTestCase {

    @Override
    public void setUp() throws Exception {
    }

    /**
     * 第一次注册的
     */
//    public void testProcess() throws Exception {
//
//        if (ConfigUtil.getChannelType() != EChannelType.tieba) {
//            return;
//        }
//        // 删除持久化的channelKey
//        InAppApplication.getInstance().getSession().removeChannelKey();
//        Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance().getSession().getChannelKey()));
//
//        RegChannelProsessor registerChannelProsessor = new RegChannelProsessor();
//        Assert.assertTrue(registerChannelProsessor.process() == ProcessorCode.SUCCESS);
//
//        String channelkey = InAppApplication.getInstance().getSession().getChannelKey();
//        Assert.assertTrue(!TextUtils.isEmpty(channelkey));
//
//    }
//
//    /**
//     * mock下行包错误 <br>
//     * channelkey 应该为空 ProcessorCode非success<br>
//     * 
//     * @throws Exception
//     */
//    public void testProcessError() throws Exception {
//
//        if (ConfigUtil.getChannelType() != EChannelType.tieba) {
//            return;
//        }
//        // 删除持久化的channelKey
//        InAppApplication.getInstance().getSession().removeChannelKey();
//        Assert.assertTrue(TextUtils.isEmpty(InAppApplication.getInstance().getSession().getChannelKey()));
//
//        MockObj mockPara = new MockObj();
//        mockPara.setBizCode(BizCodeProcessUtil.BizCode.SESSION_PARAM_ERROR);
//        mockPara.setChannelCode(BizCodeProcessUtil.ChannelCode.CHANNEL_UNKNOWN_ERROR);
//        mockPara.setServiceName(ServiceNameEnum.CoreAcc);
//        mockPara.setMethodName(MethodNameEnum.RegChannel);
//        SetUpUtil.setExceptionChannel(this.getInstrumentation(), mockPara);
//
//        RegChannelProsessor registerChannelProsessor = new RegChannelProsessor();
//        Assert.assertTrue(registerChannelProsessor.process() == ProcessorCode.PARAM_ERROR);
//        String channelkey = InAppApplication.getInstance().getSession().getChannelKey();
//        Assert.assertTrue(TextUtils.isEmpty(channelkey));
//
//        MockNetworkFramework.getInstance().stopMock();
//    }
//
//    /**
//     * 非首次注册,重新注册仍然会获得之前的channelKey。
//     */
//    @SmallTest
//    public void testRegChannelSecondTimes() throws Exception {
//
//        if (ConfigUtil.getChannelType() != EChannelType.tieba) {
//            return;
//        }
//        RegChannelProsessor registerChannelProsessor = new RegChannelProsessor();
//        Assert.assertTrue(registerChannelProsessor.process() == ProcessorCode.SUCCESS);
//
//        String channelkey = InAppApplication.getInstance().getSession().getChannelKey();
//        Assert.assertTrue(!TextUtils.isEmpty(channelkey));
//
//        registerChannelProsessor = new RegChannelProsessor();
//        Assert.assertTrue(registerChannelProsessor.process() == ProcessorCode.SUCCESS);
//
//        Assert.assertEquals(channelkey, InAppApplication.getInstance().getSession().getChannelKey());
//    }

    @Override
    protected void tearDown() throws Exception {
    }
}
