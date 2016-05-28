package com.baidu.im.sdk;

/**
 * 公共接口的消息对象接口类。
 * 
 * @author zhaowei10
 * 
 */
public interface ImMessage {

    String getMessageId();

    MessageType getType();
}
