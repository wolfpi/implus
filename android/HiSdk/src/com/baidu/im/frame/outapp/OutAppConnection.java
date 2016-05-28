package com.baidu.im.frame.outapp;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.baidu.im.frame.MessageDataEnum;
import com.baidu.im.frame.SeqDispatcher2InApp;
import com.baidu.im.frame.SequenceSender;
import com.baidu.im.frame.NetworkChannel.NetworkChannelStatus;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.StringUtil;
import com.baidu.im.outapp.OutAppApplication;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

/**
 * 接收并处理来自于in-app部分的消息。可以与多个in-app建立连接 ，N:1模式。
 * 
 * 
 */

public class OutAppConnection implements InAppSender {

    public static final String TAG = "OutAppConnection";
    private Router mRouter = null;
    private AppMap mAppMap = null;
    private SequenceSender mNetwork = null;
    private SeqDispatcher2InApp mSeq2InApp = new SeqDispatcher2InApp();

    /**
     * Server端持有的messenger。
     */
    private Messenger serverMessenger = new Messenger(handler);

    private static boolean isBind = false;

    public OutAppConnection(Router router, AppMap appMap, SequenceSender network) {
        mRouter = router;
        mAppMap = appMap;
        mNetwork = network;
    }

    public SeqDispatcher2InApp getSeqDisp() {
        return mSeq2InApp;
    }

    public IBinder getBinder() {
        isBind = true;
        LogUtil.printMainProcess("dynamicLoader1 intent=" + serverMessenger.getBinder());
        return serverMessenger.getBinder();
    }

    public void onUnbind() {
        // mRouter.unRegister(0);
    	  Collection<ClientHandler> handlers = mRouter.getAllMessengers();
          if (handlers != null) {
              Iterator<ClientHandler> it = handlers.iterator(); // 获得一个迭代子
              while (it.hasNext()) {
                  ClientHandler clientHandler = (ClientHandler) it.next(); // 得到下一个元素
                  if (clientHandler != null && clientHandler.getMessenger() != null)
                  {
                	  byte[] data = new byte[1];
                      send(clientHandler.getClientId(), data, MessageDataEnum.CHECKOFFLINE.getType(), clientHandler.getMessenger());
                  }
              }
          }
        isBind = false;
    }

    public boolean isBind() {
        return isBind;
    }

    public byte[] getNetStatus() {
        return mNetwork.getNetworkChannelStatus().name().getBytes();
    }

    /**
     * Send a network_change msg to all InApp
     */

    @Override
    public void sendNetworkChange(byte[] data, Messenger msger) {

    	/*
    	if(OutAppApplication.getInstance() != null && OutAppApplication.getInstance().getNetworkLayer() != null) {
    		OutAppApplication.getInstance().getNetworkLayer().networkChanged();
    	}*/
        if (msger == null) {

            Collection<Messenger> msgersUnKnownRegs = mAppMap.getUnknowMsger();
            if (msgersUnKnownRegs != null) {
                Iterator<Messenger> it = msgersUnKnownRegs.iterator(); // 获得一个迭代子
                while (it.hasNext()) {
                    Messenger clientMsger = (Messenger) it.next(); // 得到下一个元素
                    if (clientMsger != null) {
                        send(0, data, MessageDataEnum.NETWORK_CHANGE.getType(), clientMsger);
                    }
                }
            }

            Collection<Messenger> msgersUnRegs = mAppMap.getAllMessengers();
            if (msgersUnRegs != null) {
                Iterator<Messenger> it = msgersUnRegs.iterator(); // 获得一个迭代子
                while (it.hasNext()) {
                    Messenger clientMsger = (Messenger) it.next(); // 得到下一个元素
                    if (clientMsger != null) {
                        send(0, data, MessageDataEnum.NETWORK_CHANGE.getType(), clientMsger);
                    }
                }
            }

            Collection<ClientHandler> handlers = mRouter.getAllMessengers();
            if (handlers != null) {
                Iterator<ClientHandler> it = handlers.iterator(); // 获得一个迭代子
                while (it.hasNext()) {
                    ClientHandler clientHandler = (ClientHandler) it.next(); // 得到下一个元素
                    if (clientHandler != null && clientHandler.getMessenger() != null)
                        send(0, data, MessageDataEnum.NETWORK_CHANGE.getType(), clientHandler.getMessenger());
                }
            }
        } else {
            send(0, data, MessageDataEnum.NETWORK_CHANGE.getType(), msger);
        }
    }

    @Override
    public void send(int seq, int appId, byte[] data) {

        Messenger msger = this.getSeqDisp().getMessenger(seq);
        if (mRouter.getClientHandler(appId) == null) {

            Messenger clientMessenger = mAppMap.getMessgenger(String.valueOf(seq));

            if (clientMessenger != null && appId != 0) {
                mAppMap.addAppId(String.valueOf(seq), String.valueOf(appId));
                mRouter.register(appId, clientMessenger);
                mAppMap.removeMsger(clientMessenger);
                LogUtil.printMainProcess(TAG, "receive aidl message. appId=" + appId + ",  register it.");
            }
        }

        send(appId, data, MessageDataEnum.NORMAL.getType(), msger);
    }

