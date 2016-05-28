package com.baidu.im.outapp.transaction.processor;

/*
import android.text.TextUtils;

import com.baidu.im.frame.MessageResponser;
import com.baidu.im.frame.Processor;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.ProcessorStart;
import com.baidu.im.frame.outapp.OutAppBaseProcessor;
import com.baidu.im.frame.pb.EnumPacketType;
import com.baidu.im.frame.pb.ObjBizUpPackage.BizUpPackage;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.pb.ProHeartbeat;
import com.baidu.im.frame.pb.ProHeartbeat.HeartbeatReq;
import com.baidu.im.frame.utils.BizCodeProcessUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.outapp.OutAppApplication;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
import com.google.protobuf.micro.ByteStringMicro;

public class OutAppHeartbeatProcessor implements MessageResponser,ProcessorStart {

    public static final String TAG = "OutAppHeartbeat";
    private Processor mProcessor = new OutAppBaseProcessor(this);
    
    public OutAppHeartbeatProcessor()
    {
    }
    
    @Override
    public String getProcessorName() {
        return TAG;
    }
    

    @Override
    public ProcessorResult startWorkFlow() {

        try {
        	
        	LogUtil.i(TAG, "outapp heartbeat is called");

            ProHeartbeat.HeartbeatReq heartbeatReqBuilder = new  HeartbeatReq();  // migrate from builder

            String channelKey = OutAppApplication.getInstance().getChannelKey();
            if (TextUtils.isEmpty(channelKey)) {
                LogUtil.printMainProcess("outapp heart cant not get channelKey");
                return new ProcessorResult(ProcessorCode.PARAM_ERROR);
            } else {

                // Build a heartbeatInfo
                ProHeartbeat.HeartbeatInfo heartbeatInfoBuilder = new  ProHeartbeat.HeartbeatInfo();  // migrate from builder
                // out app should be always in background
                heartbeatInfoBuilder.setBackground(true);
                heartbeatReqBuilder.addInfo(heartbeatInfoBuilder);

                heartbeatReqBuilder.setChannelKey(channelKey);

                HeartbeatReq heartbeatReq = heartbeatReqBuilder;

                com.baidu.im.frame.pb.ObjBizUpPackage.BizUpPackage bizUpPackageBuilder =
                        new BizUpPackage(); // migrate from builder
                bizUpPackageBuilder.setPacketType(EnumPacketType.REQUEST);
                bizUpPackageBuilder.setBusiData(ByteStringMicro.copyFrom(heartbeatReq.toByteArray()));
                BizUpPackage bizUpPackage = bizUpPackageBuilder;

                UpPacket upPacketBuilder = new  UpPacket();  // migrate from builder
                upPacketBuilder.setBizPackage(bizUpPackage);
                upPacketBuilder.setServiceName(ServiceNameEnum.CoreSession.name());
                upPacketBuilder.setMethodName(MethodNameEnum.Heartbeat.name());
                upPacketBuilder.setSeq(OutAppApplication.getInstance().getOutSeq());
                upPacketBuilder.setSysPackage(true);
                UpPacket upPacket = upPacketBuilder;

                // 发包并等待, 如果超过重试次数没有回包则处理失败。
                if (!mProcessor.send(upPacket)) {
                    return new ProcessorResult(ProcessorCode.SERVER_ERROR);
                }
               
            }
        } finally {
            // 这次心跳完成，推迟心跳时间。
           // mPref.saveLastHeartbeatTime( SystemClock.elapsedRealtime());
        }
        
        return new ProcessorResult(ProcessorCode.SUCCESS); 
    }

	@Override
    public ProcessorResult onReceive(DownPacket downPacket, UpPacket upPacket) {
        return new ProcessorResult(BizCodeProcessUtil.procProcessorCode(getProcessorName(), downPacket));
	}
}*/