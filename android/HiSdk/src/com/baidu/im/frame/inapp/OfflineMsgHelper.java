package com.baidu.im.frame.inapp;

import android.content.Context;

import com.baidu.im.frame.ServiceControlUtil;
import com.baidu.im.frame.pb.EnumMessageType;
import com.baidu.im.frame.pb.EnumNotifyType;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjInformMessage.InformMessage;
import com.baidu.im.frame.pb.ObjNotifyMessage.NotifyMessage;
import com.baidu.im.frame.pb.ObjPushData.PushData;
import com.baidu.im.frame.pb.ProPush.PushMsgs;
import com.baidu.im.frame.pb.ProPush.PushOneMsg;
import com.baidu.im.frame.pb.ProPushConfirm.PushMsgConfirmReq;
import com.baidu.im.frame.utils.AppStatusUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.NotificationUtil;
import com.google.protobuf.micro.ByteStringMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

public class OfflineMsgHelper {
	
	private final static String TAG = "offlinemsghelper";

	public static void showOfflineNotificaiton(Context context,DownPacket downPacket) throws InvalidProtocolBufferMicroException
	{
		if(!ServiceControlUtil.showInSeperateProcess(context))
		{
			return ;
		}
		if(AppStatusUtil.isBackground(context) && NotificationUtil.isNotificationEnable())
		{
			ByteStringMicro byteBuffer = downPacket.getBizPackage().getBizData();
			PushMsgs pushMsgs = null;
			try {
				pushMsgs = PushMsgs.parseFrom(byteBuffer.toByteArray());
			} catch (InvalidProtocolBufferMicroException e) {
				LogUtil.printError(e);
			}

			if (pushMsgs.getMsgsCount() == 0) {
				LogUtil.printMainProcess("Receive offline null push.");
        	     return ;
			} else {
				LogUtil.printMainProcess("Receive a offline push contains " + pushMsgs.getMsgsCount() + " messages");
			}

			PushMsgConfirmReq pushMsgConfirmReqBuilder = new  PushMsgConfirmReq();  // migrate from builder
			for (PushOneMsg pushOneMsg : pushMsgs.getMsgsList()) {	
			    if(pushOneMsg == null)
			    	return;
			    
			    if(pushOneMsg.getOnlineMsg() != null) {
			    PushData pushData = PushData.parseFrom(pushOneMsg.getOnlineMsg().toByteArray());
			    	if(pushData.getMessageType() == EnumMessageType.SYS_NOTIFY_MSG) {
			    		/*if (null != pushData.getSysNotifyMessage()) {
			    			NotifyMessage notifyMessage = pushData.getSysNotifyMessage();
			    			if(notifyMessage.getNotifyType() == ENotifyType.LOGOUT_NOTIFY) {
			    				return ;
			    			}
			    		}*/
			    		return;
			    	}
			    }
			   											
				if (pushOneMsg.getOfflineMsg() != null)
             	{ 	
             		try{
                     InformMessage informMessage =
                             InformMessage.parseFrom(pushOneMsg.getOfflineMsg().toByteArray());
                     NotificationUtil.showNormal(context, informMessage,downPacket.getAppId());
             		} catch (InvalidProtocolBufferMicroException e) {
             			LogUtil.printError(e);
                    }
             	} else {
             		LogUtil.printMainProcess(TAG + ": warning, OfflineMsg should not be null.");         
             		}
				}
		}
	}
}
