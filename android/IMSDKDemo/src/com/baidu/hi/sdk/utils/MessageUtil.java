package com.baidu.hi.sdk.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baidu.hi.sdk.Constant;
import com.baidu.hi.sdk.adapters.ChatMsgEntity;
import com.baidu.imc.IMPlusSDK;
import com.baidu.imc.client.IMConversation;
import com.baidu.imc.client.MessageHelper;
import com.baidu.imc.impl.im.message.BDHiIMMessage;
import com.baidu.imc.listener.ProgressListener;
import com.baidu.imc.message.CustomMessage;
import com.baidu.imc.message.IMCustomMessage;
import com.baidu.imc.message.IMFileMessage;
import com.baidu.imc.message.IMImageMessage;
import com.baidu.imc.message.IMMessage;
import com.baidu.imc.message.IMTextMessage;
import com.baidu.imc.message.IMVoiceMessage;
import com.baidu.imc.message.ImageMessage;
import com.baidu.imc.message.TextMessage;
import com.baidu.imc.message.VoiceMessage;
import com.baidu.imc.message.content.IMMessageContent;
import com.baidu.imc.message.content.ImageMessageContent;
import com.baidu.imc.message.content.TextMessageContent;
import com.baidu.imc.message.content.URLMessageContent;
import com.baidu.imc.message.content.VoiceMessageContent;

public class MessageUtil {

    private static final String TAG = "MessageUtil";

