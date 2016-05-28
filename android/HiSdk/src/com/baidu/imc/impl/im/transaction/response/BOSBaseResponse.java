package com.baidu.imc.impl.im.transaction.response;

import java.io.InputStream;

public abstract class BOSBaseResponse implements BOSResponse {

    protected static final int BUFFER_SIZE = 50*1024;

    protected int statusCode;
    protected long length;
    protected InputStream content;

    public BOSBaseResponse(int statusCode, long length, InputStream content) {
        this.statusCode = statusCode;
        this.length = length;
        this.content = content;
    }

    @Override
    public abstract void createResponse();

    @Override
    public abstract String getResponseName();

    @Override
    public long getContentLength() {
        return length;
    }

    @Override
    public InputStream getContent() {
        return content;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }
}
