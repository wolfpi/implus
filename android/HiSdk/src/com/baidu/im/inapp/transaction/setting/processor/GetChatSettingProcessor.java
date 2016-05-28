package com.baidu.im.inapp.transaction.setting.processor;

import com.baidu.im.frame.inappCallback.QueryChatSettingCallback;
import com.baidu.im.frame.pb.EnumChatType;
import com.baidu.im.frame.pb.ProChatSetting;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.ChannelSdk;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.imc.impl.im.message.OneMsgConverter;
import com.baidu.imc.impl.im.protocol.MethodNameEnum;
import com.baidu.imc.impl.im.protocol.ServiceNameEnum;
import com.baidu.imc.impl.im.store.ChatSetting;
import com.baidu.imc.impl.im.transaction.processor.IMProcessorStart;
import com.baidu.imc.type.AddresseeType;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

/**
 * Created by gerald on 3/24/16.
 */
public class GetChatSettingProcessor implements IMProcessorStart, IMessageResultCallback {

    public static final String TAG = "FetchChatSetting";
    private final AddresseeType targetType;
    private final String targetId;
    private QueryChatSettingCallback mCallback;

    public GetChatSettingProcessor(AddresseeType targetType, String targetId, QueryChatSettingCallback callback) {
        this.targetType = targetType;
        this.targetId = targetId;
        this.mCallback = callback;
    }

    @Override
    public void startWorkFlow() {
        LogUtil.printMainProcess("FetchChatSetting start");

        ProChatSetting.GetChatSettingsReq req = new ProChatSetting.GetChatSettingsReq();
        req.setChatType(OneMsgConverter.enumChatTypeOf(targetType, EnumChatType.CHAT_P2P));
        req.addTargetIDList(targetId);

        BinaryMessage binaryMessage = new BinaryMessage();
        binaryMessage.setServiceName(ServiceNameEnum.IM_PLUS_CHAT_SETTING.getName());
        binaryMessage.setMethodName(MethodNameEnum.GET.getName());
        binaryMessage.setData(req.toByteArray());

        ChannelSdk.send(binaryMessage, this);
    }

    @Override
    public void onSuccess(String description, byte[] data) {
        try {
            ProChatSetting.GetChatSettingsRsp rsp = ProChatSetting.GetChatSettingsRsp.parseFrom(data);
            mCallback.chatSettingResult(ChatSetting.convertFrom(rsp.getSettingsList()), 0L);
        } catch (InvalidProtocolBufferMicroException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFail(int errorCode) {

    }
}
