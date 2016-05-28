package com.baidu.imc.impl.im.protocol;

import com.baidu.im.frame.pb.ProQueryActiveContacts.QueryActiveContactsReq;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.ChannelSdk;
import com.baidu.im.sdk.IMessageResultCallback;

public class QueryActiveConacts {
    public static void queryActiveContacts(long lastQueryTime, int needReturnMsgsContactNum,
            final IMessageResultCallback callback) {
        QueryActiveContactsReq reqBuilder = new  QueryActiveContactsReq();  // migrate from builder
        reqBuilder.setLastQueryTime(lastQueryTime);
        reqBuilder.setNeedReturnMsgsContactsNum(needReturnMsgsContactNum);
        reqBuilder.setNeedReturnMsgsNum(0);

        BinaryMessage binaryMessage = new BinaryMessage();
        binaryMessage.setServiceName(ServiceNameEnum.IM_PLUS_MSG.getName());
        binaryMessage.setMethodName(MethodNameEnum.QUERY_ACTIVE_CONTACTS.getName());
        binaryMessage.setData(reqBuilder.toByteArray());
        ChannelSdk.send(binaryMessage, callback);
    }
}
