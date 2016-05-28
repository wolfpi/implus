package com.baidu.im.frame.utils.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.utils.AssertUtil;
public class AssertUtilTest extends InstrumentationTestCase {

    public static final String TAG = "AssertUtilTest";
    
    //private TestFacade mTestFacade = new TestFacade();
    //private final String mTextMsg = "InboxKeyTest";

    @Override
    protected void setUp() {

    }

   @Override
   public void tearDown() {

   }
   
   public void _testAccountInvalid() {
	   Assert.assertTrue(AssertUtil.readByte(null, "") == null);
	   Assert.assertTrue(AssertUtil.readString(null, "").equalsIgnoreCase(""));
	   
	   Assert.assertTrue(AssertUtil.readByte(this.getInstrumentation().getContext(), "") == null);
	   Assert.assertTrue(AssertUtil.readString(this.getInstrumentation().getContext(), "").equalsIgnoreCase(""));
   }
}