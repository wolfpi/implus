package com.baidu.imc.impl.im.transaction.processor;

import com.baidu.imc.impl.im.protocol.file.HttpResult;

public interface BosHttpRequestCallback {

	void onBosCallbackResult(HttpResult result);
}
