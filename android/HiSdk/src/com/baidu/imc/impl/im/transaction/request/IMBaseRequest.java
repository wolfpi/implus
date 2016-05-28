package com.baidu.imc.impl.im.transaction.request;

import com.baidu.im.sdk.BinaryMessage;

public abstract class IMBaseRequest implements Request<BinaryMessage> {

    @Override
    public abstract BinaryMessage createRequest();

    @Override
    public abstract String getRequestName();

}
