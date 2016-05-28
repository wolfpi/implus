package com.baidu.im.inapp.transaction.config;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.ProcessorCode;

public class UpdateConfigTransactionTest extends InstrumentationTestCase {

    @Override
    protected void setUp()  {
     
    }

    @Override
    protected void tearDown() {
    }

   public void testTransaction() {
        UpdateConfigTransaction transaction = new UpdateConfigTransaction(null,null);
        Assert.assertTrue(transaction.startWorkFlow(null).getProcessorCode() == ProcessorCode.PARAM_ERROR);
    }
}
