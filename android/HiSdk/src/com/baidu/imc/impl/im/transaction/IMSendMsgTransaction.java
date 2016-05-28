package com.baidu.imc.impl.im.transaction;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;

import com.baidu.im.frame.pb.ObjImage.Image;
import com.baidu.im.frame.pb.ObjOneMsg.OneMsg;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.client.RemoteResourceManager;
import com.baidu.imc.exception.InitializationException;
import com.baidu.imc.impl.im.client.BDHiRemoteResourceManager;
import com.baidu.imc.impl.im.client.IMConversationMessageListener;
import com.baidu.imc.impl.im.client.ResourceManager;
import com.baidu.imc.impl.im.message.BDHI_FILE_TYPE;
import com.baidu.imc.impl.im.message.BDHiFile;
import com.baidu.imc.impl.im.message.BDHiIMCustomMessage;
import com.baidu.imc.impl.im.message.BDHiIMCustomMessage.IMMessageContentEntry;
import com.baidu.imc.impl.im.message.BDHiIMFileMessage;
import com.baidu.imc.impl.im.message.BDHiIMImageMessage;
import com.baidu.imc.impl.im.message.BDHiIMMessage;
import com.baidu.imc.impl.im.message.BDHiIMVoiceMessage;
import com.baidu.imc.impl.im.message.IMMessageConvertor;
import com.baidu.imc.impl.im.message.OneMsgConverter;
import com.baidu.imc.impl.im.message.content.BDHiFileMessageContent;
import com.baidu.imc.impl.im.message.content.BDHiImageMessageContent;
import com.baidu.imc.impl.im.message.content.BDHiVoiceMessageContent;
import com.baidu.imc.impl.im.store.IMsgStore;
import com.baidu.imc.impl.im.transaction.callback.IMTransactionFileUploadCallback;
import com.baidu.imc.impl.im.transaction.processor.IMSendMsgProcessor;
import com.baidu.imc.impl.im.transaction.processor.callback.SendMsgCallback;
import com.baidu.imc.impl.im.transaction.request.SendMsgRequest;
import com.baidu.imc.impl.im.transaction.response.SendMsgResponse;
import com.baidu.imc.listener.ProgressListener;
import com.baidu.imc.message.IMMessage;
import com.baidu.imc.message.Message;
import com.baidu.imc.message.content.FileMessageContent;
import com.baidu.imc.message.content.ImageMessageContent;
import com.baidu.imc.message.content.VoiceMessageContent;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.IMMessageChange;
import com.baidu.imc.type.IMMessageStatus;

public class IMSendMsgTransaction implements IMTransactionStart, SendMsgCallback, IMTransactionFileUploadCallback {

    private static final String TAG = "SendMsg";
    private AddresseeType addresseeType;
    private String addresseeID;
    private String addresserID;
    private Message message;
    private IMsgStore msgStore;
    private IMConversationMessageListener callback;
    private BDHiIMMessage imMessage = null;
    private OneMsg messageBuilder = null;
    private Map<String, BDHiFileMessageContent> mFileMap = new HashMap<String, BDHiFileMessageContent>();
    private List<BDHiFile> mSendFileList = new LinkedList<BDHiFile>();
    private long transactionId = 0;
    private long startTime = 0;

    private void parameterCheck() {
        if (addresseeType == null || TextUtils.isEmpty(addresseeID) || TextUtils.isEmpty(addresserID)
                || message == null || msgStore == null)
            throw new InitializationException();
    }

    public IMSendMsgTransaction(AddresseeType addresseeType, String addresseeID, String addresserID, Message message,
            IMsgStore msgStore, IMConversationMessageListener callback) {
        this.addresseeType = addresseeType;
        this.addresseeID = addresseeID;
        this.addresserID = addresserID;
        this.message = message;
        this.msgStore = msgStore;
        this.callback = callback;
        parameterCheck();
        this.mFileMap.clear();
        this.mSendFileList.clear();
    }

    private void addSendFileContent(BDHiFile file, BDHiFileMessageContent msgContent) {
        LogUtil.printIm(getThreadName(), "Add a FileMessageContent.");
        mSendFileList.add(file);
        mFileMap.put(null != file ? file.getOldLocaleFilePath() : null, msgContent);
    }

