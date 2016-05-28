package com.baidu.im.inapp.transaction.message.processor;

import android.text.TextUtils;

import com.baidu.im.frame.MessageResponser;
import com.baidu.im.frame.NotifyListener;
import com.baidu.im.frame.Processor;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.inapp.InAppBaseProcessor;
import com.baidu.im.frame.pb.EnumMessageType;
import com.baidu.im.frame.pb.EnumNotifyType;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjInformMessage.InformMessage;
import com.baidu.im.frame.pb.ObjNormalMessage.NormalMessage;
import com.baidu.im.frame.pb.ObjNotifyMessage.NotifyMessage;
import com.baidu.im.frame.pb.ObjPushData.PushData;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.pb.ProPush;
import com.baidu.im.frame.pb.ProPush.PushMsgs;
import com.baidu.im.frame.pb.ProPush.PushOneMsg;
import com.baidu.im.frame.pb.ProPushConfirm;
import com.baidu.im.frame.pb.ProPushConfirm.PushMsgConfirmReq;
import com.baidu.im.frame.pb.ProPushConfirm.PushMsgStatus;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.inapp.messagecenter.MessageCenter;
import com.baidu.im.outapp.network.ProtocolConverter;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.IMessageCallback;
import com.google.protobuf.micro.ByteStringMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

public class ReceiveMessageProsessor implements NotifyListener, MessageResponser {

    public static final String TAG = "ReceiveMessage";

    private Processor processor = new InAppBaseProcessor(this);
    // private PreferenceUtil mPref = null;

    public ReceiveMessageProsessor(PreferenceUtil pref) {
        // mPref = pref;
    }

    public String getProcessorName() {
        return TAG;
    }

