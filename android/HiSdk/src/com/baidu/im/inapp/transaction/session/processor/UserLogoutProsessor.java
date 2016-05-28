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
import com.baidu.im.frame.inappCallback.UserLoginoutCallback;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.pb.ProUserLogout.LogoutReq;
import com.baidu.im.frame.utils.BizCodeProcessUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.outapp.network.ProtocolConverter;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
import com.google.protobuf.micro.ByteStringMicro;

public class UserLogoutProsessor implements MessageResponser, ProcessorStart, ProcessorTimeCallback {

    public static final String TAG = "UserLogout";
    private Processor mProcessor = new InAppBaseProcessor(this);
    // private PreferenceUtil mPref = null;
    private UserLoginoutCallback mCallback = null;
    private TransactionLog mTransactionLog = null;
    private RootProcessor mRootProcessor = null;

    public UserLogoutProsessor(PreferenceUtil pref, UserLoginoutCallback callback) {
        // mPref = pref;
        mCallback = callback;
        mRootProcessor = new RootProcessor(this, this);
    }

    @Override
    public String getProcessorName() {
        return TAG;
    }

    @Override
    public ProcessorResult startWorkFlow() {

        LogUtil.printMainProcess("Logout start");
        ProcessorResult processorResult = null;
        if (null != mTransactionLog) {
            mTransactionLog.startProcessor(getProcessorName());
        }
        UpPacket upPacketBuilder = new  UpPacket();  // migrate from builder

        String sessionId = InAppApplication.getInstance().getSession().getSessionInfo().getSessionId();
        if (!TextUtils.isEmpty(sessionId)) {
            upPacketBuilder.setSessionId(sessionId);
            mRootProcessor.startCountDown();
            upPacketBuilder.setServiceName(ServiceNameEnum.CoreSession.name());
            upPacketBuilder.setMethodName(MethodNameEnum.Logout.name());

            LogoutReq logoutReqBuilder = new  LogoutReq();  // migrate from builder
            LogoutReq logoutReq = logoutReqBuilder;

            UpPacket upPacket = ProtocolConverter.convertUpPacket(ByteStringMicro.copyFrom(logoutReq.toByteArray()), upPacketBuilder);

            // 发包并等待, 如果超过重试次数没有回包则处理失败。
            if (!mProcessor.send(upPacket)) {
                processorResult = new ProcessorResult(ProcessorCode.SERVER_ERROR);
            } else {
                processorResult = new ProcessorResult(ProcessorCode.SUCCESS);
            }
        } else {
            LogUtil.printMainProcess("User logout success. Can not get seesionId.");
            processorResult = new ProcessorResult(ProcessorCode.NO_SESSION_ID);
        }
        if (processorResult.getProcessorCode() == ProcessorCode.SUCCESS) {
            if (mTransactionLog != null) {
                mTransactionLog.endProcessor(getProcessorName(), 0, processorResult);
            }
            if (mCallback != null) {
                mCallback.userLoginoutCallbackResult(processorResult);
            }
        }
        return processorResult;
    }

    @Override
    public ProcessorResult onReceive(DownPacket downPacket, UpPacket upPacket) {

        mRootProcessor.stopCountDown();
        ProcessorResult processorResult;
        if (downPacket != null) {
            ProcessorCode result = BizCodeProcessUtil.procProcessorCode(getProcessorName(), downPacket);
            if (result.getCode() == 0) {
                LogUtil.printMainProcess("UserLogout success");
            }
            processorResult = new ProcessorResult(result);
        } else {
            processorResult = new ProcessorResult(ProcessorCode.SERVER_ERROR);
        }
        if (mTransactionLog != null) {
            mTransactionLog.endProcessor(getProcessorName(), 0, processorResult);
        }
        if (mCallback != null) {
            mCallback.userLoginoutCallbackResult(processorResult);
        }

        return processorResult;
    }

    @Override
    public void processorTimeout() {
        mProcessor.sendReconnect();
        if (mTransactionLog != null) {
            mTransactionLog.endProcessor(getProcessorName(), 0, new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
        }
        if (mCallback != null) {
            mCallback.userLoginoutCallbackResult(new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
        }
    }
}
