package com.baidu.im.frame;

import com.baidu.im.sdk.IMessageCallback;

public interface BizTransaction {

    ProcessorResult startWorkFlow(IMessageCallback callback);

    String getThreadName();

}
