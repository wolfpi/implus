package com.baidu.imc.impl.im.message;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.im.frame.pb.EnumChatType;
import com.baidu.im.frame.pb.ObjData.Data;
import com.baidu.im.frame.pb.ObjFile.File;
import com.baidu.im.frame.pb.ObjImage.Image;
import com.baidu.im.frame.pb.ObjMsgContent.MsgContent;
import com.baidu.im.frame.pb.ObjOneMsg.OneMsg;
import com.baidu.im.frame.pb.ObjText.Text;
import com.baidu.im.frame.pb.ObjUrl.URL;
import com.baidu.im.frame.pb.ObjVoice.Voice;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.impl.im.message.content.BDHiFileMessageContent;
import com.baidu.imc.impl.im.message.content.BDHiImageMessageContent;
import com.baidu.imc.impl.im.message.content.BDHiTextMessageContent;
import com.baidu.imc.impl.im.message.content.BDHiURLMessageContent;
import com.baidu.imc.impl.im.message.content.BDHiVoiceMessageContent;
import com.baidu.imc.message.content.FileMessageContent;
import com.baidu.imc.message.content.IMMessageContent;
import com.baidu.imc.message.content.ImageMessageContent;
import com.baidu.imc.message.content.TextMessageContent;
import com.baidu.imc.message.content.URLMessageContent;
import com.baidu.imc.message.content.VoiceMessageContent;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.IMMessageStatus;
import com.google.protobuf.micro.ByteStringMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

import android.text.TextUtils;
import android.util.Log;

public class OneMsgConverter {

    private static final String TAG = "OneMsgConverter";
    private static final String IMPLUS_PREFIX = "implus://";
    private static final String FILEPATH_PREFIX = "filepath://";
    private static final String FILEPATH_REPLACEMENT = "";

    private static final String IMPLUS_DIVIDER = "/";
    public static final String EXT0_KEY_PLAYEDVOICEID = "playedVoiceId"; // used by IMMessage.ext0 field