    private boolean removeFileContent(BDHiFile file) {
        if (!mFileMap.isEmpty()) {
            BDHiFileMessageContent msgContent = mFileMap.remove(null != file ? file.getOldLocaleFilePath() : null);
            if (msgContent != null) {
                LogUtil.printIm(getThreadName(), "Remove a FileMessageContent.");
                msgContent.setFid(file.getFid());
                msgContent.setFileMD5(file.getMD5());
                msgContent.setFileName(file.getFileName());
                msgContent.setFilePath(file.getLocaleFilePath());
                return true;
            } else {
                LogUtil.printIm(getThreadName(), "Error in remove file Content");
                return false;
            }
        } else {
            return false;
        }
    }

    private void clearFileContents() {
        LogUtil.printIm(getThreadName(), "Remove FileMessageContents.");
        mFileMap.clear();
    }

    @Override
    public void startWorkFlow() throws Exception {
    	SendMsgThread thread = new SendMsgThread(this);
    	new Thread(thread).start();
    }

    public void startWorkFlow1() throws Exception {
        transactionId = this.hashCode();
        startTime = System.currentTimeMillis();
        LogUtil.printIm(getThreadName(), "IMSendMessageTransaction transactionId=" + transactionId);
        LogUtil.printIm(getThreadName(), "AddresseeType: " + addresseeType + " AddresseeID:" + addresseeID
                + " AddresserID:" + addresserID);
        // 001. Convert Message to IMMessage
        imMessage = IMMessageConvertor.convertMessage(addresseeType, addresseeID, addresserID, message);
        // 000. Get last successful message
        IMMessage previousMessage = msgStore.getLastSuccessfulMessage(addresseeType, addresseeID);
        if (null != previousMessage && null != imMessage) {
            imMessage.setMsgSeq(((BDHiIMMessage) previousMessage).getMsgSeq());
            LogUtil.printIm(getThreadName(), "try to fake a msgSeq." + imMessage.getMsgSeq());
        }
        long tmpStartTime0 = System.currentTimeMillis();
        LogUtil.printIm(getThreadName(), "Step1 DBOPT " + (tmpStartTime0 - startTime));
        startTime = tmpStartTime0;
        // 002. Convert IMMessage to OneMsg
        messageBuilder = OneMsgConverter.convertIMMessage(imMessage);
        if (null != imMessage && null != messageBuilder && null != msgStore) {
            LogUtil.printIm(getThreadName(), "Ready for send msg: " + imMessage.toString());
            // 003. set msg status as sending
            imMessage.setStatus(IMMessageStatus.SENDING);
            // 004. Save OneMsg into msgBody
            imMessage.setBody(messageBuilder.toByteArray());
            // 005. Save IMMessage to db before sending
            long id = -1;
            id = msgStore.saveIMMessage(imMessage);
            if (id > -1) {
                LogUtil.printIm(getThreadName(), "Insert message into database successed. " + id);
                long tmpStartTime1 = System.currentTimeMillis();
                LogUtil.printIm(getThreadName(), "Step2 DBOPT " + (tmpStartTime1 - startTime));
                startTime = tmpStartTime1;
                // 006. save msg dbId
                imMessage.setMessageID(id);
                // 007. notify user
                if (null != callback) {
                    LogUtil.printIm(getThreadName(), "Notify user sendMsg result.");
                    callback.onNewMessageReceived(imMessage);
                }
                long tmpStartTime2 = System.currentTimeMillis();
                LogUtil.printIm(getThreadName(), "Step3 NOTIF " + (tmpStartTime2 - startTime));
                startTime = tmpStartTime2;
                // 008. prepare files
                prepareFiles(imMessage);
                // 009. process message
                processMessage();
            } else {
                LogUtil.printIm(getThreadName(), "Can not insert message into database.");
                imMessage.setStatus(IMMessageStatus.FAILED);
                LogUtil.printMsg(imMessage.toString());
                // 010. notify user
                if (null != callback) {
                    LogUtil.printIm(getThreadName(), "Notify user sendMsg result.");
                    Map<IMMessageChange, Object> changes = new HashMap<IMMessageChange, Object>();
                    changes.put(IMMessageChange.STATUS, IMMessageStatus.FAILED);
                    callback.onMessageChanged(imMessage, changes);
                }
            }
        } else {
            LogUtil.printIm(getThreadName(), "Can not get imMessage or database is not ready.");
        }
    }

