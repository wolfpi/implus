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
public interface IMVoiceMessage extends IMMessage {
    
    /**
     * <b>获得语音内容</b>
     * 
     * @return
     */
    public VoiceMessageContent getVoice();

}
