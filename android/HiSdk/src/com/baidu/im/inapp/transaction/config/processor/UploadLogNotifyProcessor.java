package com.baidu.im.inapp.transaction.config.processor;

import com.baidu.im.frame.MessageResponser;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.ProcessorStart;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.pb.ProUploadLogNotify.UploadLogNotify;
import com.baidu.im.frame.utils.LogUtil;
import com.google.protobuf.micro.ByteStringMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

public class UploadLogNotifyProcessor implements MessageResponser, ProcessorStart {

    public static final String TAG = "UploadLogNotify";

    @Override
    public String getProcessorName() {
        return TAG;
    }

    private ByteStringMicro byteBuffer;

    public UploadLogNotifyProcessor(ByteStringMicro byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    @Override
    public ProcessorResult startWorkFlow() {
        LogUtil.printMainProcess("UploadLogNotify start");

        // Get configGroupNotify, may throws InvalidProtocolBufferMicroException
        UploadLogNotify uploadLogNotify;
        try {
            uploadLogNotify = UploadLogNotify.parseFrom(byteBuffer.toByteArray());

            // Get notifyTimestamp and compare it with localTimestamp
            long startTime = uploadLogNotify.getStartTime();
            long endTime = uploadLogNotify.getEndTime();
            LogUtil.printMainProcess("UploadLogNotify startTime = " + startTime);
            LogUtil.printMainProcess("UploadLogNotify endTime = " + endTime);
        } catch (InvalidProtocolBufferMicroException e) {

            e.printStackTrace();
        }

        return new ProcessorResult(ProcessorCode.SUCCESS);
    }

    @Override
    public ProcessorResult onReceive(DownPacket downPacket, UpPacket upPacket) {
        return null;
    }
}
