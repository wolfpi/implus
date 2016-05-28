package com.baidu.imc.impl.im.transaction.response;

import com.baidu.im.frame.utils.LogUtil;

public class IMFileUploadSuccessResponse extends IMBaseResponse {

    private static final String TAG = "UploadFileSuccessResponse";

    public IMFileUploadSuccessResponse(String description, byte[] data, int errCode) {
        super(description, data, errCode);
        if (errCode == 0 && null != getData()) {
            createResponse();
        }
    }

    @Override
    public void createResponse() {
        LogUtil.printIm(getResponseName(), "Code:" + errCode);
    }

    @Override
    public String getResponseName() {
        return TAG;
    }

}
