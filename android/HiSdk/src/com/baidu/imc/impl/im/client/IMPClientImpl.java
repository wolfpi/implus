package com.baidu.imc.impl.im.client;

import com.baidu.im.frame.NetworkChannel.NetworkChannelStatus;
import com.baidu.im.frame.inapp.IStatusListener;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.pb.ObjOneMsg;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.callback.ResultCallback;
import com.baidu.imc.client.IMChatHistory;
import com.baidu.imc.client.IMClient;
import com.baidu.imc.client.IMConversation;
import com.baidu.imc.client.IMInbox;
import com.baidu.imc.client.LocalResourceManager;
import com.baidu.imc.client.MessageHelper;
import com.baidu.imc.client.RemoteResourceManager;
import com.baidu.imc.exception.IllegalParameterException;
import com.baidu.imc.exception.InitializationException;
import com.baidu.imc.impl.im.message.BDHiIMMessage;
import com.baidu.imc.impl.im.message.IMMessageConvertor;
import com.baidu.imc.impl.im.message.OneMsgConverter;
import com.baidu.imc.impl.im.store.IMsgStore;
import com.baidu.imc.impl.im.transaction.IMTransactionFlow;
import com.baidu.imc.impl.push.client.PushClientImpl;
import com.baidu.imc.message.IMMessage;
import com.baidu.imc.message.Message;
import com.baidu.imc.message.TransientMessage;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.IMMessageStatus;
import com.baidu.imc.type.NotificationType;
import com.baidu.imc.type.UserStatus;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

/**
 * <b>IM+端控制类</b>
 * <p>
 * 兼容PushClient关于推送和通知的所有操作，并增加IM相关功能支持
 * </p>
 *
 * @author WuBin
 * @since 1.0
 */
public class IMPClientImpl extends PushClientImpl implements IMClient, IStatusListener {
    private IMConfig imConfig;
    private IMTransactionFlow transactionFlow;
    private IMInboxImpl imInboxImpl;
    private boolean mConnected = false;

    private static final String TAG = "IMClient";

    public IMPClientImpl() {
        super();
        imConfig = new IMConfig();
        ResourceManager.init(imConfig);
        transactionFlow = new IMTransactionFlow();
        imInboxImpl = new IMInboxImpl(this, transactionFlow, msgStoreImpl);
        InAppApplication.getInstance().setConnnectionListener(this);
    }

    public void init(Context context, String appkey) throws IllegalParameterException {
        getConfig(imConfig, context);
        super.init(context, appkey);
    }

    public void connect() throws InitializationException {
        if (mConnected) {
            return;
        }
        super.connect();
        messageBroadcastReceiver.addIMMsgListener(imInboxImpl);
    }

    public void disconnect() {
        if (!mConnected) {
            return;
        } else {
            mConnected = false;
        }
        super.disconnect();
    }

    @Override
    public IMInbox getIMInbox() {
        return imInboxImpl;
    }

    @Override
    public IMChatHistory getIMChatHistory(AddresseeType addresseeType, String addresseeID) {
        if (null == addresseeType || TextUtils.isEmpty(addresseeID) || TextUtils.isEmpty(getCurrentUserID())) {
            // Parameter invalid
            return null;
        }
        if (null == getCurrentUserStatus() || getCurrentUserStatus() == UserStatus.LOGOUT) {
            // userStatus invalid
            return null;
        }
        return new IMChatHistoryImpl(msgStoreImpl, transactionFlow, addresseeType, addresseeID, getCurrentUserID());
    }

    @Override
    public IMConversation openIMConversation(AddresseeType addresseeType, String addresseeID) {
        if (null == addresseeType || TextUtils.isEmpty(addresseeID) || TextUtils.isEmpty(getCurrentUserID())) {
            // Parameter invalid
            return null;
        }
        if (null == getCurrentUserStatus() || getCurrentUserStatus() == UserStatus.LOGOUT) {
            // userStatus invalid
            return null;
        }
        return new IMConversationImpl(imInboxImpl, msgStoreImpl, transactionFlow, addresseeType, addresseeID,
                getCurrentUserID());
    }

    @Override
    public void setLocalResourceManager(LocalResourceManager localResourceManager) {
        ResourceManager.getInstance().setLocalResourceManager(localResourceManager);
    }

    @Override
    public LocalResourceManager getLocalResourceManager() {
        return ResourceManager.getInstance().getLocalResourceManager();
    }

    @Override
    public void setRemoteResourceManager(RemoteResourceManager remoteResourceManager) {
        ResourceManager.getInstance().setRemoteResourceManager(remoteResourceManager);
    }

    @Override
    public void sendTransientMessage(AddresseeType addresseeType, String addresseeID,
                                     TransientMessage transientMessage, ResultCallback<Boolean> callback) {
        if (null == addresseeType || TextUtils.isEmpty(addresseeID) || null == transientMessage
                || TextUtils.isEmpty(getCurrentUserID())) {
            return;
        }
        if (null == getCurrentUserStatus() || getCurrentUserStatus() == UserStatus.LOGOUT
                || getCurrentUserStatus() == UserStatus.OFFLINE) {
            // userStatus invalid
            return;
        }
        if (null != transactionFlow) {
            transactionFlow.sendTransientMessage(addresseeType, addresseeID, getCurrentUserID(), transientMessage,
                    callback);
        }
    }

