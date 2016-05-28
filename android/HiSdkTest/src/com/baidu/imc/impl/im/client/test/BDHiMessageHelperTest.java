package com.baidu.imc.impl.im.client.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.imc.impl.im.client.BDHiMessageHelper;


public class BDHiMessageHelperTest extends InstrumentationTestCase {

    public static final String TAG = "BDHiMessageHelperTest";
    
   // private TestFacade mFacade = new TestFacade();
    
    @Override
    protected void setUp() {
      
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testNewFileMessage() {
	   BDHiMessageHelper helper = new BDHiMessageHelper();
	   Assert.assertTrue(helper.newFileMessage() == null);
	   Assert.assertTrue(helper.newFileMessage() == null);
	   Assert.assertTrue(helper.newFileMessage() == null);
   }
   
   public void testnewFileMessageContent() {
	   BDHiMessageHelper helper = new BDHiMessageHelper();
	   Assert.assertTrue(helper.newFileMessageContent(null) == null);
	   Assert.assertTrue(helper.newFileMessageContent("\\") == null);
	   Assert.assertTrue(helper.newFileMessageContent("@@@@@") == null);
   }
   
   public void testnewImageMessage()
   {
	   BDHiMessageHelper helper = new BDHiMessageHelper();
	   Assert.assertTrue(helper.newImageMessage() != null);
	   Assert.assertTrue(helper.newImageMessageContent() != null);
   }
   
   public void testnewTextMessage()
   {
	   BDHiMessageHelper helper = new BDHiMessageHelper();
	   Assert.assertTrue(helper.newTextMessage() != null);
	   Assert.assertTrue(helper.newTextMessageContent(null) == null);
	   Assert.assertTrue(helper.newTextMessageContent("test") != null);
	   Assert.assertTrue(helper.newTextMessageContent("") == null); 
   }
   
   public void testnewTransientMessage()
   {
	   BDHiMessageHelper helper = new BDHiMessageHelper();
	   Assert.assertTrue(helper.newTransientMessage() != null);
   }
   
   public void testnewURLMessageContent()
   {
	   BDHiMessageHelper helper = new BDHiMessageHelper();
	   Assert.assertTrue(helper.newURLMessageContent(null, "test") == null);
	   Assert.assertTrue(helper.newURLMessageContent(null, null) == null);
	   Assert.assertTrue(helper.newURLMessageContent("http:\\", "test") != null);
   }
   
   public void testnewVoiceMessage()
   {
	   BDHiMessageHelper helper = new BDHiMessageHelper();
	   Assert.assertTrue(helper.newVoiceMessage() != null);
	   Assert.assertTrue(helper.newVoiceMessageContent() != null);
   }
}
