package com.baidu.imc.impl.im.client.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.testutil.TestFacade;
import com.baidu.imc.impl.im.client.IMPClientImpl;
import com.baidu.imc.type.AddresseeType;

public class IMPClientImplTest extends InstrumentationTestCase {

    public static final String TAG = "IMPlusSDKTest";
    
    private TestFacade mFacade = new TestFacade();
    
    @Override
    protected void setUp() {
      
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testNormalConnnectDisconnect() {
	   IMPClientImpl client = new IMPClientImpl();
	   client.init(this.getInstrumentation().getTargetContext(), mFacade.getAppkey());
	   client.connect();
	   client.disconnect();
   }
   
   public void testAbNormalConnnectDisconnect() {
	   IMPClientImpl client = new IMPClientImpl();
	   client.init(this.getInstrumentation().getTargetContext(), mFacade.getAppkey());
	   client.disconnect();
	   client.connect();
	   client.disconnect();
	   client.disconnect();
	   client.disconnect();
	   
	   client.connect();
	   client.connect();
	   client.connect();
	   client.connect();
	   client.connect();
	   client.connect();
	   client.connect();
	   client.connect();
   }
   public void testgetIMChatHistory()
   {
	   IMPClientImpl client = new IMPClientImpl();
	   Assert.assertTrue(client.getIMChatHistory(AddresseeType.USER, mFacade.getAddresseeID()) != null);
	   
	   Assert.assertTrue(client.getIMChatHistory(AddresseeType.USER, "") == null);
	   Assert.assertTrue(client.getIMChatHistory(AddresseeType.USER, null) == null);
   }
   
   public void testopenIMConversation()
   {
	   IMPClientImpl client = new IMPClientImpl();
	   Assert.assertTrue(client.openIMConversation(AddresseeType.USER, mFacade.getAddresseeID()) != null);
	   
	   Assert.assertTrue(client.openIMConversation(AddresseeType.USER, "") == null);
	   Assert.assertTrue(client.openIMConversation(AddresseeType.USER, null) == null);
   }
   public void testgetMessageHelper() {
	   IMPClientImpl client = new IMPClientImpl();
	   Assert.assertTrue(client.getMessageHelper()!= null);
   }
   
   public void testgetIMInbox() {
	   IMPClientImpl client = new IMPClientImpl();
	   Assert.assertTrue(client.getIMInbox()!= null);
   }
}
