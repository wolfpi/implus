package com.baidu.imc.impl.im.util.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.imc.impl.im.util.InboxKey;
import com.baidu.imc.type.AddresseeType;
public class InboxKeyTest extends InstrumentationTestCase {

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
	   Assert.assertTrue(InboxKey.getInboxKey(null) == null);
	   Assert.assertTrue(InboxKey.getInboxKey(null, null) == null);
	   Assert.assertTrue(InboxKey.getInboxKey(null,"testkey") == null);
	   Assert.assertTrue(InboxKey.getInboxKey(AddresseeType.USER,"testkey") != null);
	   Assert.assertTrue(InboxKey.getInboxKey(AddresseeType.USER,"testkey").equalsIgnoreCase("user:testkey"));
	   
	   Assert.assertTrue(InboxKey.getInboxKey(null, null, null) == null);
	   Assert.assertTrue(InboxKey.getInboxKey(AddresseeType.USER, null, "test") != null);
   }
}