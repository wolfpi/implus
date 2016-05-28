package com.baidu.im.outapp.transaction.processor;

import android.content.Intent;

import com.baidu.im.constant.Constant;
import com.baidu.im.frame.MessageResponser;
import com.baidu.im.frame.Processor;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.ProcessorStart;
import com.baidu.im.frame.outapp.OutAppBaseProcessor;
import com.baidu.im.frame.pb.EnumImPushType;
import com.baidu.im.frame.pb.EnumPacketType;
import com.baidu.im.frame.pb.ObjBizUpPackage.BizUpPackage;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjImPushData.ImPushData;
import com.baidu.im.frame.pb.ObjInformMessage.InformMessage;
import com.baidu.im.frame.pb.ObjOneMsg.OneMsg;
import com.baidu.im.frame.pb.ObjPushData.PushData;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.pb.ProPush;
import com.baidu.im.frame.pb.ProPush.PushMsgs;
import com.baidu.im.frame.pb.ProPush.PushOneMsg;
import com.baidu.im.frame.pb.ProPushConfirm;
import com.baidu.im.frame.pb.ProPushConfirm.PushMsgConfirmReq;
import com.baidu.im.frame.pb.ProPushConfirm.PushMsgStatus;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.outapp.OutAppApplication;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
import com.google.protobuf.micro.ByteStringMicro;
import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

public class OutAppShowOffLineMessageProcessor implements MessageResponser,ProcessorStart {

    public static final String TAG = "OutAppShowOffLineMessage";
    private Processor mProcessor = new OutAppBaseProcessor(this);
    
    private int mChatType = -1;
    private String mFromID = "";
    private String mToID = "";

    @Override
    public String getProcessorName() {
        return TAG;
    }

    private DownPacket downPacket;

    public OutAppShowOffLineMessageProcessor(DownPacket downPacket) {
        this.downPacket = downPacket;
    }