    public String getThreadName() {
        return TAG + transactionId;
    }

    public boolean prepareFiles(BDHiIMMessage imMessage) throws Exception {
        // Upload File
        boolean finalResult = true;
        switch (imMessage.getMessageType()) {
            case FILE:
                LogUtil.printIm(getThreadName(), "It is a file message, ready for uploading.");
                if (imMessage instanceof BDHiIMFileMessage) {
                    BDHiIMFileMessage imFileMessage = (BDHiIMFileMessage) imMessage;
                    FileMessageContent fileMessageContent = imFileMessage.getFile();
                    if (null != fileMessageContent && fileMessageContent instanceof BDHiFileMessageContent) {
                        BDHiFileMessageContent bdHiFileMessageContent = (BDHiFileMessageContent) fileMessageContent;
                        // LogUtil.printIm(getThreadName(), "Add a FileMessageContent" +
                        // bdHiFileMessageContent.toString());
                        BDHiFile hiFile = new BDHiFile();
                        hiFile.setoldLocaleFilePath(bdHiFileMessageContent.getLocalResource());
                        hiFile.setLocaleFilePath(bdHiFileMessageContent.getLocalResource());
                        hiFile.setFileType(BDHI_FILE_TYPE.FILE.getName());
                        hiFile.setFileSize(bdHiFileMessageContent.getFileSize());
                        hiFile.setMD5(bdHiFileMessageContent.getFileMD5());
                        this.addSendFileContent(hiFile, bdHiFileMessageContent);
                    }
                }
                break;
            case IMAGE:
                LogUtil.printIm(getThreadName(), "It is a image message, ready for uploading.");
                if (imMessage instanceof BDHiIMImageMessage) {
                    BDHiIMImageMessage imImageMessage = (BDHiIMImageMessage) imMessage;
                    
                    ImageMessageContent thumbnailMessageContent = imImageMessage.getThumbnailImage();
                    if (null != thumbnailMessageContent && thumbnailMessageContent instanceof BDHiImageMessageContent) {
                        BDHiImageMessageContent bdHithumbnailMessageContent =
                                (BDHiImageMessageContent) thumbnailMessageContent;
                        // LogUtil.printIm(getThreadName(),
                        // "Add a ImageMessageContent" + bdHithumbnailMessageContent.toString());
                        BDHiFile hiFile = new BDHiFile();
                        hiFile.setoldLocaleFilePath(bdHithumbnailMessageContent.getLocalResource());
                        hiFile.setLocaleFilePath(bdHithumbnailMessageContent.getLocalResource());
                        hiFile.setFileType(BDHI_FILE_TYPE.IMAGE.getName());
                        hiFile.setFileSize(bdHithumbnailMessageContent.getFileSize());
                        hiFile.setMD5(bdHithumbnailMessageContent.getFileMD5());
                        this.addSendFileContent(hiFile, bdHithumbnailMessageContent);
                    }
                    
                    ImageMessageContent imageMessageContent = imImageMessage.getImage();
                    if (null != imageMessageContent && imageMessageContent instanceof BDHiImageMessageContent) {
                        BDHiImageMessageContent bdHiImageMessageContent = (BDHiImageMessageContent) imageMessageContent;
                        // LogUtil.printIm(getThreadName(),
                        // "Add a ImageMessageContent" + bdHiImageMessageContent.toString());
                        BDHiFile hiFile = new BDHiFile();
                        hiFile.setLocaleFilePath(bdHiImageMessageContent.getLocalResource());
                        hiFile.setoldLocaleFilePath(bdHiImageMessageContent.getLocalResource());
                        hiFile.setFileType(BDHI_FILE_TYPE.IMAGE.getName());
                        hiFile.setFileSize(bdHiImageMessageContent.getFileSize());
                        hiFile.setMD5(bdHiImageMessageContent.getFileMD5());
                        this.addSendFileContent(hiFile, bdHiImageMessageContent);
                    }
                    /*
                    ImageMessageContent thumbnailMessageContent = imImageMessage.getThumbnailImage();
                    if (null != thumbnailMessageContent && thumbnailMessageContent instanceof BDHiImageMessageContent) {
                        BDHiImageMessageContent bdHithumbnailMessageContent =
                                (BDHiImageMessageContent) thumbnailMessageContent;
                        // LogUtil.printIm(getThreadName(),
                        // "Add a ImageMessageContent" + bdHithumbnailMessageContent.toString());
                        BDHiFile hiFile = new BDHiFile();
                        hiFile.setLocaleFilePath(bdHithumbnailMessageContent.getLocalResource());
                        hiFile.setFileType(BDHI_FILE_TYPE.IMAGE.getName());
                        hiFile.setFileSize(bdHithumbnailMessageContent.getFileSize());
                        hiFile.setMD5(bdHithumbnailMessageContent.getFileMD5());
                        this.addSendFileContent(hiFile, bdHithumbnailMessageContent);
                    }*/
                }
                break;
            case VOICE:
                LogUtil.printIm(getThreadName(), "It is a voice message, ready for uploading.");
                if (imMessage instanceof BDHiIMVoiceMessage) {
                    BDHiIMVoiceMessage imVoiceMessage = (BDHiIMVoiceMessage) imMessage;
                    VoiceMessageContent voiceMessageContent = imVoiceMessage.getVoice();
                    if (null != voiceMessageContent && voiceMessageContent instanceof BDHiVoiceMessageContent) {
                        BDHiVoiceMessageContent bdHiVoiceMessageContent = (BDHiVoiceMessageContent) voiceMessageContent;
                        // LogUtil.printIm(getThreadName(),
                        // "Add a VoiceMessageContent" + bdHiVoiceMessageContent.toString());
                        BDHiFile hiFile = new BDHiFile();
                        hiFile.setLocaleFilePath(bdHiVoiceMessageContent.getLocalResource());
                        hiFile.setoldLocaleFilePath(bdHiVoiceMessageContent.getLocalResource());
                        hiFile.setFileType(BDHI_FILE_TYPE.VOICE.getName());
                        hiFile.setFileSize(bdHiVoiceMessageContent.getFileSize());
                        hiFile.setMD5(bdHiVoiceMessageContent.getFileMD5());
                        this.addSendFileContent(hiFile, bdHiVoiceMessageContent);
                    }
                }
                break;
            case CUSTOM:
                LogUtil.printIm(getThreadName(), "It is a custom message, ready for uploading.");
                if (imMessage instanceof BDHiIMCustomMessage) {
                    BDHiIMCustomMessage imCustomMessage = (BDHiIMCustomMessage) imMessage;
                    List<IMMessageContentEntry> messageContents = imCustomMessage.getAllMessageContent();
                    if (null != messageContents && !messageContents.isEmpty()) {
                        for (IMMessageContentEntry messageContent : messageContents) {

                            if (messageContent.messageContent instanceof BDHiImageMessageContent) {
                                BDHiImageMessageContent bdhiImageMessageContent =
                                        (BDHiImageMessageContent) messageContent.messageContent;
                                BDHiFile hiFile = new BDHiFile();
                                hiFile.setLocaleFilePath(bdhiImageMessageContent.getLocalResource());
                                hiFile.setoldLocaleFilePath(bdhiImageMessageContent.getLocalResource());
                                hiFile.setFileType(BDHI_FILE_TYPE.IMAGE.getName());
                                hiFile.setFileSize(bdhiImageMessageContent.getFileSize());
                                hiFile.setMD5(bdhiImageMessageContent.getFileMD5());
                                this.addSendFileContent(hiFile, bdhiImageMessageContent);
                            } else if (messageContent.messageContent instanceof BDHiVoiceMessageContent) {
                                BDHiVoiceMessageContent bdHiVoiceMessageContent =
                                        (BDHiVoiceMessageContent) messageContent.messageContent;
                                BDHiFile hiFile = new BDHiFile();
                                hiFile.setLocaleFilePath(bdHiVoiceMessageContent.getLocalResource());
                                hiFile.setoldLocaleFilePath(bdHiVoiceMessageContent.getLocalResource());
                                hiFile.setFileType(BDHI_FILE_TYPE.VOICE.getName());
                                hiFile.setFileSize(bdHiVoiceMessageContent.getFileSize());
                                hiFile.setMD5(bdHiVoiceMessageContent.getFileMD5());
                                this.addSendFileContent(hiFile, bdHiVoiceMessageContent);
                            } else if (messageContent.messageContent instanceof BDHiFileMessageContent) {
                                BDHiFileMessageContent bdhiFileMessageContent =
                                        (BDHiFileMessageContent) messageContent.messageContent;
                                BDHiFile hiFile = new BDHiFile();
                                hiFile.setLocaleFilePath(bdhiFileMessageContent.getLocalResource());
                                hiFile.setoldLocaleFilePath(bdhiFileMessageContent.getLocalResource());
                                hiFile.setFileType(BDHI_FILE_TYPE.FILE.getName());
                                hiFile.setFileSize(bdhiFileMessageContent.getFileSize());
                                hiFile.setMD5(bdhiFileMessageContent.getFileMD5());
                                this.addSendFileContent(hiFile, bdhiFileMessageContent);
                            }
                        }
                    }
                }
                break;
            default:
                LogUtil.printIm(getThreadName(), "It is a text message, do not need uploading.");
                break;
        }
        return finalResult;
    }