    public void send(int appId, byte[] data, String messgeType, Messenger clientMessenger) {

        if (StringUtil.isStringInValid(messgeType)) {
            return;
        }

       // LogUtil.printMainProcess(TAG, "sendMessage: first 1 AppId = " + appId);

        android.os.Message message = Message.obtain();

        Bundle bundle = new Bundle();
        if (data != null) {
            // Send normal msg
            bundle.putByteArray(messgeType, data);
        }
        message.setData(bundle);
       // LogUtil.printMainProcess(TAG, "sendMessage: first 2AppId = " + appId);
        Messenger messenger = clientMessenger;
        if (messenger == null) {
            // LogUtil.printMainProcess(TAG, "sendMessage: first 3 AppId = " + appId);
            messenger = mRouter.getMessenger(appId);
        }
        if (messenger != null) {
            try {
                // LogUtil.printMainProcess(TAG, "sendMessage: ppId = " + appId);
                messenger.send(message);
                // LogUtil.printMainProcess(TAG, "sendMessage: first  5 AppId = " + appId);
                LogUtil.printMainProcess(TAG, "sendMessage: messenger = " + messenger.hashCode());

                // LogUtil.printMainProcess(TAG, "sendMessage: first 6 AppId = " + appId);
            } catch (RemoteException e) {
                LogUtil.printMainProcess(TAG, "sendMessage error,clientId = " + appId + e.getMessage());
                // LogUtil.printMainProcess(TAG, "sendMessage: first 7 AppId = " + appId);
                mRouter.unRegister(appId);
                mAppMap.removeMsger(messenger);
                OutAppApplication.getInstance().getTransactionFlow().outAppSetAppOffline(appId);
            }
        } else {
            LogUtil.printMainProcess(TAG, "sendMessage: can not find client messenger. AppId = " + appId);
        }

    }

    /**
     * 由service主线程获得的handler，用于处理aidl消息。
     */
    private static Handler handler = new Handler() {
        public void handleMessage(Message message) {
            if (OutAppApplication.getInstance().getOutAppConnection() != null) {
                receive(message);
            }
        }
    };

    private static void receive(Message message) {

        if (message == null) {
            return;
        }

        if (OutAppApplication.getInstance().getContext() == null) {
            return;
        }
        // if (message == null) {
        // LogUtil.printMainProcess(TAG, "receive a null aidl message.");
        // return;
        // }

        Messenger clientMessenger = message.replyTo;

        /*
         * if (clientMessenger != null) { int clientId = 0; if
         * (OutAppApplication.getInstance().getRouter().getClientHandler(clientId) == null) { isBind = true;
         * OutAppApplication.getInstance().getRouter().register(clientId, clientMessenger);
         * LogUtil.printMainProcess(TAG, "receive aidl message. clientId=" + clientId + ",  register it."); } }
         */
        // Get data
        Bundle bundle = message.getData();
        // Receive network change msg
        if (bundle.containsKey(MessageDataEnum.NETWORK_CHECK.getType())) {
        	
        	byte[] data = OutAppApplication.getInstance().getOutAppConnection().getNetStatus();
        	NetworkChannelStatus networkChannelStatus =
                    NetworkChannelStatus.valueOf(new String(data));
        	if(networkChannelStatus == NetworkChannelStatus.Connected)
        	{
        		String channelKey =  OutAppApplication.getInstance().getChannelKey();
        		if(channelKey == null)
        		{
        			LogUtil.i(TAG, "impossible in channelkey");
        		}else
        		{
        		 LogUtil.i(TAG, "channelkey send");
        		 send(MessageDataEnum.CHANNELKEYRECEIVE.getType(),
        				 channelKey.getBytes(),
        				 clientMessenger);
        		}
        	}
            OutAppApplication
                    .getInstance()
                    .getOutAppConnection()
                    .sendNetworkChange(data,clientMessenger);
            OutAppApplication.getInstance().getAppMap().addUnknowMsger(clientMessenger);
            return;
        }

        if (bundle.containsKey(MessageDataEnum.Reconnect.getType())) {
            LogUtil.printMainProcess("Reconnect is called");
            if(OutAppApplication.getInstance() != null && OutAppApplication.getInstance().getNetworkLayer() != null) {
        		OutAppApplication.getInstance().getNetworkLayer().networkChanged();
        	}
            //OutAppApplication.getInstance().getNetworkLayer().reConnect();
            return;
        }

        if (bundle.containsKey(MessageDataEnum.SEQFETCH.getType())) {
        	send(MessageDataEnum.SEQFETCH.getType(),OutAppApplication.getInstance().getInSeq(),clientMessenger);
            return;
        }
        /*
         * if(bundle.containsKey(MessageDataEnum.BIND.getType())) { String appKey =
         * bundle.getString(MessageDataEnum.BIND.getType()); int seq = bundle.getInt(MessageDataEnum.BINDSEQ.getType());
         * OutAppApplication.getInstance().getAppMap().addSeq2AppKey(String.valueOf(seq), appKey,clientMessenger); }
         */

        // Receive normal msg
        if (bundle.containsKey(MessageDataEnum.NORMAL.getType())) {
            byte[] data = bundle.getByteArray(MessageDataEnum.NORMAL.getType());
            String apiKey = bundle.getString(MessageDataEnum.APPKEY.getType());

            try {
                UpPacket upPacket = UpPacket.parseFrom(data);

                // if (!upPacket.getMethodName().equals(MethodNameEnum.PushConfirm.name()))
                {
                    OutAppApplication.getInstance().getOutAppConnection().getSeqDisp()
                            .addSeqDispatch(upPacket.getSeq(), clientMessenger);
                }
                if (upPacket.getAppId() == 0 && upPacket.getMethodName().equals(MethodNameEnum.RegApp.name())) {
                    OutAppApplication.getInstance().getAppMap()
                            .addSeq2AppKey(String.valueOf(upPacket.getSeq()), apiKey, clientMessenger);
                } else if (upPacket.getAppId() != 0) {
                    OutAppApplication.getInstance().getRouter().register(upPacket.getAppId(), clientMessenger);
                    OutAppApplication.getInstance().getAppMap().addAppId(apiKey, upPacket.getAppId());
                    OutAppApplication.getInstance().getAppMap().removeMsger(clientMessenger);
                }

                OutAppApplication.getInstance().getOutAppConnection().receive(upPacket);
            } catch (InvalidProtocolBufferMicroException e) {
                LogUtil.printError(e);
            }
        }
    }

