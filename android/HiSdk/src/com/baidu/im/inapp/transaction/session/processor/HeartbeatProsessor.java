package com.baidu.im.inapp.transaction.session.processor;

import android.os.SystemClock;
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
import com.baidu.im.frame.inappCallback.HeartbeatCallback;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.pb.ProHeartbeat;
import com.baidu.im.frame.pb.ProHeartbeat.HeartbeatReq;
import com.baidu.im.frame.utils.AppStatusUtil;
import com.baidu.im.frame.utils.BizCodeProcessUtil;
import com.baidu.im.frame.utils.GlobalInstance;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceKey;
import com.baidu.im.frame.utils.PreferenceUtil;
//import com.baidu.im.frame.utils.PreferenceKey;
import com.baidu.im.outapp.network.ProtocolConverter;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
import com.google.protobuf.micro.ByteStringMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

public class HeartbeatProsessor implements MessageResponser, ProcessorStart, ProcessorTimeCallback {

    public static final String TAG = "Heartbeat";
    private Processor mProcessor = new InAppBaseProcessor(this);
    private HeartbeatCallback mCallback = null;
    private TransactionLog mTransactionLog = null;
    private boolean realHeartbeat = false;
    private RootProcessor mRootProcessor = null;

    public HeartbeatProsessor(PreferenceUtil pref, HeartbeatCallback callback, TransactionLog transactionLog,
            boolean heartbeatstatus) {
        mRootProcessor = new RootProcessor(this,this);
        mCallback = callback;
        mTransactionLog = transactionLog;
        realHeartbeat = heartbeatstatus;
    }

    @Override
    public String getProcessorName() {
        return TAG;
    }

    // It's true in HeartbeatTransact

    @Override
    public ProcessorResult startWorkFlow() {
        ProcessorResult processorResult = null;
        if (mTransactionLog != null) {
            mTransactionLog.startProcessor(getProcessorName());
        }
        ProHeartbeat.HeartbeatReq heartbeatReqBuilder = new  HeartbeatReq();  // migrate from builder

        // Build a heartbeatInfo
        ProHeartbeat.HeartbeatInfo heartbeatInfoBuilder = new  ProHeartbeat.HeartbeatInfo();  // migrate from builder
        boolean background = AppStatusUtil.isBackground(InAppApplication.getInstance().getContext());
        heartbeatInfoBuilder.setBackground(background);

        com.baidu.im.frame.pb.ObjUpPacket.UpPacket upPacketBuilder = new  UpPacket();  // migrate from builder
        upPacketBuilder.setServiceName(ServiceNameEnum.CoreSession.name());
        upPacketBuilder.setMethodName(MethodNameEnum.Heartbeat.name());

        // 如果有sessionId，则心跳需要带上；如果没有，则需要带上channelKey
        String sessionId = InAppApplication.getInstance().getSession().getSessionInfo().getSessionId();
        if (!TextUtils.isEmpty(sessionId)) {
            upPacketBuilder.setSessionId(sessionId);
            heartbeatInfoBuilder.setSessionId(sessionId);
        }
        // else {
        // heartbeatReqBuilder.setChannelKey(InAppApplication.getInstance().getSession().getChannel().getChannelKey());
        // }
        heartbeatInfoBuilder.setAppId(InAppApplication.getInstance().getSession().getApp().getAppId());
        heartbeatReqBuilder.addInfo(heartbeatInfoBuilder);

        heartbeatReqBuilder.setChannelKey(InAppApplication.getInstance().getSession().getChannel().getChannelKey());
        HeartbeatReq heartbeatReq = heartbeatReqBuilder;
        UpPacket upPacket =
                ProtocolConverter.convertUpPacket(ByteStringMicro.copyFrom(heartbeatReq.toByteArray()), !realHeartbeat, upPacketBuilder);

        // 发包并等待, 如果超过重试次数没有回包则处理失败。
        mRootProcessor.startCountDown();
        if (!mProcessor.send(upPacket)) {
            processorResult = new ProcessorResult(ProcessorCode.SERVER_ERROR);
        } else {
            processorResult = new ProcessorResult(ProcessorCode.SUCCESS);
        }

        if (processorResult.getProcessorCode() != ProcessorCode.SUCCESS) {
            if (mTransactionLog != null) {
                mTransactionLog.endProcessor(getProcessorName(), 0, processorResult);
            }
            if (mCallback != null) {
                mCallback.heartBeatResult(processorResult);
            }
        }
        return processorResult;
    }

    @Override
    public ProcessorResult onReceive(DownPacket downPacket, UpPacket upPacket) {
    	
    	InAppApplication.getInstance().setLastInappHearbeat();
        ProcessorResult processorResult = null;
        mRootProcessor.stopCountDown();
        GlobalInstance.Instance().preferenceInstace()
                .save(PreferenceKey.lastHeartbeatTime, SystemClock.elapsedRealtime());
        ProcessorCode heartbeatCode = BizCodeProcessUtil.procProcessorCode(getProcessorName(), downPacket);
        ProHeartbeat.HeartbeatResp rsp = new ProHeartbeat.HeartbeatResp(); 
        try {
			rsp.mergeFrom(downPacket.getBizPackage().getBizData().toByteArray());
			 if(rsp.hasTimestamp())
		     {
		        	LogUtil.printIm("rsp time is ture" + rsp.getTimestamp()+":" + (rsp.getTimestamp()-System.currentTimeMillis()));
		        	InAppApplication.getInstance().setTimeDelta(  rsp.getTimestamp() - System.currentTimeMillis());
		     }
		} catch (InvalidProtocolBufferMicroException e) {
			e.printStackTrace();
		}
       
        // String sessionId = InAppApplication.getInstance().getSession().getSessionInfo().getSessionId();
        // 如果带session心跳成功，则说明上线成功。
        // if (!TextUtils.isEmpty(sessionId)) {
        if (heartbeatCode == ProcessorCode.SUCCESS) {
            InAppApplication.getInstance().getSession().heartbeatSuccess(false);
        }
        // }
        // else {
        // InAppApplication.getInstance().getSession().heartbeatSuccess(true);
        // }
        processorResult = new ProcessorResult(heartbeatCode);
        if (mTransactionLog != null) {
            mTransactionLog.endProcessor(getProcessorName(), null != downPacket ? downPacket.getSeq() : 0,
                    processorResult);
        }
        if (mCallback != null) {
            mCallback.heartBeatResult(processorResult);
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
            mCallback.heartBeatResult(new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
        }
    }
}
