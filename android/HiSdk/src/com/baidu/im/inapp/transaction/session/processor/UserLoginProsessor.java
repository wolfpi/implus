package com.baidu.im.inapp.transaction.session.processor;

import android.text.TextUtils;

import com.baidu.im.frame.MessageResponser;
import com.baidu.im.frame.Processor;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.ProcessorStart;
import com.baidu.im.frame.ProcessorTimeCallback;
import com.baidu.im.frame.RootProcessor;
import com.baidu.im.frame.TransactionLog;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.inapp.InAppBaseProcessor;
import com.baidu.im.frame.inappCallback.UserLoginCallback;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.pb.ProUserLogin.LoginReq;
import com.baidu.im.frame.utils.BizCodeProcessUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.outapp.network.ProtocolConverter;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
import com.baidu.im.sdk.LoginMessage;
import com.google.protobuf.micro.ByteStringMicro;

public class UserLoginProsessor implements MessageResponser, ProcessorStart, ProcessorTimeCallback {

    public static final String TAG = "UserLogin";
    private Processor mProcessor = new InAppBaseProcessor(this);
    // private PreferenceUtil mPref = null;
    private UserLoginCallback mCallback = null;
    private TransactionLog mTransactionLog = null;
    private RootProcessor mRootProcessor = null;

    @Override
    public String getProcessorName() {
        return TAG;
    }

    private LoginMessage message;

    public UserLoginProsessor(LoginMessage message, PreferenceUtil pref, UserLoginCallback callback,
            TransactionLog transactionLog) {
        this.message = message;
        // mPref = pref;
        mCallback = callback;
        mTransactionLog = transactionLog;
        mRootProcessor = new RootProcessor(this, this);
    }

    @Override
    public ProcessorResult startWorkFlow() {
        ProcessorResult processorResult = null;
        if (mTransactionLog != null) {
            mTransactionLog.startProcessor(getProcessorName());
        }
        LogUtil.printMainProcess("Login start, accountId=" + message.getAccountId() + " token=" + message.getToken());
        if (!TextUtils.isEmpty(message.getAccountId())) {
            if (!TextUtils.isEmpty(message.getToken())) {
                mRootProcessor.startCountDown();
                LoginReq loginReqBuilder = new  LoginReq();  // migrate from builder
                loginReqBuilder.setLoginName(message.getAccountId());
                loginReqBuilder.setToken(message.getToken());
                loginReqBuilder.setChannelKey(InAppApplication.getInstance().getSession().getChannel().getChannelKey());
                LoginReq loginReq = loginReqBuilder;

                com.baidu.im.frame.pb.ObjUpPacket.UpPacket upPacketBuilder = new  UpPacket();  // migrate from builder
                upPacketBuilder.setServiceName(ServiceNameEnum.CoreSession.name());
                upPacketBuilder.setMethodName(MethodNameEnum.Login.name());

                UpPacket upPacket = ProtocolConverter.convertUpPacket(ByteStringMicro.copyFrom(loginReq.toByteArray()), upPacketBuilder);

                // 发包并等待, 如果超过重试次数没有回包则处理失败。
                if (!mProcessor.send(upPacket)) {
                    processorResult = new ProcessorResult(ProcessorCode.SERVER_ERROR);
                } else {
                    processorResult = new ProcessorResult(ProcessorCode.SUCCESS);
                }
            } else {
                processorResult = new ProcessorResult(ProcessorCode.TOKEN_ERROR);
            }
        } else {
            processorResult = new ProcessorResult(ProcessorCode.PARAM_ERROR);
        }
        if (processorResult.getProcessorCode() != ProcessorCode.SUCCESS) {
            if (mTransactionLog != null) {
                mTransactionLog.endProcessor(getProcessorName(), 0, processorResult);
            }
            if (mCallback != null) {
                mCallback.userLoginCallbackResult(processorResult);
            }
        }
        return processorResult;
    }

    @Override
    public ProcessorResult onReceive(DownPacket downPacket, UpPacket upPacket) {
        ProcessorResult processorResult = null;
        mRootProcessor.stopCountDown();
        processorResult = new ProcessorResult(ProcessorCode.SERVER_ERROR);

        if (downPacket != null) {
            ProcessorCode result = BizCodeProcessUtil.procProcessorCode(getProcessorName(), downPacket);
            if (result.getCode() == 0) {
                InAppApplication
                        .getInstance()
                        .getSession()
                        .userLoginFinished(downPacket.getSessionId(), message.getAccountId(), message.getToken(),
                                downPacket.getUid());

                LogUtil.printMainProcess("UserLogin success, sessionId=" + downPacket.getSessionId() + ", uid="
                        + downPacket.getUid());
            }
            processorResult = new ProcessorResult(result);
        }
        if (mTransactionLog != null) {
            mTransactionLog.endProcessor(getProcessorName(), downPacket != null ? downPacket.getSeq() : 0,
                    processorResult);
        }
        if (mCallback != null) {
            mCallback.userLoginCallbackResult(processorResult);
        }
        return processorResult;
    }

    @Override
    public void processorTimeout() {
        mProcessor.sendReconnect();
        ProcessorResult processorResult = new ProcessorResult(ProcessorCode.SEND_TIME_OUT);
        if (mTransactionLog != null) {
            mTransactionLog.endProcessor(getProcessorName(), 0, processorResult);
        }
        if (mCallback != null) {
            mCallback.userLoginCallbackResult(processorResult);
        }
    }
}
