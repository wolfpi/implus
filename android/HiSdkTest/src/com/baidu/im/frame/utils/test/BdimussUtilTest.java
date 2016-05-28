package com.baidu.im.frame.utils.test;

import java.util.HashMap;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.utils.BdimussUtil;

public class BdimussUtilTest extends InstrumentationTestCase {

    public static final String TAG = "AssertUtilTest";
    
    //private TestFacade mTestFacade = new TestFacade();
    //private final String mTextMsg = "InboxKeyTest";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testEncodeBDimuss() {
	   try {
		Assert.assertTrue(BdimussUtil.EncodeBdimuss(null, null, 0).equalsIgnoreCase(""));
		Assert.assertTrue(BdimussUtil.EncodeBdimuss("test", null, 0).equalsIgnoreCase(""));
		
		byte[] sed = new byte[1];
		sed[0] = 1;
		Assert.assertTrue(BdimussUtil.EncodeBdimuss("test", sed, 0).equalsIgnoreCase("3cd86989fb8bb35e8dc53c09adbe55900000000001"));
		
	} catch (Exception e){
		e.printStackTrace();
		Assert.assertTrue(false);
	}
   }
   
   public void testGetPassportParams() {
	   try {
		Assert.assertTrue(BdimussUtil.GetPassportParams(null, null) == null);
		
		String account = "account";
		String password = "password";
		HashMap<String,String> result = BdimussUtil.GetPassportParams(account,password);
		
		String passwdResult = result.get(password);
		Assert.assertTrue(passwdResult.equalsIgnoreCase("696d29e0940a4957748fe3fc9efd22a3"));
		
	} catch (Exception e){
		e.printStackTrace();
		Assert.assertTrue(false);
	}
   }
   
}