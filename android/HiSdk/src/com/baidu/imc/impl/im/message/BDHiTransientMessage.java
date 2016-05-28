package com.baidu.imc.impl.im.message;

import com.baidu.imc.message.TransientMessage;

public class BDHiTransientMessage implements TransientMessage {

    private String content;

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
