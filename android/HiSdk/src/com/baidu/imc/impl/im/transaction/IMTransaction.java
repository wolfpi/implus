package com.baidu.imc.impl.im.transaction;

import com.baidu.imc.impl.im.transaction.processor.IMProcessor;

public interface IMTransaction extends Runnable {

    final static String ID = "id";
    final static String NAME = "name";
    final static String TIME = "time";
    final static String KEY = "key";
    final static String ACTION = "action";
    final static String RCODE = "rcode";
    final static String COST = "cost";
    final static String CLIENT = "client";
    final static String PROTOCOL = "protocol";

    /**
     * 业务逻辑描述
     * 
     * @return
     * @throws Exception
     */
    void startWorkFlow() throws Exception;

    /**
     * 业务结束
     */
    void terminate();

    /**
     * 获取本线程名称
     * 
     * @return
     */
    String getThreadName();

    /**
     * 执行业务请求
     */
    void runProcessor(IMProcessor processor) throws Exception;

}
