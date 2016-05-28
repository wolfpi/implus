package com.baidu.imc.impl.im.transaction.processor;

import com.baidu.im.frame.inapp.BaseTimeout;

public class IMProcessorTimeout extends BaseTimeout {
    
    private static final int DEFAULT_TIMEOUT = 1500;
    private static final int DEFAULT_RETRYTIME = 2;
    
    public IMProcessorTimeout() {
        setValue(DEFAULT_TIMEOUT);
        setTotalRetryTimes(DEFAULT_RETRYTIME);
    }
}
