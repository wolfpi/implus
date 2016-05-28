package com.baidu.imc.impl.im.transaction.response;

import java.io.InputStream;

import com.baidu.im.frame.utils.LogUtil;

public class BOSUploadFileResponse extends BOSBaseResponse {

    private static final String TAG = "UploadFileResponse";
    private boolean isFileUploaded = false;

    public BOSUploadFileResponse(int statusCode, int length, InputStream content) {
        super(statusCode, length, content);
        if (statusCode > -1 && length > -1) {
            createResponse();
        }
    }

    @Override
    public void createResponse() {
        LogUtil.printIm(getResponseName(), "StatusCode:" + statusCode);
        if (statusCode == 200) {
            isFileUploaded = true;
        }
        LogUtil.printIm(getResponseName(), "Result:" + isFileUploaded + " statusCode:" + statusCode + " contentLength:"
                + length);
    }

    @Override
    public String getResponseName() {
        return TAG;
    }

    public boolean isFileUploaded() {
        return isFileUploaded;
    }

}
