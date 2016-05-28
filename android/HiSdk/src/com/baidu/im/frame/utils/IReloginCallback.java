package com.baidu.im.frame.utils;

import com.baidu.im.sdk.IMessageCallback;

public interface IReloginCallback extends IMessageCallback {
    void reloginSuccess();

    void reloginFail(int errCode);
}
