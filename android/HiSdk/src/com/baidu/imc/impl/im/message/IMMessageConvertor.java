package com.baidu.imc.impl.im.message;

import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.imc.impl.im.util.MessageIDGenerator;
import com.baidu.imc.message.Message;
import com.baidu.imc.message.TransientMessage;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.IMMessageStatus;

public class IMMessageConvertor {

    /**
     * Convert a Message to IMMessage
     * 
     * @param addresseeType
     * @param addresseeID
     * @param addresserID
     * @param message
     */
    public static BDHiIMMessage convertMessage(AddresseeType addresseeType, String addresseeID, String addresserID,
            Message message) {
        // BDHiIMMessage imMessage = null;
        // if (message instanceof TextMessage) {
        // imMessage = convertTextMessage((TextMessage) message);
        // } else if (message instanceof VoiceMessage) {
        // imMessage = convertVoiceMessage((VoiceMessage) message);
        // } else if (message instanceof ImageMessage) {
        // imMessage = convertImageMessage((ImageMessage) message);
        // } else if (message instanceof FileMessage) {
        // imMessage = convertFileMessage((FileMessage) message);
        // } else {
        // imMessage = null;
        // }
        BDHiIMMessage imMessage = null;
        if (message instanceof BDHiIMMessage) {
            imMessage = (BDHiIMMessage) message;
        }
        convertCommonInfo(addresseeType, addresseeID, addresserID, imMessage, message);
        return imMessage;
    }

    /**
     * Convert the common information of a Message into a IMMessage
     * 
     * @param addresseeType
     * @param addresseeID
     * @param addresserID
     * @param imMessage
     * @param message
     */
    private static void convertCommonInfo(AddresseeType addresseeType, String addresseeID, String addresserID,
            BDHiIMMessage imMessage, Message message) {
        if (null != imMessage) {
            imMessage.setAddresseeID(addresseeID);
            imMessage.setAddresseeType(addresseeType);
            imMessage.setAddresserID(addresserID);
            // imMessage.setAddresserName(addresserName);
            long currentTime = System.currentTimeMillis();
            
            long fixcurrentTime = InAppApplication.getInstance().getTimeDelta() + currentTime;
            imMessage.setSendTime(fixcurrentTime);
            imMessage.setServerTime(fixcurrentTime);
            imMessage.setClientMessageID(MessageIDGenerator.getInstance().generateClientMessageId());
            // if (message instanceof BDHiMessage) {
            // imMessage.setAddresserName(((BDHiMessage) message).getAddresserName());
            // imMessage.setCompatibleText(((BDHiMessage) message).getCompatibleText());
            // imMessage.setNotificationText(((BDHiMessage) message).getNotificationText());
            // imMessage.setExtra(((BDHiMessage) message).getExtra());
            // }
        }
    }

    // /**
    // * Convert a TextMessage to IMTextMessage
    // *
    // * @param textMessage
    // * @return imTextMessage
    // */
    // private static BDHiIMTextMessage convertTextMessage(TextMessage textMessage) {
    // BDHiIMTextMessage imTextMessage = new BDHiIMTextMessage();
    // imTextMessage.setMessageType(BDHI_IMMESSAGE_TYPE.TEXT);
    // // if (textMessage instanceof BDHiTextMessage) {
    // // BDHiTextMessage bdhiTextMessage = (BDHiTextMessage) textMessage;
    // // // List<TextMessageContent> textMessageContents = bdhiTextMessage.getTextList();
    // // // if (null != textMessageContents) {
    // // // imTextMessage.addTexts(textMessageContents);
    // // // }
    // // // List<URLMessageContent> urlMessageContents = bdhiTextMessage.getUrlList();
    // // // if (null != urlMessageContents) {
    // // // imTextMessage.addURLs(urlMessageContents);
    // // // }
    // // imTextMessage.addMessageContentList(bdhiTextMessage.getMessageContentList());
    // // }
    // return imTextMessage;
    // }

