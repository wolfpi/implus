package com.baidu.im.inapp.transaction.session.processor;

import com.baidu.im.frame.MessageResponser;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.ProcessorStart;
import com.baidu.im.frame.TransactionLog;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.inappCallback.RegChannelCallback;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.pb.ProRegChannel.RegChannelRsp;
import com.baidu.im.frame.utils.BizCodeProcessUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

/**
 * 注册通道。
 * <p>
 * 1）第一次注册，获取channelKey并持久化。channelKeyh是设备的唯一通道标识。<br>
 * 2）非第一次注册，上传channelKey至server，维持之前的通道。能够取到持久化的channelkey，则为非第一次注册。<br>
 * 
 * @author zhaowei10
 * 
 */
public class RegChannelProsessor implements MessageResponser, ProcessorStart {

    public static final String TAG = "RegChannel";
    // private Processor mProcessor = new InAppBaseProcessor(this);
    private RegChannelCallback mCallBack = null;
    private TransactionLog mTransactionLog = null;

    // private PreferenceUtil mPref = null;

    public RegChannelProsessor(RegChannelCallback callback, TransactionLog transactionLog, PreferenceUtil pref) {
        mCallBack = callback;
        mTransactionLog = transactionLog;
        // mPref = pref;
    }

    @Override
    public String getProcessorName() {
        return TAG;
    }

    @Override
    public ProcessorResult startWorkFlow() {
        if (null != mTransactionLog) {
            mTransactionLog.startProcessor(getProcessorName());
        }
        // if (ConfigUtil.getChannelType() != EChannelType.tieba) {
        if (null != mTransactionLog) {
            mTransactionLog.endProcessor(getProcessorName(), 0, new ProcessorResult(ProcessorCode.SUCCESS));
        }
        if (null != mCallBack) {
            mCallBack.regChannelSuccess();
        }

        return new ProcessorResult(ProcessorCode.SUCCESS);
        // }
        // 发包
        // String channelKey = InAppApplication.getInstance().getSession().getChannel().getChannelKey();
        // LogUtil.printMainProcess("RegChannel start, channelkey=" + channelKey);
        //
        // RegChannelReq regChannelReqBuilder = new  RegChannelReq();  // migrate from builder
        //
        // regChannelReqBuilder.setDeviceToken(DeviceInfoUtil.getDeviceToken(InAppApplication.getInstance().getContext()));
        //
        // if (!TextUtils.isEmpty(channelKey)) {
        // regChannelReqBuilder.setChannelKey(channelKey);
        // }
        //
        // RegChannelReq regChannelReq = regChannelReqBuilder;
        //
        // com.baidu.im.frame.pb.ObjUpPacket.UpPacket upPacketBuilder = new  UpPacket();  // migrate from builder
        // upPacketBuilder.setServiceName(ServiceNameEnum.CoreAcc.name());
        // upPacketBuilder.setMethodName(MethodNameEnum.RegChannel.name());
        //
        // UpPacket upPacket = ProtocolConverter.convertUpPacket(ByteStringMicro.copyFrom(regChannelReq.toByteArray()), upPacketBuilder,mPref);
        //
        // // 发包并等待, 如果超过重试次数没有回包则处理失败。
        // if (!mProcessor.send(upPacket)) {
        // return new ProcessorResult(ProcessorCode.SERVER_ERROR);
        // }
        //
        // return new ProcessorResult(ProcessorCode.SUCCESS);
    }

    @Override
    public ProcessorResult onReceive(DownPacket downPacket, UpPacket upPacket) {
        ProcessorCode result = BizCodeProcessUtil.procProcessorCode(getProcessorName(), downPacket);
        if (result.getCode() == 0) {
            RegChannelRsp regChannelRsp;
            try {
                regChannelRsp = RegChannelRsp.parseFrom(downPacket.getBizPackage().getBizData().toByteArray());
                InAppApplication.getInstance().getSession().regChannelSuccess(regChannelRsp.getChannelKey());
                LogUtil.printMainProcess("RegChannel success, channelkey=" + regChannelRsp.getChannelKey());
                if (mCallBack != null) {
                    mCallBack.regChannelSuccess();
                }
            } catch (InvalidProtocolBufferMicroException e) {
                e.printStackTrace();
            }

        }
        mTransactionLog.endProcessor(getProcessorName(), 0, new ProcessorResult(result));
        mCallBack.regChannelFail(new ProcessorResult(result));
        return new ProcessorResult(result);
    }

}
