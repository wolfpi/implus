package com.baidu.imc.impl.im.transaction;

import java.util.Collections;

import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.inapp.transaction.setting.GetChatSettingTransaction;
import com.baidu.im.inapp.transaction.setting.QueryChatSettingTransaction;
import com.baidu.im.inapp.transaction.setting.SetChatSettingTransaction;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.imc.callback.PageableResultCallback;
import com.baidu.imc.callback.ResultCallback;
import com.baidu.imc.impl.im.client.IMConversationMessageListener;
import com.baidu.imc.impl.im.store.ChatSetting;
import com.baidu.imc.impl.im.store.IMsgStore;
import com.baidu.imc.impl.im.store.MemoryMsgStore;
import com.baidu.imc.impl.im.transaction.processor.BosService;
import com.baidu.imc.message.IMInboxEntry;
import com.baidu.imc.message.IMMessage;
import com.baidu.imc.message.Message;
import com.baidu.imc.message.TransientMessage;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.NotificationType;

public class IMTransactionFlow {

    private static final String TAG = "TransactionFlow";

    private void sendReconnect()
    {
    	LogUtil.i(TAG, "decide whether is");
    	InAppApplication.getInstance().sendReconnect();
    }
    public IMTransactionFlow() {
        initialize();
    }

    public void initialize() {

    }

    public void destory() {
        BosService.destroy();
    }

    public void execute(Runnable runnable) {

    }

    /**
     * Send a message
     *
     * @param addresseeType
     * @param addresseeID
     * @param addresser
     * @param message
     * @param messageStore
     * @param callback
     */
    public void sendMsg(AddresseeType addresseeType, String addresseeID, String addresserID, Message message,
                        IMsgStore msgStore, IMConversationMessageListener callback) {
        LogUtil.printIm(TAG, "[sendMsg] addresseeType:" + addresseeType + " addresseeID:" + addresseeID
                + " addresserID:" + addresserID);
        sendReconnect();

        IMTransactionStart transaction =
                new IMSendMsgTransaction(addresseeType, addresseeID, addresserID, message, msgStore, callback);
        try {
            transaction.startWorkFlow();
        } catch (Exception e) {
            LogUtil.printImE(TAG, "[sendMsg]", e);
        }
    }

    /**
     * Query Active Contacts
     *
     * @param lastQueryTime
     * @param needReturnMsgsContactNum
     * @param msgStore
     */
    public void queryActiveContacts(String myUserID, int needReturnMsgsContactNum, IMsgStore msgStore) {
        LogUtil.printIm(TAG, "[queryActiveContacts] myUserID:" + myUserID + " needReturnMsgsContactNum:"
                + needReturnMsgsContactNum);
        sendReconnect();

        IMTransactionStart transaction =
                new IMQueryActiveContactsTransaction(myUserID, needReturnMsgsContactNum, msgStore);
        try {
            transaction.startWorkFlow();
        } catch (Exception e) {
            LogUtil.printImE(TAG, "[queryActiveContacts]", e);
        }
    }

    /**
     * Query messages
     *
     * @param IMsgStore
     * @param addresseeType
     * @param addresseeID
     * @param addresserID
     * @param startSeq
     * @param endSeq
     * @param count
     * @param timeout
     * @param callback
     */
    public void queryMsgs(IMsgStore msgStore, AddresseeType addresseeType, String addresseeID, String addresserID,
                          long endSeq, int count, int timeout, PageableResultCallback<IMMessage> callback) {
        LogUtil.printIm(TAG, "[queryMsgs] addresseeType:" + addresseeType + " addresseeID:" + addresseeID
                + " addresserID:" + addresserID + " endSeq:" + endSeq + " count:" + count + " timeout:" + timeout);
        sendReconnect();

        IMTransactionStart transaction =
                new IMQueryMsgsTransaction(msgStore, addresseeType, addresseeID, addresserID, endSeq, count, timeout,
                        callback);
        try {
            transaction.startWorkFlow();
        } catch (Exception e) {
            LogUtil.printImE(TAG, "[queryMsgs]", e);
        }
    }