    /**
     * Convert all MessageContents of a IMCustomMessage into a MsgContent(of a OneMsg)
     *
     * IMMessage -> OneMsg
     *
     * @param msgContentBuilder
     * @param imCustomMessage
     */
    private static void convertIMCustomMessage(MsgContent msgContentBuilder,
                                               BDHiIMCustomMessage imCustomMessage) {  // migrate from builder
        if (null != msgContentBuilder && null != imCustomMessage) {
            Map<String, List<IMMessageContent>> messageContentMap = imCustomMessage.getAllMessageContentMap();
            Iterator<Entry<String, List<IMMessageContent>>> messageContentListIterator =
                    messageContentMap.entrySet().iterator();
            while (messageContentListIterator.hasNext()) {
                Entry<String, List<IMMessageContent>> entry = messageContentListIterator.next();
                String key = entry.getKey();
                List<IMMessageContent> messageContents = entry.getValue();
                if (!TextUtils.isEmpty(key) && null != messageContents) {
                    for (int i = 0; i < messageContents.size(); i++) {
                        IMMessageContent messageContent = messageContents.get(i);
                        String tmpKey = key + "_" + i;
                        if (null != messageContent) {
                            if (messageContent instanceof TextMessageContent) {
                                TextMessageContent textMessageContent = (TextMessageContent) messageContent;
                                Text textBuilder = new Text();  // migrate from builder
                                textBuilder.setTextId(tmpKey);
                                textBuilder.setContent(null != textMessageContent.getText() ? textMessageContent
                                        .getText() : "");
                                textBuilder.setTitle("");
                                textBuilder.setDescription("");
                                msgContentBuilder.addTexts(textBuilder);
                            } else if (messageContent instanceof URLMessageContent) {
                                URLMessageContent urlMessageContent = (URLMessageContent) messageContent;
                                URL urlBuilder = new URL(); // migrate from builder
                                urlBuilder.setUrlId(tmpKey);
                                urlBuilder.setUrl(null != urlMessageContent.getURL() ? urlMessageContent.getURL() : "");
                                urlBuilder.setTitle(null != urlMessageContent.getText() ? urlMessageContent.getText()
                                        : "");
                                msgContentBuilder.addUrls(urlBuilder);
                            } else if (messageContent instanceof BDHiImageMessageContent) {
                                BDHiImageMessageContent bdhiImageContent = (BDHiImageMessageContent) messageContent;
                                Image imageBuilder = new Image();  // migrate from builder
                                imageBuilder.setImageId(tmpKey);
                                imageBuilder.setMd5(null != bdhiImageContent.getFileMD5() ? bdhiImageContent
                                        .getFileMD5() : "");
                                imageBuilder.setSize((int) bdhiImageContent.getFileSize());
                                imageBuilder.setWidth(bdhiImageContent.getWidth());
                                imageBuilder.setHeight(bdhiImageContent.getHeight());
                                if (null != bdhiImageContent.getFileURL()
                                        && bdhiImageContent.getFileURL().length() > 0) {
                                    imageBuilder.setUrl(bdhiImageContent.getFileURL());
                                } else if (null != bdhiImageContent.getFid()
                                        && bdhiImageContent.getFid().length() > 0) {
                                    imageBuilder.setUrl(IMPLUS_PREFIX + bdhiImageContent.getFid() + IMPLUS_DIVIDER
                                            + bdhiImageContent.getFileMD5());
                                } else if (null != bdhiImageContent.getLocalResource()
                                        && bdhiImageContent.getLocalResource().length() > 0) {
                                    imageBuilder.setUrl(FILEPATH_PREFIX + bdhiImageContent.getLocalResource());
                                } else {
                                    imageBuilder.setUrl("");
                                }
                                msgContentBuilder.addImages(imageBuilder);
                            } else if (messageContent instanceof BDHiVoiceMessageContent) {
                                BDHiVoiceMessageContent bdhiVoiceContent = (BDHiVoiceMessageContent) messageContent;
                                Voice voiceBuilder = new Voice(); // migrate from builder
                                voiceBuilder.setVoiceId(tmpKey);
                                voiceBuilder.setMd5(null != bdhiVoiceContent.getFileMD5() ? bdhiVoiceContent
                                        .getFileMD5() : "");
                                voiceBuilder.setSize((int) bdhiVoiceContent.getFileSize());
                                voiceBuilder.setTimeLen(bdhiVoiceContent.getDuration());
                                markVoicePlayedToIMMessage(imCustomMessage, key, bdhiVoiceContent.isPlayed());
                                if (null != bdhiVoiceContent.getFileURL()
                                        && bdhiVoiceContent.getFileURL().length() > 0) {
                                    voiceBuilder.setUrl(bdhiVoiceContent.getFileURL());
                                } else if (null != bdhiVoiceContent.getFid()
                                        && bdhiVoiceContent.getFid().length() > 0) {
                                    voiceBuilder.setUrl(IMPLUS_PREFIX + bdhiVoiceContent.getFid() + IMPLUS_DIVIDER
                                            + bdhiVoiceContent.getFileMD5());
                                } else if (null != bdhiVoiceContent.getLocalResource()
                                        && bdhiVoiceContent.getLocalResource().length() > 0) {
                                    voiceBuilder.setUrl(FILEPATH_PREFIX + bdhiVoiceContent.getLocalResource());
                                } else {
                                    voiceBuilder.setUrl("");
                                }
                            } else if (messageContent instanceof BDHiFileMessageContent) {
                                BDHiFileMessageContent bdhiFileContent = (BDHiFileMessageContent) messageContent;
                                File fileBuilder = new File(); // migrate from builder
                                fileBuilder.setFileId(tmpKey);
                                fileBuilder.setMd5(null != bdhiFileContent.getFileMD5() ? bdhiFileContent.getFileMD5()
                                        : "");
                                fileBuilder.setSize((int) bdhiFileContent.getFileSize());
                                fileBuilder.setName(null != bdhiFileContent.getFileName() ? bdhiFileContent
                                        .getFileName() : "");
                                if (null != bdhiFileContent.getFileURL() && bdhiFileContent.getFileURL().length() > 0) {
                                    fileBuilder.setFileUrl(bdhiFileContent.getFileURL());
                                } else if (null != bdhiFileContent.getFid() && bdhiFileContent.getFid().length() > 0) {
                                    fileBuilder.setFileUrl(IMPLUS_PREFIX + bdhiFileContent.getFid() + IMPLUS_DIVIDER
                                            + bdhiFileContent.getFileMD5());
                                } else if (null != bdhiFileContent.getLocalResource()
                                        && bdhiFileContent.getLocalResource().length() > 0) {
                                    fileBuilder.setFileUrl(FILEPATH_PREFIX + bdhiFileContent.getLocalResource());
                                } else {
                                    fileBuilder.setFileUrl("");
                                }
                                msgContentBuilder.addFiles(fileBuilder);
                            }
                        }
                    }
                }
            }
            // List<IMMessageContentEntry> messageContents = imCustomMessage.getAllMessageContent();
            // if (null != messageContents) {
            // for (int i = 0; i < messageContents.size(); i++) {
            // IMMessageContentEntry messageContentEntry = messageContents.get(i);
            // if (null != messageContentEntry && messageContentEntry.isValid()) {
            // if (messageContentEntry.messageContent instanceof TextMessageContent) {
            // BDHiTextMessageContent textMessageContent =
            // (BDHiTextMessageContent) messageContentEntry.messageContent;
            // Text textBuilder = new Text(); // migrate from builder 
            // textBuilder.setTextId(messageContentEntry.key);
            // textBuilder.setContent(null != textMessageContent.getText() ? textMessageContent.getText()
            // : "");
            // textBuilder.setTitle("");
            // textBuilder.setDescription("");
            // msgContentBuilder.addTexts(textBuilder);
            // } else if (messageContentEntry.messageContent instanceof URLMessageContent) {
            // BDHiURLMessageContent urlMessageContent =
            // (BDHiURLMessageContent) messageContentEntry.messageContent;
            // URL urlBuilder = new URL; // migrate from builder 
            // urlBuilder.setUrlId(messageContentEntry.key);
            // urlBuilder.setUrl(null != urlMessageContent.getURL() ? urlMessageContent.getURL() : "");
            // urlBuilder.setTitle(null != urlMessageContent.getText() ? urlMessageContent.getText() : "");
            // msgContentBuilder.addUrls(urlBuilder);
            // } else if (messageContentEntry.messageContent instanceof BDHiImageMessageContent) {
            // BDHiImageMessageContent bdhiImageContent =
            // (BDHiImageMessageContent) messageContentEntry.messageContent;
            // Image imageBuilder = Image(); // migrate from builder 
            // imageBuilder.setImageId(messageContentEntry.key);
            // imageBuilder.setMd5(null != bdhiImageContent.getFileMD5() ? bdhiImageContent.getFileMD5()
            // : "");
            // imageBuilder.setSize((int) bdhiImageContent.getFileSize());
            // imageBuilder.setWidth(bdhiImageContent.getWidth());
            // imageBuilder.setHeight(bdhiImageContent.getHeight());
            // if (null != bdhiImageContent.getFileURL() && bdhiImageContent.getFileURL().length() > 0) {
            // imageBuilder.setUrl(bdhiImageContent.getFileURL());
            // } else if (null != bdhiImageContent.getFid() && bdhiImageContent.getFid().length() > 0) {
            // imageBuilder.setUrl(IMPLUS_PREFIX + bdhiImageContent.getFid() + IMPLUS_DIVIDER
            // + bdhiImageContent.getFileMD5());
            // } else if (null != bdhiImageContent.getLocalResource()
            // && bdhiImageContent.getLocalResource().length() > 0) {
            // imageBuilder.setUrl(FILEPATH_PREFIX + bdhiImageContent.getLocalResource());
            // } else {
            // imageBuilder.setUrl("");
            // }
            // msgContentBuilder.addImages(imageBuilder);
            // } else if (messageContentEntry.messageContent instanceof BDHiFileMessageContent) {
            // BDHiFileMessageContent bdhiFileContent =
            // (BDHiFileMessageContent) messageContentEntry.messageContent;
            // File fileBuilder = File(); // migrate from builder 
            // fileBuilder.setFileId(messageContentEntry.key);
            // fileBuilder
            // .setMd5(null != bdhiFileContent.getFileMD5() ? bdhiFileContent.getFileMD5() : "");
            // fileBuilder.setSize((int) bdhiFileContent.getFileSize());
            // fileBuilder.setName(null != bdhiFileContent.getFileName() ? bdhiFileContent.getFileName()
            // : "");
            // if (null != bdhiFileContent.getFileURL() && bdhiFileContent.getFileURL().length() > 0) {
            // fileBuilder.setFileUrl(bdhiFileContent.getFileURL());
            // } else if (null != bdhiFileContent.getFid() && bdhiFileContent.getFid().length() > 0) {
            // fileBuilder.setFileUrl(IMPLUS_PREFIX + bdhiFileContent.getFid() + IMPLUS_DIVIDER
            // + bdhiFileContent.getFileMD5());
            // } else if (null != bdhiFileContent.getLocalResource()
            // && bdhiFileContent.getLocalResource().length() > 0) {
            // fileBuilder.setFileUrl(FILEPATH_PREFIX + bdhiFileContent.getLocalResource());
            // } else {
            // fileBuilder.setFileUrl("");
            // }
            // msgContentBuilder.addFiles(fileBuilder);
            // } else if (messageContentEntry.messageContent instanceof BDHiVoiceMessageContent) {
            // BDHiVoiceMessageContent bdhiVoiceContent =
            // (BDHiVoiceMessageContent) messageContentEntry.messageContent;
            // Voice voiceBuilder = Voice(); // migrate from builder 
            // voiceBuilder.setVoiceId(messageContentEntry.key);
            // voiceBuilder.setMd5(null != bdhiVoiceContent.getFileMD5() ? bdhiVoiceContent.getFileMD5()
            // : "");
            // voiceBuilder.setSize((int) bdhiVoiceContent.getFileSize());
            // voiceBuilder.setTimeLen(bdhiVoiceContent.getDuration());
            // if (null != bdhiVoiceContent.getFileURL() && bdhiVoiceContent.getFileURL().length() > 0) {
            // voiceBuilder.setUrl(bdhiVoiceContent.getFileURL());
            // } else if (null != bdhiVoiceContent.getFid() && bdhiVoiceContent.getFid().length() > 0) {
            // voiceBuilder.setUrl(IMPLUS_PREFIX + bdhiVoiceContent.getFid() + IMPLUS_DIVIDER
            // + bdhiVoiceContent.getFileMD5());
            // } else if (null != bdhiVoiceContent.getLocalResource()
            // && bdhiVoiceContent.getLocalResource().length() > 0) {
            // voiceBuilder.setUrl(FILEPATH_PREFIX + bdhiVoiceContent.getLocalResource());
            // } else {
            // voiceBuilder.setUrl("");
            // }
            // }
            // }
            // }
            // }
        }
    }

