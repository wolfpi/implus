package com.baidu.im.frame.utils.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.utils.OsUtil;

public class OsUtilTest extends InstrumentationTestCase {

    public static final String TAG = "SignatureUtillTest";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testMarshUnmarsh() {
	 
       Assert.assertTrue(OsUtil.getProcessNameBy(null) == null);
       OsUtil.getProcessNameBy(this.getInstrumentation().getContext());
       
       Assert.assertTrue(OsUtil.getProcessNameBy(this.getInstrumentation().getContext()) != null);
   }
   
}