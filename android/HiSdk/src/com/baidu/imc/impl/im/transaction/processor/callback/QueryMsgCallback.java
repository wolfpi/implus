package com.baidu.imc.impl.im.transaction.processor.callback;

import com.baidu.imc.impl.im.transaction.response.QueryMsgsResponse;

public interface QueryMsgCallback {
    void onQueryMsgCallback(QueryMsgsResponse rsp);
}
