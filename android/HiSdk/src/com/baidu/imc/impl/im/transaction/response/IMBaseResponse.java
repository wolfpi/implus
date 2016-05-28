package com.baidu.imc.impl.im.transaction.response;

public abstract class IMBaseResponse implements IMResponse {

    protected String description;
    protected byte[] data;
    protected int errCode;

    public IMBaseResponse() {

    }

    public IMBaseResponse(String description, byte[] data, int errCode) {
        this.description = description;
        this.data = data;
        this.errCode = errCode;
    }

    @Override
    public abstract void createResponse();

    @Override
    public abstract String getResponseName();

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public byte[] getData() {
        return data;
    }

    @Override
    public int getErrCode() {
        return errCode;
    }
}