    /**
     * Convert the TextMessageContent and URLMessageContent of a IMTextMessage into a MsgContent
     *
     * IMMessage -> OneMsg
     *
     * @param msgContentBuilder
     * @param imTextMessage
     */
    private static void convertIMTextMessage(MsgContent msgContentBuilder, BDHiIMTextMessage imTextMessage) {
        if (null != msgContentBuilder && null != imTextMessage) {
            List<IMMessageContent> messageContents = imTextMessage.getMessageContentList();
            if (null != messageContents) {
                for (int i = 0; i < messageContents.size(); i++) {
                    IMMessageContent messageContent = messageContents.get(i);
                    if (messageContent instanceof TextMessageContent) {
                        TextMessageContent textMessageContent = (TextMessageContent) messageContent;
                        Text textBuilder = new Text(); // migrate from builder 
                        textBuilder.setTextId(BDHI_MESSAGE_CONTENT_ID.TEXT.getName() + i);
                        textBuilder
                                .setContent(null != textMessageContent.getText() ? textMessageContent.getText() : "");
                        textBuilder.setTitle("");
                        textBuilder.setDescription("");
                        msgContentBuilder.addTexts(textBuilder);
                    } else if (messageContent instanceof URLMessageContent) {
                        URLMessageContent urlMessageContent = (URLMessageContent) messageContent;
                        URL urlBuilder = new URL(); // migrate from builder 
                        urlBuilder.setUrlId(BDHI_MESSAGE_CONTENT_ID.URL.getName() + i);
                        urlBuilder.setUrl(null != urlMessageContent.getURL() ? urlMessageContent.getURL() : "");
                        urlBuilder.setTitle(null != urlMessageContent.getText() ? urlMessageContent.getText() : "");
                        msgContentBuilder.addUrls(urlBuilder);
                    }
                }
            }
        }
    }

    /**
     * Convert the FileMessageContent of a IMFileMessage into a MsgContent
     *
     * IMMessage -> OneMsg
     *
     * @param msgContentBuilder
     * @param imFileMessage
     */
    private static void convertIMFileMessage(MsgContent msgContentBuilder, BDHiIMFileMessage imFileMessage) {
        if (null != msgContentBuilder && null != imFileMessage) {
            FileMessageContent fileContent = imFileMessage.getFile();
            if (null != fileContent && fileContent instanceof BDHiFileMessageContent) {
                BDHiFileMessageContent bdhiFileContent = (BDHiFileMessageContent) fileContent;
                File fileBuilder = new File(); // migrate from builder
                fileBuilder.setFileId(BDHI_MESSAGE_CONTENT_ID.FILE.getName());
                fileBuilder.setMd5(null != bdhiFileContent.getFileMD5() ? bdhiFileContent.getFileMD5() : "");
                fileBuilder.setSize((int) bdhiFileContent.getFileSize());
                fileBuilder.setName(null != bdhiFileContent.getFileName() ? bdhiFileContent.getFileName() : "");
                if (null != bdhiFileContent.getFileURL() && bdhiFileContent.getFileURL().length() > 0) {
                    fileBuilder.setFileUrl(bdhiFileContent.getFileURL());
                } else if (null != bdhiFileContent.getFid() && bdhiFileContent.getFid().length() > 0) {
                    fileBuilder.setFileUrl(IMPLUS_PREFIX + bdhiFileContent.getFid() + IMPLUS_DIVIDER
                            + bdhiFileContent.getFileMD5());
                } else if (null != bdhiFileContent.getLocalResource()
                        && bdhiFileContent.getLocalResource().length() > 0) {
                    fileBuilder.setFileUrl(FILEPATH_PREFIX + bdhiFileContent.getLocalResource());
                } else {
                    fileBuilder.setFileUrl("");
                }
                msgContentBuilder.addFiles(fileBuilder);
            }
        }
    }

    /**
     * Convert the ImageMessageContent and the ThumbnailMessageContent of a IMImageMessage into a MsgContent
     *
     * IMMessage -> OneMsg
     *
     * @param msgContentBuilder
     * @param imImageMessage
     */
    private static void convertIMImageMessage(MsgContent msgContentBuilder, BDHiIMImageMessage imImageMessage) {
        if (null != msgContentBuilder && null != imImageMessage) {
            ImageMessageContent imageContent = imImageMessage.getImage();
            if (null != imageContent) {
                BDHiImageMessageContent bdhiImageContent = (BDHiImageMessageContent) imageContent;
                Image imageBuilder = new Image(); // migrate from builder
                imageBuilder.setImageId(BDHI_MESSAGE_CONTENT_ID.IMAGE.getName());
                imageBuilder.setMd5(null != bdhiImageContent.getFileMD5() ? bdhiImageContent.getFileMD5() : "");
                imageBuilder.setSize((int) bdhiImageContent.getFileSize());
                imageBuilder.setWidth(bdhiImageContent.getWidth());
                imageBuilder.setHeight(bdhiImageContent.getHeight());
                if (null != bdhiImageContent.getFileURL() && bdhiImageContent.getFileURL().length() > 0) {
                    imageBuilder.setUrl(bdhiImageContent.getFileURL());
                } else if (null != bdhiImageContent.getFid() && bdhiImageContent.getFid().length() > 0) {
                    imageBuilder.setUrl(IMPLUS_PREFIX + bdhiImageContent.getFid() + IMPLUS_DIVIDER
                            + bdhiImageContent.getFileMD5());
                } else if (null != bdhiImageContent.getLocalResource()
                        && bdhiImageContent.getLocalResource().length() > 0) {
                    imageBuilder.setUrl(FILEPATH_PREFIX + bdhiImageContent.getLocalResource());
                } else {
                    imageBuilder.setUrl("");
                }
                msgContentBuilder.addImages(imageBuilder);
            }
            ImageMessageContent thumbnailContent = imImageMessage.getThumbnailImage();
            if (null != thumbnailContent && thumbnailContent instanceof BDHiImageMessageContent) {
                BDHiImageMessageContent bdhiThumbnailContent = (BDHiImageMessageContent) thumbnailContent;
                Image thumbnailBuilder = new Image(); // migrate from builder
                thumbnailBuilder.setImageId(BDHI_MESSAGE_CONTENT_ID.THUMBNAIL.getName());
                thumbnailBuilder.setMd5(null != bdhiThumbnailContent.getFileMD5() ? bdhiThumbnailContent.getFileMD5()
                        : "");
                thumbnailBuilder.setSize((int) bdhiThumbnailContent.getFileSize());
                thumbnailBuilder.setWidth(bdhiThumbnailContent.getWidth());
                thumbnailBuilder.setHeight(bdhiThumbnailContent.getHeight());
                if (null != bdhiThumbnailContent.getFileURL() && bdhiThumbnailContent.getFileURL().length() > 0) {
                    thumbnailBuilder.setUrl(bdhiThumbnailContent.getFileURL());
                } else if (null != bdhiThumbnailContent.getFid() && bdhiThumbnailContent.getFid().length() > 0) {
                    thumbnailBuilder.setUrl(IMPLUS_PREFIX + bdhiThumbnailContent.getFid() + IMPLUS_DIVIDER
                            + bdhiThumbnailContent.getFileMD5());
                } else if (null != bdhiThumbnailContent.getLocalResource()
                        && bdhiThumbnailContent.getLocalResource().length() > 0) {
                    thumbnailBuilder.setUrl(FILEPATH_PREFIX + bdhiThumbnailContent.getLocalResource());
                } else {
                    thumbnailBuilder.setUrl("");
                }
                msgContentBuilder.addImages(thumbnailBuilder);
            }
        }
    }

