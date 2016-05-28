package com.baidu.imc.message.content;

/**
 * <b>图片内容[发送]</b>
 *
 * @author WuBin
 * @since 1.0
 */
public interface VoiceMessageContent extends IMVoiceMessageContent, FileMessageContent {

    /**
     * <b>设置语音时长[毫秒]</b>
     *
     * @since 1.0
     */
    public void setDuration(int duration);

    /**
     * <b>语音已播放</b>
     *
     * @since 1.0
     */
    public boolean isPlayed();

    /**
     * <b>设置语音已播放</b>
     *
     * @since 1.0
     */
    public void setPlayed(boolean played);
}
