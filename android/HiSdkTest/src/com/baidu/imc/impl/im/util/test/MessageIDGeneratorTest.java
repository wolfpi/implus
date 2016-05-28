package com.baidu.imc.impl.im.util.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.imc.impl.im.util.MessageIDGenerator;
public class MessageIDGeneratorTest extends InstrumentationTestCase {

    public static final String TAG = "PushMessageImplTest";
    
    //private TestFacade mTestFacade = new TestFacade();
    //private final String mTextMsg = "InboxKeyTest";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testInBoxKey() {
	   Assert.assertTrue(MessageIDGenerator.getInstance().generateClientMessageId() != 0);
   }
}