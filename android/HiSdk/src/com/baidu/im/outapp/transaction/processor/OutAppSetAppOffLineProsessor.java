package com.baidu.im.outapp.transaction.processor;

import com.baidu.im.frame.MessageResponser;
import com.baidu.im.frame.Processor;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.ProcessorStart;
import com.baidu.im.frame.outapp.OutAppBaseProcessor;
import com.baidu.im.frame.pb.EnumAppStatus;
import com.baidu.im.frame.pb.EnumPacketType;
import com.baidu.im.frame.pb.ObjBizUpPackage.BizUpPackage;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.pb.ProSetAppStatus.SetAppStatusReq;
import com.baidu.im.frame.utils.BizCodeProcessUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.outapp.OutAppApplication;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
import com.google.protobuf.micro.ByteStringMicro;

public class OutAppSetAppOffLineProsessor implements MessageResponser,ProcessorStart {

    public static final String TAG = "OutAppSetAppOffLine";
    private Processor 			mProcessor = new OutAppBaseProcessor(this);
    private int			       	mAppId = 0;
    public OutAppSetAppOffLineProsessor(int appid)
    {
    	mAppId = appid;
    }

    @Override
    public String getProcessorName() {
        return TAG;
    }

    @Override
    public ProcessorResult startWorkFlow() {
        {
            SetAppStatusReq setAppStatusReqBuilder = new  SetAppStatusReq();  // migrate from builder
            setAppStatusReqBuilder.setStatus(EnumAppStatus.APP_OFFLINE);
            setAppStatusReqBuilder.addAppIds(mAppId);
            setAppStatusReqBuilder.setChannelKey(OutAppApplication.getInstance().getChannelKey());

            com.baidu.im.frame.pb.ObjBizUpPackage.BizUpPackage bizUpPackageBuilder = new  BizUpPackage();  // migrate from builder
            bizUpPackageBuilder.setPacketType(EnumPacketType.REQUEST);
            bizUpPackageBuilder.setBusiData(ByteStringMicro.copyFrom(setAppStatusReqBuilder.toByteArray()));
            BizUpPackage bizUpPackage = bizUpPackageBuilder;

            UpPacket upPacketBuilder = new  UpPacket();  // migrate from builder
            upPacketBuilder.setBizPackage(bizUpPackage);
            upPacketBuilder.setServiceName(ServiceNameEnum.CoreSession.name());
            upPacketBuilder.setMethodName(MethodNameEnum.SetAppStatus.name());
            upPacketBuilder.setSeq(OutAppApplication.getInstance().getOutSeq());
            upPacketBuilder.setAppId(mAppId);
            upPacketBuilder.setSysPackage(true);
            UpPacket upPacket = upPacketBuilder;

            // 发包并等待, 如果超过重试次数没有回包则处理失败。
            if (!mProcessor.send(upPacket)) {
                return new ProcessorResult(ProcessorCode.SEND_TIME_OUT);
            }

        }
        return new ProcessorResult(ProcessorCode.SUCCESS);
    }
    
	@Override
    public ProcessorResult onReceive(DownPacket downPacket, UpPacket upPacket) {
		LogUtil.i(TAG, "set App offline");
        return new ProcessorResult(BizCodeProcessUtil.procProcessorCode(getProcessorName(), downPacket));
	}
}
