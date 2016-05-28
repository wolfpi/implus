package com.baidu.imc.impl.im.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.callback.PageableResult;
import com.baidu.imc.callback.PageableResultCallback;
import com.baidu.imc.client.IMClient;
import com.baidu.imc.client.IMInbox;
import com.baidu.imc.exception.InitializationException;
import com.baidu.imc.impl.im.message.BDHiIMMessage;
import com.baidu.imc.impl.im.message.IMMessageListener;
import com.baidu.imc.impl.im.store.IMsgStore;
import com.baidu.imc.impl.im.store.MemoryMsgStore;
import com.baidu.imc.impl.im.transaction.IMTransactionFlow;
import com.baidu.imc.impl.im.util.MessageAvoidDuplication;
import com.baidu.imc.listener.IMInboxListener;
import com.baidu.imc.message.IMInboxEntry;
import com.baidu.imc.message.IMMessage;
import com.baidu.imc.message.IMTransientMessage;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.IMMessageStatus;
import com.baidu.imc.type.NotificationType;
import com.baidu.imc.type.UserStatus;

import android.text.TextUtils;

/**
 *
 * <b>会话管理器</b>
 * <p>
 * 管理会话相关操作
 * </p>
 *
 */
public class IMInboxImpl implements IMInbox, IMMessageListener, UserStatusListener, IMInboxListener {

    private static final String TAG = "IMInbox";
    private IMClient imClient;
    private IMTransactionFlow transactionFlow;
    private IMsgStore msgStore;
    private final List<IMConversationImpl> activeInboxList = new ArrayList<IMConversationImpl>();
    private IMInboxListener oldListener;
    private AtomicBoolean isInitized;
    private UserStatus userStatus;
    private AtomicBoolean isUserStatusChanged;
    private MessageAvoidDuplication messageAvoidDuplication = new MessageAvoidDuplication();

    public IMInboxImpl(IMClient imClient, IMTransactionFlow transactionFlow, IMsgStore msgStore) {

        if (imClient == null || transactionFlow == null || msgStore == null) {
            throw new InitializationException();
        }
        this.imClient = imClient;
        this.transactionFlow = transactionFlow;
        this.msgStore = msgStore;
        this.isInitized = new AtomicBoolean(false);
        this.userStatus = imClient.getCurrentUserStatus();
        this.msgStore.addIMInboxListener(this);
        this.isUserStatusChanged = new AtomicBoolean(false);
    }

    @Override
    public void setIMInboxListener(IMInboxListener listener) {
        LogUtil.printIm(TAG, "Set a new IMInbox Listener.");
        if (msgStore == null) {
            return;
        }
        if (oldListener != null) {
            msgStore.removeIMInboxListener(oldListener);
        }
        if(listener != null) {
            msgStore.addIMInboxListener(listener);
        }
        oldListener = listener;
    }

    @Override
    public List<IMInboxEntry> getIMInboxEntryList() {
        if (isInitized.compareAndSet(false, true)) {
            LogUtil.printIm(TAG, "Initializing.");
            getIMInboxEntryListFromRemote();
            return getIMInboxEntryListFromLocal();
        } else {
            LogUtil.printIm(TAG, "Initialized.");
            return getIMInboxEntryListFromLocal();
        }
    }

    private void getIMInboxEntryListFromRemote() {
        LogUtil.printIm(TAG, "Get IMInbox List from remote.");
        if (imClient != null && transactionFlow != null && msgStore!= null) {
            String currentUserID = imClient.getCurrentUserID();
            if (!TextUtils.isEmpty(currentUserID)) {
                transactionFlow.queryActiveContacts(currentUserID, 100, msgStore);
            }
        }
    }

    private List<IMInboxEntry> getIMInboxEntryListFromLocal() {
        LogUtil.printIm(TAG, "Get IMInbox List from database.");
        if (msgStore == null) {
            return null;
        }
        return msgStore.getIMInboxEntryList();
    }

    @Override
    public void deleteIMInboxEntry(String ID) {
        if (TextUtils.isEmpty(ID) || msgStore == null) {
            return;
        }
        LogUtil.printIm(TAG, "Delete a IMInbox from database. ID:" + ID);
        msgStore.deleteIMInboxEntry(ID);
    }

    @Override
    public void deleteIMInboxEntry(AddresseeType addresseeType, String addresseeID) {
        if (null == addresseeType || TextUtils.isEmpty(addresseeID)) {
            return;
        }
        LogUtil.printIm(TAG, "Delete a IMInbox from database. AddresseeType:" + addresseeType + " AddresseeID:"
                + addresseeID);
        msgStore.deleteIMInboxEntry(addresseeType, addresseeID);
    }

    @Override
    public void deleteAllIMInboxEntry() {
        LogUtil.printIm(TAG, "Delete All IMInboxs from database.");
        if (msgStore == null) {
            return;
        }
        msgStore.deleteAllIMInboxEntry();
    }

    @Override
    public void onNewMessageReceived(IMMessage message) {
        saveMessage(message, true);
    }

    @Override
    public void onNewTransientMessageReceived(IMTransientMessage message) {
        if (message == null || msgStore == null) {
            return;
        }
        LogUtil.printIm(TAG, "Receive a new transient message. ");
        msgStore.saveIMTransientMessage(message);
    }

