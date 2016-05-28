package com.baidu.imc.impl.im.transaction.response;

import java.io.InputStream;

public interface BOSResponse {

    public void createResponse();

    public String getResponseName();

    public long getContentLength();

    public InputStream getContent();

    public int getStatusCode();
}
