package com.baidu.imc.impl.im.message.content;

import com.baidu.imc.message.content.ImageMessageContent;

public class BDHiImageMessageContent extends BDHiFileMessageContent implements ImageMessageContent {

    private int height;
    private int width;

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "BDHiImageMessageContent [height=" + height + ", width=" + width + ", toString()=" + super.toString()
                + "]";
    }

}
