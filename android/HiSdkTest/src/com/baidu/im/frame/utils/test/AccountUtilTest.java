package com.baidu.im.frame.utils.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.utils.AccountUtil;
public class AccountUtilTest extends InstrumentationTestCase {

    public static final String TAG = "AccountUtilTest";
    
    //private TestFacade mTestFacade = new TestFacade();
    //private final String mTextMsg = "InboxKeyTest";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void _testAccountInvalid() {
	   Assert.assertTrue(AccountUtil.getUid() == null);
	   Assert.assertTrue(AccountUtil.getBduss(null, "123") == null);
	   Assert.assertTrue(AccountUtil.getBduss("123", null) == null);
   }
}