    @Override
    public MessageHelper getMessageHelper() {
        return new BDHiMessageHelper();
    }

    @Override
    public void statusChange(NetworkChannelStatus networkChannelStatus) {

    }

    @Override
    public void statusChanged(int oldStatus, int newStatus) {
        super.statusChanged(oldStatus, newStatus);
        if (null != imInboxImpl) {
            LogUtil.printIm(TAG, "statusChanged： UserStatus:" + getCurrentUserStatus() + "; AppStatus:"
                    + getCurrentClientConnectStatus());
            imInboxImpl.onUserStatusChanged(getCurrentUserStatus());
        }
    }

    private void getConfig(IMConfig imConfig, Context context) {
        if (null != imConfig && null != context) {
            try {
                ApplicationInfo applicationInfo =
                        context.getApplicationContext().getPackageManager()
                                .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if ((null != applicationInfo) && (null != applicationInfo.metaData)) {
                    String bosHost = applicationInfo.metaData.getString(BDIM_BOS_HOST);
                    if (!TextUtils.isEmpty(bosHost)) {
                        imConfig.bosHost = bosHost;
                        LogUtil.printIm(TAG, "GetConfig bosHost" + imConfig.bosHost);
                    } else {
                        LogUtil.printImE(TAG, "Can not get BOS HOST.", null);
                    }
                } else {
                    LogUtil.printIm(TAG, "Can not get meta data.");
                }
            } catch (NameNotFoundException e) {
                LogUtil.printImE(TAG, "GetConfig NameNotFoundException error", e);
            }
        }
    }

    private static final String BDIM_BOS_HOST = "BDIM_BOS_HOST";

    /**
     * 增加一条本地消息, 发件人永远是自己
     *
     * @param addresseeType
     * @param addresseeID
     * @param transientMessage
     * @param setInbox
     */
    @Override
    public void insertLocalMessage(AddresseeType addresseeType, String addresseeID, Message transientMessage,
                                   boolean setInbox) {
        if (addresseeType == null || addresseeID == null || transientMessage == null) {
            throw new IllegalArgumentException("Any null argument is not accepted");
        }
        // 参考 发送消息的流程
        IMsgStore msgStore = msgStoreImpl;
        // 001. Convert Message to IMMessage
        BDHiIMMessage imMessage =
                IMMessageConvertor.convertMessage(addresseeType, addresseeID, getCurrentUserID(), transientMessage);
        // 000. Get last successful message
        IMMessage previousMessage = msgStore.getLastSuccessfulMessage(addresseeType, addresseeID);
        if (null != previousMessage && null != imMessage) {
            imMessage.setMsgSeq(((BDHiIMMessage) previousMessage).getMsgSeq());
            LogUtil.printIm(getThreadName(), "set local message msgSeq to " + imMessage.getMsgSeq());
        }
        // 002. Convert IMMessage to OneMsg
        ObjOneMsg.OneMsg messageBuilder = OneMsgConverter.convertIMMessage(imMessage);
        if (null != imMessage && null != messageBuilder && null != msgStore) {
            LogUtil.printIm(getThreadName(), "Ready for insert local msg: " + imMessage.toString());
            // 003. set msg status as sending
            imMessage.setStatus(IMMessageStatus.SENDING);
            // 004. Save OneMsg into msgBody
            imMessage.setBody(messageBuilder.toByteArray());
            // 005. Save IMMessage to db
            ensureMessage(imMessage);
            imInboxImpl.saveMessage(imMessage, setInbox);
        } else {
            LogUtil.printIm(getThreadName(), "Can not get imMessage or database is not ready.");
        }

    }

    private String getThreadName() {
        return Thread.currentThread().getName();
    }

    /**
     * 补全入库的非空要求
     *
     * @param message
     */
    private void ensureMessage(BDHiIMMessage message) {
        if (message.getAddresserName() == null) {
            message.setAddresserName("");
        }
        if (message.getCompatibleText() == null) {
            message.setCompatibleText("");
        }
    }

    @Override
    public NotificationType getNotificationSetting(AddresseeType addresseeType, String addresseeID) {
        NotificationType chatSetting = msgStoreImpl.getChatSetting(addresseeType, addresseeID);
        transactionFlow.getChatSetting(msgStoreImpl, addresseeType, addresseeID);
        return chatSetting;
    }

    @Override
    public void setNotificationSetting(AddresseeType addresseeType, String addresseeId,
                                       NotificationType notificationType, ResultCallback resultCallback) {
        transactionFlow.setChatSetting(msgStoreImpl, addresseeType, addresseeId, notificationType, resultCallback);
    }

    // 测试用, 不暴露
    public void queryNotificationSetting() {
        transactionFlow.queryChatSetting(msgStoreImpl);
    }
}
