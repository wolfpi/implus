package com.baidu.imc.impl.im.transaction.processor.callback;

import com.baidu.imc.impl.im.transaction.response.IMFileGetUploadSignResponse;

public interface FileGetUploadSignCallback {
    void onFileGetUploadSignCallback(IMFileGetUploadSignResponse rsp);
}
