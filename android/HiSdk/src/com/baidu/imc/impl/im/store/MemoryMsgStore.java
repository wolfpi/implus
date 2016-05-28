package com.baidu.imc.impl.im.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.callback.PageableResult;
import com.baidu.imc.impl.im.callback.PageableResultImpl;
import com.baidu.imc.impl.im.client.IMPreference;
import com.baidu.imc.impl.im.message.BDHiIMCustomMessage;
import com.baidu.imc.impl.im.message.BDHiIMMessage;
import com.baidu.imc.impl.im.message.BDHiIMVoiceMessage;
import com.baidu.imc.impl.im.message.IMInboxEntryImpl;
import com.baidu.imc.impl.im.message.content.BDHiVoiceMessageContent;
import com.baidu.imc.impl.im.util.InboxKey;
import com.baidu.imc.listener.IMInboxListener;
import com.baidu.imc.message.IMInboxEntry;
import com.baidu.imc.message.IMMessage;
import com.baidu.imc.message.IMTransientMessage;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.IMMessageStatus;
import com.baidu.imc.type.NotificationType;

import android.content.Context;
import android.text.TextUtils;

/**
 * 存储相关类，包括会话数据库、消息相关数据库和用户数据存储相关操作 IMInboxDB, IMMessageDB and IMPreference
 *
 * @author liubinzhe
 */
public class MemoryMsgStore implements IMsgStore {

    private static final String TAG = "MsgStore";
    private final List<IMInboxListener> inboxListeners = new ArrayList<IMInboxListener>();
    private Map<String, IMMessage> mLastSuccessfulMessage = new HashMap<String, IMMessage>();

    private Context context;
    private String appKey;
    private String userID;

    public MemoryMsgStore() {

    }

