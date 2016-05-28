package com.baidu.im.frame.utils.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.utils.FileUtil;
import com.baidu.im.frame.utils.HexUtil;

public class FileUtillTest extends InstrumentationTestCase {

    public static final String TAG = "FileUtillTest";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testFileUtil() {
	 
      FileUtil.writeStringToFileInSdkFolder(null, "test");
      FileUtil.writeStringToFileInSdkFolder("test", "write_str");
      FileUtil.writeStringToFileInSdkFolder(null, null);
      
      FileUtil.readBytesFromFileInSdkFolder(null);
      FileUtil.readBytesFromFileInSdkFolder("test");
      
      FileUtil.readStringFromFileInSdkFolder(null);
      FileUtil.readStringFromFileInSdkFolder("test");
      
      String str = "test";
      FileUtil.saveBytesToFileInSdkFolder(null, null);
      FileUtil.saveBytesToFileInSdkFolder(null, str.getBytes());
   }
   
}