    private void processMessage() {
        if (!mFileMap.isEmpty()) {
            boolean result = true;
            for (int i = 0; i < mSendFileList.size() && result; i++) {
                BDHiFile file = mSendFileList.get(i);
                sendFile(file);
            }
        } else {
            sendMsg();
        }
    }

    private void sendMsg() {
        long tmpStartTime3 = System.currentTimeMillis();
        LogUtil.printIm(getThreadName(), "Step4 PROCE " + (tmpStartTime3 - startTime));
        startTime = tmpStartTime3;
        LogUtil.printIm(getThreadName(), "It is successfully uploaded or it don't have any file to upload.");
        // send OneMsg
        LogUtil.printIm(getThreadName(), "Send OneMsg.");
        SendMsgRequest sendMsgRequest = new SendMsgRequest(messageBuilder);
        IMSendMsgProcessor sendMsgProcessor = new IMSendMsgProcessor(sendMsgRequest, this);
        try {
            sendMsgProcessor.startWorkFlow();
        } catch (Exception e) {
            LogUtil.printImE(TAG, e);
        }
    }

    /**
     * Upload a file
     *
     * @param bdhiFile
     * @return true if it is successful uploaded.
     * */
    protected void sendFile(BDHiFile bdhiFile) {
        if (null != ResourceManager.getInstance() && null != bdhiFile
                && !TextUtils.isEmpty(bdhiFile.getLocaleFilePath()) && !TextUtils.isEmpty(bdhiFile.getFileType())
                && !TextUtils.isEmpty(bdhiFile.getMD5())) {
            File file = new File(bdhiFile.getLocaleFilePath());
            if (file.exists()) {
                LogUtil.printIm(getThreadName(), "SendFile " + bdhiFile.toString());
                // IMUploadFileTransaction upload = new IMUploadFileTransaction(bdhiFile, this);
                // upload.startWorkFlow();
                RemoteResourceManager remoteResourceManager;
                if (null != ResourceManager.getInstance().getRemoteResourceManager()) {
                    remoteResourceManager = ResourceManager.getInstance().getRemoteResourceManager();
                } else {
                    remoteResourceManager = ResourceManager.getInstance().getDefaultRemoteRemoteManager();
                }
                if (null != remoteResourceManager) {
                    if (remoteResourceManager instanceof BDHiRemoteResourceManager) {
                        ((BDHiRemoteResourceManager) remoteResourceManager).uploadFile(bdhiFile, this);
                        return;
                    } else {
                        remoteResourceManager.uploadFile(bdhiFile.getLocaleFilePath(), new UploadFileProgressListener(
                                bdhiFile));
                        LogUtil.printIm(getThreadName(), "Custom RemoteResourceManager");
                    }
                } else {
                    LogUtil.printIm(getThreadName(), "Can not get remoteResourceManager");
                }
                return;
            } else {
                LogUtil.printIm(getThreadName(), "Send param error. File is not existed.");
            }
        } else {
            LogUtil.printImE(getThreadName(), "Send file param error.", null);
        }

        LogUtil.printIm(getThreadName(), "BDHiFile upload failed: " + imMessage.toString());
        if (removeFileContent(bdhiFile)) {
            clearFileContents();
            imMessage.setStatus(IMMessageStatus.FAILED);
            // 015 convert IMMessage into MessageBuilder
            messageBuilder = OneMsgConverter.convertIMMessage(imMessage);
            if (null != messageBuilder) {
                // Save OneMsg into msgBody
                imMessage.setBody(messageBuilder.toByteArray());
                // Save IMMessage to db
                msgStore.saveIMMessage(imMessage);
            }
            // 016 Notify User
            if (null != callback) {
                LogUtil.printIm(getThreadName(), "Notify user sendMsg result.");
                Map<IMMessageChange, Object> changes = new HashMap<IMMessageChange, Object>();
                changes.put(IMMessageChange.STATUS, IMMessageStatus.FAILED);
                callback.onMessageChanged(imMessage, changes);
            }
        }
    }