    /**
     * Convert the VoiceMessageContent of a IMVoiceMessage into a MsgContent
     *
     * IMMessage -> OneMsg
     *
     * @param msgContentBuilder
     * @param imVoiceMessage
     */
    private static void convertIMVoiceMessage(MsgContent msgContentBuilder, BDHiIMVoiceMessage imVoiceMessage) {
        if (null != msgContentBuilder && null != imVoiceMessage) {
            VoiceMessageContent voiceContent = imVoiceMessage.getVoice();
            if (null != voiceContent && voiceContent instanceof BDHiVoiceMessageContent) {
                BDHiVoiceMessageContent bdhiVoiceContent = (BDHiVoiceMessageContent) voiceContent;
                Voice voiceBuilder = new Voice(); // migrate from builder
                voiceBuilder.setVoiceId(BDHI_MESSAGE_CONTENT_ID.VOICE.getName());
                voiceBuilder.setMd5(null != bdhiVoiceContent.getFileMD5() ? bdhiVoiceContent.getFileMD5() : "");
                voiceBuilder.setSize((int) bdhiVoiceContent.getFileSize());
                voiceBuilder.setTimeLen(bdhiVoiceContent.getDuration());
                markVoicePlayedToIMMessage(imVoiceMessage, ".", bdhiVoiceContent.isPlayed());
                if (null != bdhiVoiceContent.getFileURL() && bdhiVoiceContent.getFileURL().length() > 0) {
                    voiceBuilder.setUrl(bdhiVoiceContent.getFileURL());
                } else if (null != bdhiVoiceContent.getFid() && bdhiVoiceContent.getFid().length() > 0) {
                    voiceBuilder.setUrl(IMPLUS_PREFIX + bdhiVoiceContent.getFid() + IMPLUS_DIVIDER
                            + bdhiVoiceContent.getFileMD5());
                } else if (null != bdhiVoiceContent.getLocalResource()
                        && bdhiVoiceContent.getLocalResource().length() > 0) {
                    voiceBuilder.setUrl(FILEPATH_PREFIX + bdhiVoiceContent.getLocalResource());
                } else {
                    voiceBuilder.setUrl("");
                }
                msgContentBuilder.addVoices(voiceBuilder);
            }
        }
    }

    /**
     * Convert the common information of a IMMessasge into a OneMsg
     *
     * IMMessage -> OneMsg
     *
     * @param oneMsgBuilder
     * @param imMessage
     */
    private static void convertCommonInfo(OneMsg oneMsgBuilder, BDHiIMMessage imMessage) {
        if (null != oneMsgBuilder && null != imMessage) {
            int enumChatType = enumChatTypeOf(imMessage.getAddresseeType(), -1);
            if(enumChatType == -1){
                oneMsgBuilder = null;
            }else{
                oneMsgBuilder.setChatType(enumChatType);
            }
            if (null != oneMsgBuilder) {
                oneMsgBuilder.setFromId(imMessage.getAddresserID());
                oneMsgBuilder.setFromName(null != imMessage.getAddresserName() ? imMessage.getAddresserName() : "");
                oneMsgBuilder.setToId(imMessage.getAddresseeID());
                oneMsgBuilder.setSeq(imMessage.getMsgSeq());
                oneMsgBuilder.setView(null != imMessage.getMsgView() ? imMessage.getMsgView() : "");
                oneMsgBuilder.setServerTime(imMessage.getServerTime());
                oneMsgBuilder.setSendTime(imMessage.getSendTime());
                oneMsgBuilder.setIsRealTime(false);
                if (null != imMessage.getMsgTemplate() && imMessage.getMsgTemplate().length() > 0) {
                    oneMsgBuilder.setMsgTemplate(imMessage.getMsgTemplate());
                } else {
                    oneMsgBuilder.setMsgTemplate(null != imMessage.getMessageType() ? imMessage.getMessageType()
                            .getName() : null);
                }
                oneMsgBuilder.setClientMsgID(imMessage.getClientMessageID());
                oneMsgBuilder.setNotifyText(null != imMessage.getNotificationText() ? imMessage.getNotificationText()
                        : "");
                oneMsgBuilder.setCompatibleText(null != imMessage.getCompatibleText() ? imMessage.getCompatibleText()
                        : "");
                oneMsgBuilder.setPreviousSeq(0 == imMessage.getPreviousMsgID() ? imMessage.getPreviousMsgID() : 0);
                oneMsgBuilder.setExtra(null != imMessage.getExtra() ? imMessage.getExtra() : "");
            }
        }
    }

    /**
     * Convert a IMMessage to a OneMsg
     *
     * IMMessage -> OneMsg
     *
     * @param imMessage
     *
     * @return oneMessageBuilder
     */
    public static OneMsg convertIMMessage(BDHiIMMessage imMessage) {
        OneMsg oneMsgBuilder = null;
        if (null != imMessage) {
            MsgContent msgContentBuilder = new MsgContent(); // migrate from builder
            switch (imMessage.getMessageType()) {
                case TEXT:
                    if (imMessage instanceof BDHiIMTextMessage) {
                        convertIMTextMessage(msgContentBuilder, (BDHiIMTextMessage) imMessage);
                    }
                    break;
                case FILE:
                    if (imMessage instanceof BDHiIMFileMessage) {
                        convertIMFileMessage(msgContentBuilder, (BDHiIMFileMessage) imMessage);
                    }
                    break;
                case IMAGE:
                    if (imMessage instanceof BDHiIMImageMessage) {
                        convertIMImageMessage(msgContentBuilder, (BDHiIMImageMessage) imMessage);
                    }
                    break;
                case VOICE:
                    if (imMessage instanceof BDHiIMVoiceMessage) {
                        convertIMVoiceMessage(msgContentBuilder, (BDHiIMVoiceMessage) imMessage);
                    }
                    break;
                default:
                    if (imMessage instanceof BDHiIMCustomMessage) {
                        convertIMCustomMessage(msgContentBuilder, (BDHiIMCustomMessage) imMessage);
                    }
                    break;
            }
            oneMsgBuilder = new OneMsg(); // migrate from builder
            oneMsgBuilder.setContent(msgContentBuilder);
            convertCommonInfo(oneMsgBuilder, imMessage);
        }
        return oneMsgBuilder;
    }

    /**
     * Convert a String to IMMessage
     *
     * Incoming byte array -> IMMessage
     *
     * @param msgBody
     *
     * @return IMMessage
     */
    public static BDHiIMMessage convertServerMsg(byte[] msgBody) {
        OneMsg oneMsg = null;
        try {
            oneMsg = OneMsg.parseFrom(msgBody);
        } catch (InvalidProtocolBufferMicroException e) {
            LogUtil.printImE(TAG + "[convertServerMsg(String msgBody)]", e);
        }
        if (null != oneMsg) {
            return convertServerMsg(oneMsg);
        } else {
            return null;
        }
    }

    //    /**
    //     * Convert a OneMsg to a IMMessage
    //     *
    //     * @param oneMsg
    //     * @param imMessage
    //     */
    //    public static BDHiIMMessage convertServerMsg(OneMsg oneMsg) {
    //        return convertServerMsg(oneMsg.toBuilder());
    //    }

