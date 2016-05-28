package com.baidu.imc.impl.im.transaction.processor;

import com.baidu.im.frame.inapp.BaseTimeout;

public class BOSProcessorTimeout extends BaseTimeout {

    private static final int BOS_TIMEOUT = 10000;
    private static final int BOS_RETRYTIME = 2;

    public BOSProcessorTimeout() {
        setValue(BOS_TIMEOUT);
        setTotalRetryTimes(BOS_RETRYTIME);
    }

}