    @Override
    public void onSendMsgCallback(SendMsgResponse sendMsgResponse) {
        long tmpStartTime4 = System.currentTimeMillis();
        LogUtil.printIm(getThreadName(), "Step5 PROTO " + (tmpStartTime4 - startTime));
        startTime = tmpStartTime4;
        // 011 SendMsg finished
        if (null != sendMsgResponse && sendMsgResponse.getErrCode() == 0) {
            // SendMsg succeed
            LogUtil.printIm(getThreadName(), "SendMsg succeed.");
            imMessage.setPreviousMsgID(sendMsgResponse.getPreviousMessageID());
            imMessage.setMsgSeq(sendMsgResponse.getSeq());
            imMessage.setServerTime(sendMsgResponse.getServerTime());
            imMessage.setStatus(IMMessageStatus.SENT);
        } else {
            // SendMsg failed
            LogUtil.printIm(getThreadName(), "SendMsg failed.");
            imMessage.setStatus(IMMessageStatus.FAILED);
        }
        // 012 convert IMMessage into MessageBuilder
        
        long tmpStartTime5 = System.currentTimeMillis();
        LogUtil.printIm(getThreadName(), "Step6 DBOPT " + (tmpStartTime5 - startTime));
        startTime = tmpStartTime5;
        // 013 Notify User
        if (null != callback) {
            Map<IMMessageChange, Object> changes = new HashMap<IMMessageChange, Object>();
            if (null != sendMsgResponse && sendMsgResponse.getErrCode() == 0) {
                changes.put(IMMessageChange.STATUS, IMMessageStatus.SENT);
            } else {
                changes.put(IMMessageChange.STATUS, IMMessageStatus.FAILED);
            }
            LogUtil.printIm(getThreadName(), "Notify user sendMsg result.");
            callback.onMessageChanged(imMessage, changes);
        }
        long tmpStartTime6 = System.currentTimeMillis();
        LogUtil.printIm(getThreadName(), "Step7 NOTIF " + (tmpStartTime6 - startTime));
        startTime = tmpStartTime6;
        
        long tmpStartTime8 = System.currentTimeMillis();
        messageBuilder = OneMsgConverter.convertIMMessage(imMessage);
        if (null != messageBuilder) {
            // Save OneMsg into msgBody
            imMessage.setBody(messageBuilder.toByteArray());
            // Save IMMessage to db
            msgStore.saveIMMessage(imMessage);
        }
        LogUtil.printIm(getThreadName(), "Step8 DBOPT " + (System.currentTimeMillis() - tmpStartTime8));
    }

