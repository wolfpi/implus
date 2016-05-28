package com.baidu.imc.message;

import com.baidu.imc.message.content.VoiceMessageContent;

/**
*
* <b>语音消息</b>
*
* @since 1.0
* @author WuBin
*
*/
public interface VoiceMessage extends Message {

    /**
     * <b>设置消息语音[必须]</b>
     * 
     * @since 1.0
     * 
     * @param voice 语音内容
     */
    public void setVoice(VoiceMessageContent voice);

}
