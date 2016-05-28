package com.baidu.im.inapp.transaction.session;

import com.baidu.im.frame.BizBaseTransaction;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.pb.ProUserLogoutNotify.LogoutNotify;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.inapp.messagecenter.MessageCenter;
import com.baidu.im.sdk.IMessageCallback;
import com.google.protobuf.micro.ByteStringMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

public class UserLogoutNotifyTransaction extends BizBaseTransaction {

    public static final String TAG = "UserLogoutNotify";

    private ByteStringMicro byteBuffer;
    private MessageCenter msgCenter;

    public UserLogoutNotifyTransaction(ByteStringMicro byteBuffer, MessageCenter msgCenter) {
        this.byteBuffer = byteBuffer;
        this.msgCenter = msgCenter;
    }

    public String getThreadName() {
        return TAG;
    }

    @Override
    public ProcessorResult startWorkFlow(IMessageCallback callback) {
        transactionStart(this.hashCode());
        this.msgCenter.cacheSendingMessage(this.hashCode(), null, callback);
        LogoutNotify userLogoutNotify = null;
        if (null != byteBuffer) {
            try {
                userLogoutNotify = LogoutNotify.parseFrom(byteBuffer.toByteArray());
            } catch (InvalidProtocolBufferMicroException e) {
                LogUtil.printMainProcess(getThreadName(), "Receive logout notify error. Cannot parse ByteStringMicro.");
            }
        }
        ProcessorResult result =
                new ProcessorResult(ProcessorCode.USER_LOGOUT_NOTIFY, null != userLogoutNotify ? userLogoutNotify
                        .getCause().getBytes() : null);

        InAppApplication.getInstance().getSession().userLogoutSuccess();
        transactionCallback(this.hashCode(), result);
        return result;
    }
}
