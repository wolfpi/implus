package com.baidu.im.frame.utils.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.utils.SignatureUtil;


public class SignatureUtillTest extends InstrumentationTestCase {

    public static final String TAG = "SignatureUtillTest";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testGetSeceretKey() {
	   Assert.assertTrue(SignatureUtil.getSecureKey(null) == null);
	   
	   SignatureUtil.getSecureKey(this.getInstrumentation().getContext());
	   //只能调用一下，防止吃问题。
	   //Assert.assertTrue(SignatureUtil.getSecureKey(this.getInstrumentation().getContext()) == null);
   }
   
}