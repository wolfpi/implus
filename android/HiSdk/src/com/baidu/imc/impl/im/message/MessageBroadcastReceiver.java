package com.baidu.imc.impl.im.message;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.pb.EnumImPushType;
import com.baidu.im.frame.pb.ObjImPushData.ImPushData;
import com.baidu.im.frame.pb.ObjOneMsg.OneMsg;
import com.baidu.im.frame.utils.AppStatusUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.NotificationUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.ImMessage;
import com.baidu.imc.impl.push.message.PushMessageImpl;
import com.baidu.imc.impl.push.message.PushMessageImpl.NotificationImpl;
import com.baidu.imc.listener.PushMessageListener;

public class MessageBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "ReceiveMessage";

    private List<IMMessageListener> imMsgListenerList = new ArrayList<IMMessageListener>();
    private List<PushMessageListener> pushMsgListenerList = new ArrayList<PushMessageListener>();

    public MessageBroadcastReceiver() {

    }

    public void addIMMsgListener(IMMessageListener listener) {
        removeIMMsgListener(listener);
        imMsgListenerList.add(listener);
    }

    public void removeIMMsgListener(IMMessageListener listener) {
        imMsgListenerList.remove(listener);
    }

    public void clearIMMsgListener() {
        imMsgListenerList.clear();
    }

    public void addPushMsgListener(PushMessageListener listener) {
        removePushMsgListener(listener);
        pushMsgListenerList.add(listener);
    }

    public void removePushMsgListener(PushMessageListener listener) {
        pushMsgListenerList.remove(listener);
    }

    public void clearPushMsgListener() {
        pushMsgListenerList.clear();
    }

    public void clearMsgListener() {
        clearIMMsgListener();
        clearPushMsgListener();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 获取消息ID
        String messageId = intent.getStringExtra("messageId"); // 消息id
        if (TextUtils.isEmpty(messageId)) {
            return;
        }
        // 获取消息
        if (InAppApplication.getInstance().getMessageCenter() == null) {
            return;
        }
        ImMessage message = InAppApplication.getInstance().getMessageCenter().getReceivedMessage(messageId);
        if (message == null) {
            return;
        }
        // 根据消息类型解析，分别为IM消息、Push消息和其它
        switch (message.getType()) {
            case Binary:
                BinaryMessage binaryMessage = (BinaryMessage) message;
                if (binaryMessage.getContentType() == 1) {
                    LogUtil.printIm(TAG, "Receive a IM message.");
                    // 这里只处理im推送的消息
                    if (imMsgListenerList == null || imMsgListenerList.size() == 0) {
                        InAppApplication.getInstance().getMessageCenter().removeReceivedMessage(messageId);
                        return;
                    }
                    try {
                        ImPushData imPushDataBuilder = new ImPushData(); // migrate from builder
                        imPushDataBuilder.mergeFrom(binaryMessage.getData());

                        int pushType = imPushDataBuilder.getImPushType();
                        if (pushType != EnumImPushType.IM_PUSH_CHAT_MSG) {
                            InAppApplication.getInstance().getMessageCenter().removeReceivedMessage(messageId);
                            return;
                        }
                        OneMsg oneMsgBuilder = new OneMsg(); // migrate from builder
                        oneMsgBuilder.mergeFrom(imPushDataBuilder.getPushData().toByteArray()); // migrate from builder
                        // if (oneMsgBuilder.getChatType() != EChatType.CHAT_P2P) {
                        // InAppApplication.getInstance().getMessageCenter().removeReceivedMessage(messageId);
                        // return;
                        // }
                        // isRealTimeMessage
                        boolean isReadlTimeMessage = oneMsgBuilder.getIsRealTime();
                        if (!isReadlTimeMessage) {
                            LogUtil.printIm(TAG, "Receive a un-realtime Message.");
                            // IMMessage
                            BDHiIMMessage imMessage = OneMsgConverter.convertServerMsg(oneMsgBuilder);
                            // imMessage.setBody(oneMsgBuilder.toStringUtf8());

                            if (imMessage != null) {
                                LogUtil.printIm(TAG, "Notify a un-realtime Message.");
                                for (IMMessageListener listener : imMsgListenerList) {
                                    listener.onNewMessageReceived(imMessage);
                                }
                            }
                        } else {
                            // TransientMessage
                            LogUtil.printIm(TAG, "Receive a realtime Message.");
                            BDHiIMTransientMessage dataMessage =
                                    OneMsgConverter.convertOneMsgToIMTransientMessage(oneMsgBuilder);
                            if (dataMessage != null) {
                                LogUtil.printIm(TAG, "Notify a realtime Message.");
                                for (IMMessageListener listener : imMsgListenerList) {
                                    listener.onNewTransientMessageReceived(dataMessage);
                                }
                            }
                        }
                    } catch (Exception e) {
                        LogUtil.printImE(TAG, "Processing IMMsg Error.", e);
                    }
                } else if (binaryMessage.getContentType() == 0) {
                    LogUtil.printIm(TAG, "Receive a Push message.");
                    // 这里只处理push推送的消息
                    try {
                        PushMessageImpl pushMessage = new PushMessageImpl();
                        pushMessage.setMessage(new String(binaryMessage.getData(), Charset.forName("utf-8")));
                        if (binaryMessage.getOfflineNotify() != null) {
                            LogUtil.printIm(TAG, "Receive a Push message with offlineNotify.");
                            NotificationImpl notification = new NotificationImpl();
                            notification.setTitle(binaryMessage.getOfflineNotify().getTitle());
                            notification.setAlert(binaryMessage.getOfflineNotify().getDescription());
                            pushMessage.setNotification(notification);
                        }
                        boolean result = false;
                        if (pushMsgListenerList != null && !pushMsgListenerList.isEmpty()) {
                            LogUtil.printIm(TAG, "Notify a Push message.");
                            for (PushMessageListener listener : pushMsgListenerList) {
                                result = listener.onPushMessageReceived(pushMessage);
                            }
                        }
                        if (!result) {
                            LogUtil.printIm(TAG, "Show a Push message notification.");
                            // TODO send notification
                            // NotificationUtil.showNormal(context, binaryMessage.getOfflineNotify());
                        }
                    } catch (Exception e) {
                        LogUtil.printImE(TAG, "Processing PushMsg Error.", e);
                    }
                } else {
                    // other content type
                    LogUtil.printIm(TAG, "Receive an unknown content-type message.");
                } 
                
                
                if (binaryMessage.getOfflineNotify() != null) {
                    LogUtil.printIm(TAG, "Receive a offline Push message.");
                    // 这里只处理push推送的消息
                    try {
                        PushMessageImpl pushMessage = new PushMessageImpl();
                        pushMessage.setMessage(new String(binaryMessage.getData(), Charset.forName("utf-8")));
                        if (binaryMessage.getOfflineNotify() != null) {
                            LogUtil.printIm(TAG, "Receive a Push message with offlineNotify.");
                            NotificationImpl notification = new NotificationImpl();
                            notification.setTitle(binaryMessage.getOfflineNotify().getTitle());
                            notification.setAlert(binaryMessage.getOfflineNotify().getDescription());
                            pushMessage.setNotification(notification);
                        }
                        boolean result = false;
                        if (pushMsgListenerList != null && !pushMsgListenerList.isEmpty()) {
                            LogUtil.printIm(TAG, "Notify a Push message.");
                            for (PushMessageListener listener : pushMsgListenerList) {
                                result = listener.onPushMessageReceived(pushMessage);
                            }
                        }
                        if (!result) {
                            LogUtil.printIm(TAG, "Show a Push message notification.");
                            // TODO send notification
                            if(AppStatusUtil.isBackground(context) && NotificationUtil.isNotificationEnable()) {
                            NotificationUtil.showNormal(context, binaryMessage.getOfflineNotify(),
                            		InAppApplication.getInstance().getSession().getApp().getAppId());
                            }
                        }
                    } catch (Exception e) {
                        LogUtil.printImE(TAG, "Processing PushMsg Error.", e);
                    }
                }
                

                InAppApplication.getInstance().getMessageCenter().removeReceivedMessage(messageId);
                break;
            default:
                break;
        }
    }

}
