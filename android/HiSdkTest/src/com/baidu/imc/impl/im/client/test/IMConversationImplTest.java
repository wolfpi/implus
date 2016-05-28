package com.baidu.imc.impl.im.client.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.dummy.DummyMsgStore;
import com.baidu.im.testutil.TestFacade;
import com.baidu.imc.IMPlusSDK;
import com.baidu.imc.impl.im.client.IMConversationImpl;
import com.baidu.imc.impl.im.client.IMInboxImpl;
import com.baidu.imc.impl.im.transaction.IMTransactionFlow;
import com.baidu.imc.type.AddresseeType;
public class IMConversationImplTest extends InstrumentationTestCase {

    public static final String TAG = "IMConversationImplTest";
    
    private TestFacade mTestFacade = new TestFacade();
    private IMTransactionFlow tf = new IMTransactionFlow();
    private DummyMsgStore mMsgStore = new DummyMsgStore();
    private IMInboxImpl inBox = null;
    
    @Override
    protected void setUp() {
       IMPlusSDK.init(mTestFacade.getAppkey(), this.getInstrumentation().getContext());
       inBox = new IMInboxImpl( IMPlusSDK.getImpClient(),tf,mMsgStore);
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testInvalidConversation() {
	  try{
		   new IMConversationImpl(null,null,null,null,null,null);
	  }
	  catch (Exception e)
	  {
		  Assert.assertTrue(true);
		  return;
	  }	  
	  Assert.assertTrue(false);
   }
   
   public void testConversation() {
	   IMConversationImpl conversation = new IMConversationImpl( inBox,mMsgStore,tf,AddresseeType.USER,"1","2");
	   conversation.sendMessage(null);
	   conversation.onNewMessageReceived(null);
   }
}