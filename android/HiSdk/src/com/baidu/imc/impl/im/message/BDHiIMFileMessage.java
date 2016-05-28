package com.baidu.imc.impl.im.message;

import java.util.HashMap;
import java.util.Map;

import com.baidu.imc.message.FileMessage;
import com.baidu.imc.message.IMFileMessage;
import com.baidu.imc.message.content.FileMessageContent;

public class BDHiIMFileMessage extends BDHiIMMessage implements IMFileMessage, FileMessage {
    private Map<BDHI_MESSAGE_CONTENT_ID, FileMessageContent> fileContents =
            new HashMap<BDHI_MESSAGE_CONTENT_ID, FileMessageContent>();

    public BDHiIMFileMessage() {
        setMessageType(BDHI_IMMESSAGE_TYPE.FILE);
    }

    public Map<BDHI_MESSAGE_CONTENT_ID, FileMessageContent> getFiles() {
        return fileContents;
    }

    protected void putFileContent(BDHI_MESSAGE_CONTENT_ID fileID, FileMessageContent file) {
        fileContents.put(fileID, file);
    }

    protected FileMessageContent getFileContent(BDHI_MESSAGE_CONTENT_ID fileID) {
        return fileContents.get(fileID);
    }

    @Override
    public FileMessageContent getFile() {
        FileMessageContent messageContent = getFileContent(BDHI_MESSAGE_CONTENT_ID.FILE);
        return messageContent;
    }

    @Override
    public void setFile(FileMessageContent fileContent) {
        putFileContent(BDHI_MESSAGE_CONTENT_ID.FILE, fileContent);
    }

}
