package com.baidu.im.inapp.transaction.config.processor;

import junit.framework.Assert;

import com.baidu.im.frame.ProcessorCode;

import android.test.InstrumentationTestCase;

public class ConfigBaseProcessorTest extends InstrumentationTestCase {

    @Override
    protected void setUp() throws Exception {
    }

    @Override
    protected void tearDown() throws Exception {
    }

    
    public void testUpdateConfig() throws Exception {
        ConfigBaseProcessor processor = new ConfigBaseProcessor(null);
        Assert.assertTrue(processor.onReceive(null, null).getProcessorCode()== ProcessorCode.SERVER_ERROR);
        Assert.assertTrue(processor.startWorkFlow().getProcessorCode()== ProcessorCode.PARAM_ERROR);
        
    }

}
