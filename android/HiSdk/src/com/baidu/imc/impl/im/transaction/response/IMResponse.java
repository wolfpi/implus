package com.baidu.imc.impl.im.transaction.response;

public interface IMResponse {

    public void createResponse();

    public String getResponseName();

    public String getDescription();

    public byte[] getData();

    public int getErrCode();
}