    public void addActiveInbox(IMConversationImpl imConversation) {
        if (null == imConversation) {
            return;
        }
        LogUtil.printIm(TAG, "Add a new active IMConversation. AddresseeType:" + imConversation.getAddresseeType()
                + " AddresseeID:" + imConversation.getAddresseeID());

        synchronized (activeInboxList) {
            if(imConversation != null)
            {
                activeInboxList.remove(imConversation);
                activeInboxList.add(imConversation);
            }
        }
    }

    public void removeActiveInbox(IMConversationImpl imConversation) {
        if (null == imConversation) {
            return;
        }
        LogUtil.printIm(TAG, "Remove a new active IMConversation. AddresseeType:" + imConversation.getAddresseeType()
                + " AddresseeID:" + imConversation.getAddresseeID());
        synchronized (activeInboxList) {
            if(imConversation != null)
                activeInboxList.remove(imConversation);
        }
    }

    private List<IMConversationMessageListener> listenerList = new ArrayList<IMConversationMessageListener>();

    public void addListener(IMConversationMessageListener listener) {
        removeListener(listener);
        listenerList.add(listener);
    }

    public void removeListener(IMConversationMessageListener listener) {
        listenerList.remove(listener);
    }

    @Override
    public void onUserStatusChanged(UserStatus status) {
        if (null != status && status != userStatus && status == UserStatus.ONLINE) {
            isInitized.set(false);
            getIMInboxEntryListFromRemote();
            getChatSettingFromRemote();
            isUserStatusChanged.set(true);
        }
        userStatus = status;
    }

    private void getChatSettingFromRemote() {
        LogUtil.printIm(TAG, "Get ChatSetting List from remote.");
        if (imClient != null && transactionFlow != null && msgStore!= null) {
            String currentUserID = imClient.getCurrentUserID();
            if (!TextUtils.isEmpty(currentUserID)) {
                transactionFlow.queryChatSetting(msgStore);
            }
        }

    }

    @Override
    public void onIMInboxEntryChanged(List<IMInboxEntry> inboxEntryList) {
        if (null != inboxEntryList && !inboxEntryList.isEmpty()) {
            if (isUserStatusChanged.compareAndSet(true, false)) {

                synchronized (activeInboxList) {
                    for (int i = 0; i < activeInboxList.size(); i++) {
                        final IMConversationImpl conversation = activeInboxList.get(i);
                        if (conversation != null && conversation.getAddresseeType() != null
                                && !TextUtils.isEmpty(conversation.getAddresseeID())
                                && conversation.getIMConversationListener() != null) {
                            String conversationID =
                                    conversation.getAddresseeType() + ":" + conversation.getAddresseeID();
                            LogUtil.printIm(TAG, "Active conversation: " + conversationID);
                            for (int j = 0; j < inboxEntryList.size(); j++) {
                                IMInboxEntry newInboxEntry = inboxEntryList.get(j);
                                if (conversationID.equals(newInboxEntry.getID()) && newInboxEntry != null
                                        && newInboxEntry.getUnreadCount() > 0) {
                                    LogUtil.printIm(TAG,
                                            "Active conversation get new messages. " + newInboxEntry.getUnreadCount());
                                    conversation.getMessageList(0,
                                            newInboxEntry.getUnreadCount() <= 50 ? newInboxEntry.getUnreadCount() : 50,
                                            3000, new PageableResultCallback<IMMessage>() {

                                                @Override
                                                public void result(PageableResult<IMMessage> result, Throwable arg1) {
                                                    if (null != result && null != result.getList()
                                                            && result.getList().size() > 0) {
                                                        for (IMMessage message : result.getList()) {
                                                            LogUtil.printIm(
                                                                    TAG,
                                                                    "Active conversation get new messages. "
                                                                            + message.getMessageID());
                                                            conversation.onNewMessageReceived(message);
                                                        }
                                                    }
                                                }

                                            });
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onNewIMTransientMessageReceived(IMTransientMessage arg0) {

    }

    @Override
    public void onNotificationTypeSetting(AddresseeType addresseeType, String s, NotificationType notificationType) {

    }

    public MessageAvoidDuplication getMessageAvoidDuplication() {
        return messageAvoidDuplication;
    }

    /**
     * 保存本地消息
     * @param message
     */
    public void saveMessage(IMMessage message, boolean updateInbox) {
        if (null != message && msgStore != null && null != imClient && !TextUtils.isEmpty(imClient.getCurrentUserID())
                && !TextUtils.isEmpty(message.getAddresseeID())) {
            LogUtil.printIm(TAG, "Receive a new im message. updateInbox = " + updateInbox);
            BDHiIMMessage hiIMMessage = (BDHiIMMessage) message;
            if (!imClient.getCurrentUserID().equals(message.getAddresserID())) {
                hiIMMessage.setStatus(IMMessageStatus.READ);
            } else {
                hiIMMessage.setStatus(IMMessageStatus.SENT);
            }

            long msgId = msgStore.saveIMMessage(message, updateInbox);
            if (msgId > -1 && null != listenerList) {
                hiIMMessage.setMessageID(msgId);
                for (IMConversationMessageListener listener : listenerList) {
                    if (null != listener) {
                        listener.onNewMessageReceived(message);
                    }
                }
            }
        }
    }

    /**
     * 更新消息
     * @param message
     */
    public void updateMessage(IMMessage message){
        if(message == null || message.getMessageID() == 0){
            throw new IllegalArgumentException("message must have id");
        }
        boolean ok = ((MemoryMsgStore) msgStore).updateIMMessage(message);
        if(ok && null != listenerList){
            for (IMConversationMessageListener listener : listenerList) {
                if (null != listener) {
                    listener.onNewMessageReceived(message);
                }
            }
        }
    }

}
