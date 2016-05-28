//package com.baidu.im.inapp.transaction.config.processor;
//
//import junit.framework.Assert;
//import android.test.InstrumentationTestCase;
//
//import com.baidu.im.frame.ProcessorCode;
//import com.baidu.im.testutil.MockDownPacket;
//import com.baidu.im.testutil.SetUpUtil;
//import com.google.protobuf.ByteString;
//
//public class ConfigChangedNotifyProcessorTest extends InstrumentationTestCase {
//
//    @Override
//    protected void setUp() throws Exception {
//        SetUpUtil.initialize(this.getInstrumentation());
//    }
//
//    @Override
//    protected void tearDown() throws Exception {
//        SetUpUtil.destroy();
//    }
//
///*    public void testProcessor() throws Exception {
//        ByteString configChangedNotifyByteString = MockDownPacket.mockOneOnlineSysNotifyDownPacketConfigChangedNotifyByteString();
//        ConfigChangeNotifyProcessor processor = new ConfigChangeNotifyProcessor(configChangedNotifyByteString);
//        Assert.assertTrue(processor.process() == ProcessorCode.SUCCESS);
//    }*/
//}
