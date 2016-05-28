package com.baidu.im.frame.utils;

import com.baidu.im.sdk.IMessageCallback;

public interface IRegAppCallback extends IMessageCallback{
    void regAppSucess();

    void regAppFail(int errCode);
}
