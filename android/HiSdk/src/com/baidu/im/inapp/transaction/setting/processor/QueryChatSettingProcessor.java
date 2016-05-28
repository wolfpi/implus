package com.baidu.im.inapp.transaction.setting.processor;

import com.baidu.im.frame.inappCallback.QueryChatSettingCallback;
import com.baidu.im.frame.pb.ProChatSetting;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.ChannelSdk;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.imc.impl.im.protocol.MethodNameEnum;
import com.baidu.imc.impl.im.protocol.ServiceNameEnum;
import com.baidu.imc.impl.im.store.ChatSetting;
import com.baidu.imc.impl.im.transaction.processor.IMProcessorStart;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

/**
 * Created by gerald on 3/24/16.
 */
public class QueryChatSettingProcessor implements IMProcessorStart {

    public static final String TAG = "FetchChatSetting";
    private final QueryChatSettingCallback mCallback;
    private final long lastQueryTime;

    public QueryChatSettingProcessor(QueryChatSettingCallback callback, long lastQueryTime) {
        mCallback = callback;
        this.lastQueryTime = lastQueryTime;
    }

    @Override
    public void startWorkFlow() {
        LogUtil.printMainProcess("FetchChatSetting start");

        ProChatSetting.QueryChatSettingsReq logoutReq = new ProChatSetting.QueryChatSettingsReq();
        logoutReq.setLastQueryTime(lastQueryTime);

        BinaryMessage binaryMessage = new BinaryMessage();
        binaryMessage.setServiceName(ServiceNameEnum.IM_PLUS_CHAT_SETTING.getName());
        binaryMessage.setMethodName(MethodNameEnum.QUERY.getName());
        binaryMessage.setData(logoutReq.toByteArray());

        ChannelSdk.send(binaryMessage, new IMessageResultCallback() {

            @Override
            public void onSuccess(String description, byte[] data) {
                LogUtil.printIm("chat setting success");

                try {
                    ProChatSetting.QueryChatSettingsRsp resp =
                            ProChatSetting.QueryChatSettingsRsp
                                    .parseFrom(data);
                    if(resp != null) {
                        LogUtil.printIm("chat setting parse ok. count = " + resp.getSettingsCount());
                        if (mCallback != null) {
                            mCallback.chatSettingResult(ChatSetting.convertFrom(resp.getSettingsList()),
                                    resp.getLastQueryTime());
                        }
                    }else{
                        LogUtil.printIm("chat setting parse fail.");
                    }
                } catch (InvalidProtocolBufferMicroException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(int errorCode) {
                LogUtil.printIm("chat setting fail");
            }
        });
    }
}
