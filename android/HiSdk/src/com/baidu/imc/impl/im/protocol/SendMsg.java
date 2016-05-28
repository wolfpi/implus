package com.baidu.imc.impl.im.protocol;

import com.baidu.im.frame.pb.ProSendMsg.SendMsgReq;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.ChannelSdk;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.imc.impl.im.message.BDHiIMMessage;
import com.baidu.imc.impl.im.message.OneMsgConverter;

public class SendMsg {
    public static void send(BDHiIMMessage message, final IMessageResultCallback callback) {
    	if(message == null || callback == null)
    		return;
    	
        SendMsgReq sendMsgReqBuilder = new  SendMsgReq();  // migrate from builder
        sendMsgReqBuilder.setMsg(OneMsgConverter.convertIMMessage(message));

        BinaryMessage binaryMessage = new BinaryMessage();
        binaryMessage.setServiceName(ServiceNameEnum.IM_PLUS_MSG.getName());
        binaryMessage.setMethodName(MethodNameEnum.SEND.getName());
        binaryMessage.setData(sendMsgReqBuilder.toByteArray());
        ChannelSdk.send(binaryMessage, callback);
    }
}
