package com.baidu.imc.impl.im.message.content;

import com.baidu.imc.message.content.URLMessageContent;

public class BDHiURLMessageContent extends BDHiIMessageContent implements URLMessageContent {

    private String text;
    private String url;
    private String urlID;

    public String getUrlID() {
        return urlID;
    }

    public void setUrlID(String urlID) {
        this.urlID = urlID;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void setURL(String url) {
        this.url = url;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getURL() {
        return url;
    }

}
