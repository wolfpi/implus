package com.baidu.imc.impl.im.client.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.dummy.DummyMsgStore;
import com.baidu.im.testutil.TestFacade;
import com.baidu.imc.impl.im.client.IMChatHistoryImpl;
import com.baidu.imc.impl.im.transaction.IMTransactionFlow;
import com.baidu.imc.type.AddresseeType;
public class IMChatHistoryImplTest extends InstrumentationTestCase {

    public static final String TAG = "IMInboxImplTest";
    
    private TestFacade mTestFacade = new TestFacade();
    private IMTransactionFlow tf = new IMTransactionFlow();
    private DummyMsgStore mMsgStore = new DummyMsgStore();
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testInvalidChatHistory() {
	  try{
		   new IMChatHistoryImpl(null,null,null,null,null);
	  }
	  catch (Exception e)
	  {
		  Assert.assertTrue(true);
		  return;
	  }	  
	  Assert.assertTrue(false);
   }
   
   public void testChatHistory() {
	  IMChatHistoryImpl chatHistory  = new IMChatHistoryImpl(mMsgStore,tf,AddresseeType.USER,mTestFacade.getAddresseeID(),"0");
	  Assert.assertTrue(chatHistory.getAddresseeID().equals( mTestFacade.getAddresseeID()));
	  Assert.assertTrue(chatHistory.getAddresserID().equals("0") );
	  Assert.assertTrue(chatHistory.getAddresseeType() == AddresseeType.USER);
	  Assert.assertTrue(chatHistory.getMessageList(0, 0) == null);
	  Assert.assertTrue(chatHistory.getTransactionFlow() == tf);
   }
}