package com.baidu.im.inapp.transaction.setting.processor;

import java.util.List;

import com.baidu.im.frame.pb.ProChatSetting;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.ChannelSdk;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.imc.impl.im.protocol.MethodNameEnum;
import com.baidu.imc.impl.im.protocol.ServiceNameEnum;
import com.baidu.imc.impl.im.store.ChatSetting;
import com.baidu.imc.impl.im.transaction.processor.IMProcessorStart;

/**
 * Created by gerald on 3/24/16.
 */
public class SetChatSettingProcessor implements IMProcessorStart {

    public static final String TAG = "FetchChatSetting";
    private final IMessageResultCallback mCallback;
    private final List<ChatSetting> settingList;

    public SetChatSettingProcessor(IMessageResultCallback callback, List<ChatSetting> settingList) {
        mCallback = callback;
        this.settingList = settingList;
    }

    @Override
    public void startWorkFlow() {
        LogUtil.printMainProcess("FetchChatSetting start");

        ProChatSetting.SetChatSettingsReq logoutReq = new ProChatSetting.SetChatSettingsReq();
        for (ChatSetting chatSetting : settingList) {
            logoutReq.addSettings(chatSetting.convertTo());
        }

        BinaryMessage binaryMessage = new BinaryMessage();
        binaryMessage.setServiceName(ServiceNameEnum.IM_PLUS_CHAT_SETTING.getName());
        binaryMessage.setMethodName(MethodNameEnum.SET.getName());
        binaryMessage.setData(logoutReq.toByteArray());

        ChannelSdk.send(binaryMessage, mCallback);
    }
}
