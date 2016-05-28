package com.baidu.imc.impl.im.message;

import com.baidu.imc.IMPlusSDK;
import com.baidu.imc.impl.im.client.IMInboxImpl;
import com.baidu.imc.impl.im.message.content.BDHiVoiceMessageContent;
import com.baidu.imc.impl.im.message.content.PlayedChangedListener;
import com.baidu.imc.impl.im.message.content.PlayedChangedReporter;
import com.baidu.imc.message.IMVoiceMessage;
import com.baidu.imc.message.VoiceMessage;
import com.baidu.imc.message.content.FileMessageContent;
import com.baidu.imc.message.content.VoiceMessageContent;

public class BDHiIMVoiceMessage extends BDHiIMFileMessage implements IMVoiceMessage, VoiceMessage,
        PlayedChangedListener {

    public BDHiIMVoiceMessage() {
        setMessageType(BDHI_IMMESSAGE_TYPE.VOICE);
    }

    @Override
    public VoiceMessageContent getVoice() {
        FileMessageContent file = getFileContent(BDHI_MESSAGE_CONTENT_ID.VOICE);
        if (null != file && file instanceof VoiceMessageContent) {
            return (VoiceMessageContent) file;
        } else {
            return null;
        }
    }

    @Override
    public void setVoice(VoiceMessageContent voiceContent) {
        putFileContent(BDHI_MESSAGE_CONTENT_ID.VOICE, voiceContent);
        OneMsgConverter.markVoicePlayedToIMMessage(this, ".", voiceContent.isPlayed());
        if (voiceContent instanceof PlayedChangedReporter) {
            PlayedChangedReporter reporter = (PlayedChangedReporter) voiceContent;
            reporter.setPlayChangedListener(this);
        }
    }

    @Override
    public void onVoicePlayChanged(VoiceMessageContent voiceMessageContent, boolean saveNow) {
        OneMsgConverter.markVoicePlayedToIMMessage(this, ".", voiceMessageContent.isPlayed());
        if(saveNow) {
            ((IMInboxImpl) IMPlusSDK.getImpClient().getIMInbox()).updateMessage(this);
        }
    }
}
