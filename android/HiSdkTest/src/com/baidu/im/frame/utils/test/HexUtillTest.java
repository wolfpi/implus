package com.baidu.im.frame.utils.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.utils.HexUtil;

public class HexUtillTest extends InstrumentationTestCase {

    public static final String TAG = "MD5UtillTest";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testhex() {
	 
       Assert.assertTrue(HexUtil.hex(null) == null);
       
       byte data[] = new byte[3];
       data[0] = (byte) 0xaf;
       data[1] = (byte) 0xdf;
       data[2] = (byte) 0xcd;
       
       //String s = HexUtil.hex(data);
       Assert.assertTrue(HexUtil.hex(data).equalsIgnoreCase("afdfcd"));
   }
   
}