    /**
     * Convert a OneMsg into a IMMessage
     *
     * OneMsg -> IMMessage
     *
     * @param oneMsgBuilder
     *
     * @return imMessage
     */
    public static BDHiIMMessage convertServerMsg(OneMsg oneMsgBuilder) {
        BDHiIMMessage imMessage = null;
        if (null != oneMsgBuilder) {
            String msgTemplate = oneMsgBuilder.getMsgTemplate();
            BDHI_IMMESSAGE_TYPE messageType = BDHI_IMMESSAGE_TYPE.parse(msgTemplate);
            switch (messageType) {
                case TEXT:
                    imMessage = new BDHiIMTextMessage();
                    convertOneMsgBuilderTexts((BDHiIMTextMessage) imMessage, oneMsgBuilder);
                    break;
                case FILE:
                    imMessage = new BDHiIMFileMessage();
                    convertOneMsgBuilderFiles((BDHiIMFileMessage) imMessage, oneMsgBuilder);
                    break;
                case IMAGE:
                    imMessage = new BDHiIMImageMessage();
                    convertOneMsgBuilderImages((BDHiIMImageMessage) imMessage, oneMsgBuilder);
                    break;
                case VOICE:
                    imMessage = new BDHiIMVoiceMessage();
                    convertOneMsgBuilderVoices((BDHiIMVoiceMessage) imMessage, oneMsgBuilder);
                    break;
                case CUSTOM:
                default:
                    imMessage = new BDHiIMCustomMessage();
                    convertOneMsgBuilderCustoms((BDHiIMCustomMessage) imMessage, oneMsgBuilder);
                    break;
            }
            convertOneMsgBuilderCommonInfo(imMessage, oneMsgBuilder);
        }
        return imMessage;
    }

    /**
     * OneMsg -> IMMessage
     *
     * @param imMessage
     * @param msgBody
     */
    public static void convertServerMsgContent(BDHiIMMessage imMessage, byte[] msgBody) {
        OneMsg oneMsg = null;
        try {
            oneMsg = OneMsg.parseFrom(msgBody);
        } catch (InvalidProtocolBufferMicroException e) {
            LogUtil.printImE(TAG + "[convertServerMsg(String msgBody)]", e);
        }
        if (null != oneMsg) {
            OneMsg oneMsgBuilder = oneMsg;
            String msgTemplate = oneMsgBuilder.getMsgTemplate();
            BDHI_IMMESSAGE_TYPE messageType = BDHI_IMMESSAGE_TYPE.parse(msgTemplate);
            switch (messageType) {
                case TEXT:
                    if (imMessage instanceof BDHiIMTextMessage) {
                        convertOneMsgBuilderTexts((BDHiIMTextMessage) imMessage, oneMsgBuilder);
                    }
                    break;
                case FILE:
                    if (imMessage instanceof BDHiIMFileMessage) {
                        convertOneMsgBuilderFiles((BDHiIMFileMessage) imMessage, oneMsgBuilder);
                    }
                    break;
                case IMAGE:
                    if (imMessage instanceof BDHiIMImageMessage) {
                        convertOneMsgBuilderImages((BDHiIMImageMessage) imMessage, oneMsgBuilder);
                    }
                    break;
                case VOICE:
                    if (imMessage instanceof BDHiIMVoiceMessage) {
                        convertOneMsgBuilderVoices((BDHiIMVoiceMessage) imMessage, oneMsgBuilder);
                    }
                    break;
                case CUSTOM:
                default:
                    if (imMessage instanceof BDHiIMCustomMessage) {
                        convertOneMsgBuilderCustoms((BDHiIMCustomMessage) imMessage, oneMsgBuilder);
                    }
                    break;
            }
        }
    }

    /**
     * Convert the Texts and Urls of a OneMsg into a IMTextMessage
     *
     * OneMsg -> IMMessage
     *
     * @param imTextMessage
     * @param oneMsgBuilder
     */
    private static void convertOneMsgBuilderTexts(BDHiIMTextMessage imTextMessage, OneMsg oneMsgBuilder) {
        if (null != imTextMessage && null != oneMsgBuilder && null != oneMsgBuilder.getContent()) {
            TreeMap<Integer, IMMessageContent> messageContentMap = new TreeMap<Integer, IMMessageContent>();
            if (oneMsgBuilder.getContent().getTextsCount() > 0) {
                for (Text text : oneMsgBuilder.getContent().getTextsList()) {
                    if (null != text && null != text.getTextId() && text.getTextId().length() > 0) {
                        BDHiTextMessageContent textMessageContent = getTextMessageContent(text);
                        String id = textMessageContent.getTextID().replace(BDHI_MESSAGE_CONTENT_ID.TEXT.getName(), "");
                        try {
                            Integer key = Integer.valueOf(id);
                            messageContentMap.put(key, textMessageContent);
                        } catch (NumberFormatException e) {
                            LogUtil.printImE(TAG, e);
                        }
                    }
                }
            }
            if (oneMsgBuilder.getContent().getUrlsCount() > 0) {
                for (URL url : oneMsgBuilder.getContent().getUrlsList()) {
                    if (null != url && null != url.getUrl() && url.getUrlId().length() > 0) {
                        BDHiURLMessageContent urlMessageContent = getURLMessageContent(url);
                        String id = urlMessageContent.getUrlID().replace(BDHI_MESSAGE_CONTENT_ID.URL.getName(), "");
                        try {
                            Integer key = Integer.valueOf(id);
                            messageContentMap.put(key, urlMessageContent);
                        } catch (NumberFormatException e) {
                            LogUtil.printImE(TAG, e);
                        }
                    }
                }
            }
            List<IMMessageContent> messageContentList = new ArrayList<IMMessageContent>();
            messageContentList.addAll(messageContentMap.values());
            imTextMessage.addMessageContentList(messageContentList);
            imTextMessage.setMessageType(BDHI_IMMESSAGE_TYPE.TEXT);
        }
    }

    /**
     * Convert a Text to a TextMessageContent
     *
     * @param text
     *
     * @return textMessageContent
     */
    private static BDHiTextMessageContent getTextMessageContent(Text text) {
        BDHiTextMessageContent textMessageContent = new BDHiTextMessageContent();
        textMessageContent.setTextID(text.getTextId());
        textMessageContent.setText(text.getContent());
        return textMessageContent;
    }

    /**
     * Convert a URL to a URLMessageContent
     *
     * @param url
     *
     * @return urlMessageContent
     */
    private static BDHiURLMessageContent getURLMessageContent(URL url) {
        BDHiURLMessageContent urlMessageContent = new BDHiURLMessageContent();
        urlMessageContent.setUrlID(url.getUrlId());
        urlMessageContent.setText(url.getTitle());
        urlMessageContent.setURL(url.getUrl());
        return urlMessageContent;
    }

    /**
     * Convert the File of a OneMsg into a IMImageMessage
     *
     * OneMsg -> IMMessage
     *
     * @param imFileMessage
     * @param oneMsgBuilder
     */
    private static void convertOneMsgBuilderFiles(BDHiIMFileMessage imFileMessage, OneMsg oneMsgBuilder) {
        if (null != imFileMessage && null != oneMsgBuilder && null != oneMsgBuilder.getContent()
                && oneMsgBuilder.getContent().getFilesCount() > 0) {
            for (File file : oneMsgBuilder.getContent().getFilesList()) {
                if (null != file) {
                    String fileID = file.getFileId();
                    if (BDHI_MESSAGE_CONTENT_ID.FILE.getName().equals(fileID)) {
                        BDHiFileMessageContent fileMessageContent = getFileMessageContent(file);
                        imFileMessage.setFile(fileMessageContent);
                        break;
                    }
                }
            }
            imFileMessage.setMessageType(BDHI_IMMESSAGE_TYPE.FILE);
        }
    }