    public static void sendImageMessage(final Context context, final Uri data, final String currentAccount,
                                        final IMConversation conversation) {
        new Thread() {

            @Override
            public void run() {
                String tmpFileName = ImageUtil.generatePhotoFileName();
                File tmpFile = new File(Constant.IMAGE_DIR, tmpFileName);
                String imageName = ImageUtil.copyToFile(context, data, tmpFile, false);
//                String imageName = ImageUtil.doSavePhoto(context, data, 10, Constant.IMAGE_DIR, tmpFileName);
                String imagePath = tmpFile.getPath();
                String thumbnailPath = null;
                if (!TextUtils.isEmpty(imagePath) && new File(imagePath).exists()) {
                    File image = new File(imagePath);
                    if (image.length() > 1024 * 20) {
                        // compress and save into thumb nail folder
                        // Directly get Thumbnail from Gallery
                        Bitmap bitmap = null;
                        try {
                            long origId = Long.parseLong(data.getLastPathSegment());
                            bitmap = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), origId,
                                    MediaStore.Images.Thumbnails.MINI_KIND, null);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            Log.i(TAG, "Cannot access Thumbnails directly, fall back to extract thumbnail directly");
                        }
                        if(bitmap == null){
                            bitmap = ImageUtil.getCompressBitmap(imagePath, 200);
                        }
                        String thumbnailName =
                                ImageUtil.doSavePhoto(context, bitmap, 50, Constant.THUMBNAIL_DIR, tmpFileName);
                        thumbnailPath = Constant.THUMBNAIL_DIR + "/" + thumbnailName;
                    } else {
                        thumbnailPath = Constant.THUMBNAIL_DIR + "/" + imageName;
                        boolean copyResult = ImageUtil.doCopyImage(imagePath, thumbnailPath);
                        if (!copyResult) {
                            thumbnailPath = null;
                        }
                    }
                }
                ImageMessage imageMessage = createImageMessage(context, imagePath, thumbnailPath, currentAccount);
                conversation.sendMessage(imageMessage);
            }

        }.start();
    }

    private static ImageMessage createImageMessage(final Context context, String imagePath, String thumbnailPath,
            String currentAccount) {
        if (!TextUtils.isEmpty(imagePath) && new File(imagePath).exists() && !TextUtils.isEmpty(thumbnailPath)
                && new File(thumbnailPath).exists()) {
            // image
            File targetFile = new File(imagePath);
            String targetFileName = targetFile.getName();
            String targetFilePath = targetFile.getAbsolutePath();
            int targetImageWidth = 0;
            int targetImageHeight = 0;
            InputStream in = null;
            try {
                in = new FileInputStream(targetFilePath);
                BitmapFactory.Options options = ImageUtil.getBitmapOptions(in);
                targetImageWidth = options.outWidth;
                targetImageHeight = options.outHeight;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (null != in) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            ImageMessageContent imageMessageContent =
                    IMPlusSDK.getImpClient().getMessageHelper().newImageMessageContent();
            imageMessageContent.setFileName(targetFileName);
            imageMessageContent.setFilePath(targetFilePath);
            imageMessageContent.setHeight(targetImageHeight);
            imageMessageContent.setWidth(targetImageWidth);
            imageMessageContent.setFileUploadListener(new ProgressListener<String>() {

                @Override
                public void onSuccess(final String result) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "上传成功" + result, Toast.LENGTH_SHORT).show();
                        }

                    });
                }

                @Override
                public void onProgress(final float progress) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "上传进度" + progress, Toast.LENGTH_SHORT).show();
                        }

                    });
                }

                @Override
                public void onError(final int code, final String result) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "上传错误 code:" + code + " result:" + result, Toast.LENGTH_SHORT)
                                    .show();
                        }

                    });
                }
            });
            // thumbnail
            File targetThumbnailFile = new File(thumbnailPath);
            String targetThumbnailFileName = targetThumbnailFile.getName();
            String targetThumbnailFilePath = targetThumbnailFile.getAbsolutePath();
            int targetThumbnailImageWidth = 0;
            int targetThumbnailImageHeight = 0;
            InputStream thumbnailIn = null;
            try {
                thumbnailIn = new FileInputStream(targetThumbnailFilePath);
                BitmapFactory.Options options = ImageUtil.getBitmapOptions(thumbnailIn);
                targetThumbnailImageWidth = options.outWidth;
                targetThumbnailImageHeight = options.outHeight;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (null != thumbnailIn) {
                    try {
                        thumbnailIn.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            ImageMessageContent thumbnailMessageContent =
                    IMPlusSDK.getImpClient().getMessageHelper().newImageMessageContent();
            thumbnailMessageContent.setFileName(targetThumbnailFileName);
            thumbnailMessageContent.setFilePath(targetThumbnailFilePath);
            thumbnailMessageContent.setHeight(targetThumbnailImageHeight);
            thumbnailMessageContent.setWidth(targetThumbnailImageWidth);
            thumbnailMessageContent.setFileUploadListener(new ProgressListener<String>() {

                @Override
                public void onSuccess(final String result) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "上传成功" + result, Toast.LENGTH_SHORT).show();
                        }

                    });
                }

                @Override
                public void onProgress(final float progress) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "上传进度" + progress, Toast.LENGTH_SHORT).show();
                        }

                    });
                }

                @Override
                public void onError(final int code, final String result) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "上传错误 code:" + code + " result:" + result, Toast.LENGTH_SHORT)
                                    .show();
                        }

                    });
                }
            });
            ImageMessage imageMessage = IMPlusSDK.getImpClient().getMessageHelper().newImageMessage();
            imageMessage.setAddresserName(currentAccount);
            imageMessage.setCompatibleText("收到一张图片。");
            imageMessage.setImage(imageMessageContent);
            imageMessage.setThumbnailImage(thumbnailMessageContent);
            imageMessage.setNotificationText("收到了一张图片。");
            return imageMessage;
        } else {
            return null;
        }
    }

    public static void sendTextMessage(final Context context, final String contString, final String currentAccount,
            final IMConversation conversation) {
        new Thread() {

            @Override
            public void run() {
                TextMessage textMessage = createTextMessage(contString, currentAccount);
                conversation.sendMessage(textMessage);
            }

        }.start();
    }

    private static TextMessage createTextMessage(String contString, String currentAccount) {

        if (!TextUtils.isEmpty(contString)) {
            TextMessage textMessage = IMPlusSDK.getImpClient().getMessageHelper().newTextMessage();
            textMessage.setAddresserName(currentAccount);
            textMessage.setCompatibleText("收到一条消息文字消息：" + contString);
            textMessage.setNotificationText("收到一条文字消息" + contString);
            TextMessageContent textMessageContent =
                    IMPlusSDK.getImpClient().getMessageHelper().newTextMessageContent(contString);
            textMessageContent.setText(contString);
            textMessage.addText(textMessageContent);
            return textMessage;
        } else {
            return null;
        }
    }

    public static void sendCustomMessage(final Context context, final Uri data, final String textMessage1,
            final String textMessage2, final String currentAccount, final IMConversation conversation) {
        new Thread() {

            @Override
            public void run() {
                String tmpFileName = ImageUtil.generatePhotoFileName();
                String imageName = ImageUtil.doSavePhoto(context, data, 10, Constant.IMAGE_DIR, tmpFileName);
                String imagePath = Constant.IMAGE_DIR + "/" + imageName;
                String thumbnailPath = null;
                if (!TextUtils.isEmpty(imagePath) && new File(imagePath).exists()) {
                    File image = new File(imagePath);
                    if (image.length() > 1024 * 20) {
                        // compress and save into thumb nail folder
                        Bitmap bitmap = ImageUtil.getCompressBitmap(imagePath, 200);
                        String thumbnailName =
                                ImageUtil.doSavePhoto(context, bitmap, 50, Constant.THUMBNAIL_DIR, tmpFileName);
                        thumbnailPath = Constant.THUMBNAIL_DIR + "/" + thumbnailName;
                    } else {
                        thumbnailPath = Constant.THUMBNAIL_DIR + "/" + imageName;
                        boolean copyResult = ImageUtil.doCopyImage(imagePath, thumbnailPath);
                        if (!copyResult) {
                            thumbnailPath = null;
                        }
                    }
                }
                CustomMessage textMessage =
                        createCustomMessage(context, imagePath, thumbnailPath, textMessage1, textMessage2,
                                currentAccount);
                conversation.sendMessage(textMessage);
            }

        }.start();
    }

    public static void sendVoiceMessage(final Context context, final Uri data, final String currentAccount,
            final IMConversation conversation) {
        new Thread() {

            @Override
            public void run() {
                String tmpFileName = ImageUtil.generatePhotoFileName();
                String imageName = ImageUtil.doSavePhoto(context, data, 10, Constant.IMAGE_DIR, tmpFileName);
                String imagePath = Constant.IMAGE_DIR + "/" + imageName;
                // System.out.println("MessageUtil: filePath" + imagePath);
                VoiceMessage textMessage = createVoiceMessage(context, imagePath, 10, currentAccount);
                conversation.sendMessage(textMessage);
            }

        }.start();
    }

    private static CustomMessage createCustomMessage(final Context context, String imagePath, String thumbnailPath,
            String textMessage1, String textMessage2, String currentAccount) {
        if (!TextUtils.isEmpty(imagePath) && new File(imagePath).exists() && !TextUtils.isEmpty(thumbnailPath)
                && new File(thumbnailPath).exists() && !TextUtils.isEmpty(textMessage1)
                && !TextUtils.isEmpty(textMessage2)) {
            MessageHelper messageHelper = IMPlusSDK.getImpClient().getMessageHelper();
            CustomMessage customMessage = messageHelper.newCustomMessage();
            // image
            File targetFile = new File(imagePath);
            String targetFileName = targetFile.getName();
            String targetFilePath = targetFile.getAbsolutePath();
            int targetImageWidth = 0;
            int targetImageHeight = 0;
            InputStream in = null;
            try {
                in = new FileInputStream(targetFilePath);
                BitmapFactory.Options options = ImageUtil.getBitmapOptions(in);
                targetImageWidth = options.outWidth;
                targetImageHeight = options.outHeight;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (null != in) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            ImageMessageContent imageMessageContent = messageHelper.newImageMessageContent();
            imageMessageContent.setFileName(targetFileName);
            imageMessageContent.setFilePath(targetFilePath);
            imageMessageContent.setHeight(targetImageHeight);
            imageMessageContent.setWidth(targetImageWidth);
            imageMessageContent.setFileUploadListener(new ProgressListener<String>() {

                @Override
                public void onSuccess(final String result) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "上传成功" + result, Toast.LENGTH_SHORT).show();
                        }

                    });
                }

                @Override
                public void onProgress(final float progress) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "上传进度" + progress, Toast.LENGTH_SHORT).show();
                        }

                    });
                }

                @Override
                public void onError(final int code, final String result) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "上传错误 code:" + code + " result:" + result, Toast.LENGTH_SHORT)
                                    .show();
                        }

                    });
                }
            });
            customMessage.addMessageContent("image1", imageMessageContent);
            // thumbnail
            File targetThumbnailFile = new File(thumbnailPath);
            String targetThumbnailFileName = targetThumbnailFile.getName();
            String targetThumbnailFilePath = targetThumbnailFile.getAbsolutePath();
            int targetThumbnailImageWidth = 0;
            int targetThumbnailImageHeight = 0;
            InputStream thumbnailIn = null;
            try {
                thumbnailIn = new FileInputStream(targetThumbnailFilePath);
                BitmapFactory.Options options = ImageUtil.getBitmapOptions(thumbnailIn);
                targetThumbnailImageWidth = options.outWidth;
                targetThumbnailImageHeight = options.outHeight;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (null != thumbnailIn) {
                    try {
                        thumbnailIn.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            ImageMessageContent thumbnailMessageContent = messageHelper.newImageMessageContent();
            thumbnailMessageContent.setFileName(targetThumbnailFileName);
            thumbnailMessageContent.setFilePath(targetThumbnailFilePath);
            thumbnailMessageContent.setHeight(targetThumbnailImageHeight);
            thumbnailMessageContent.setWidth(targetThumbnailImageWidth);
            thumbnailMessageContent.setFileUploadListener(new ProgressListener<String>() {

                @Override
                public void onSuccess(final String result) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "上传成功" + result, Toast.LENGTH_SHORT).show();
                        }

                    });
                }

                @Override
                public void onProgress(final float progress) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "上传进度" + progress, Toast.LENGTH_SHORT).show();
                        }

                    });
                }

                @Override
                public void onError(final int code, final String result) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "上传错误 code:" + code + " result:" + result, Toast.LENGTH_SHORT)
                                    .show();
                        }

                    });
                }
            });
            customMessage.addMessageContent("image2", thumbnailMessageContent);
            TextMessageContent textMessageContent1 = messageHelper.newTextMessageContent(textMessage1);
            customMessage.addMessageContent("text1", textMessageContent1);
            TextMessageContent textMessageContent2 = messageHelper.newTextMessageContent(textMessage2);
            customMessage.addMessageContent("text2", textMessageContent2);
            String[] keys = { "image1", "image2", "text1", "text2" };
            customMessage.setExtra(Arrays.toString(keys));
            customMessage.setAddresserName(currentAccount);
            customMessage.setCompatibleText("收到一条自定义消息。");
            customMessage.setNotificationText("收到一条自定义消息。");
            return customMessage;
        } else {
            return null;
        }
    }

    private static VoiceMessage createVoiceMessage(final Context context, String voicePath, int voiceLength,
            String currentAccount) {
        if (!TextUtils.isEmpty(voicePath) && new File(voicePath).exists() && voiceLength > 0) {
            MessageHelper messageHelper = IMPlusSDK.getImpClient().getMessageHelper();
            VoiceMessage voiceMessage = messageHelper.newVoiceMessage();
            // image
            File targetFile = new File(voicePath);
            String targetFileName = targetFile.getName();
            String targetFilePath = targetFile.getAbsolutePath();
            VoiceMessageContent voiceMessageContent = messageHelper.newVoiceMessageContent();
            voiceMessageContent.setFileName(targetFileName);
            voiceMessageContent.setFilePath(targetFilePath);
            voiceMessageContent.setDuration(voiceLength);
            voiceMessageContent.setFileUploadListener(new ProgressListener<String>() {

                @Override
                public void onSuccess(final String result) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "上传成功" + result, Toast.LENGTH_SHORT).show();
                        }

                    });
                }

                @Override
                public void onProgress(final float progress) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "上传进度" + progress, Toast.LENGTH_SHORT).show();
                        }

                    });
                }

                @Override
                public void onError(final int code, final String result) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "上传错误 code:" + code + " result:" + result, Toast.LENGTH_SHORT)
                                    .show();
                        }

                    });
                }
            });
            voiceMessage.setVoice(voiceMessageContent);
            voiceMessage.setAddresserName(currentAccount);
            voiceMessage.setCompatibleText("收到一条语音消息。");
            voiceMessage.setNotificationText("收到一条语音消息。");
            return voiceMessage;
        } else {
            return null;
        }
    }

    public static void receiveMessage(List<ChatMsgEntity> list, IMMessage newMessage) {
        if (null != list && null != newMessage) {
            list.add(getChatMsgEntity(newMessage));
        }
    }

    public static void updateMessage(List<ChatMsgEntity> list, IMMessage oldMessage) {
        if (null != list && null != oldMessage) {
            for (ChatMsgEntity entity : list) {
                if (entity.getMessageID() == oldMessage.getMessageID()) {
                    ChatMsgEntity tmpEntity = getChatMsgEntity(oldMessage);
                    entity.setDate(tmpEntity.getDate());
                    entity.setText(tmpEntity.getText());
                    entity.setStatus(tmpEntity.getStatus());
                    entity.setId(tmpEntity.getId());
                }
            }
        }
    }

    public static ChatMsgEntity getChatMsgEntity(IMMessage message) {
        ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
        if (null != message) {
            chatMsgEntity.setMessageID(message.getMessageID());
            chatMsgEntity.setDate(getTimeLabel(message.getSendTime()));
            chatMsgEntity.setStatus(message.getStatus().name());
            chatMsgEntity.setMsgType(!IMPlusSDK.getImpClient().getCurrentUserID().equals(message.getAddresserID()));
            chatMsgEntity.setName(message.getAddresserName());
            // 这一行是测试使用
            chatMsgEntity.setId(message.getMessageID() + ":" + ((BDHiIMMessage) message).getMsgSeq());
            if (message instanceof IMTextMessage) {
                chatMsgEntity.setDisplayMsgType("text");
                IMTextMessage textMessage = (IMTextMessage) message;
                List<IMMessageContent> list = textMessage.getMessageContentList();
                StringBuilder sb = new StringBuilder();
                if (null != list) {
                    for (IMMessageContent messageContent : list) {
                        if (messageContent instanceof TextMessageContent) {
                            TextMessageContent textMessageContent = (TextMessageContent) messageContent;
                            if (sb.length() > 0) {
                                sb.append(";");
                            }
                            sb.append(textMessageContent.getText());
                        } else if (messageContent instanceof URLMessageContent) {
                            URLMessageContent urlMessageContent = (URLMessageContent) messageContent;
                            if (sb.length() > 0) {
                                sb.append(";");
                            }
                            sb.append(urlMessageContent.getText() + urlMessageContent.getURL());
                        }
                    }
                }
                if (sb.length() > 0) {
                    chatMsgEntity.setText(sb.toString());
                } else {
                    chatMsgEntity.setText(textMessage.getCompatibleText());
                }
            } else if (message instanceof IMImageMessage) {
                chatMsgEntity.setDisplayMsgType("image");
                IMImageMessage imageMessage = (IMImageMessage) message;
                chatMsgEntity.setText(imageMessage.getCompatibleText());
                ImageMessageContent image = imageMessage.getImage();
                ImageMessageContent thumbnail = imageMessage.getThumbnailImage();
                if (thumbnail != null && !TextUtils.isEmpty(thumbnail.getFileName())) {
                    chatMsgEntity.setFileName(thumbnail.getFileName());
                    chatMsgEntity.setFileMessageContent(thumbnail);
                }
                if (image != null && !TextUtils.isEmpty(image.getFileName())) {
                    chatMsgEntity.setBigFileName(image.getFileName());
                    chatMsgEntity.setBigFileMessageContent(image);
                }
            } else if (message instanceof IMVoiceMessage) {
                chatMsgEntity.setDisplayMsgType("voice");
                IMVoiceMessage voiceMessage = (IMVoiceMessage) message;
                VoiceMessageContent voiceMessageContent = voiceMessage.getVoice();
                if (null != voiceMessageContent && !TextUtils.isEmpty(voiceMessageContent.getFileName())) {
                    chatMsgEntity.setFileName(voiceMessageContent.getFileName());
                    chatMsgEntity.setFileMessageContent(voiceMessageContent);
                }
                chatMsgEntity.setText(voiceMessage.getCompatibleText());
            } else if (message instanceof IMFileMessage) {
                chatMsgEntity.setDisplayMsgType("file");
                IMFileMessage fileMessage = (IMFileMessage) message;
                chatMsgEntity.setText(fileMessage.getCompatibleText());
            } else if (message instanceof IMCustomMessage) {
                chatMsgEntity.setDisplayMsgType("custom");
                IMCustomMessage customMessage = (IMCustomMessage) message;
                String msgTemplate = customMessage.getExtra();
                chatMsgEntity.setMsgTemplate(msgTemplate);
                IMMessageContent textMessageContent1 = customMessage.getMessageContent("text1");
                IMMessageContent textMessageContent2 = customMessage.getMessageContent("text2");
                if (null != textMessageContent1 && textMessageContent1 instanceof TextMessageContent
                        && null != textMessageContent2 && textMessageContent2 instanceof TextMessageContent) {
                    TextMessageContent imTextMessageContent1 = (TextMessageContent) textMessageContent1;
                    TextMessageContent imTextMessageContent2 = (TextMessageContent) textMessageContent2;
                    chatMsgEntity.setText(imTextMessageContent1.getText() + imTextMessageContent2.getText());
                }

                IMMessageContent imageMessageContent1 = customMessage.getMessageContent("image1");
                if (imageMessageContent1 instanceof ImageMessageContent) {
                    ImageMessageContent imImageMessageContent1 = (ImageMessageContent) imageMessageContent1;
                    if (imImageMessageContent1 != null && !TextUtils.isEmpty(imImageMessageContent1.getFileName())) {
                        chatMsgEntity.setBigFileName(imImageMessageContent1.getFileName());
                        chatMsgEntity.setBigFileMessageContent(imImageMessageContent1);
                    }
                }
                IMMessageContent imageMessageContent2 = customMessage.getMessageContent("image2");
                if (imageMessageContent2 instanceof ImageMessageContent) {
                    ImageMessageContent imImageMessageContent2 = (ImageMessageContent) imageMessageContent2;
                    if (imImageMessageContent2 != null && !TextUtils.isEmpty(imImageMessageContent2.getFileName())) {
                        chatMsgEntity.setFileName(imImageMessageContent2.getFileName());
                        chatMsgEntity.setFileMessageContent(imImageMessageContent2);
                    }
                }
            } else {
                chatMsgEntity.setText(message.getCompatibleText());
            }
        } else {
            chatMsgEntity.setDate(getTimeLabel(0l));
            chatMsgEntity.setMsgType(false);
            chatMsgEntity.setName(null);
            chatMsgEntity.setText(null);
        }
        return chatMsgEntity;
    }

    private static String getTimeLabel(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(new Date(time));
    }
}
