package com.baidu.im.inapp.messagecenter.test;

import junit.framework.Assert;
import android.test.AndroidTestCase;

import com.baidu.im.inapp.messagecenter.MessageCenter;
import com.baidu.im.sdk.ImMessage;
import com.baidu.im.sdk.MessageType;

public class MessageCenterTest extends AndroidTestCase {

public static final String TAG = "SendingMessageContainerTest";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testMessageContainer() {
	   MessageCenter mc = new MessageCenter();
	   mc.cacheSendingMessage(0, null, null);
	   Assert.assertTrue(mc.getSendingMessage(0) == null);
	   dummyMessage dm = new dummyMessage();
	   
	   mc.cacheSendingMessage(0, dm, null);
	   Assert.assertTrue(mc.getSendingMessage(0) != null);
	   mc.clear();
	   Assert.assertTrue(mc.getSendingMessage(0) == null);
	   
	   mc.addReceivedMessage(null, null);
	   mc.addReceivedMessage("1", dm);
	   Assert.assertTrue(mc.getReceivedMessage("1")!= null);
	   Assert.assertTrue(mc.getAndRemoveReceivedMessage("1")!= null);
	   Assert.assertTrue(mc.getReceivedMessage("1") == null);
	   
   }
}

class dummyMessage implements ImMessage
{

	@Override
	public String getMessageId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageType getType() {
		// TODO Auto-generated method stub
		return null;
	}
	}