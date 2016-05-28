package com.baidu.imc.impl.im.util.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;
import com.baidu.imc.impl.im.util.StringUtil;
public class StringUtilTest extends InstrumentationTestCase {

    public static final String TAG = "StringUtilTest";
    
    //private TestFacade mTestFacade = new TestFacade();
    //private final String mTextMsg = "InboxKeyTest";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testInBoxKey() {
	   Assert.assertTrue(StringUtil.bytesToString(null) == null);
	   Assert.assertTrue(StringUtil.stringToBytes(null) == null);
	   
	   byte[] empty = new byte[0] ;
	   Assert.assertTrue(StringUtil.bytesToString(empty) == null);
	   Assert.assertTrue(StringUtil.stringToBytes("") == null);
	   
	   byte[] noempty = new byte[1] ;
	   Assert.assertTrue(StringUtil.bytesToString(noempty) != null);
	   Assert.assertTrue(StringUtil.stringToBytes("test") != null);
	   
   }
}