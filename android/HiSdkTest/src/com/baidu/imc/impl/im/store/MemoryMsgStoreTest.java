package com.baidu.imc.impl.im.store;

import com.baidu.imc.impl.im.message.BDHiIMCustomMessage;
import com.baidu.imc.impl.im.message.BDHiIMVoiceMessage;
import com.baidu.imc.impl.im.message.content.BDHiVoiceMessageContent;

import android.test.AndroidTestCase;

/**
 * Created by gerald on 3/17/16.
 */
public class MemoryMsgStoreTest extends AndroidTestCase {

    public void testMarkVoiceContentAsPlayedShouldWorkWithVoiceMsg() {
        BDHiIMVoiceMessage msg = new BDHiIMVoiceMessage();
        BDHiVoiceMessageContent content = new BDHiVoiceMessageContent();
        msg.setVoice(content);
        assertFalse(((BDHiVoiceMessageContent) msg.getVoice()).isPlayed());
        MemoryMsgStore.markVoiceContentAsPlayed(msg, true);
        assertTrue(((BDHiVoiceMessageContent) msg.getVoice()).isPlayed());
    }

    public void testMarkVoiceContentAsPlayedShouldWorkWithCustomMsg() {
        BDHiIMCustomMessage msg = new BDHiIMCustomMessage();
        BDHiVoiceMessageContent content = new BDHiVoiceMessageContent();
        msg.addMessageContent("voiceid1", content);
        assertFalse(((BDHiVoiceMessageContent) msg.getMessageContent("voiceid1")).isPlayed());
        MemoryMsgStore.markVoiceContentAsPlayed(msg, true);
        assertTrue(((BDHiVoiceMessageContent) msg.getMessageContent("voiceid1")).isPlayed());
    }
}
