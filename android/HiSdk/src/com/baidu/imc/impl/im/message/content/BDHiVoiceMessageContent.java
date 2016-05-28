package com.baidu.imc.impl.im.message.content;

import com.baidu.imc.message.content.VoiceMessageContent;

public class BDHiVoiceMessageContent extends BDHiFileMessageContent implements VoiceMessageContent, PlayedChangedReporter {

    private int duration;
    private boolean played;
    private PlayedChangedListener playChangedListener;

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
       setPlayed(played, true);
    }

    public void setPlayed(boolean played, boolean saveNow){
        boolean changed = this.played != played;
        this.played = played;
        if(changed && playChangedListener != null){
            playChangedListener.onVoicePlayChanged(this, saveNow);
        }
    }

    @Override
    public String toString() {
        return "BDHiVoiceMessageContent [duration=" + duration + ", toString()=" + super.toString() + "]";
    }

    public void setPlayChangedListener(PlayedChangedListener listener) {
        this.playChangedListener = listener;
    }

}