    // /**
    // * Convert a VoiceMessage to a IMVoiceMessage
    // *
    // * @param voiceMessage
    // * @return imVoiceMessage
    // */
    // private static BDHiIMVoiceMessage convertVoiceMessage(VoiceMessage voiceMessage) {
    // BDHiIMVoiceMessage imVoiceMessage = new BDHiIMVoiceMessage();
    // imVoiceMessage.setMessageType(BDHI_IMMESSAGE_TYPE.VOICE);
    // // if (voiceMessage instanceof BDHiVoiceMessage) {
    // // BDHiVoiceMessage bdhiVoiceMessage = (BDHiVoiceMessage) voiceMessage;
    // // VoiceMessageContent messageContent = bdhiVoiceMessage.getVoice();
    // // if (null != messageContent) {
    // // imVoiceMessage.setVoice(messageContent);
    // // }
    // // }
    // return imVoiceMessage;
    // }

    // /**
    // * Convert a ImageMessage to a IMImageMessage
    // *
    // * @param imageMessage
    // * @return imImageMessage
    // */
    // private static BDHiIMImageMessage convertImageMessage(ImageMessage imageMessage) {
    // BDHiIMImageMessage imImageMessage = new BDHiIMImageMessage();
    // imImageMessage.setMessageType(BDHI_IMMESSAGE_TYPE.IMAGE);
    // // if (imageMessage instanceof BDHiImageMessage) {
    // // BDHiImageMessage bdhiImageMessage = (BDHiImageMessage) imageMessage;
    // // ImageMessageContent imageMessageContent = bdhiImageMessage.getImage();
    // // if (null != imageMessageContent) {
    // // imImageMessage.setImage(imageMessageContent);
    // // }
    // // ImageMessageContent thumbnailMessageContent = bdhiImageMessage.getThumbnail();
    // // if (null != thumbnailMessageContent) {
    // // imImageMessage.setThumbnailImage(thumbnailMessageContent);
    // // }
    // // }
    // return imImageMessage;
    // }

    // /**
    // * Convert a FileMessage to a IMFileMessage
    // *
    // * @param fileMessage
    // * @return imFileMessage
    // */
    // private static BDHiIMFileMessage convertFileMessage(FileMessage fileMessage) {
    // BDHiIMFileMessage imFileMessage = new BDHiIMImageMessage();
    // imFileMessage.setMessageType(BDHI_IMMESSAGE_TYPE.FILE);
    // // if (fileMessage instanceof BDHiFileMessage) {
    // // BDHiFileMessage bdhiFileMessage = (BDHiFileMessage) fileMessage;
    // // FileMessageContent messageContent = bdhiFileMessage.getFile();
    // // if (null != messageContent) {
    // // imFileMessage.setFile(messageContent);
    // // }
    // // }
    // return imFileMessage;
    // }

    /**
     * Convert a TrasientMessage to a IMTransientMessage
     * 
     * @param transientMessage
     * @param imTransientMessage
     */
    public static BDHiIMTransientMessage convertTransientMessageToIMTransientMessage(AddresseeType addresseeType,
            String addresseeID, String addresserID, TransientMessage transientMessage) {
        BDHiIMTransientMessage imTransientMessage = null;
        if (null != transientMessage) {
            imTransientMessage = new BDHiIMTransientMessage();
            imTransientMessage.setAddresseeType(addresseeType);
            imTransientMessage.setAddresseeID(addresseeID);
            imTransientMessage.setAddresserID(addresserID);
            imTransientMessage.setAddresserName(addresserID);
            if (transientMessage instanceof BDHiTransientMessage) {
                imTransientMessage.setContent(((BDHiTransientMessage) transientMessage).getContent());
            }
            imTransientMessage.setStatus(IMMessageStatus.SENDING);
        }
        return imTransientMessage;
    }
}