    /**
     * Convert a File to a FileMessageContent
     *
     * @param file
     *
     * @return fileMessageContent
     */
    private static BDHiFileMessageContent getFileMessageContent(File file) {
        BDHiFileMessageContent fileMessageContent = new BDHiFileMessageContent();
        fileMessageContent.setFileID(file.getFileId());
        fileMessageContent.setFileMD5(file.getMd5());
        fileMessageContent.setFileName(file.getName());
        fileMessageContent.setFileSize(file.getSize());
        String fileURL = file.getFileUrl();
        if (null != fileURL && fileURL.length() > 0) {
            if (fileURL.startsWith(IMPLUS_PREFIX)) {
                String[] results = fileURL.split(IMPLUS_DIVIDER);
                if (null != results && results.length == 4) {
                    String fid = results[2];
                    String fileMD5 = results[3];
                    fileMessageContent.setFid(fid);
                    fileMessageContent.setFileMD5(fileMD5);
                }
                fileMessageContent.setFileURL(fileURL);
            } else if (fileURL.startsWith(FILEPATH_PREFIX)) {
                String filePath = fileURL.replace(FILEPATH_PREFIX, FILEPATH_REPLACEMENT);
                fileMessageContent.setFilePath(filePath);
            } else {
                fileMessageContent.setFileURL(fileURL);
            }
        }
        return fileMessageContent;
    }

    /**
     * Convert the Image and the Thumbnail of a OneMsg into a IMImageMessage
     *
     * @param imImageMessage
     * @param oneMsgBuilder
     */
    private static void convertOneMsgBuilderImages(BDHiIMImageMessage imImageMessage, OneMsg oneMsgBuilder) {
        if (null != imImageMessage && null != oneMsgBuilder && null != oneMsgBuilder.getContent()
                && oneMsgBuilder.getContent().getImagesCount() > 0) {
            for (Image image : oneMsgBuilder.getContent().getImagesList()) {
                if (null != image) {
                    String imageID = image.getImageId();
                    if (BDHI_MESSAGE_CONTENT_ID.IMAGE.getName().equals(imageID)) {
                        BDHiImageMessageContent imageMessageContent = getImageMessageContent(image);
                        imImageMessage.setImage(imageMessageContent);
                    } else if (BDHI_MESSAGE_CONTENT_ID.THUMBNAIL.getName().equals(imageID)) {
                        BDHiImageMessageContent thumbnailMessageContent = getImageMessageContent(image);
                        imImageMessage.setThumbnailImage(thumbnailMessageContent);
                    }
                }
            }
            imImageMessage.setMessageType(BDHI_IMMESSAGE_TYPE.IMAGE);
        }
    }

    /**
     * Convert a Image to a ImageMessageContent
     *
     * @param image
     *
     * @return imageMessageContent
     */
    private static BDHiImageMessageContent getImageMessageContent(Image image) {
        BDHiImageMessageContent imageMessageContent = new BDHiImageMessageContent();
        imageMessageContent.setFileID(image.getImageId());
        imageMessageContent.setFileName(image.getMd5());
        imageMessageContent.setFileMD5(image.getMd5());
        imageMessageContent.setFileSize(image.getSize());
        imageMessageContent.setWidth(image.getWidth());
        imageMessageContent.setHeight(image.getHeight());
        String fileURL = image.getUrl();
        if (null != fileURL && fileURL.length() > 0) {
            if (fileURL.startsWith(IMPLUS_PREFIX)) {
                String[] results = fileURL.split(IMPLUS_DIVIDER);
                if (null != results && results.length == 4) {
                    String fid = results[2];
                    String fileMD5 = results[3];
                    imageMessageContent.setFid(fid);
                    imageMessageContent.setFileMD5(fileMD5);
                }
                imageMessageContent.setFileURL(fileURL);
            } else if (fileURL.startsWith(FILEPATH_PREFIX)) {
                String filePath = fileURL.replace(FILEPATH_PREFIX, FILEPATH_REPLACEMENT);
                imageMessageContent.setFilePath(filePath);
            } else {
                imageMessageContent.setFileURL(fileURL);
            }
        }
        return imageMessageContent;
    }

    /**
     * Convert the Voice of a OneMsg into a IMVoiceMessage
     *
     * OneMsg -> IMMessage
     *
     * @param imVoiceMessage to
     * @param oneMsgBuilder from
     */
    private static void convertOneMsgBuilderVoices(BDHiIMVoiceMessage imVoiceMessage, OneMsg oneMsgBuilder) {
        if (null != imVoiceMessage && null != imVoiceMessage && null != oneMsgBuilder.getContent()
                && oneMsgBuilder.getContent().getVoicesCount() > 0) {
            for (Voice voice : oneMsgBuilder.getContent().getVoicesList()) {
                if (null != voice) {
                    String voiceID = voice.getVoiceId();
                    if (BDHI_MESSAGE_CONTENT_ID.VOICE.getName().equals(voiceID)) {
                        BDHiVoiceMessageContent voiceMessageContent = getVoiceMessageContent(voice);
                        markVoicePlayedToContent(imVoiceMessage, ".", voiceMessageContent);
                        imVoiceMessage.setVoice(voiceMessageContent);
                        break;
                    }
                }
            }
            imVoiceMessage.setMessageType(BDHI_IMMESSAGE_TYPE.VOICE);
        }
    }

