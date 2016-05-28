package com.baidu.im.inapp.transaction.test;

import android.test.InstrumentationTestCase;

import com.baidu.im.inapp.transaction.TransactionResend;


public class TransactionResendTest extends InstrumentationTestCase {

    public static final String TAG = "TransactionResendTest";
    
   // private TestFacade mFacade = new TestFacade();
    
    @Override
    protected void setUp() {
      
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testResend() {
	   TransactionResend rsd = new TransactionResend();
	   rsd.addTransaction(0, null, null);
	   rsd.resendAll();
	   rsd.resendAll();
	   rsd.removeTransaction(0);
	   rsd.resendAll();
   }
}
