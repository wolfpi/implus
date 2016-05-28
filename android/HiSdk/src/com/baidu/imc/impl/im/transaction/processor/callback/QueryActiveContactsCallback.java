package com.baidu.imc.impl.im.transaction.processor.callback;

import com.baidu.imc.impl.im.transaction.response.QueryActiveContactsResponse;

public interface QueryActiveContactsCallback {
	void onQueryActiveContactsCallback(QueryActiveContactsResponse resp);
}