    public static void markVoicePlayedToIMMessage(BDHiIMMessage imMessage,
                                                     String key, boolean isPlayed) {
        String ext0 = imMessage.getExt0(); // will be overrode
        if (ext0 == null || ext0.length() == 0) {
            ext0 = "{}";
        }

        try {
            // extract voicePlayed JO from IMMessage
            JSONObject extJO = null;
            try {
                extJO = new JSONObject(ext0);
            } catch (Exception e) {
                Log.w(TAG,
                        "ext0 is occupied with non JSONObject structure, SDK is claiming this field, if you want to "
                                + "use this field too, please make sure data is in a JSONObject", e);
                extJO = new JSONObject();
            }

            JSONObject voicePlayedJO = extJO.optJSONObject(EXT0_KEY_PLAYEDVOICEID);
            if (voicePlayedJO == null) {
                voicePlayedJO = new JSONObject();
            }

            // write/append value to JO
            voicePlayedJO.put(key, isPlayed);

            // write JO back to parent JO, then to ext0 field
            extJO.put(EXT0_KEY_PLAYEDVOICEID, voicePlayedJO);
            ext0 = extJO.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        imMessage.setExt0(ext0);
    }

    public static void markVoicePlayedToContent(BDHiIMMessage imMessage, String key,
                                                   BDHiVoiceMessageContent voiceMessageContent) {
        boolean isPlayed = false;
        // extract imMessage.voicePlayedId[key] value
        String ext0 = imMessage.getExt0(); // will be overrode
        if (ext0 == null || ext0.length() == 0) {
            ext0 = "{}";
        }

        // extract voicePlayed JO from IMMessage
        JSONObject extJO = null;
        try {
            extJO = new JSONObject(ext0);
        } catch (JSONException e) {
            Log.w(TAG,
                    "ext0 is occupied with non JSONObject structure, SDK is claiming this field, if you want to "
                            + "use this field too, please make sure data is in a JSONObject", e);
            extJO = new JSONObject();
        }

        JSONObject voicePlayedJO = extJO.optJSONObject(EXT0_KEY_PLAYEDVOICEID);
        if (voicePlayedJO == null) {
            voicePlayedJO = new JSONObject();
        }

        // default is false
        isPlayed = voicePlayedJO.optBoolean(key, false);

        voiceMessageContent.setPlayed(isPlayed, false);
    }

    /**
     * Convert a Voice to a VoiceMessageContent
     *
     * @param voice
     *
     * @return voiceMessageContent
     */
    private static BDHiVoiceMessageContent getVoiceMessageContent(Voice voice) {
        BDHiVoiceMessageContent voiceMessageContent = new BDHiVoiceMessageContent();
        voiceMessageContent.setFileID(voice.getVoiceId());
        voiceMessageContent.setFileName(voice.getMd5());
        voiceMessageContent.setFileMD5(voice.getMd5());
        voiceMessageContent.setFileSize(voice.getSize());
        voiceMessageContent.setDuration(voice.getTimeLen());
        String fileURL = voice.getUrl();
        if (null != fileURL && fileURL.length() > 0) {
            if (fileURL.startsWith(IMPLUS_PREFIX)) {
                String[] results = fileURL.split(IMPLUS_DIVIDER);
                if (null != results && results.length == 4) {
                    String fid = results[2];
                    String fileMD5 = results[3];
                    voiceMessageContent.setFid(fid);
                    voiceMessageContent.setFileMD5(fileMD5);
                }
                voiceMessageContent.setFileURL(fileURL);
            } else if (fileURL.startsWith(FILEPATH_PREFIX)) {
                String filePath = fileURL.replace(FILEPATH_PREFIX, FILEPATH_REPLACEMENT);
                voiceMessageContent.setFilePath(filePath);
            } else {
                voiceMessageContent.setFileURL(fileURL);
            }
        }
        return voiceMessageContent;
    }

    /**
     * Convert a OneMsg into a IMCustomMessage.
     *
     * OneMsg -> IMMessage
     *
     * @param imCustomMessage
     * @param oneMsgBuilder
     */
    private static void convertOneMsgBuilderCustoms(BDHiIMCustomMessage imCustomMessage, OneMsg oneMsgBuilder) {
        if (null != imCustomMessage && null != oneMsgBuilder) {
            if (null != oneMsgBuilder.getContent()) {
                if (oneMsgBuilder.getContent().getTextsCount() > 0) {
                    for (Text text : oneMsgBuilder.getContent().getTextsList()) {
                        if (null != text && null != text.getTextId() && text.getTextId().length() > 0) {
                            BDHiTextMessageContent textMessageContent = getTextMessageContent(text);
                            String textID = text.getTextId();
                            if (!TextUtils.isEmpty(textID)) {
                                String[] ids = textID.split("_");
                                if (ids.length > 1) {
                                    String latestIDStr = ids[ids.length - 1];
                                    int latestID = -1;
                                    try {
                                        latestID = Integer.parseInt(latestIDStr);
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                    if (latestID > -1) {
                                        textID = textID.replace("_" + latestIDStr, "");
                                        textMessageContent.setTextID(textID);
                                    }
                                }
                            }
                            imCustomMessage.addMessageContent(textID, textMessageContent);
                        }
                    }
                }
                if (oneMsgBuilder.getContent().getUrlsCount() > 0) {
                    for (URL url : oneMsgBuilder.getContent().getUrlsList()) {
                        if (null != url && null != url.getUrlId() && url.getUrlId().length() > 0) {
                            BDHiURLMessageContent urlMessageContent = getURLMessageContent(url);
                            String urlID = url.getUrlId();
                            if (!TextUtils.isEmpty(urlID)) {
                                String[] ids = urlID.split("_");
                                if (ids.length > 1) {
                                    String latestIDStr = ids[ids.length - 1];
                                    int latestID = -1;
                                    try {
                                        latestID = Integer.parseInt(latestIDStr);
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                    if (latestID > -1) {
                                        urlID = urlID.replace("_" + latestIDStr, "");
                                        urlMessageContent.setURL(urlID);
                                    }
                                }
                            }
                            imCustomMessage.addMessageContent(urlID, urlMessageContent);
                        }
                    }
                }
                if (oneMsgBuilder.getContent().getFilesCount() > 0) {
                    for (File file : oneMsgBuilder.getContent().getFilesList()) {
                        if (null != file && null != file.getFileId() && file.getFileId().length() > 0) {
                            BDHiFileMessageContent fileMessageContent = getFileMessageContent(file);
                            String fileID = file.getFileId();
                            if (!TextUtils.isEmpty(fileID)) {
                                String[] ids = fileID.split("_");
                                if (ids.length > 1) {
                                    String latestIDStr = ids[ids.length - 1];
                                    int latestID = -1;
                                    try {
                                        latestID = Integer.parseInt(latestIDStr);
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                    if (latestID > -1) {
                                        fileID = fileID.replace("_" + latestIDStr, "");
                                        fileMessageContent.setFileID(fileID);
                                    }
                                }
                            }
                            imCustomMessage.addMessageContent(fileID, fileMessageContent);
                        }
                    }
                }
                if (oneMsgBuilder.getContent().getImagesCount() > 0) {
                    for (Image image : oneMsgBuilder.getContent().getImagesList()) {
                        if (null != image && null != image.getImageId() && image.getImageId().length() > 0) {
                            BDHiFileMessageContent imageMessageContent = getImageMessageContent(image);
                            String imageID = image.getImageId();
                            if (!TextUtils.isEmpty(imageID)) {
                                String[] ids = imageID.split("_");
                                if (ids.length > 1) {
                                    String latestIDStr = ids[ids.length - 1];
                                    int latestID = -1;
                                    try {
                                        latestID = Integer.parseInt(latestIDStr);
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                    if (latestID > -1) {
                                        imageID = imageID.replace("_" + latestIDStr, "");
                                        imageMessageContent.setFileID(imageID);
                                    }
                                }
                            }
                            imCustomMessage.addMessageContent(imageID, imageMessageContent);
                        }
                    }
                }
                if (oneMsgBuilder.getContent().getVoicesCount() > 0) {
                    for (Voice voice : oneMsgBuilder.getContent().getVoicesList()) {
                        if (null != voice && null != voice.getVoiceId() && voice.getVoiceId().length() > 0) {
                            BDHiVoiceMessageContent imageMessageContent = getVoiceMessageContent(voice);
                            String voiceId = voice.getVoiceId();
                            if (!TextUtils.isEmpty(voiceId)) {
                                String[] ids = voiceId.split("_");
                                if (ids.length > 1) {
                                    String latestIDStr = ids[ids.length - 1];
                                    int latestID = -1;
                                    try {
                                        latestID = Integer.parseInt(latestIDStr);
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                    if (latestID > -1) {
                                        voiceId = voiceId.replace("_" + latestIDStr, "");
                                        imageMessageContent.setFileID(voiceId);
                                    }
                                }
                            }
                            markVoicePlayedToContent(imCustomMessage, voiceId, imageMessageContent);
                            imCustomMessage.addMessageContent(voiceId, imageMessageContent);
                        }
                    }
                }
                imCustomMessage.setMessageType(BDHI_IMMESSAGE_TYPE.CUSTOM);
            }
        }
    }

    /**
     * Convert the common information of a OneMsg into a IMMessage
     *
     * OneMsg -> IMMessage
     *
     * @param imMessage to
     * @param oneMsgBuilder from
     */
    private static void convertOneMsgBuilderCommonInfo(BDHiIMMessage imMessage, OneMsg oneMsgBuilder) {
        if (null != imMessage && null != oneMsgBuilder) {
            AddresseeType addresseeType = addresseeTypeOfChatType(oneMsgBuilder.getChatType(), null);
            if(addresseeType!=null){
                imMessage.setAddresseeType(addresseeType);
            }else{
                imMessage = null;
            }
        }
        if (null != imMessage) {
            imMessage.setAddresserID(oneMsgBuilder.getFromId());
            imMessage.setAddresserName(null != oneMsgBuilder.getFromName() ? oneMsgBuilder.getFromName()
                    : oneMsgBuilder.getFromId());
            imMessage.setAddresseeID(oneMsgBuilder.getToId());
            imMessage.setMsgSeq(oneMsgBuilder.getSeq());
            imMessage.setMsgView(oneMsgBuilder.getView());
            imMessage.setServerTime(oneMsgBuilder.getServerTime());
            imMessage.setSendTime(oneMsgBuilder.getSendTime());
            imMessage.setMsgTemplate(oneMsgBuilder.getMsgTemplate());
            imMessage.setClientMessageID(oneMsgBuilder.getClientMsgID());
            imMessage.setNotificationText(oneMsgBuilder.getNotifyText());
            imMessage.setCompatibleText(oneMsgBuilder.getCompatibleText());
            imMessage.setPreviousMsgID(oneMsgBuilder.getPreviousSeq());
            imMessage.setExtra(oneMsgBuilder.getExtra());
            imMessage.setStatus(IMMessageStatus.UNREAD);
            imMessage.setBody(oneMsgBuilder.toByteArray());
        }
    }

    public static List<BDHiFile> convertOneMsgFiles(OneMsg oneMsgBuilder) {
        List<BDHiFile> fileList = new ArrayList<BDHiFile>();
        if (null != oneMsgBuilder) {
            if (oneMsgBuilder.getContent().getFilesCount() > 0 && null != oneMsgBuilder.getContent().getFilesList()) {
                for (File file : oneMsgBuilder.getContent().getFilesList()) {
                    BDHiFile imFile = new BDHiFile();
                    imFile.setFileSize(file.getSize());
                    imFile.setMD5(file.getMd5());
                    String fileURL = file.getFileUrl();
                    if (null != fileURL && fileURL.length() > 0) {
                        if (fileURL.startsWith(IMPLUS_PREFIX)) {
                            String[] results = fileURL.split(IMPLUS_DIVIDER);
                            if (null != results && results.length == 4) {
                                String fid = results[3];
                                imFile.setFid(fid);
                            }
                            imFile.setURL(fileURL);
                        } else if (fileURL.startsWith(FILEPATH_PREFIX)) {
                            imFile.setLocaleFilePath(fileURL);
                        } else {
                            imFile.setURL(fileURL);
                        }
                    }
                    imFile.setFileID(file.getFileId());
                    imFile.setFileType(BDHI_FILE_TYPE.FILE.getName());
                    fileList.add(imFile);
                }
            }
            if (oneMsgBuilder.getContent().getImagesCount() > 0 && null != oneMsgBuilder.getContent().getImagesList()) {
                for (Image image : oneMsgBuilder.getContent().getImagesList()) {
                    BDHiFile imFile = new BDHiFile();
                    imFile.setFileSize(image.getSize());
                    imFile.setMD5(image.getMd5());
                    String fileURL = image.getUrl();
                    if (null != fileURL && fileURL.length() > 0) {
                        if (fileURL.startsWith(IMPLUS_PREFIX)) {
                            String[] results = fileURL.split(IMPLUS_DIVIDER);
                            if (null != results && results.length == 4) {
                                String fid = results[3];
                                imFile.setFid(fid);
                            }
                            imFile.setURL(fileURL);
                        } else if (fileURL.startsWith(FILEPATH_PREFIX)) {
                            imFile.setLocaleFilePath(fileURL);
                        } else {
                            imFile.setURL(fileURL);
                        }
                    }
                    imFile.setFileID(image.getImageId());
                    imFile.setFileType(BDHI_FILE_TYPE.IMAGE.getName());
                    fileList.add(imFile);
                }
            }
            if (oneMsgBuilder.getContent().getVoicesCount() > 0 && null != oneMsgBuilder.getContent().getVoicesList()) {
                for (Voice voice : oneMsgBuilder.getContent().getVoicesList()) {
                    BDHiFile imFile = new BDHiFile();
                    imFile.setFileSize(voice.getSize());
                    imFile.setMD5(voice.getMd5());
                    String fileURL = voice.getUrl();
                    if (null != fileURL && fileURL.length() > 0) {
                        if (fileURL.startsWith(IMPLUS_PREFIX)) {
                            String[] results = fileURL.split(IMPLUS_DIVIDER);
                            if (null != results && results.length == 4) {
                                String fid = results[3];
                                imFile.setFid(fid);
                            }
                            imFile.setURL(fileURL);
                        } else if (fileURL.startsWith(FILEPATH_PREFIX)) {
                            imFile.setLocaleFilePath(fileURL);
                        } else {
                            imFile.setURL(fileURL);
                        }
                    }
                    imFile.setFileID(voice.getVoiceId());
                    imFile.setFileType(BDHI_FILE_TYPE.VOICE.getName());
                    fileList.add(imFile);
                }
            }
        }
        return fileList;
    }

    /**
     * IMMessage -> OneMsg
     *
     * @param imMessage
     * @return
     */
    public static OneMsg convertIMTransientMessageToOneMsg(BDHiIMTransientMessage imMessage) {
        OneMsg result = new OneMsg(); // migrate from builder

        int chatType = enumChatTypeOf(imMessage.getAddresseeType(), -1);
        if(chatType != -1){
            result.setChatType(chatType);
        }else{
            result = null;
        }

        if (result != null) {
            MsgContent msgContentBuilder = new MsgContent(); // migrate from builder
            Data dataBuilder = new Data(); // migrate from builder
            dataBuilder.setDataId("data");
            try {
                dataBuilder.setContent(ByteStringMicro.copyFrom(
                        null != imMessage.getContent() ? imMessage.getContent() : "", "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            msgContentBuilder.addDatas(dataBuilder);

            result.setContent(msgContentBuilder); // required
            result.setIsRealTime(true); // required
            result.setSendTime(System.currentTimeMillis()); // required
            result.setView(""); // required
            result.setFromId(imMessage.getAddresserID());
            result.setToId(imMessage.getAddresseeID());

            result.setFromName(imMessage.getAddresserName());
            // result.setSeq(imMessage.getMessageID());
            // result.setServerTime(System.currentTimeMillis());
        }
        return result;
    }
    //
    //    public static BDHiIMTransientMessage convertOneMsgToIMTransientMessage(OneMsg oneMsg) {
    //        return convertOneMsgToIMTransientMessage(oneMsg);
    //    }

    /**
     * OneMsg -> IMMessage
     *
     * @param builder
     * @return
     */
    public static BDHiIMTransientMessage convertOneMsgToIMTransientMessage(OneMsg builder) {
        BDHiIMTransientMessage imMessage = null;
        if (builder.getContent().getDatasCount() > 0) {
            imMessage = new BDHiIMTransientMessage();

            Data data = builder.getContent().getDatas(0);
            imMessage.setContent(data.getContent().toStringUtf8());

            imMessage.setSendTime(builder.getSendTime());

            imMessage.setAddresserID(builder.getFromId());
            imMessage.setAddresseeID(builder.getToId());

            imMessage.setAddresserName(builder.getFromName());
            imMessage.setMessageID(builder.getSeq());
            imMessage.setServerTime(builder.getServerTime());
        }
        return imMessage;
    }


    public static AddresseeType addresseeTypeOfChatType(int chatType, AddresseeType defaultValue){
        switch (chatType) {
            case EnumChatType.CHAT_P2P:
                return AddresseeType.USER;
            case EnumChatType.CHAT_P2G:
                return AddresseeType.GROUP;
            case EnumChatType.CHAT_PA:
                return AddresseeType.SERVICE;
            default:
                return defaultValue;
        }
    }

    public static int enumChatTypeOf(AddresseeType type, int defaultValue){
        switch (type) {
            case USER:
                return EnumChatType.CHAT_P2P;
            case GROUP:
                return EnumChatType.CHAT_P2G;
            case SERVICE:
                return EnumChatType.CHAT_PA;
            default:
                return defaultValue;
        }
    }

}