    /**
     * Send read ack
     *
     * @param entry
     * @param callback
     */
    public void readAck(AddresseeType addresseeType, String addresserID, String addresseeID, IMInboxEntry entry,
                        IMessageResultCallback callback) {
        LogUtil.printIm(TAG, "[readAck] entry:" + (null != entry ? entry.getID() : null) + " addresserID:"
                + addresserID + " addresseeID:" + addresseeID + " addressType:" + addresseeType);
        sendReconnect();

        IMTransactionStart transaction =
                new IMReadAckTransaction(addresseeType, addresserID, addresseeID, entry, callback);
        try {
            transaction.startWorkFlow();
        } catch (Exception e) {
            LogUtil.printImE(TAG, "[readAck]", e);
        }
    }

    /**
     * Send a transient message
     *
     * @param addresseeType
     * @param addresseeID
     * @param addresserID
     * @param message
     * @param callback<Boolean>
     */
    public void sendTransientMessage(AddresseeType addresseeType, String addresseeID, String addresserID,
                                     TransientMessage message, ResultCallback<Boolean> callback) {
        LogUtil.printIm(TAG, "[sendTransientMessage] addresseeType:" + addresseeType + " addresseeID:" + addresseeID
                + " addresserID:" + addresserID);
        sendReconnect();

        IMTransactionStart transaction =
                new IMSendTransientMessageTransaction(addresseeType, addresseeID, addresserID, message, callback);
        try {
            transaction.startWorkFlow();
        } catch (Exception e) {
            LogUtil.printImE(TAG, "[sendTransientMessage]", e);
        }
    }

    public void queryChatSetting(IMsgStore msgStore) {
        QueryChatSettingTransaction transaction =
                new QueryChatSettingTransaction(msgStore);
        int id = transaction.hashCode();
        try {
            transaction.startWorkFlow();
        } catch (Exception e) {
            LogUtil.printImE(TAG, "[queryChatSetting]", e);
        }
    }

    public void setChatSetting(IMsgStore msgStore, AddresseeType addresseeType, String addresseeId, NotificationType
            notificationType,
                               ResultCallback resultCallback) {

        ChatSetting setting = new ChatSetting();
        setting.setTargetID(addresseeId);
        setting.setChatType(addresseeType);
        setting.setReceiveMode(notificationType);
        SetChatSettingTransaction trans =
                new SetChatSettingTransaction(Collections.singletonList(setting), resultCallback,
                        (MemoryMsgStore) msgStore);
        try {
            trans.startWorkFlow();
        } catch (Exception e) {
            LogUtil.printImE(TAG, "[setChatSetting", e);
        }
    }

    public void getChatSetting(MemoryMsgStore msgStore, AddresseeType addresseeType, String addresseeID) {
        GetChatSettingTransaction trans = new GetChatSettingTransaction(msgStore, addresseeType, addresseeID);
        try {
            trans.startWorkFlow();
        } catch (Exception e) {
            LogUtil.printImE(TAG, "[setChatSetting", e);
        }
    }

    // /**
    // * Upload a local file
    // *
    // * @param file
    // * @param fileType
    // * @param hiFile
    // * @param callback
    // */
    // public boolean uploadFile(File file, String fileType, BDHiFile hiFile, IMTransactionFileUploadCallback callback)
    // {
    // LogUtil.printIm(TAG, "[uploadFile] fileName:" + (null != file ? file.getName() : "") + " fileType:" + fileType
    // + " file:" + hiFile.toString());
    // IMTransactionStart transaction = new IMUploadFileTransaction(file, fileType, hiFile, callback);
    // try {
    // transaction.startWorkFlow();
    // return true;
    // } catch (Exception e) {
    // LogUtil.printImE(TAG, "[uploadFile]", e);
    // return false;
    // }
    // }
    //
    // /**
    // * Download a file from server
    // *
    // * @param fid
    // * @param callback
    // */
    // public boolean downloadFile(String fid, String host, String md5, BDHiFile file,
    // IMTransactionFileDownloadCallback callback) {
    // LogUtil.printIm(TAG, "[downloadFile] fid:" + fid + " host:" + host + " md5:" + md5 + " file:" + file);
    // IMTransactionStart transaction = new IMDownloadFileTransaction(fid, host, md5, file, callback);
    // try {
    // transaction.startWorkFlow();
    // return true;
    // } catch (Exception e) {
    // LogUtil.printImE(TAG, "[downloadFile]", e);
    // return false;
    // }
    // }
}
