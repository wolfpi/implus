package com.baidu.im.inapp.transaction.config.processor;

import java.util.List;

import com.baidu.im.frame.MessageResponser;
import com.baidu.im.frame.Processor;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.ProcessorStart;
import com.baidu.im.frame.inapp.InAppBaseProcessor;
import com.baidu.im.frame.pb.ObjConfigGroup.ConfigGroup;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.pb.ProUpdateConfig.UpdateConfigReq;
import com.baidu.im.frame.pb.ProUpdateConfig.UpdateConfigRsp;
import com.baidu.im.frame.utils.BizCodeProcessUtil;
import com.baidu.im.frame.utils.DynamicConfigUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.outapp.network.ProtocolConverter;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
import com.google.protobuf.micro.ByteStringMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

public class ConfigBaseProcessor  implements MessageResponser,ProcessorStart{

    public static final String TAG = "ConfigBase";
    private Processor mProcessor = new InAppBaseProcessor(this);
    private PreferenceUtil mPref = null;

    public String getProcessorName() {
        return TAG;
    }

    public ConfigBaseProcessor(PreferenceUtil pref)
    {
    	mPref = pref;
    }
    
    @Override
    public ProcessorResult startWorkFlow() {
        return new ProcessorResult(ProcessorCode.SUCCESS);
    }

    public ProcessorResult updateConfig() {
        LogUtil.printMainProcess("UpdateConfig start");

        if(mPref == null)
        	return new ProcessorResult(ProcessorCode.PARAM_ERROR);
        // Build ConfigGroup
        ConfigGroup configGroupBuilder = new  ConfigGroup();  // migrate from builder
        configGroupBuilder.setGroupName(DynamicConfigUtil.CONFIG_LOG);
        String logConfigMD5 = DynamicConfigUtil.getConfigMD5(DynamicConfigUtil.CONFIG_LOG);
        configGroupBuilder.setMd5(logConfigMD5);
        ConfigGroup logConfigGroup = configGroupBuilder;
        configGroupBuilder.clear();
        configGroupBuilder.setGroupName(DynamicConfigUtil.CONFIG_SERVER);
        String serverConfigMD5 = DynamicConfigUtil.getConfigMD5(DynamicConfigUtil.CONFIG_SERVER);
        configGroupBuilder.setMd5(serverConfigMD5);
        ConfigGroup serverConfigGroup = configGroupBuilder;

        // Build UpdateConfigReq
        UpdateConfigReq updateConfigReqBuilder = new  UpdateConfigReq();  // migrate from builder
        updateConfigReqBuilder.addLocalConfig(logConfigGroup);
        updateConfigReqBuilder.addLocalConfig(serverConfigGroup);
        UpdateConfigReq updateConfigReq = updateConfigReqBuilder;

        // Build UpPacket
        com.baidu.im.frame.pb.ObjUpPacket.UpPacket upPacketBuilder = new  UpPacket();  // migrate from builder
        upPacketBuilder.setServiceName(ServiceNameEnum.CoreConfig.name());
        upPacketBuilder.setMethodName(MethodNameEnum.UpdateConfig.name());

        // Convert UpPacket
        UpPacket upPacket = ProtocolConverter.convertUpPacket(ByteStringMicro.copyFrom(updateConfigReq.toByteArray()), upPacketBuilder);

        // 发包并等待, 如果超过重试次数没有回包则处理失败。
        if (!mProcessor.send(upPacket)) {
            return new ProcessorResult(ProcessorCode.SEND_TIME_OUT);
        }
        
        return new ProcessorResult(ProcessorCode.SUCCESS);
    }

	@Override
	public ProcessorResult onReceive(DownPacket downPacket, UpPacket upPacket) {
		if(downPacket == null)
			return new ProcessorResult(ProcessorCode.SERVER_ERROR);
		
        ProcessorCode result = BizCodeProcessUtil.procProcessorCode(getProcessorName(), downPacket);

	        if (result.getCode() == 0) {
	            // Get UpdateConfigRsp
	            ByteStringMicro byteStringMicro = downPacket.getBizPackage().getBizData();
	            // may throws InvalidProtocolBufferMicroException
	            UpdateConfigRsp updateConfigRsp;
				try {
					updateConfigRsp = UpdateConfigRsp.parseFrom(byteStringMicro.toByteArray());
					  // Parse new config group
		            List<ConfigGroup> configGroupList = updateConfigRsp.getNewConfigList();
		            if (null != configGroupList && configGroupList.size() > 0) {
		                for (ConfigGroup config : configGroupList) {
		                    if (DynamicConfigUtil.CONFIG_LOG.equals(config.getGroupName())) {
		                        String configMD5 = config.getMd5();
		                        String configJson = config.getConfigJson();
		                        DynamicConfigUtil.setConfig(DynamicConfigUtil.CONFIG_LOG, configMD5, configJson);
		                        LogUtil.printMainProcess("UpdateConfig " + DynamicConfigUtil.CONFIG_LOG, configMD5 + ": "
		                                + configJson);
		                    } else if (DynamicConfigUtil.CONFIG_SERVER.equals(config.getGroupName())) {
		                        String configMD5 = config.getMd5();
		                        String configJson = config.getConfigJson();
		                        DynamicConfigUtil.setConfig(DynamicConfigUtil.CONFIG_SERVER, configMD5, configJson);
		                        LogUtil.printMainProcess("UpdateConfig " + DynamicConfigUtil.CONFIG_SERVER, configMD5 + ": "
		                                + configJson);
		                    }
		                }
		            }
		            long timestamp = updateConfigRsp.getTimestamp();
		            // Save new config timestamp
		            DynamicConfigUtil.setConfigTimestamp(timestamp);
		            LogUtil.printMainProcess(Long.toString(timestamp));

		            LogUtil.printMainProcess("UpdateConfig success");
				} catch (InvalidProtocolBufferMicroException e) {
					e.printStackTrace();
					LogUtil.printMainProcess("UpdateConfit error");
				}
	        }

	        return new ProcessorResult(result);
	}
}
