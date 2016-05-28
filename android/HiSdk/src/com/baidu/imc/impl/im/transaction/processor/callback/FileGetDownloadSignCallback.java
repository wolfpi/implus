package com.baidu.imc.impl.im.transaction.processor.callback;

import com.baidu.imc.impl.im.transaction.response.IMFileGetDownloadSignResponse;

public interface FileGetDownloadSignCallback {
    void onFileGetDownloadSignCallback(IMFileGetDownloadSignResponse rsp);
}