    @Override
    public ProcessorResult startWorkFlow() {

        if (downPacket == null) {
            return new ProcessorResult(ProcessorCode.EMPTY_PUSH);
        }

       /* if(!AppStatusUtil.isVisible(OutAppApplication.getInstance().getContext()) || 
            null ==OutAppApplication.getInstance().getRouter().getClientHandler(downPacket.getAppId()))*/
        {
        ByteStringMicro byteBuffer = downPacket.getBizPackage().getBizData();
        
        LogUtil.i(TAG, "start push msg show");

        PushMsgs pushMsgs = null;
        try {
            pushMsgs = PushMsgs.parseFrom(byteBuffer.toByteArray());
        } catch (InvalidProtocolBufferMicroException e) {
            LogUtil.printError(e);
        }

        if (pushMsgs.getMsgsCount() == 0) {
            LogUtil.printMainProcess("Receive a empty push.");
            return new ProcessorResult(ProcessorCode.EMPTY_PUSH);
        } else {
            LogUtil.printMainProcess("Receive a push contains " + pushMsgs.getMsgsCount() + " messages");
        }

        PushMsgConfirmReq pushMsgConfirmReqBuilder = new  PushMsgConfirmReq();  // migrate from builder
        for (PushOneMsg pushOneMsg : pushMsgs.getMsgsList()) {
            if (pushOneMsg.getOfflineMsg() != null) {
                LogUtil.printMainProcess("receive a offline message.  messageId=" + pushOneMsg.getMsgId());

                if (pushOneMsg.getConfirmMode() == ProPush.ALWAYS_YES) {
                    PushMsgStatus pushMsgStatusBuilder = new  PushMsgStatus();  // migrate from builder
                    pushMsgStatusBuilder.setAckId(pushOneMsg.getAckId());
                    pushMsgStatusBuilder.setOfflineStatus(ProPushConfirm.STATUS_SUCCESS);
                    pushMsgConfirmReqBuilder.addMsgStatus(pushMsgStatusBuilder);
                }

                try {
                   /* if (NotificationUtil.isNotificationEnable()
                            &&*/        	
                	{
                    	
                        InformMessage informMessage =
                                InformMessage.parseFrom(pushOneMsg.getOfflineMsg().toByteArray());
                        
                        
                        Intent intent = new Intent();  
                        intent.setAction(Constant.sdkPushAction); 
                        intent.putExtra(Constant.sdkBDAppId, downPacket.getAppId());
                        intent.putExtra(Constant.sdkBDData, pushOneMsg.getOfflineMsg().toByteArray());
                        
                        /*
                        if(getMsgInfo(pushOneMsg))
                        {
                        	 LogUtil.i(TAG, "Get conversation info");
                        	 intent.putExtra(Constant.sdkBDChatType, mChatType);
                        	 intent.putExtra(Constant.sdkBDFORMUID, mFromID);
                        	 intent.putExtra(Constant.sdkBDTOUID, mToID );
                        	 
                        }
                        else
                        {
                            LogUtil.i(TAG, "Can not get conversation info");
                        }*/
                        
                        OutAppApplication.getInstance().getContext().sendBroadcast(intent);
                        
                        LogUtil.i(TAG, "broadCast send ok");
                       
                        //NotificationUtil.showNormal(OutAppApplication.getInstance().getContext(), informMessage);
                    }
                	/*
                	else {
                        LogUtil.printMainProcess(TAG, "skip notification.");
                    }*/
                } catch (InvalidProtocolBufferMicroException e) {
                    LogUtil.printError(e);
                }

            } else {
                LogUtil.printMainProcess(TAG + ": warning, OfflineMsg should not be null.");
            }

        }

        // Push confirm.
        if (pushMsgConfirmReqBuilder.getMsgStatusCount() > 0) {

            com.baidu.im.frame.pb.ObjBizUpPackage.BizUpPackage bizUpPackageBuilder = new  BizUpPackage();  // migrate from builder
            bizUpPackageBuilder.setPacketType(EnumPacketType.REQUEST);
            bizUpPackageBuilder.setBusiData(ByteStringMicro.copyFrom(pushMsgConfirmReqBuilder.toByteArray()));

            UpPacket upPacketBuilder = new  UpPacket();  // migrate from builder
            upPacketBuilder.setServiceName(ServiceNameEnum.CoreMsg.name());
            upPacketBuilder.setMethodName(MethodNameEnum.PushConfirm.name());

            upPacketBuilder.setBizPackage(bizUpPackageBuilder);
            upPacketBuilder.setSeq(OutAppApplication.getInstance().getOutSeq());
            upPacketBuilder.setAppId(downPacket.getAppId());
            upPacketBuilder.setSysPackage(true);
            UpPacket upPacket = upPacketBuilder;

            // 发包并等待, 如果超过重试次数没有回包则处理失败。
            if (!mProcessor.send(upPacket)) {
                LogUtil.printMainProcess(TAG + ", offline message Confirm error.");
                return new ProcessorResult(ProcessorCode.SERVER_ERROR);
            }
        }
        }
        return new ProcessorResult(ProcessorCode.SUCCESS);
    }

	@Override
	public ProcessorResult onReceive(DownPacket downPacket, UpPacket upPacket) {
		  return new ProcessorResult(ProcessorCode.SUCCESS);
	}
	
	private boolean getMsgInfo(PushOneMsg pushOneMsg)
	{
		ImPushData imPushDataBuilder = new  ImPushData();  // migrate from builder
        try {
        	if(pushOneMsg.getOnlineMsg() == null)
        	{
        		LogUtil.e("hichannel", "online is empty");
        	}
        	PushData pushData = PushData.parseFrom(pushOneMsg.getOnlineMsg().toByteArray());
        	;
			imPushDataBuilder.mergeFrom(pushData.getNormalMessage().getContent().toByteArray());
		      OneMsg oneMsgBuilder = new  OneMsg();  // migrate from builder
		      if(imPushDataBuilder.getImPushType() == EnumImPushType.IM_PUSH_CHAT_MSG)
		      {
		    	  oneMsgBuilder.mergeFrom(imPushDataBuilder.getPushData().toByteArray());
		      
		      //EChatType chattype;
		      //chattype.valueOf(arg0)
		      mChatType =  oneMsgBuilder.getChatType();
		      mToID = oneMsgBuilder.getToId();
		      mFromID = oneMsgBuilder.getFromId();
		      
		      if(mToID != null)
		      {
		    	  LogUtil.i("hichannel", mToID);
		      }
		      else
		      {
		    	  LogUtil.i("hichannel", "get ID failed");
		      }
		      }
		      
		} catch (InvalidProtocolBufferMicroException e) {
			LogUtil.e(TAG, e);
			return false;
		}
      
        return true;
	}
}