    @Override
    public void onIMTransactionFileUploadCallback(BDHiFile bdhiFile, boolean result) {
        // 014 Send One File Finished
        if (result) {
            LogUtil.printIm(getThreadName(), "BDHiFile upload success: " + bdhiFile);
            if (removeFileContent(bdhiFile) && mFileMap.isEmpty()) {
                // Send All Files Finished
                messageBuilder = OneMsgConverter.convertIMMessage(imMessage);
                LogUtil.printIm(getThreadName(), "Send All files finished");
                for (Image image : messageBuilder.getContent().getImagesList()) {
                    LogUtil.printIm(getThreadName(), image.getUrl());
                }
                // 017 send one msg
                sendMsg();
            }
        } else {
            LogUtil.printIm(getThreadName(), "BDHiFile upload failed: " + imMessage.toString());
            if (removeFileContent(bdhiFile)) {
                clearFileContents();
                imMessage.setStatus(IMMessageStatus.FAILED);
                // 015 convert IMMessage into MessageBuilder
                messageBuilder = OneMsgConverter.convertIMMessage(imMessage);
                if (null != messageBuilder) {
                    // Save OneMsg into msgBody
                    imMessage.setBody(messageBuilder.toByteArray());
                    // Save IMMessage to db
                    msgStore.saveIMMessage(imMessage);
                }
                // 016 Notify User
                if (null != callback) {
                    Map<IMMessageChange, Object> changes = new HashMap<IMMessageChange, Object>();
                    changes.put(IMMessageChange.STATUS, IMMessageStatus.FAILED);
                    LogUtil.printIm(getThreadName(), "Notify user sendMsg result.");
                    callback.onMessageChanged(imMessage, changes);
                }
            }
        }
    }

