package com.baidu.im.inapp.transaction.config.processor;

import com.baidu.im.frame.MessageResponser;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.ProcessorStart;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.pb.ProConfigNotify.ConfigChangedNotify;
import com.baidu.im.frame.utils.DynamicConfigUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.google.protobuf.micro.ByteStringMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

public class ConfigChangeNotifyProcessor implements MessageResponser,ProcessorStart {

    public static final String TAG = "ConfigChangeNotify";

    @Override
    public String getProcessorName() {
        return TAG;
    }

    private ByteStringMicro byteBuffer;
    private PreferenceUtil mPref = null;

    public ConfigChangeNotifyProcessor(ByteStringMicro byteBuffer,PreferenceUtil pref) {
        this.byteBuffer = byteBuffer;
        mPref = pref;
    }

    @Override
    public ProcessorResult startWorkFlow() {
        LogUtil.printMainProcess("ConfigChangeNotify start");

        // Get configGroupNotify, may throws InvalidProtocolBufferMicroException
        ConfigChangedNotify configGroupNotify;
		try {
			configGroupNotify = ConfigChangedNotify.parseFrom(byteBuffer.toByteArray());
        // Get notifyTimestamp and compare it with localTimestamp
        long notifyTimestamp = configGroupNotify.getTimestamp();
        long localTimestamp = DynamicConfigUtil.getConfigTimestamp();
        if (notifyTimestamp > localTimestamp) {
        	
        	UpdateConfigProcessor updateCfgProcessor = new UpdateConfigProcessor(mPref); 
            ProcessorResult configUpdateCode = updateCfgProcessor.updateConfig();
            LogUtil.printMainProcess("ConfigChangeNotify success");
            return configUpdateCode;
        } else {
            LogUtil.printMainProcess("ConfigChangeNotify success");
            return new ProcessorResult(ProcessorCode.SUCCESS);
        }
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
