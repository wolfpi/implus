package com.baidu.imc.impl.im.message.content;

import com.baidu.imc.message.content.TextMessageContent;

public class BDHiTextMessageContent extends BDHiIMessageContent implements TextMessageContent {

    private String text;
    private String textID;

    public String getTextID() {
        return textID;
    }

    public void setTextID(String textID) {
        this.textID = textID;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "BDHiTextMessageContent [text=" + text + ", textID=" + textID + "]";
    }

}
