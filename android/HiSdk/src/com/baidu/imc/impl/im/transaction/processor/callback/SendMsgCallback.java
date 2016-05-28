package com.baidu.imc.impl.im.transaction.processor.callback;

import com.baidu.imc.impl.im.transaction.response.SendMsgResponse;

public interface SendMsgCallback {
    void onSendMsgCallback(SendMsgResponse rsp);
}
