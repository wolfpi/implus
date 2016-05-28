package com.baidu.imc.message;

import java.util.List;

import com.baidu.imc.message.content.IMMessageContent;

/**
 *
 * <b>文本消息</b>
 * 
 * <p>
 * 文本消息由被允许的消息内容构成
 * </p>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface IMTextMessage extends IMMessage {

    /**
     * <b>获得消息内容列表</b>
     * 
     * @return
     */
    public List<IMMessageContent> getMessageContentList();
}
