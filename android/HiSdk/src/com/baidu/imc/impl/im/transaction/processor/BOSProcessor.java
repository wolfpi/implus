package com.baidu.imc.impl.im.transaction.processor;

import com.baidu.imc.impl.im.protocol.file.HttpResult;

public interface BOSProcessor {

    void process() throws Exception;

    HttpResult getResult();

    BOSProcessorTimeout getTimeout();
    
    String getProcessorName();
}
