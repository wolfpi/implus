package com.baidu.imc.impl.im.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import android.text.TextUtils;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.callback.PageableResult;
import com.baidu.imc.callback.PageableResultCallback;
import com.baidu.imc.client.IMConversation;
import com.baidu.imc.exception.IllegalParameterException;
import com.baidu.imc.exception.InitializationException;
import com.baidu.imc.impl.im.callback.PageableResultImpl;
import com.baidu.imc.impl.im.message.BDHiIMMessage;
import com.baidu.imc.impl.im.store.IMsgStore;
import com.baidu.imc.impl.im.transaction.IMReadAck;
import com.baidu.imc.impl.im.transaction.IMTransactionFlow;
import com.baidu.imc.impl.im.util.InboxKey;
import com.baidu.imc.listener.IMConversationListener;
import com.baidu.imc.message.IMMessage;
import com.baidu.imc.message.Message;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.IMMessageChange;

public class IMConversationImpl extends IMChatHistoryImpl implements IMConversation, IMConversationMessageListener {

    private static final String TAG = "IMConversation";
    private IMConversationListener conversationListener;
    // private IMMessageBroadcastReceiver msgReceiver;
    private IMInboxImpl inboxManager;
    private IMReadAck imReadAck;

    private AtomicBoolean isReceivable;
    private AtomicBoolean isActive;

    public IMConversationImpl(IMInboxImpl conversationManager, /* IMMessageBroadcastReceiver msgReceiver, */
            IMsgStore msgStore, IMTransactionFlow transactionFlow, AddresseeType addresseeType, String addresseeID,
            String addresserID) {
        super(msgStore, transactionFlow, addresseeType, addresseeID, addresserID);
        if (transactionFlow == null || addresseeType == null || TextUtils.isEmpty(addresseeID)
                || TextUtils.isEmpty(addresserID) || conversationManager == null) {
            throw new InitializationException();
        }

        this.inboxManager = conversationManager;
        this.imReadAck = new IMReadAck();

        isReceivable = new AtomicBoolean(false);
        isActive = new AtomicBoolean(false);
    }

    @Override
    public void start() {
        if (isReceivable.compareAndSet(false, true)) {
            LogUtil.printIm(TAG, "Start IMConversation. AddresseeType:" + addresseeType + " AddresseeID:" + addresseeID);
            inboxManager.addListener(this);
            inboxManager.addActiveInbox(this);
            active();
        }
    }

    @Override
    public void close() {
        if (isReceivable.compareAndSet(true, false)) {
            LogUtil.printIm(TAG, "Close IMConversation. AddresseeType:" + addresseeType + " AddresseeID:" + addresseeID);
            inboxManager.removeListener(this);
            inboxManager.removeActiveInbox(this);
            inboxManager.getMessageAvoidDuplication().clear();
            deactive();
        }
    }

    @Override
    public void active() {
        if (isActive.compareAndSet(false, true)) {
            LogUtil.printIm(TAG, "Active IMConversation. AddresserID:" + addresserID + " AddresseeType:"
                    + addresseeType + " AddresseeID:" + addresseeID);
            imReadAck.start(addresserID, addresseeType, addresseeID, msgStore, transactionFlow, null);
        }
    }

    @Override
    public void deactive() {
        if (isActive.compareAndSet(true, false)) {
            LogUtil.printIm(TAG, "Deactive IMConversation. AddresseeType:" + addresseeType + " AddresseeID:"
                    + addresseeID);
            imReadAck.stop();
        }
    }

    @Override
    public void setIMConversationListener(IMConversationListener listener) {
        LogUtil.printIm(TAG, "Set IMConversationListener.");
        conversationListener = listener;
    }

    public IMConversationListener getIMConversationListener() {
        return conversationListener;
    }

