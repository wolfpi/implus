package com.baidu.im.inapp.transaction.config;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.testutil.MockDownPacket;
import com.google.protobuf.micro.ByteStringMicro;

public class ConfigChangedNotifyTransactionTest extends InstrumentationTestCase {

    @Override
    protected void setUp(){
    }

    @Override
    protected void tearDown() {
    }

    public void testTransaction() {
    	
        ByteStringMicro configChangedNotifyByteString = MockDownPacket
                .mockOneOnlineSysNotifyDownPacketConfigChangedNotifyByteStringMicro();
        ConfigChangeNotifyTransaction transaction = new ConfigChangeNotifyTransaction(configChangedNotifyByteString,null);
        Assert.assertTrue(transaction.startWorkFlow(null).getProcessorCode() == ProcessorCode.PARAM_ERROR);
        
        transaction = new ConfigChangeNotifyTransaction(null,null);
        Assert.assertTrue(transaction.startWorkFlow(null).getProcessorCode() == ProcessorCode.PARAM_ERROR);
    }
}
