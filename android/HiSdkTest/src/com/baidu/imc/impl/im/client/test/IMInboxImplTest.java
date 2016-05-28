package com.baidu.imc.impl.im.client.test;

import java.util.List;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.dummy.DummyMsgStore;
import com.baidu.im.testutil.TestFacade;
import com.baidu.imc.impl.im.client.IMConversationImpl;
import com.baidu.imc.impl.im.client.IMInboxImpl;
import com.baidu.imc.impl.im.message.BDHiIMMessage;
import com.baidu.imc.impl.im.message.BDHiIMTransientMessage;
import com.baidu.imc.impl.im.store.IMsgStore;
import com.baidu.imc.impl.im.store.MemoryMsgStore;
import com.baidu.imc.impl.im.transaction.IMTransactionFlow;
import com.baidu.imc.listener.IMInboxListener;
import com.baidu.imc.message.IMInboxEntry;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.NotificationType;

public class IMInboxImplTest extends InstrumentationTestCase {

    public static final String TAG = "IMInboxImplTest";
    
    private TestFacade mTestFacade = new TestFacade();
    private IMsgStore msgStore = new MemoryMsgStore();
    private IMTransactionFlow tf = new IMTransactionFlow();
   
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testActiveInbox() {
	   IMInboxImpl inbox = new IMInboxImpl(null,null,null);
	   inbox.addActiveInbox( null);
	   IMConversationImpl conversation = new IMConversationImpl( inbox,msgStore,tf,AddresseeType.USER,"1","2");  
	   inbox.addActiveInbox(conversation);
   }
   
   public void testsetIMInboxListener() {
	   IMInboxImpl inbox = new IMInboxImpl(null,null,null);
	   inbox.setIMInboxListener(null);
	   inbox.setIMInboxListener(null);
	   inbox.setIMInboxListener(null);
	   
	   IMInboxListener listener = new DummyInboxListener();
	   inbox.setIMInboxListener(listener);
	   inbox.setIMInboxListener(listener);
	   inbox.setIMInboxListener(listener);
      }
   
   public void testsetgetIMInboxEntryList() {
	   IMInboxImpl inbox = new IMInboxImpl(null,null,null);
	   Assert.assertTrue(inbox.getIMInboxEntryList() == null);
	   
	   inbox = new IMInboxImpl(null,null,msgStore);
	   inbox.getIMInboxEntryList();
      }
   
   public void testdeleteIMInboxEntry() {
	   IMInboxImpl inbox = new IMInboxImpl(null,null,null);
	   inbox.deleteIMInboxEntry(null);
	   inbox.deleteIMInboxEntry("");
      }
   
   public void testdeleteAllIMInboxEntry() {
	   IMInboxImpl inbox = new IMInboxImpl(null,null,null);
	   inbox.deleteAllIMInboxEntry();
	   inbox.deleteAllIMInboxEntry();
	   inbox.deleteAllIMInboxEntry();
      }
   
   public void testonNewMessageReceived() {
	   IMInboxImpl inbox = new IMInboxImpl(null,null,null);
	   inbox.onNewMessageReceived(null);
	   inbox.onNewMessageReceived(new BDHiIMMessage());
      }
   
   public void testonNewTransientMessageReceived() {
	   IMInboxImpl inbox = new IMInboxImpl(null,null,null);
	   inbox.onNewTransientMessageReceived(null);
	   inbox.onNewTransientMessageReceived(new BDHiIMTransientMessage());
      }
   
   public void testaddActiveInbox() {
	   IMInboxImpl inbox = new IMInboxImpl(null,null,null);
	   inbox.addActiveInbox(null);

	   IMConversationImpl conversation = new IMConversationImpl( inbox,msgStore,tf,AddresseeType.USER,"1","2");  
	   inbox.addActiveInbox(conversation);
	   inbox.addActiveInbox(conversation);
	   inbox.addActiveInbox(conversation);
      }
   public void testremoveActiveInbox() {
	   IMInboxImpl inbox = new IMInboxImpl(null,null,null);
	   IMConversationImpl conversation = new IMConversationImpl( inbox,msgStore,tf,AddresseeType.USER,"1","2");  
	   inbox.addActiveInbox(conversation);
	   inbox.removeActiveInbox(null);
	   inbox.removeActiveInbox(conversation);
	   inbox.removeActiveInbox(conversation);
      }
}

class DummyInboxListener implements IMInboxListener
{
	public  void onIMInboxEntryChanged(List<IMInboxEntry> arg0)
	{}
	 
	public  void onNewIMTransientMessageReceived(com.baidu.imc.message.IMTransientMessage arg0)
	{}

	@Override
	public void onNotificationTypeSetting(AddresseeType addresseeType, String s, NotificationType notificationType) {

	}
}
