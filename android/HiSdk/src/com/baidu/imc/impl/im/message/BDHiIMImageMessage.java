package com.baidu.imc.impl.im.message;

import com.baidu.imc.message.IMImageMessage;
import com.baidu.imc.message.ImageMessage;
import com.baidu.imc.message.content.FileMessageContent;
import com.baidu.imc.message.content.ImageMessageContent;

public class BDHiIMImageMessage extends BDHiIMFileMessage implements IMImageMessage, ImageMessage {

    public BDHiIMImageMessage() {
        setMessageType(BDHI_IMMESSAGE_TYPE.IMAGE);
    }
    
    @Override
    public ImageMessageContent getImage() {
        FileMessageContent file = getFileContent(BDHI_MESSAGE_CONTENT_ID.IMAGE);
        if (null != file && file instanceof ImageMessageContent) {
            return (ImageMessageContent) file;
        } else {
            return null;
        }
    }

    @Override
    public ImageMessageContent getThumbnailImage() {
        FileMessageContent file = getFileContent(BDHI_MESSAGE_CONTENT_ID.THUMBNAIL);
        if (null != file && file instanceof ImageMessageContent) {
            return (ImageMessageContent) file;
        } else {
            return null;
        }
    }

    @Override
    public void setImage(ImageMessageContent imageContent) {
        putFileContent(BDHI_MESSAGE_CONTENT_ID.IMAGE, imageContent);
    }

    @Override
    public void setThumbnailImage(ImageMessageContent thumbnailContent) {
        putFileContent(BDHI_MESSAGE_CONTENT_ID.THUMBNAIL, thumbnailContent);
    }
}
