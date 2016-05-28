package com.baidu.im.inapp.transaction.session.processor;

import android.text.TextUtils;
import android.util.Log;

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
import com.baidu.im.frame.inappCallback.RegAppCallback;
import com.baidu.im.frame.pb.ObjDeviceInfo.DeviceInfo;
import com.baidu.im.frame.pb.ObjDeviceTypeInfo.DeviceTypeInfo;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.pb.ProRegApp.RegAppReq;
import com.baidu.im.frame.pb.ProRegApp.RegAppRsp;
import com.baidu.im.frame.utils.BizCodeProcessUtil;
import com.baidu.im.frame.utils.DeviceInfoMapUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.MD5Util;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.frame.utils.SignatureUtil;
import com.baidu.im.outapp.network.ProtocolConverter;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
import com.google.protobuf.micro.ByteStringMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

public class RegAppProsessor implements MessageResponser, ProcessorStart, ProcessorTimeCallback {

    public static final String TAG = "RegApp";
    private Processor mProcessor = new InAppBaseProcessor(this);
    private RegAppCallback mCallback = null;
    // private PreferenceUtil mPref = null;
    private RootProcessor mRootProcessor = null;
    private TransactionLog mTransactionLog = null;

    @Override
    public String getProcessorName() {
        return TAG;
    }

    public RegAppProsessor(RegAppCallback callback, TransactionLog transactionLog, PreferenceUtil pref) {
        mCallback = callback;
        mTransactionLog = transactionLog;
        // mPref = pref;
        mRootProcessor = new RootProcessor(this, this);
    }

    @Override
    public ProcessorResult startWorkFlow() {
        ProcessorResult processorResult;
        if (mTransactionLog != null) {
            mTransactionLog.startProcessor(getProcessorName());
        }

        String apiKey = InAppApplication.getInstance().getSession().getApp().getApiKey();

        if (!TextUtils.isEmpty(apiKey)) {
            mRootProcessor.startCountDown();
            String secureKey = null;
            if ("2if2neqvvnc4owk".equals(apiKey)) {
                secureKey = "imsdkdemosecret";
            } else if (null != InAppApplication.getInstance().getContext()) {
                secureKey = SignatureUtil.getSecureKey(InAppApplication.getInstance().getContext());
            }
            LogUtil.printMainProcess("get secure key = " + secureKey);
            secureKey = MD5Util.getMD5(secureKey);
            LogUtil.printMainProcess("get MD5(secure key) = " + secureKey);

            // 发包
            LogUtil.printMainProcess("RegApp start, apiKey=" + apiKey);

            DeviceInfo deviceInfoBuilder = new  DeviceInfo();  // migrate from builder
            DeviceInfoMapUtil.getDeviceInfoWithEmptyTypeId(InAppApplication.getInstance().getContext(), deviceInfoBuilder);

            DeviceTypeInfo deviceTypeInfoBuilder = new  DeviceTypeInfo();  // migrate from builder
            DeviceInfoMapUtil.getDeviceTypeInfo(InAppApplication.getInstance().getContext(), deviceTypeInfoBuilder);

            RegAppReq regAppReqBuilder = new  RegAppReq();  // migrate from builder
            regAppReqBuilder.setApiKey(apiKey);
            regAppReqBuilder.setSign(secureKey);

            regAppReqBuilder.setDeviceInfo(deviceInfoBuilder);
            regAppReqBuilder.setDeviceTypeInfo(deviceTypeInfoBuilder);
            regAppReqBuilder.setChannelKey(InAppApplication.getInstance().getSession().getChannel().getChannelKey());
            RegAppReq regAppReq = regAppReqBuilder;

            com.baidu.im.frame.pb.ObjUpPacket.UpPacket upPacketBuilder = new  UpPacket();  // migrate from builder
            upPacketBuilder.setServiceName(ServiceNameEnum.CoreSession.name());
            upPacketBuilder.setMethodName(MethodNameEnum.RegApp.name());

            UpPacket upPacket = ProtocolConverter.convertUpPacket(ByteStringMicro.copyFrom(regAppReq.toByteArray()), upPacketBuilder);

            // 发包并等待, 如果超过重试次数没有回包则处理失败
            if (!mProcessor.send(upPacket)) {
                processorResult = new ProcessorResult(ProcessorCode.SERVER_ERROR);
            } else {
                processorResult = new ProcessorResult(ProcessorCode.SUCCESS);
            }

        } else {
            LogUtil.printMainProcess("RegApp error. Can not get apiKey.");
            processorResult = new ProcessorResult(ProcessorCode.NO_API_KEY);
        }
        if (processorResult.getProcessorCode() != ProcessorCode.SUCCESS) {
            if (mTransactionLog != null) {
                mTransactionLog.endProcessor(getProcessorName(), 0, processorResult);
            }
            if (mCallback != null) {
                mCallback.regAppFail(processorResult);
            }
        }
        return processorResult;
    }

    @Override
    public ProcessorResult onReceive(DownPacket downPacket, UpPacket upPacket) {
        mRootProcessor.stopCountDown();
        ProcessorResult processorResult;
        if (downPacket != null) {
            String apiKey = InAppApplication.getInstance().getSession().getApp().getApiKey();
            ProcessorCode result = BizCodeProcessUtil.procProcessorCode(getProcessorName(), downPacket);
            if (result == ProcessorCode.SUCCESS) {
                String deviceId = null;
                int deviceTypeId = 0;
                if (null != downPacket.getBizPackage().getBizData()) {
                    RegAppRsp regAppRsp;
                    try {
                        regAppRsp = RegAppRsp.parseFrom(downPacket.getBizPackage().getBizData().toByteArray());
                        deviceId = regAppRsp.getDeviceId();
                        deviceTypeId = regAppRsp.getDeviceTypeId();
                    } catch (InvalidProtocolBufferMicroException e) {
                        e.printStackTrace();
                    }
                }
                InAppApplication.getInstance().getSession()
                        .regAppSuccess(downPacket.getAppId(), deviceId, deviceTypeId);

                LogUtil.printMainProcess("RegApp success, appId=" + downPacket.getAppId());
                processorResult = new ProcessorResult(result);
            } else {
                Log.e("ImSdk", "Sdk initialize failed.");
                Log.e("ImSdk", "Wrong apiKey, your input apiKey is " + apiKey);
                Log.e("ImSdk", "Please register your App on IM platform.");
                processorResult = new ProcessorResult(result);
            }
        } else {
            processorResult = new ProcessorResult(ProcessorCode.SERVER_ERROR);
        }

        if (mTransactionLog != null) {
            mTransactionLog.endProcessor(getProcessorName(), downPacket != null ? downPacket.getSeq() : 0,
                    processorResult);
        }
        if (processorResult.getProcessorCode() == ProcessorCode.SUCCESS) {
            if (mCallback != null) {
                mCallback.regAppSucess();
            }
        } else {
            if (mCallback != null) {
                mCallback.regAppFail(processorResult);
            }
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
            mCallback.regAppFail(new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
        }
    }
}
