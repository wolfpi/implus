package com.baidu.hi.sdk.adapters;

import com.baidu.imc.message.content.FileMessageContent;

public class ChatMsgEntity {
    private String name;
    private String date;
    private String status;
    private String text;
    private boolean isComMeg = true;
    private String displayMsgType;
    private long messageID;
    private String fileName;
    private FileMessageContent fileMessageContent;
    private String bigFileName;
    private FileMessageContent bigFileMessageContent;
    private String id;
    private String msgTemplate;

    public ChatMsgEntity() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getMsgType() {
        return isComMeg;
    }

    public void setMsgType(boolean isComMsg) {
        isComMeg = isComMsg;
    }

    public long getMessageID() {
        return messageID;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }

    public String getDisplayMsgType() {
        return displayMsgType;
    }

    public void setDisplayMsgType(String displayMsgType) {
        this.displayMsgType = displayMsgType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileMessageContent getFileMessageContent() {
        return fileMessageContent;
    }

    public void setFileMessageContent(FileMessageContent fileMessageContent) {
        this.fileMessageContent = fileMessageContent;
    }

    public String getBigFileName() {
        return bigFileName;
    }

    public void setBigFileName(String bigFileName) {
        this.bigFileName = bigFileName;
    }

    public FileMessageContent getBigFileMessageContent() {
        return bigFileMessageContent;
    }

    public void setBigFileMessageContent(FileMessageContent bigFileMessageContent) {
        this.bigFileMessageContent = bigFileMessageContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgTemplate() {
        return msgTemplate;
    }

    public void setMsgTemplate(String msgTemplate) {
        this.msgTemplate = msgTemplate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (messageID ^ (messageID >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ChatMsgEntity other = (ChatMsgEntity) obj;
        if (messageID != other.messageID)
            return false;
        return true;
    }
}