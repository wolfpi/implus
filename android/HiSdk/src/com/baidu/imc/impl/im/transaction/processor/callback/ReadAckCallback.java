package com.baidu.imc.impl.im.transaction.processor.callback;

import com.baidu.imc.impl.im.transaction.response.ReadAckResponse;

public interface ReadAckCallback {
	void onReadAckCallback(ReadAckResponse rsp);
}
