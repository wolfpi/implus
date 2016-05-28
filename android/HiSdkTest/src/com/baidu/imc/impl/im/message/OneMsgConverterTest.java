package com.baidu.imc.impl.im.message;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.imc.impl.im.message.content.BDHiVoiceMessageContent;

import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by gerald on 3/16/16.
 */
public class OneMsgConverterTest extends AndroidTestCase {

    private static final String TAG = "OneMsgConverterTest";

    public void testMarkVoicePlayedToIMMessageShouldWorkFirstTime() throws Exception {
        BDHiIMVoiceMessage msg = new BDHiIMVoiceMessage();
        BDHiVoiceMessageContent content = new BDHiVoiceMessageContent();
        content.setPlayed(true);
        msg.setVoice(content);
        OneMsgConverter.markVoicePlayedToIMMessage(msg, ".", true);
        assertNotNull("ext0 != null", msg.getExt0());
        assertTrue("ext0 not empty", msg.getExt0().length() != 0);
        JSONObject jo = new JSONObject(msg.getExt0());
        assertTrue("has ext0.playedVoiceId", jo.has("playedVoiceId"));
        JSONObject playedVoiceId = jo.getJSONObject("playedVoiceId");
        assertTrue("ext0.playedVoiceId has value", playedVoiceId.length() != 0);
        Log.i(TAG, "ext0.playedVoiceId = " + playedVoiceId.toString());
    }

    public void testMarkVoicePlayedToIMMessageShouldWorkByAppendingExistingEntry() throws Exception {
        BDHiIMVoiceMessage msg = new BDHiIMVoiceMessage();
        BDHiVoiceMessageContent content = new BDHiVoiceMessageContent();
        content.setPlayed(true);
        msg.setVoice(content);
        OneMsgConverter.markVoicePlayedToIMMessage(msg, ".", true);
        assertNotNull("ext0 != null", msg.getExt0());
        assertTrue("ext0 not empty", msg.getExt0().length() != 0);
        JSONObject jo = new JSONObject(msg.getExt0());
        assertTrue("has ext0.playedVoiceId", jo.has("playedVoiceId"));
        JSONObject playedVoiceId = jo.getJSONObject("playedVoiceId");
        assertEquals("ext0.playedVoiceId[.] == true", true, playedVoiceId.getBoolean("."));
        OneMsgConverter.markVoicePlayedToIMMessage(msg, "anotherVoiceID", true);
        playedVoiceId = new JSONObject(msg.getExt0()).getJSONObject("playedVoiceId");
        assertEquals("ext0.playedVoiceId[.] == true", true, playedVoiceId.getBoolean("."));
        assertEquals("ext0.playedVoiceId[anotherVoiceID] == true", true, playedVoiceId.getBoolean("anotherVoiceID"));

    }

    public void testMarkVoicePlayedToContentShouldWork() throws Exception {
        BDHiIMVoiceMessage msg = new BDHiIMVoiceMessage();
        msg.setExt0("{\""+OneMsgConverter.EXT0_KEY_PLAYEDVOICEID+"\":{\".\":true}}");
        BDHiVoiceMessageContent content = new BDHiVoiceMessageContent();
        OneMsgConverter.markVoicePlayedToContent(msg, ".", content);
        assertTrue("content.played == true", content.isPlayed());
    }

    public void testCreateJsonFromNullShouldFail() throws Exception {
        try {
            String json = null;
            new JSONObject(json);
        } catch (JSONException e) {
            fail("wrong exception, should be NullPointerException");
            return;
        } catch (Exception e) {
            return;
        }
        fail();
    }

    public void testCreateJsonFromEmptyStringShouldFail() throws Exception {
        try {
            String json = "";
            new JSONObject(json);
        } catch (JSONException e) {
            return;
        }
        fail();
    }

    public void testCreateJsonFromEmptyStringsShouldFail() throws Exception {
        try {
            String json = "      ";
            new JSONObject(json);
        } catch (JSONException e) {
            return;
        }
        fail();
    }
}
