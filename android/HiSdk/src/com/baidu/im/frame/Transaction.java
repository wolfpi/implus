package com.baidu.im.frame;

/**
 * 一个Transaction定义了具体的一个业务。<br>
 * 一个业务里会有一次与服务器的交互，但不局限于一次。比如：登陆，发送文本消息，分片发送文件。
 * 
 * @author zhaowei10
 * 
 */
public interface Transaction extends Runnable {

    final static String TIME = "time";
    final static String KEY = "key";
    final static String ACTION = "action";
    final static String RCODE = "rcode";
    final static String COST = "cost";
    final static String CLIENT = "client";
    final static String PROTOCOL = "protocol";

    final static String ID = "id";
    final static String NAME = "name";

    /**
     * 业务逻辑描述
     * 
     * @return
     * @throws Exception
     */
    ProcessorResult startWorkFlow() throws Exception;

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

    ProcessorResult runProcessor(BaseProcessor processor) throws Exception;
}