    public MemoryMsgStore(Context context, String appKey, String userID) {
        this.context = context;
        this.appKey = appKey;
        this.userID = userID;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * 判断当前数据库是否合法，通常用户登录后（获得context、appKey和userID）合法。
     */
    private boolean isValidMsgStore() {
        if (null != getContext() && null != getAppKey() && getAppKey().length() > 0 && null != getUserID()
                && getUserID().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<IMInboxListener> getInboxListeners() {
        return inboxListeners;
    }

    /**
     * 添加一个用户监听会话变化和实时消息的监听器
     */
    @Override
    public void addIMInboxListener(IMInboxListener listener) {
        synchronized(inboxListeners) {
            inboxListeners.add(listener);
        }
    }

    /**
     * 删除一个用户监听会话变化和实时消息的监听器
     */
    @Override
    public void removeIMInboxListener(IMInboxListener listener) {
        synchronized(inboxListeners) {
            inboxListeners.remove(listener);
        }
    }

    /**
     * 通知 InboxListener
     *
     * @param entryList
     */
    private void notifyInboxListeners(List<IMInboxEntry> entryList) {
        if (entryList == null || entryList.isEmpty()) {
            return;
        }
        synchronized(inboxListeners) {
            for (IMInboxListener listener : inboxListeners) {
                if (listener != null) {
                    listener.onIMInboxEntryChanged(entryList);
                }
            }
        }
    }

    /**
     * 获取当前所有用户的会话
     */
    @Override
    public List<IMInboxEntry> getIMInboxEntryList() {
        List<IMInboxEntryImpl> result = new ArrayList<IMInboxEntryImpl>();
        if (isValidMsgStore()) {
            if (getInboxDbUtil() != null) {
                result = getInboxDbUtil().getAllIMInboxEntry();
            }
        }
        if (result != null) {
            LogUtil.printIm(TAG, "[GetAllIMInbox]dbresult:" + !result.isEmpty());
        }
        List<IMInboxEntry> entrys = new ArrayList<IMInboxEntry>();
        entrys.addAll(result);
        return entrys;
    }

    /**
     * 获取一个指定的会话
     *
     * @param addresseeType 对方类型
     * @param addresseeID   对方ID
     */
    @Override
    public IMInboxEntry getIMInbox(AddresseeType addresseeType, String addresseeID) {
        IMInboxEntry entry = null;
        if (isValidMsgStore()) {
            entry = getInboxDbUtil().getIMInbox(addresseeType, addresseeID);
        }
        LogUtil.printIm(TAG, "[GetIMInbox]dbresult:" + (null != entry));
        return entry;
    }

    /**
     * 存储一批会话，通过会话中最后一条消息新增或者更新会话；并通知用户更新界面
     *
     * @param entryList 一批会话
     */
    @Override
    public void saveIMInboxEntryList(List<IMInboxEntry> entryList) {
        // save changed inbox
        for (IMInboxEntry entry : entryList) {
            if (isValidMsgStore()) {
                IMInboxEntryImpl tmpEntry =
                        getInboxDbUtil().getIMInbox(
                                entry.getAddresseeType(), entry.getAddresseeID());
                if (null != tmpEntry && null != entry) {
                    ((IMInboxEntryImpl) entry).setIneffective(tmpEntry.isIneffective());
                    String msg = entry.toString() + ":tmp_" + tmpEntry.toString();
                    LogUtil.printDebug(TAG, msg);
                }
                boolean dbresult =
                        getInboxDbUtil().saveIMInbox(
                                (IMInboxEntryImpl) entry);
                LogUtil.printIm(TAG, "[SaveIMInbox]dbresult:" + dbresult);
                if (entry.getLastMessage() != null && !TextUtils.isEmpty(entry.getLastMessage().getAddresseeID())
                        && !TextUtils.isEmpty(getUserID())) {
                    if (!getUserID().equals(entry.getLastMessage().getAddresserID())) {
                        ((BDHiIMMessage) entry.getLastMessage()).setStatus(IMMessageStatus.READ);
                    } else {
                        ((BDHiIMMessage) entry.getLastMessage()).setStatus(IMMessageStatus.SENT);
                    }
                    this.saveIMMessage(entry.getLastMessage(), false, false);
                }
            }
        }

        // notify inbox
        if (null != entryList && !entryList.isEmpty()) {
            notifyInboxListeners(entryList);
        }
    }
    @Override
    public long saveIMMessage(IMMessage message, boolean updateInbox){
        return saveIMMessage(message, updateInbox, false);
    }

    /**
     * 存储一条消息，并新增或者更新对应会话；通知用户会话变化。
     *
     * @param message IM消息
     */
    public long saveIMMessage(IMMessage message, boolean updateInbox, boolean insertOnly) {

        long id = -1;
        final String addresseeID = message.getAddresseeID();
        final String addresserID = message.getAddresserID();
        if (null == message || null == addresseeID || 0 == addresseeID.length()
                || null == message.getAddresseeType()) {
            return id;
        }

        final BDHiIMMessage imMessage = (BDHiIMMessage) message;
        final boolean isValidMsgStore = isValidMsgStore();
        final boolean isOutgoingMsg = getUserID().equals(addresserID); // 发送方是自己

        boolean changed = false;
        List<IMInboxEntry> entryList = new ArrayList<IMInboxEntry>();
        // IMInboxEntryImpl entry = (IMInboxEntryImpl) inboxMap.get(inboxKey);
        if (!TextUtils.isEmpty(getUserID())) {
            IMInboxEntryImpl entry = null;

            if (isValidMsgStore) {
                String inboxAddresseeID = isOutgoingMsg ? addresserID : addresseeID;

                String key = message.getAddresseeType().name() + "|" + inboxAddresseeID;
                LogUtil.printDebug(TAG, key);
                IMMessage lastSucMsg = this.mLastSuccessfulMessage.get(key);
                if (lastSucMsg != null) {
                    if (((BDHiIMMessage) lastSucMsg).getMsgSeq() < imMessage.getMsgSeq()) {
                        mLastSuccessfulMessage.put(key, message);
                        LogUtil.printIm(TAG, "last success message is updated!!!");
                    }
                }
                entry =
                        getInboxDbUtil().getIMInbox(
                                message.getAddresseeType(), inboxAddresseeID);

                boolean markAsPlayed = shouldMarkAsPlayed(imMessage, entry, isOutgoingMsg);
                markVoiceContentAsPlayed(imMessage, markAsPlayed);

                // 入库
                if(insertOnly) {
                    id = getMessageDbUtil().insertMessage(imMessage);
                }else{
                    id = getMessageDbUtil().saveMessage(imMessage);
                }
                LogUtil.printIm(TAG, "[SaveIMMessage]dbresult:" + (id > -1));
                imMessage.setMessageID(id);
            }

            if (updateInbox) {
                if (entry == null) {
                    // 本地没有对应Inbox, 创建一个
                    entry = new IMInboxEntryImpl();
                    entry.setIneffective(false);
                    LogUtil.printDebug(TAG, "entry did not exist in save im message");
                    entry.setLastMessage(imMessage);
                    entry.setMsgBody(imMessage.getBody());
                    entry.setLastReceiveMessageID(imMessage.getMsgSeq());
                    entry.setLastReceiveMessageTime(imMessage.getServerTime());

                    if (!TextUtils.isEmpty(getUserID())) {
                        if (message.getAddresseeType() == AddresseeType.USER) {
                            if (!isOutgoingMsg) {
                                entry.setAddresseeType(message.getAddresseeType());
                                entry.setAddresseeID(addresserID);
                                entry.setAddresseeName(message.getAddresserName());
                                entry.setUnreadCount(1);
                            } else {
                                entry.setAddresseeType(message.getAddresseeType());
                                entry.setAddresseeID(addresseeID);
                                entry.setAddresseeName(addresseeID);
                                entry.setUnreadCount(0);
                                entry.setLastReadMessageID(imMessage.getMsgSeq());
                                entry.setLastReadMessageTime(imMessage.getServerTime());
                            }
                        } else //group or service
                        {
                            entry.setAddresseeType(message.getAddresseeType());
                            entry.setAddresseeID(message.getAddresseeID());
                            entry.setAddresseeName((((BDHiIMMessage) message).getAddresseeName()));
                            if (isOutgoingMsg) {
                                entry.setUnreadCount(0);
                                entry.setLastReadMessageID(((BDHiIMMessage) message).getMsgSeq());
                                entry.setLastReadMessageTime(((BDHiIMMessage) message).getServerTime());
                            } else {
                                entry.setUnreadCount(1);
                            }
                        }

                        // entry.setUnreadCount((int) (entry.getLastReceiveMessageID() - entry.getLastReadMessageID()));
                        boolean dbresult = false;
                        if (isValidMsgStore) {
                            dbresult = getInboxDbUtil().saveIMInbox(entry);
                        }
                        LogUtil.printIm(TAG, "[InsertIMInbox]dbresult:" + dbresult);

                        changed = true;

                        entryList.add(entry);
                    }
                } else {
                    // 本地有对应Inbox, 更新最后消息
                    if (imMessage.getMsgSeq() > entry.getLastReceiveMessageID()) {
                        // 如果新消息更新, 则更新lastMessageID
                        // inboxSet.remove(entry);
                        entry.setIneffective(false);

                        String msg = "Ineffective " + entry.getLastReceiveMessageID() + "__" + imMessage
                                .getMsgSeq();
                        LogUtil.printDebug(TAG, msg);

                        entry.setLastMessage(imMessage);
                        entry.setMsgBody(imMessage.getBody());
                        if (!TextUtils.isEmpty(getUserID())) {
                            entry.setLastReceiveMessageID(imMessage.getMsgSeq());
                            entry.setLastReceiveMessageTime(imMessage.getServerTime());
                            if (isOutgoingMsg) {
                                entry.setLastReadMessageID(imMessage.getMsgSeq());
                                entry.setLastReadMessageTime(imMessage.getServerTime());
                            }
                            entry.setUnreadCount(
                                    (int) (entry.getLastReceiveMessageID() - entry.getLastReadMessageID()));
                            // inboxSet.add(entry);

//                            if (!TextUtils.isEmpty(getUserID()) && isOutgoingMsg) {
//                                entry.setAddresseeName(message.getAddresserName());
//                            }

                            boolean dbresult =
                                    getInboxDbUtil().saveIMInbox(entry);
                            LogUtil.printIm(TAG, "[UpdateIMInbox]dbresult:" + dbresult);

                            changed = true;

                            entryList.add(entry);
                        }
                    }
                }
            }
        }

        if (changed) {
            // notify inbox
            synchronized(inboxListeners) {
                for (IMInboxListener listener : inboxListeners) {
                    if (listener != null) {
                        listener.onIMInboxEntryChanged(entryList);
                    }
                }
            }
        }
        return id;
    }


    public boolean updateIMMessage(IMMessage message) {
        long id = -1;
        final String addresseeID = message.getAddresseeID();
        final String addresserID = message.getAddresserID();
        if (null == message || null == addresseeID || 0 == addresseeID.length()
                || null == message.getAddresseeType()) {
            return false;
        }

        final BDHiIMMessage imMessage = (BDHiIMMessage) message;
        id = getMessageDbUtil().saveMessage(imMessage);
        LogUtil.printIm(TAG, "[updateIMMessage]dbresult:" + (id > -1));
        imMessage.setMessageID(id);

        if (id != -1) {
            return true;
        }
        return false;
    }

    protected static void markVoiceContentAsPlayed(BDHiIMMessage imMessage, boolean played) {
        if (imMessage instanceof BDHiIMVoiceMessage) {
            BDHiIMVoiceMessage bdHiIMVoiceMessage = (BDHiIMVoiceMessage) imMessage;
            ((BDHiVoiceMessageContent) bdHiIMVoiceMessage.getVoice()).setPlayed(played, false);
        } else if (imMessage instanceof BDHiIMCustomMessage) {
            BDHiIMCustomMessage bdHiIMVoiceMessage = (BDHiIMCustomMessage) imMessage;
            for (BDHiIMCustomMessage.IMMessageContentEntry imMessageContentEntry : bdHiIMVoiceMessage
                    .getAllMessageContent()) {
                if (imMessageContentEntry.messageContent instanceof BDHiVoiceMessageContent) {
                    BDHiVoiceMessageContent content =
                            (BDHiVoiceMessageContent) imMessageContentEntry.messageContent;
                    content.setPlayed(played, false);
                }
            }
        }
    }

    @Override
    public long saveIMMessage(IMMessage message) {
        return saveIMMessage(message, true);
    }


    private boolean shouldMarkAsPlayed(BDHiIMMessage imMessage, IMInboxEntryImpl entry, boolean isOutGoingMsg) {
        long lastReadMessageID = entry == null ? 0 : entry.getLastReadMessageID();

        boolean markAsPlayed = true;
        if (isOutGoingMsg) {
            // 是发出的消息, 则直接设置为已读
            markAsPlayed = true;
        } else {
            if (lastReadMessageID == 0) {
                if (entry == null) {
                    // 如果没有最后一条已读
                    markAsPlayed = imMessage.getStatus() == IMMessageStatus.READ;
                } else {
                    // 如果有最后一条消息
                    markAsPlayed = false;
                }
            } else if (imMessage.getMsgSeq() > lastReadMessageID) {
                // 将语音消息标记为未读, 此标记和消息一起入库
                // TODO ZX 还需推敲
                markAsPlayed = false;
            } else {
                // 将语音消息标记为已读，此标记和消息一起入库
                markAsPlayed = true;
            }
        }
        return markAsPlayed;
    }

    /**
     * 存储一条实时消息（目前并未存储）；通知用户新增实时消息
     */
    @Override
    public void saveIMTransientMessage(IMTransientMessage message) {
        LogUtil.printIm(TAG, "Receive a transient message.");
        // notify inbox
        List<IMInboxListener> inboxListeners = new ArrayList<IMInboxListener>();
        synchronized(inboxListeners) {
            inboxListeners.addAll(this.inboxListeners);
        }

        for (IMInboxListener listener : inboxListeners) {
            listener.onNewIMTransientMessageReceived(message);
        }
    }

    /**
     * 删除指定会话
     *
     * @param key 对方类型:对方ID eg.USER:123
     */
    @Override
    public void deleteIMInboxEntry(String key) {
        if (null == key || key.length() == 0) {
            return;
        }
        String[] keys = key.split(":");
        if (null == keys || keys.length != 2) {
            return;
        }
        // IMInboxEntryImpl entry = (IMInboxEntryImpl) inboxMap.get(key);
        IMInboxEntryImpl entry = null;
        if (isValidMsgStore()) {
            entry =
                    getInboxDbUtil().getIMInbox(
                            AddresseeType.valueOf(keys[0]), keys[1]);
        }
        LogUtil.printIm(TAG, "[GETIMInbox]dbresult:" + (null != entry));
        if (entry != null) {
            // remove inbox
            boolean dbresult = false;
            if (isValidMsgStore()) {
                dbresult =
                        getInboxDbUtil().removeIMInbox(
                                AddresseeType.valueOf(keys[0]), keys[1]);
            }
            LogUtil.printIm(TAG, "[RemoveIMInbox]dbresult:" + dbresult);

            // get the IMInboxEntryList
            List<IMInboxEntry> entryList = new ArrayList<IMInboxEntry>();
            entryList.add(entry);

            // notify inbox
            List<IMInboxListener> conversationListenerList = new ArrayList<IMInboxListener>();
            synchronized(conversationListenerList) {
                conversationListenerList.addAll(this.inboxListeners);
            }

            for (IMInboxListener listener : conversationListenerList) {
                if (listener != null) {
                    listener.onIMInboxEntryChanged(entryList);
                }
            }
        }
    }

    /**
     * 删除指定会话
     *
     * @param addresseeType 对方类型
     * @param addresseeID   对方ID
     */
    @Override
    public void deleteIMInboxEntry(AddresseeType addresseeType, String addresseeID) {
        String inboxKey = InboxKey.getInboxKey(addresseeType, addresseeID);
        deleteIMInboxEntry(inboxKey);
    }

    /**
     * 删除所有会话
     */
    @Override
    public void deleteAllIMInboxEntry() {
        // List<IMInboxEntry> inboxList = new ArrayList<IMInboxEntry>();
        // if (isValidMsgStore()) {
        // inboxList.addAll(IMInboxDBUtil.getDB(getContext(), getAppKey() + getUserID()).getAllIMInboxEntry());
        // }
        // LogUtil.printIm(TAG, "[GetAllIMInbox]dbresult:" + !inboxList.isEmpty());

        boolean dbresult = false;
        if (isValidMsgStore()) {
            dbresult = getInboxDbUtil().removeAllImInbox();
        }
        LogUtil.printIm(TAG, "[DeleteAllIMInbox]dbresult:" + dbresult);
    }

    /**
     * 获取指定消息列表
     *
     * @param addresseeType 对方类型
     * @param addresseeID   对方ID
     * @param pageNum
     * @param pageSize
     */

    /*
    @Override
    public PageableResult<IMMessage> getMessageList(AddresseeType addresseeType, String addresseeID, int pageNum,
                                                    int pageSize) {
        if (pageNum < 0) {
            pageNum = 0;
        }
        PageableResultImpl result = new PageableResultImpl();
        if (null == addresseeType || null == addresseeID || addresseeID.length() == 0) {
            return result;
        }
        List<BDHiIMMessage> messagesSet = new ArrayList<BDHiIMMessage>();
        if (isValidMsgStore()) {
            messagesSet =
                    IMMessageDBUtil.getDB(getContext(), getAppKey() + getUserID()).getMessageList(addresseeType,
                            addresseeID);
        }
        LogUtil.printIm(TAG, "[GetMessageList]dbresult:" + !messagesSet.isEmpty());
        if (null == messagesSet || messagesSet.isEmpty()) {
            return result;
        }

        IMMessage[] msgs = messagesSet.toArray(new IMMessage[messagesSet.size()]);
        messagesSet.toArray(msgs);

        List<IMMessage> msgsList = new ArrayList<IMMessage>();
        int totalSize = msgs.length;

        int startNo = totalSize - pageSize * (pageNum + 1);
        int endNo = totalSize - pageSize * pageNum;
        if (startNo < 0) {
            startNo = 0;
        }

        for (int i = startNo; i < endNo && i < totalSize; i++) {
            msgsList.add(msgs[i]);
        }

        result.setList(msgsList);
        result.setTotal(totalSize);
        return result;
    }*/

    /**
     * 获取指定消息列表
     *
     * @param addresseeType   对方类型
     * @param addresseeID     对法ID
     * @param beforeMessageID
     * @param num
     */
    /*
    public PageableResult<IMMessage> getMessageListByMsgId(AddresseeType addresseeType, String addresseeID,
                                                           long beforeMessageID, int num) {

        long lastQueryCientID = -1;

        PageableResultImpl result = new PageableResultImpl();
        if (null == addresseeType || null == addresseeID || addresseeID.length() == 0) {
            return result;
        }
        List<BDHiIMMessage> messagesSet = new ArrayList<BDHiIMMessage>();
        BDHiIMMessage message = null;
        long beforeMsgSeq = 0;
        if (isValidMsgStore()) {
            message =
                    IMMessageDBUtil.getDB(getContext(), getAppKey() + getUserID()).getMessageByMessageID(addresseeType,
                            addresseeID, beforeMessageID);

        }
        beforeMsgSeq = (null != message) ? message.getMsgSeq() : 0;

        if (message != null) {
            lastQueryCientID = message.getClientMessageID();
        }

        LogUtil.printIm(TAG, "[getMessageByMessageID]dbresult:" + (null != message));
        if (isValidMsgStore()) {
            messagesSet =
                    IMMessageDBUtil.getDB(getContext(), getAppKey() + getUserID()).getMessageList3(addresseeType,
                            addresseeID);
        }
        int size = messagesSet.size();
        LogUtil.printIm(TAG, "[GetMessageList]dbresult:" + !messagesSet.isEmpty() + " size:" + size);

        IMMessage[] msgs = new IMMessage[size];
        messagesSet.toArray(msgs);
        int startNo = 0;
        int endNo = 0;
        if (beforeMsgSeq <= 0 && beforeMessageID == 0) {
            startNo = msgs.length - num;
            endNo = msgs.length - 1;
            LogUtil.printIm(TAG, "start:" + startNo + " endNo:" + endNo);
        } else {
            int i = msgs.length - 1;
            for (; i >= 0; i--) {

                LogUtil.i("@@@@@@", "msgids:" +
                        ((BDHiIMMessage) msgs[i]).getMessageID() + ":" + ((BDHiIMMessage) msgs[i]).getClientMessageID()
                        + ":" + ((BDHiIMMessage) msgs[i]).getMsgSeq());

                {

                    if (beforeMsgSeq > ((BDHiIMMessage) msgs[i]).getMsgSeq()) {
                        break;
                    }
                    if (beforeMsgSeq == ((BDHiIMMessage) msgs[i]).getMsgSeq()) {
                        if ((lastQueryCientID == -1) || lastQueryCientID >= ((BDHiIMMessage) msgs[i])
                                .getClientMessageID()) {
                            break;
                        }
                    } 
                    }


            startNo = i - num;
            endNo = i - 1;

            LogUtil.printIm(TAG, "2start:" + startNo + " endNo:" + endNo);
        }
        if (startNo >= msgs.length) {
            return result;
        }
        if (startNo < 0) {
            startNo = 0;
        }
        List<IMMessage> msgsList = new ArrayList<IMMessage>();
        for (int i = startNo; i <= endNo; i++) {
            msgsList.add(msgs[i]);
        }

        LogUtil.printIm(TAG, "msgs:" + msgsList.size());
        result.setList(msgsList);
        result.setTotal(msgs.length);
        return result;
    }
*/
    public PageableResult<IMMessage> getMessageListByMsgId2(AddresseeType addresseeType, String addresseeID,
                                                            long beforeMessageID, int num) {

        long querylasttime = 0;

        PageableResultImpl result = new PageableResultImpl();
        if (null == addresseeType || null == addresseeID || addresseeID.length() == 0) {
            return result;
        }
        List<BDHiIMMessage> messagesSet = new ArrayList<BDHiIMMessage>();
        BDHiIMMessage message = null;
        if (isValidMsgStore()) {
            message =
                    getMessageDbUtil().getMessageByMessageID(addresseeType,
                            addresseeID, beforeMessageID);

        }

        if (message != null) {
            querylasttime = message.getServerTime();
        }

        LogUtil.printIm(TAG, "[getMessageByMessageID]dbresult:" + (null != message));
        if (isValidMsgStore()) {
            messagesSet =
                    getMessageDbUtil().getMessageList3(addresseeType,
                            addresseeID, querylasttime, num);
        }
        int size = messagesSet.size();
        LogUtil.printIm(TAG, "[GetMessageList]dbresult:" + !messagesSet.isEmpty() + " size:" + size);

        IMMessage[] msgs = new IMMessage[size];
        messagesSet.toArray(msgs);

        List<IMMessage> msgsList = new ArrayList<IMMessage>();
        for (int i = 0; i < size; i++) {
            msgsList.add(msgs[i]);
        }

        LogUtil.printIm(TAG, "msgs:" + msgsList.size());
        result.setList(msgsList);
        result.setTotal(msgs.length);
        return result;
    }

    // @Override
    // public IMMessage getMessageByMsgId(AddresseeType addresseeType, String addresseeID, String addresserID,
    // long messageID) {
    // IMMessage imMessage = null;
    // if (isValidMsgStore()) {
    // imMessage =
    // IMMessageDBUtil.getDB(getContext(), getAppKey() + getUserID()).getMessageByMessageID(addresseeType,
    // addresseeID, messageID);
    // }
    // LogUtil.printIm(TAG, "[GetMessageByMsgId]dbresult:" + (null != imMessage));
    // return imMessage;
    // }

    /**
     * 获取某一条消息
     *
     * @param id 消息ID
     */
    @Override
    public IMMessage getMessageByDBId(long id) {
        IMMessage imMessage = null;
        if (isValidMsgStore()) {
            imMessage = getMessageDbUtil().getMessageByDBId(id);
        }
        LogUtil.printIm(TAG, "[getMessageByDBId]dbresult:" + (null != imMessage));
        return imMessage;
    }

    @Override
    public IMMessage getLastSuccessfulMessage(AddresseeType addresseeType, String addresseeID) {
        IMMessage imMessage = null;
        if (isValidMsgStore()) {
            String key = addresseeType.name() + "|" + addresseeID;
            imMessage = this.mLastSuccessfulMessage.get(key);
            if (imMessage == null) {
                imMessage =
                        getMessageDbUtil().getLastSuccessfulMessage(
                                addresseeType, addresseeID);
                this.mLastSuccessfulMessage.put(key, imMessage);
            } else {
                LogUtil.printIm("use cached message");
            }
        }
        LogUtil.printIm(TAG, "[getLastSuccessfulMessage] dbresult:" + (null != imMessage));
        return imMessage;
    }

    /**
     * 删除和某个对象所有的消息
     *
     * @param addresseeType 对方类型
     * @param addresseeID   对方ID
     */
    @Override
    public void deleteAllMessage(AddresseeType addresseeType, String addresseeID) {
        boolean dbresult = false;
        if (isValidMsgStore()) {
            dbresult =
                    getMessageDbUtil().removeMessageList(addresseeType,
                            addresseeID);
        }
        LogUtil.printIm(TAG, "[DeleteAllMessage]dbresult:" + dbresult);
    }

    // @Override
    // public void deleteMessage(AddresseeType addresseeType, String addresseeID, long messageID) {
    // boolean dbresult = false;
    // if (isValidMsgStore()) {
    // dbresult =
    // IMMessageDBUtil.getDB(getContext(), getAppKey() + getUserID()).removeMessage(addresseeType,
    // addresseeID, messageID);
    // }
    // LogUtil.printIm(TAG, "[DeleteMessage]dbresult:" + dbresult);
    // }

    /**
     * 删除指定消息
     *
     * @param id 消息ID
     */
    @Override
    public void deleteMessage(long id, AddresseeType addresseeType, String addresseeID) {

        if (addresseeType == null || TextUtils.isEmpty(addresseeID)) {
            return;
        }

        String log = "deleteMessage" + addresseeType.name() + "||" + addresseeID;
        LogUtil.printDebug(TAG, log);
        boolean dbresult = false;
        if (isValidMsgStore()) {
            IMMessage lastMessage =
                    getMessageDbUtil().getLastEffectiveMessage(
                            addresseeType, addresseeID);
            dbresult = getMessageDbUtil().removeMessage(id);
            LogUtil.printIm(TAG, "[DeleteMessage]dbresult:" + dbresult);
            if (dbresult) {
                boolean changed = false;
                List<IMInboxEntry> entryList = new ArrayList<IMInboxEntry>();
                if (lastMessage != null && lastMessage.getMessageID() == id) {
                    LogUtil.printIm(TAG, "Last Message has been removed.");
                    IMMessage newLastMessage =
                            getMessageDbUtil().getLastEffectiveMessage(
                                    addresseeType, addresseeID);
                    if (newLastMessage != null) {
                        LogUtil.printIm(TAG, "Get a new last message.");
                        // IMInboxEntryImpl entry = (IMInboxEntryImpl) inboxMap.get(inboxKey);
                        IMInboxEntryImpl entry = null;
                        if (isValidMsgStore()) {
                            entry =
                                    getInboxDbUtil().getIMInbox(
                                            addresseeType, addresseeID);
                        }
                        if (entry == null) {
                            entry = new IMInboxEntryImpl();
                            entry.setIneffective(false);
                            LogUtil.printDebug(TAG, "delete entry is empty should not");
                            entry.setLastMessage((BDHiIMMessage) newLastMessage);
                            entry.setMsgBody(((BDHiIMMessage) newLastMessage).getBody());
                            entry.setUnreadCount(entry.getUnreadCount() + 1);
                            entry.setLastReceiveMessageID(((BDHiIMMessage) newLastMessage).getMsgSeq());
                            entry.setLastReceiveMessageTime(((BDHiIMMessage) newLastMessage).getServerTime());

                            if (null != getUserID() && getUserID().equals(newLastMessage.getAddresseeID())) {
                                entry.setAddresseeType(newLastMessage.getAddresseeType());
                                entry.setAddresseeID(newLastMessage.getAddresserID());
                                entry.setAddresseeName(newLastMessage.getAddresserName());
                            } else {
                                entry.setAddresseeType(newLastMessage.getAddresseeType());
                                entry.setAddresseeID(newLastMessage.getAddresseeID());
                                entry.setAddresseeName(newLastMessage.getAddresseeID());
                            }
                            entry.setUnreadCount(
                                    (int) (entry.getLastReceiveMessageID() - entry.getLastReadMessageID()));
                            boolean inboxDBResult = false;
                            if (isValidMsgStore()) {
                                inboxDBResult =
                                        getInboxDbUtil().saveIMInbox(entry);
                            }
                            LogUtil.printIm(TAG, "[InsertIMInbox]dbresult:" + inboxDBResult);
                            changed = true;
                            entryList.add(entry);
                        } else {
                            LogUtil.printDebug(TAG, "delete entry is not empty");

                            entry.setIneffective(false);
                            entry.setLastReceiveMessageID(((BDHiIMMessage) newLastMessage).getMsgSeq());
                            entry.setLastReceiveMessageTime(((BDHiIMMessage) newLastMessage).getServerTime());
                            entry.setLastMessage((BDHiIMMessage) newLastMessage);
                            entry.setMsgBody(((BDHiIMMessage) newLastMessage).getBody());
                            entry.setUnreadCount(
                                    (int) (entry.getLastReceiveMessageID() - entry.getLastReadMessageID()));
                            if (null != getUserID() && getUserID().equals(newLastMessage.getAddresseeID())) {
                                entry.setAddresseeName(newLastMessage.getAddresserName());
                            }
                            boolean inboxDBResult = false;
                            if (isValidMsgStore()) {
                                inboxDBResult =
                                        getInboxDbUtil().saveIMInbox(entry);
                            }
                            LogUtil.printIm(TAG, "[UpdateIMInbox]dbresult:" + inboxDBResult);
                            changed = true;
                            entryList.add(entry);
                        }
                    } else {
                        LogUtil.printIm(TAG, "Dont have any effective messages.");
                        boolean inboxDBResult = false;
                        if (isValidMsgStore()) {
                            inboxDBResult =
                                    getInboxDbUtil().removeIMInbox(
                                            addresseeType, addresseeID);
                        }
                        LogUtil.printIm(TAG, "[removeIMInbox]dbresult:" + inboxDBResult);
                        changed = true;
                    }
                }

                if (changed) {
                    // notify inbox
                    notifyInboxListeners(entryList);
                }
            }
        }
    }

    /**
     * 数据库刚刚重建, 则删除用户所有相关数据, 这里是 IMPreference
     * @param db
     */
    private void clearRelatedDataWhenDbRecreation(DataBaseUtil db) {
        if(db.isJustRecreated()){
            imPreference.clear();
            db.setJustRecreated(false);
        }
    }

    private IMInboxDBUtil getInboxDbUtil() {
        IMInboxDBUtil db = IMInboxDBUtil.getDB(getContext(), getAppKey() + getUserID());
        clearRelatedDataWhenDbRecreation(db);
        return db;
    }

    private IMMessageDBUtil getMessageDbUtil() {
        IMMessageDBUtil db = IMMessageDBUtil.getDB(getContext(), getAppKey() + getUserID());
        clearRelatedDataWhenDbRecreation(db);
        return db;
    }

    private ChatSettingDBUtil getChatSettingDbUtil() {
        ChatSettingDBUtil db = ChatSettingDBUtil.getDB(getContext(), getAppKey() + getUserID());
        clearRelatedDataWhenDbRecreation(db);
        return db;
    }

    @Override
    public void saveSyncSuccessRange(AddresseeType conversationType, String conversationID, ISyncSuccessRange range) {
        // TODO
    }

    @Override
    public List<ISyncSuccessRange> getSyncSuccessRanges(AddresseeType conversationType, String conversationID) {
        // TODO
        return null;
    }

    /**
     * 清除未读数
     *
     * @param addresseeType 对方类型
     * @param addresseeID   对方ID
     */
    @Override
    public IMInboxEntry clearUnread(AddresseeType addresseeType, String addresseeID) {
        IMInboxEntryImpl entry = null;
        if (isValidMsgStore()) {
            entry = getInboxDbUtil().getIMInbox(addresseeType, addresseeID);
        }
        LogUtil.printIm(TAG, "[GetIMInbox]dbresult:" + (null != entry));
        // set inbox as unread
        if (entry != null
                && ((entry.getLastReceiveMessageID() > entry.getLastReadMessageID()) || entry.getUnreadCount() > 0)) {
            entry.setUnreadCount(0);
            entry.setLastReadMessageID(entry.getLastReceiveMessageID());
            entry.setLastReadMessageTime(System.currentTimeMillis());
            boolean dbresult = false;
            if (isValidMsgStore()) {
                dbresult = getInboxDbUtil().saveIMInbox(entry);
            }
            LogUtil.printIm(TAG, "[SaveIMInbox]dbresult:" + dbresult);
            // send read_ack
            // ReadAck.sendReadAck(entry, null);

            // get entry list
            List<IMInboxEntry> entryList = Collections.singletonList(((IMInboxEntry) entry));

            // notify inbox
            notifyInboxListeners(entryList);

            LogUtil.printIm(TAG, "[clearUnread] addresseeType:" + addresseeType + " addresseeID:" + addresseeID
                    + " need to clearUnread.");
            return entry;
        } else {
            // LogUtil.printIm(TAG, "[clearUnread] addresseeType:" + addresseeType + " addresseeID:" + addresseeID
            // + " dont need to clearUnread.");
            return null;
        }
    }

    @Override
    public void clearAllDB() {
        DataBaseUtil.clearAllDBCache();
        imPreference.destory();
    }

    public int insertOrUpdateChatSetting(List<ChatSetting> settings) {
        final ChatSettingDBUtil db = getChatSettingDbUtil();
        int changedItemCount = db.insertOrUpdateSettings(settings);
        return changedItemCount;
    }

    public NotificationType getChatSetting(AddresseeType addresseeType, String addresseeID) {
        final ChatSettingDBUtil db = getChatSettingDbUtil();
        ChatSetting chatSetting =
                db.getChatSetting(addresseeType, addresseeID);
        return chatSetting == null ? NotificationType.RECEIVE_NOTIFICATION : chatSetting.getReceiveMode();
    }

    public void setChatSettingLastQueryTime(long chatSettingLastQueryTime) {
        if (isValidMsgStore()) {
            imPreference.initialize(getContext(), getUserID());
        }
        imPreference.setChatSettingLastQueryTime(chatSettingLastQueryTime);
    }

    public long getChatSettingLastQueryTime() {
        if (isValidMsgStore()) {
            imPreference.initialize(getContext(), getUserID());
        }
        return imPreference.getChatSettingLastQueryTime();
    }

    public static class SyncSuccessRange implements ISyncSuccessRange {
        private int startSeq;
        private int endSeq;

        public int getStartSeq() {
            return startSeq;
        }

        public void setStartSeq(int startSeq) {
            this.startSeq = startSeq;
        }

        public int getEndSeq() {
            return endSeq;
        }

        public void setEndSeq(int endSeq) {
            this.endSeq = endSeq;
        }

    }

    private IMPreference imPreference = new IMPreference();

    /**
     * 获取最近会话时间戳
     */
    @Override
    public long getLastQueryInboxTime() {
        if (isValidMsgStore()) {
            imPreference.initialize(getContext(), getUserID());
        }
        return imPreference.getLastQueryInboxTime();
    }

    /**
     * 存储最近会话时间戳
     *
     * @param lastQueryInboxTime
     */
    @Override
    public void setLastQueryInboxTime(long lastQueryInboxTime) {
        if (isValidMsgStore()) {
            imPreference.initialize(getContext(), getUserID());
        }
        imPreference.setLastQueryInboxTime(lastQueryInboxTime);
    }

    @Override
    public PageableResult<IMMessage> getMessageList(
            AddresseeType addresseeType, String addresseeID, int pageNum,
            int pageSize) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageableResult<IMMessage> getMessageListByMsgId(
            AddresseeType addresseeType, String addresseeID,
            long beforeMessageID, int num) {
        // TODO Auto-generated method stub
        return null;
    }
}
