package com.baidu.imc.impl.im.transaction.request;

import com.baidu.im.frame.pb.ProQueryActiveContacts.QueryActiveContactsReq;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.imc.exception.InitializationException;
import com.baidu.imc.impl.im.protocol.MethodNameEnum;
import com.baidu.imc.impl.im.protocol.ServiceNameEnum;

public class QueryActiveContactsRequest extends IMBaseRequest {

    private static final String TAG = "QueryActiveContactsRequest";
    private long lastQueryTime;
    private int needReturnMsgsContactNum;

    public QueryActiveContactsRequest(long lastQueryTime, int needReturnMsgsContactNum) {
        if (lastQueryTime < 0 || needReturnMsgsContactNum <= 0) {
            throw new InitializationException();
        }
        this.lastQueryTime = lastQueryTime + 1;
        this.needReturnMsgsContactNum = needReturnMsgsContactNum;
    }

    @Override
    public BinaryMessage createRequest() {
        LogUtil.printIm(getRequestName(), "LastQueryTime:" + lastQueryTime + " needReturnMsgsContactNum:"
                + needReturnMsgsContactNum);
        BinaryMessage binaryMessage = null;
        if (lastQueryTime >= 0 && needReturnMsgsContactNum >= 0) {
            QueryActiveContactsReq reqBuilder = new QueryActiveContactsReq(); // migrate from builder
            reqBuilder.setLastQueryTime(lastQueryTime);
            reqBuilder.setNeedReturnMsgsContactsNum(0);
            reqBuilder.setNeedReturnMsgsNum(0);

            binaryMessage = new BinaryMessage();
            binaryMessage.setServiceName(ServiceNameEnum.IM_PLUS_MSG.getName());
            binaryMessage.setMethodName(MethodNameEnum.QUERY_ACTIVE_CONTACTS.getName());
            binaryMessage.setData(reqBuilder.toByteArray());

        }
        return binaryMessage;
    }

    @Override
    public String getRequestName() {
        return TAG;
    }

    public long getLastQueryTime() {
        return lastQueryTime;
    }

    public int getNeedReturnMsgsContactNum() {
        return needReturnMsgsContactNum;
    }

}