    private void receive(UpPacket upPacket) {
        if (upPacket == null) {
            return;
        }
        try {
            mNetwork.send(upPacket);
        } catch (IOException e) {
            LogUtil.printError(e);
        } catch (Exception e) {
            LogUtil.printError(e);
        }
    }

    public void initialize() {

    }

    public void destroy() {
    }

    @Override
    public void send(DownPacket downPacket) {
        if (downPacket == null) {
            return;
        }

        this.mAppMap.addAppId(String.valueOf(downPacket.getSeq()), String.valueOf(downPacket.getAppId()));
        // LogUtil.printMainProcess("Receive messge @@@@@START@@@@@");
        send(downPacket.getSeq(), downPacket.getAppId(), downPacket.toByteArray());
        LogUtil.printMainProcess("Receive messge E N D");
    }
    

    public static void  send(String type,int data, Messenger msger) {
    	android.os.Message msg = Message.obtain();

        Bundle bundle2 = new Bundle();
        bundle2.putInt(type, data);
        msg.setData(bundle2);
		try {
			msger.send(msg);
		} catch (RemoteException e) {
			LogUtil.e(TAG, e);
		}
    }
    
    public static void  send(String type, byte[] data, Messenger msger) {
    	android.os.Message msg = Message.obtain();

        Bundle bundle2 = new Bundle();
        bundle2.putByteArray(type, data);
        msg.setData(bundle2);
		try {
			msger.send(msg);
		} catch (RemoteException e) {
			LogUtil.e(TAG, e);
		}
    }
    
    public void sendChannelKey(byte[] data, Messenger msger) {

    	LogUtil.i(TAG, "send channelkey to inapp");
        if (msger == null) {

            Collection<Messenger> msgersUnKnownRegs = mAppMap.getUnknowMsger();
            if (msgersUnKnownRegs != null) {
                Iterator<Messenger> it = msgersUnKnownRegs.iterator(); // 获得一个迭代子
                while (it.hasNext()) {
                    Messenger clientMsger = (Messenger) it.next(); // 得到下一个元素
                    if (clientMsger != null) {
                        send(0, data, MessageDataEnum.CHANNELKEYRECEIVE.getType(), clientMsger);
                    }
                }
            }

            Collection<Messenger> msgersUnRegs = mAppMap.getAllMessengers();
            if (msgersUnRegs != null) {
                Iterator<Messenger> it = msgersUnRegs.iterator(); // 获得一个迭代子
                while (it.hasNext()) {
                    Messenger clientMsger = (Messenger) it.next(); // 得到下一个元素
                    if (clientMsger != null) {
                        send(0, data, MessageDataEnum.CHANNELKEYRECEIVE.getType(), clientMsger);
                    }
                }
            }

            Collection<ClientHandler> handlers = mRouter.getAllMessengers();
            if (handlers != null) {
                Iterator<ClientHandler> it = handlers.iterator(); // 获得一个迭代子
                while (it.hasNext()) {
                    ClientHandler clientHandler = (ClientHandler) it.next(); // 得到下一个元素
                    if (clientHandler != null && clientHandler.getMessenger() != null)
                        send(0, data, MessageDataEnum.CHANNELKEYRECEIVE.getType(), clientHandler.getMessenger());
                }
            }
        } else {
            send(0, data, MessageDataEnum.CHANNELKEYRECEIVE.getType(), msger);
        }
    }

}
