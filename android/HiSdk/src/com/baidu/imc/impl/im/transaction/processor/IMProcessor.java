package com.baidu.imc.impl.im.transaction.processor;

public interface IMProcessor {

    IMProcessorTimeout getTimeout();

    void process() throws Exception;

    String getProcessorName();

    String getDescription();

    byte[] getData();

    int getErrCode();
}
