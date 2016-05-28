package com.baidu.imc.impl.im.transaction.processor.callback;

import com.baidu.imc.impl.im.transaction.response.IMFileUploadSuccessResponse;

public interface FileUploadSuccessCallback {
    void onFileUploadSuccessCallback(IMFileUploadSuccessResponse rsp);
}