    private class UploadFileProgressListener implements ProgressListener<String> {

        private BDHiFile bdHiFile;

        public UploadFileProgressListener(BDHiFile bdHiFile) {
            this.bdHiFile = bdHiFile;
        }

        @Override
        public void onError(int errorCode, String message) {

            LogUtil.printIm(getThreadName(), "BDHiFile upload failed. code:" + errorCode + " message:" + message);
            if (removeFileContent(bdHiFile)) {
                clearFileContents();
                imMessage.setStatus(IMMessageStatus.FAILED);
                // 015 convert IMMessage into MessageBuilder
                messageBuilder = OneMsgConverter.convertIMMessage(imMessage);
                if (null != messageBuilder) {
                    // Save OneMsg into msgBody
                    imMessage.setBody(messageBuilder.toByteArray());
                    // Save IMMessage to db
                    msgStore.saveIMMessage(imMessage);
                }
                // 016 Notify User
                if (null != callback) {
                    LogUtil.printIm(getThreadName(), "Notify user sendMsg result.");
                    Map<IMMessageChange, Object> changes = new HashMap<IMMessageChange, Object>();
                    changes.put(IMMessageChange.STATUS, IMMessageStatus.FAILED);
                    callback.onMessageChanged(imMessage, changes);
                }
            }
        }

        @Override
        public void onProgress(float arg0) {

        }

        @Override
        public void onSuccess(String fileURL) {
            LogUtil.printIm(getThreadName(), "BDHiFile upload success: " + fileURL);
            if (removeFileContent(bdHiFile, fileURL) && mFileMap.isEmpty()) {
                // Send All Files Finished
                messageBuilder = OneMsgConverter.convertIMMessage(imMessage);
                LogUtil.printIm(getThreadName(), "Send All files finished");
                for (Image image : messageBuilder.getContent().getImagesList()) {
                    LogUtil.printIm(getThreadName(), image.getUrl());
                }
                // 017 send one msg
                sendMsg();
            }
        }

    }

    /**
     * Remove FileMessageContent by BDHiFile and set fileURL into it
     * 
     * @param file BDHiFile
     * @param fileURL
     */
    private boolean removeFileContent(BDHiFile file, String fileURL) {
        if (!mFileMap.isEmpty()) {
            BDHiFileMessageContent msgContent = mFileMap.remove(null != file ? file.getOldLocaleFilePath() : null);
            if (msgContent != null) {
                LogUtil.printIm(getThreadName(), "Remove a FileMessageContent.");
                msgContent.setFileURL(fileURL);
                return true;
            } else {
                LogUtil.printIm(getThreadName(), "Error in remove file Content");
                return false;
            }
        } else {
            return false;
        }
    }
}


//will be replaced to biz thread since of poor business (doctor client)
class SendMsgThread implements Runnable
{
	private IMSendMsgTransaction transaction;
	public SendMsgThread(IMSendMsgTransaction obj){
		transaction = obj;
	}
	@Override
	public void run() {
		try {
			transaction.startWorkFlow1();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}