package com.baidu.imc.impl.im.transaction.request;

import com.baidu.im.frame.pb.ObjOneMsg.OneMsg;
import com.baidu.im.frame.pb.ProSendMsg.SendMsgReq;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.imc.exception.InitializationException;
import com.baidu.imc.impl.im.protocol.MethodNameEnum;
import com.baidu.imc.impl.im.protocol.ServiceNameEnum;

public class SendMsgRequest extends IMBaseRequest {

    private static final String TAG = "SendMsgRequest";
    private OneMsg builder;

    public SendMsgRequest(OneMsg messageBuilder) {
    	
    	if(messageBuilder == null)
    		throw new InitializationException();
    	
        this.builder = messageBuilder;
    }

    @Override
    public BinaryMessage createRequest() {
        SendMsgReq sendMsgReqBuilder = new SendMsgReq(); // migrate from builder
        sendMsgReqBuilder.setMsg(builder);

        BinaryMessage binaryMessage = new BinaryMessage();
        binaryMessage.setServiceName(ServiceNameEnum.IM_PLUS_MSG.getName());
        binaryMessage.setMethodName(MethodNameEnum.SEND.getName());
        binaryMessage.setData(sendMsgReqBuilder.toByteArray());
        return binaryMessage;
    }

    @Override
    public String getRequestName() {
        return TAG;
    }

}
