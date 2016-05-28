package com.baidu.imc.message;

import com.baidu.imc.message.content.IMMessageContent;

/**
*
* <b>自定义消息</b>
*
* @since 1.0
* @author WuBin
*
*/
public interface CustomMessage extends Message {
    
    /**
     * <b>添加一个消息内容</b>
     * <p>添加进去的消息内容会会放入一个区分key的消息内容数组</p>
     * 
     * @param key
     * @param content
     */
    public void addMessageContent(String key, IMMessageContent content);

}