    @Override
    public void getMessageList(long beforeMessageID, int num, int timeout,
            final PageableResultCallback<IMMessage> callback) {
        LogUtil.printIm(TAG, "Get message list. beforeMessageID:" + beforeMessageID + " num:" + num + " timeout:"
                + timeout);
        getTransactionFlow().queryMsgs(msgStore, addresseeType, addresseeID, addresserID, beforeMessageID, num,
                timeout, new PageableResultCallback<IMMessage>() {

                    @Override
                    public void result(PageableResult<IMMessage> msgResult, Throwable t) {
                        if (msgResult != null && msgResult.getList() != null && !msgResult.getList().isEmpty()) {
                            List<IMMessage> msgList = new ArrayList<IMMessage>();
                            
                            HashSet<String> msgCache=new HashSet<String>();
                            for (IMMessage message : msgResult.getList()) {
                                if (message != null) {
                                    boolean isDuplicated =
                                            inboxManager.getMessageAvoidDuplication().isMessageDuplicated(
                                                    message.getAddresseeType().name(), message.getAddresseeID(),
                                                    message.getAddresserID(),
                                                    ((BDHiIMMessage) message).getClientMessageID(), message);
                                    
                                    String msgKey = getServerMsgKey(message);
                                    if (!msgCache.contains(msgKey)) 
                                    {
                                        LogUtil.printIm(TAG, "It is not duplicated.");
                                        msgList.add(message);
                                    } else {
                                    	msgCache.add(msgKey);
                                        LogUtil.printIm(TAG, "It is duplicated. " + message.toString());
                                    }
                                }
                            }
                            if (callback != null) {
                                PageableResultImpl tmpResult = new PageableResultImpl();
                                tmpResult.setList(msgList);
                                tmpResult.setTotal(msgList.size());
                                callback.result(tmpResult, t);
                            }
                        } else {
                            if (callback != null) {
                                callback.result(msgResult, t);
                            }
                        }
                    }

                });
    }

    @Override
    public void sendMessage(Message message) throws IllegalParameterException {
        LogUtil.printIm(TAG, "Send a new im message.");
        if (message == null) {
            return;
        }
        getTransactionFlow().sendMsg(addresseeType, addresseeID, addresserID, message, msgStore, this);
    }

    @Override
    public void onNewMessageReceived(IMMessage message) {
        if (message == null) {
            return;
        }
        LogUtil.printIm(TAG, "Receive a new im message.");

        boolean isDuplicated =
                inboxManager.getMessageAvoidDuplication().isMessageDuplicated(message.getAddresseeType().name(),
                        message.getAddresseeID(), message.getAddresserID(),
                        ((BDHiIMMessage) message).getClientMessageID(), message);
        if (!isDuplicated) {
            LogUtil.printIm(TAG, "It is not duplicated.");
            if (isReceivable.get() && conversationListener != null && null != addresseeType && null != addresseeID
                    && null != addresserID) {
                String key1 = InboxKey.getInboxKey(addresseeType, addresseeID);
                String key2 = null;
                
                if(message.getAddresseeType() == AddresseeType.USER)
                {
                if (addresserID.equals(message.getAddresseeID())) {
                    key2 = InboxKey.getInboxKey(message.getAddresseeType(), message.getAddresserID());
                } else {
                    key2 = InboxKey.getInboxKey(message.getAddresseeType(), message.getAddresseeID());
                }
                }
                else //group or service type
                {
                	 key2 = InboxKey.getInboxKey(message.getAddresseeType(), message.getAddresseeID());
                }
                if (key1.equals(key2)) {
                    conversationListener.onNewMessageReceived(message);
                }
            }
        } else {
            LogUtil.printIm(TAG, "It is duplicated. " + message.toString()+" call onMessageChanged()");
            // 是重复的 Message, 但是调用 change 回调
            conversationListener.onMessageChanged(message, null);
        }
    }

    @Override
    public void onMessageChanged(IMMessage newMessage, Map<IMMessageChange, Object> changes) {
        if (newMessage == null) {
            return;
        }
        LogUtil.printIm(TAG, "Update a im message.");
        if (isReceivable.get() && conversationListener != null && null != addresseeType && null != addresseeID
                && null != addresserID) {
            String key1 = InboxKey.getInboxKey(addresseeType, addresseeID);
            String key2 = null;
            if(newMessage.getAddresseeType() == AddresseeType.USER)
            {
            if (addresserID.equals(newMessage.getAddresseeID())) {
                key2 = InboxKey.getInboxKey(newMessage.getAddresseeType(), newMessage.getAddresserID());
            } else {
                key2 = InboxKey.getInboxKey(newMessage.getAddresseeType(), newMessage.getAddresseeID());
            }
            }
            else
            {
            	key2 = key1;
            }
            if (key1.equals(key2)) {
                conversationListener.onMessageChanged(newMessage, null);
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            close();
        } catch (Exception e) {

        }
    }
    
    private String  getServerMsgKey(IMMessage msg)
    {
    	  String key;
          if (msg.getAddresserID().compareTo(msg.getAddresseeID()) < 0) {
              key = msg.getAddresseeType().name() + ":" + msg.getAddresserID() + ":" + msg.getAddresseeID() + ((BDHiIMMessage) msg).getClientMessageID();
          } else {
              key = msg.getAddresseeType().name() + ":" + msg.getAddresseeID() + ":" + msg.getAddresserID() + ((BDHiIMMessage) msg).getClientMessageID();
          }

          return key;
    }
}
