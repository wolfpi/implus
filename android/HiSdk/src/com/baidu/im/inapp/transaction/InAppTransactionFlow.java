package com.baidu.im.inapp.transaction;

import com.baidu.im.frame.BizTransaction;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.inapp.messagecenter.MessageCenter;
import com.baidu.im.inapp.transaction.config.ConfigChangeNotifyTransaction;
import com.baidu.im.inapp.transaction.config.UpdateConfigTransaction;
import com.baidu.im.inapp.transaction.config.UploadLogNotifyTransaction;
import com.baidu.im.inapp.transaction.message.SendMessageTransaction;
//import com.baidu.im.inapp.transaction.session.AppLoginTransaction;
//import com.baidu.im.inapp.transaction.session.AppLogoutTransaction;
import com.baidu.im.inapp.transaction.session.ChannelReconnectTransaction;
import com.baidu.im.inapp.transaction.session.HeartbeatTransaction;
import com.baidu.im.inapp.transaction.session.RegAppTransaction;
import com.baidu.im.inapp.transaction.session.UnRegAppTransaction;
import com.baidu.im.inapp.transaction.session.UserLoginTransaction;
import com.baidu.im.inapp.transaction.session.UserLogoutNotifyTransaction;
import com.baidu.im.inapp.transaction.session.UserLogoutTransaction;
import com.baidu.im.inapp.transaction.setting.QueryChatSettingTransaction;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.IMessageCallback;
import com.baidu.im.sdk.LoginMessage;
import com.google.protobuf.micro.ByteStringMicro;

public class InAppTransactionFlow {

    public static final String TAG = "InAppTransactionFlow";

    private PreferenceUtil mPref = null;
    private MessageCenter mMsgCenter = null;
    private TransactionResend mTranResend = new TransactionResend();

    public InAppTransactionFlow(PreferenceUtil pref, MessageCenter msgCenter) {
        mPref = pref;
        mMsgCenter = msgCenter;
    }

    public void initialize() {
    }

    public int regApp(IMessageCallback callback) {
        BizTransaction transaction = new RegAppTransaction(mPref, mMsgCenter, mTranResend);
        int transactionId = transaction.hashCode();
        transaction.startWorkFlow(callback);
        return transactionId;
    }

    public int unRegApp(IMessageCallback callback) {
        BizTransaction transaction = new UnRegAppTransaction(mPref, mMsgCenter, mTranResend);
        int transactionId = transaction.hashCode();
        transaction.startWorkFlow(callback);
        return transactionId;
    }

    public int appLogin(IMessageCallback callback) {
       
    	/*BizTransaction transaction = new AppLoginTransaction(mPref, mMsgCenter, mTranResend);
        int transactionId = transaction.hashCode();
        transaction.startWorkFlow(callback);

        return transactionId;*/
    	return 0;
    }

    public int appLogout(IMessageCallback callback) {
        
    	/*BizTransaction transaction = new AppLogoutTransaction(mPref, mMsgCenter, mTranResend);
        int transactionId = transaction.hashCode();
        transaction.startWorkFlow(callback);
        return transactionId;*/
    	return 0;
    }

    public int userLogin(LoginMessage message, IMessageCallback callback) {

        BizTransaction transaction = new UserLoginTransaction(message, mPref, mMsgCenter, mTranResend);
        int transactionId = transaction.hashCode();
        LogUtil.e("UserLogin", "UserLogin is called");
        transaction.startWorkFlow(callback);
        return transactionId;
    }

    public int userLogout(IMessageCallback callback) {
        UserLogoutTransaction transaction = new UserLogoutTransaction(mPref, mMsgCenter, mTranResend);
        int transactionId = transaction.hashCode();
        mTranResend.clearall();
        transaction.startWorkFlow(callback);
        return transactionId;
    }

    public int channelReconnect() {
        BizTransaction transaction = new ChannelReconnectTransaction(mPref, mMsgCenter, mTranResend);
        transaction.startWorkFlow(null);
        return transaction.hashCode();
    }

    public int heartbeat(IMessageCallback callback) {
        BizTransaction transaction = new HeartbeatTransaction(mPref, mMsgCenter, mTranResend);
        int transactionId = transaction.hashCode();
        transaction.startWorkFlow(callback);
        return transactionId;
    }

    public int sendMessage(BinaryMessage message, IMessageCallback callback) {
        BizTransaction transaction = new SendMessageTransaction(message, mPref, mMsgCenter, mTranResend);
        int transactionId = transaction.hashCode();
        transaction.startWorkFlow(callback);
        return transactionId;
    }

    public int updateConfig() {
        BizTransaction transaction = new UpdateConfigTransaction(mPref, mTranResend);
        transaction.startWorkFlow(null);
        return transaction.hashCode();
    }

    public int receiveConfigChangeNotify(ByteStringMicro byteBuffer) {
        BizTransaction transaction = new ConfigChangeNotifyTransaction(byteBuffer, mPref);
        transaction.startWorkFlow(null);
        return transaction.hashCode();
    }

    public int receiveUploadLogNotify(ByteStringMicro byteBuffer) {
        BizTransaction transaction = new UploadLogNotifyTransaction(byteBuffer, mPref);
        transaction.startWorkFlow(null);
        return transaction.hashCode();
    }

    /**
     * Receive User LogoutNotify.
     * 
     * @param byteBuffer
     * @param callback
     * 
     * @return the hasCode of the UserLogoutNotifyTransaction
     */
    public int receiveUserLogoutNotify(ByteStringMicro byteBuffer, IMessageCallback callback) {
        BizTransaction transaction = new UserLogoutNotifyTransaction(byteBuffer, mMsgCenter);
        // mTranResend.addTransaction(transaction.hashCode(), transaction, callback);
        transaction.startWorkFlow(callback);
        return transaction.hashCode();
    }

    public void removeTransactionId(int tranId) {
        mTranResend.removeTransaction(tranId);
    }

    public void resend() {
        mTranResend.resendAll();
    }

    public void destroy() {
    }

}