    @Override
    public ProcessorResult receiveNotify(DownPacket downPacket) {
        LogUtil.i(getProcessorName(), "ReceiveMessageProsessor processorId=" + this.hashCode());
        ProcessorResult receiveMessageResult;
        if (InAppApplication.getInstance().getSession().getSessionInfo() != null
                && !TextUtils.isEmpty(InAppApplication.getInstance().getSession().getSessionInfo().getSessionId())) {
            ByteStringMicro byteBuffer = downPacket.getBizPackage().getBizData();
            PushMsgs pushMsgs = null;
            try {
                pushMsgs = PushMsgs.parseFrom(byteBuffer.toByteArray());
                if (null != pushMsgs && pushMsgs.getMsgsCount() > 0) {
                    LogUtil.i(getProcessorName(), "Receive a push contains " + pushMsgs.getMsgsCount() + " messages");
                    for (PushOneMsg pushOneMsg : pushMsgs.getMsgsList()) {
                        if (pushOneMsg.hasOnlineMsg() && pushOneMsg.getOnlineMsg() != null) {
                            LogUtil.i(getProcessorName(), "Receive a online message.");
                            PushMsgConfirmReq pushMsgConfirmReqBuilder = new  PushMsgConfirmReq();  // migrate from builder
                            if (pushOneMsg.getConfirmMode() == ProPush.ALWAYS_YES) {
                                // TODO
                                // set online msg confirm builder
                                PushMsgStatus pushMsgStatusBuilder = new  PushMsgStatus();  // migrate from builder
                                pushMsgStatusBuilder.setAckId(pushOneMsg.getAckId());
                                pushMsgStatusBuilder.setOnlineStatus(ProPushConfirm.STATUS_SUCCESS);
                                pushMsgConfirmReqBuilder.addMsgStatus(pushMsgStatusBuilder);
                            }

                            PushData pushData = PushData.parseFrom(pushOneMsg.getOnlineMsg().toByteArray());
                            long messageId = pushOneMsg.getMsgId();
                            if (pushData.getMessageType() == EnumMessageType.NORMAL_MSG) {
                                // 排重
                                if (null != pushData.getNormalMessage()) {
                                    LogUtil.i(getProcessorName(), "Receive a normal message.");
                                    NormalMessage normalMessage = pushData.getNormalMessage();

                                    BinaryMessage binaryMessage = new BinaryMessage();
                                    binaryMessage.setData(normalMessage.getContent().toByteArray());
                                    binaryMessage.setMessageId(Long.toString(messageId));
                                    binaryMessage.setContentType(normalMessage.getContentType());
                                    if (pushOneMsg.hasOfflineMsg()) {
                                        InformMessage informMsgBuilder = new  InformMessage();  // migrate from builder
                                        informMsgBuilder.mergeFrom(pushOneMsg.getOfflineMsg().toByteArray());
                                        InformMessage informMessage = informMsgBuilder;
                                        binaryMessage.setOfflineNotify(informMessage);
                                        LogUtil.i(getProcessorName(), "Receive a normal message with offlineMsg. "
                                                + ByteStringMicro.copyFrom(informMessage.toByteArray()).toStringUtf8());
                                        // TODO
                                        // set offline msg confirm builder
                                        PushMsgStatus pushMsgStatusBuilder = new  PushMsgStatus();  // migrate from builder
                                        pushMsgStatusBuilder.setAckId(pushOneMsg.getAckId());
                                        pushMsgStatusBuilder.setOfflineStatus(ProPushConfirm.STATUS_SUCCESS);
                                        pushMsgConfirmReqBuilder.addMsgStatus(pushMsgStatusBuilder);
                                    }

                                    InAppApplication.getInstance().getMessageCenter()
                                            .addReceivedMessage(binaryMessage.getMessageId(), binaryMessage);
                                    MessageCenter.sendBroadcast(InAppApplication.getInstance().getContext(),
                                            binaryMessage.getMessageId());
                                } else {
                                    LogUtil.i(getProcessorName(), "Receive a Empty normal message.");
                                }
                            } else if (pushData.getMessageType() == EnumMessageType.SYS_NOTIFY_MSG) {
                                LogUtil.i(getProcessorName(), "Receive a system notify message.");
                                // 排重
                                // Dispatch system notify message
                                if (null != pushData.getSysNotifyMessage()) {
                                    NotifyMessage notifyMessage = pushData.getSysNotifyMessage();
                                    if (notifyMessage.getNotifyType() == EnumNotifyType.CONFIG_CHANGED_NOTIFY) {
                                        LogUtil.i(getProcessorName(),
                                                "Receive a system notify message: CONFIG_CHANGED_NOTIFY");
                                        // should pass notifyMessage
                                        if (null != notifyMessage.getNotifyData()) {
                                            InAppApplication.getInstance().getTransactionFlow()
                                                    .receiveConfigChangeNotify(notifyMessage.getNotifyData());
                                        } else {
                                            LogUtil.i(getProcessorName(),
                                                    "SysNotifyMsg's notifyData should not be null.");
                                        }
                                    } else if (notifyMessage.getNotifyType() == EnumNotifyType.LOGOUT_NOTIFY) {
                                        LogUtil.i(getProcessorName(), "Receive a system notify message: LOGOUT_NOTIFY");
                                        // kickout
                                        IMessageCallback callback = null;
                                        if (null != InAppApplication.getInstance().getInAppHeartbeat()
                                                && null != InAppApplication.getInstance().getInAppHeartbeat()
                                                        .getHeartbeatCallback()) {
                                            callback =
                                                    InAppApplication.getInstance().getInAppHeartbeat()
                                                            .getHeartbeatCallback();
                                        }
                                        InAppApplication.getInstance().getTransactionFlow()
                                                .receiveUserLogoutNotify(notifyMessage.getNotifyData(), callback);
                                    } else if (notifyMessage.getNotifyType() == EnumNotifyType.UPLOAD_LOG_NOTIFY) {
                                        LogUtil.i(getProcessorName(),
                                                "Receive a system notify message: UPLOAD_LOG_NOTIFY");
                                        InAppApplication.getInstance().getTransactionFlow()
                                                .receiveUploadLogNotify(notifyMessage.getNotifyData());

                                    } else {
                                        LogUtil.i(getProcessorName(),
                                                "Receive a system notify message: UNKNOWN TYPE NOTIFY. SysNotifyMsg should not have other type msg.");
                                    }
                                } else {
                                    LogUtil.i(getProcessorName(),
                                            "Receive a EMPTY system notify message. SysNotifyMsg should not be null.");
                                }
                            } else { // pushData.getMessageType() == EnumMessageType.INFORM_MSG
                                LogUtil.i(getProcessorName(),
                                        "Receive a unknown type message. Push msg have msg type error.");
                            }

                            // Push confirm.
                            if (pushMsgConfirmReqBuilder.getMsgStatusCount() > 0) {
                                UpPacket upPacketBuilder = new  UpPacket();  // migrate from builder
                                upPacketBuilder.setServiceName(ServiceNameEnum.CoreMsg.name());
                                upPacketBuilder.setMethodName(MethodNameEnum.PushConfirm.name());
                                String sessionId =
                                        InAppApplication.getInstance().getSession().getSessionInfo().getSessionId();
                                if (sessionId != null) {
                                    upPacketBuilder.setSessionId(sessionId);
                                }
                                UpPacket upPacket =
                                        ProtocolConverter.convertUpPacket(
                                                ByteStringMicro.copyFrom(pushMsgConfirmReqBuilder.toByteArray()),
                                                upPacketBuilder);
                                // 发包并等待, 如果超过重试次数没有回包则处理失败。
                                if (!processor.send(upPacket)) {
                                    LogUtil.i(getProcessorName(), "Send push Confirm error. Timeout normally.");
                                    receiveMessageResult = new ProcessorResult(ProcessorCode.SEND_TIME_OUT);
                                    break;
                                }
                            }
                        } else {
                            LogUtil.i(getProcessorName(), "Receive a EMPTY onlineMsg .");
                        }
                    }
                    receiveMessageResult = new ProcessorResult(ProcessorCode.SUCCESS);
                } else {
                    LogUtil.i(getProcessorName(), "ReceiveMessage Error. Receive a empty push.");
                    receiveMessageResult = new ProcessorResult(ProcessorCode.EMPTY_PUSH);
                }
            } catch (InvalidProtocolBufferMicroException e) {
                LogUtil.i(getProcessorName(),
                        "ReceiveMessage Error. InvalidProtocolBufferMicroException IOException: " + e.getMessage());
                receiveMessageResult = new ProcessorResult(ProcessorCode.SERVER_ERROR);
            }
        } else {
            LogUtil.i(getProcessorName(), "ReceiveMessage error. Can not get seesionId.");
            receiveMessageResult = new ProcessorResult(ProcessorCode.NO_SESSION_ID_FAILURE);
        }
        return receiveMessageResult;
    }

    @Override
    public ProcessorResult onReceive(DownPacket downPacket, UpPacket upPacket) {
        return null;
    }
}
