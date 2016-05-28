package com.baidu.imc.impl.im.message.content;

import com.baidu.imc.message.content.VoiceMessageContent;

/**
 * Created by gerald on 3/21/16.
 */
public interface PlayedChangedListener {
    /**
     *
     * @param voiceMessageContent 语音内容
     * @param saveNow 是否立即保存该 Message
     */
    void onVoicePlayChanged(VoiceMessageContent voiceMessageContent, boolean saveNow);

}
