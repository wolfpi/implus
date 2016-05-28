package com.baidu.imc.impl.im.transaction.processor.callback;

import com.baidu.imc.impl.im.transaction.response.BOSUploadFileResponse;

public interface BosUploadFileCallback {
     public void onBosUploadFileCallback(BOSUploadFileResponse rsp);
}
