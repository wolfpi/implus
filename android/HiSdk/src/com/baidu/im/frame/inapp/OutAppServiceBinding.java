package com.baidu.im.frame.inapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.baidu.im.constant.Constant;
import com.baidu.im.frame.GlobalTimerTasks;
import com.baidu.im.frame.MessageDataEnum;
import com.baidu.im.frame.NetworkChannel.NetworkChannelStatus;
import com.baidu.im.frame.ServiceControlUtil;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceKey;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.frame.utils.ToastUtil;
import com.baidu.im.sdk.OutAppService;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

/**
 * Bind and unbind to the remote service. This demonstrates the implementation of a service which the client will bind
 * to, interacting with it through an aidl interface.
 */

public class OutAppServiceBinding {

    public static final String TAG = "OutAppServiceBinding";

    /** The primary interface we will be calling on the service. */
    private Messenger serverMessenger = null;
    private Messenger clientMessenger = null;
    // private static MessageListener mMsgListener = null;

    private boolean mIsBound;
    private PreferenceUtil mPref = null;
    private static HandlerThread messengerThread;
    static Handler handler;
    final static Handler.Callback handlerCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            LogUtil.printMainProcess("inapp receive from outapp, message=" + message);
            receive(message);
            return true;
        }
    };

    public void initialize(PreferenceUtil pref) {
        synchronized(handlerCallback) {
            if (messengerThread == null) {
                messengerThread = new HandlerThread("OutAppServiceBinding");
                messengerThread.start();
                handler = new Handler(messengerThread.getLooper(), handlerCallback);
            }
        }
        clientMessenger = new Messenger(handler);
        bind();
        mPref = pref;
    }

    public OutAppServiceBinding(MessageListener msgListener) {
        // mMsgListener = msgListener;
        InAppApplication.getInstance().getBizThread().setListener(msgListener);
    }

    /**
     * Class for interacting with the interface of the service.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            serverMessenger = new Messenger(service);
            LogUtil.printMainProcess("onServiceConnected. serverMessenger = " + serverMessenger);
            ToastUtil.toast("Connected remote service.");

            LogUtil.e(TAG, "check network is called in connection");
            // 连接上需要检测网络状态
            try {
            	//fetchSeqRange();
            	fetchSeqRange();
                checkNetworkChannelStatus();
            } catch (RemoteException e) {
                LogUtil.printMainProcess("onServiceConnected. Can not send checkNetworkChannelStatus message");
                e.printStackTrace();
            }

        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            serverMessenger = null;
            ToastUtil.toast("Remote service disconnected");
            
            SeqGenerator.reset();

            // 通知上层连接变化
            try {
                InAppApplication.getInstance().getSession()
                        .networkChannelStatusChanged(NetworkChannelStatus.Disconnected);
            } catch (RuntimeException e) {
                LogUtil.e(TAG, e);
            }

            GlobalTimerTasks.getInstance().removeAllTask();
            bind();
        }
    };

    public void bind() {

        // Establish a couple connections with the service, binding by interface
        // names. This allows other applications to be installed that replace
        // the remote service by implementing the same interface.
    	
    	Intent startintent = new Intent(InAppApplication.getInstance().getContext(), OutAppService.class);
    	startintent.setAction("com.baidu.im.sdk.service");
    	
    	if(ServiceControlUtil.showInSeperateProcess(InAppApplication.getInstance().getContext())) {
            try {
                InAppApplication.getInstance().getContext().startService(startintent);
            } catch (Exception e) {
                LogUtil.printImE(TAG, "fail to startService", e);
            }
        }
    	
        //Intent intent = new Intent(InAppApplication.getInstance().getContext(), OutAppService.class);
       
    	
    	boolean result =
                InAppApplication.getInstance().getContext().bindService(startintent, mConnection, Context.BIND_AUTO_CREATE);

        LogUtil.printMainProcess("Service bind. reslut = " + result);
        LogUtil.printMainProcess("bind. serverMessenger = " + serverMessenger);
        mIsBound = result;
    }

    public void unbind() {
        if (mIsBound) {

            // Detach our existing connection.
            InAppApplication.getInstance().getContext().unbindService(mConnection);
            mIsBound = false;
            SeqGenerator.reset();
        }
    }

    public void destroy() {
        unbind();
    }

    /**
     * 发送消息给OutAppService
     */
    public void send(UpPacket upPacket) throws RemoteException {

        if (upPacket == null)
            return;

        Message message = Message.obtain(handler);
        message.replyTo = clientMessenger;
        Bundle data = new Bundle();

        data.putByteArray(MessageDataEnum.NORMAL.getType(), upPacket.toByteArray());
        data.putString(MessageDataEnum.APPKEY.getType(), mPref.getString(PreferenceKey.apiKey));
        // data.putInt(APPID, mPref.getInt(PreferenceKey.appId));

        message.setData(data);
        send(message);
    }

    /**
     * 发送消息给OutAppService
     */
    public void sendAlive() throws RemoteException {

        Message message = Message.obtain(handler);
        message.replyTo = clientMessenger;
        Bundle data = new Bundle();

        data.putString(MessageDataEnum.ALIVE.getType(), "");
        message.setData(data);
        send(message);
    }

    public void sendReconnect() {

        Message message = Message.obtain(handler);
        message.replyTo = clientMessenger;
        Bundle data = new Bundle();

        data.putString(MessageDataEnum.Reconnect.getType(), "");
        message.setData(data);
        try {
            send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息给OutAppService
     */
    /*
     * public void sendApiKey(String apiKey, int seq) throws RemoteException {
     * 
     * Message message = Message.obtain(handler); message.replyTo = clientMessenger; Bundle data = new Bundle();
     * 
     * data.putString(MessageDataEnum.BIND.getType(), apiKey); data.putInt(MessageDataEnum.BINDSEQ.getType(), seq);
     * message.setData(data); send(message); }
     */
    
    public void fetchSeqRange() throws RemoteException {

        LogUtil.printMainProcess("fetchSeqRange is send");
        Message message = Message.obtain(handler);
        message.replyTo = clientMessenger;
        Bundle data = new Bundle();

        data.putByteArray(MessageDataEnum.SEQFETCH.getType(), new byte[0]);
        message.setData(data);
        send(message);
    }

    /**
     * 发消息给OutAppService，检查网络状态
     */
    public void checkNetworkChannelStatus() throws RemoteException {

        LogUtil.printMainProcess("Check network status is send");
        Message message = Message.obtain(handler);
        message.replyTo = clientMessenger;
        Bundle data = new Bundle();

        data.putByteArray(MessageDataEnum.NETWORK_CHECK.getType(), new byte[0]);
        message.setData(data);
        send(message);
    }

    private void send(Message message) throws RemoteException {
        LogUtil.printMainProcess("send. serverMessenger = " + serverMessenger);
        if (mIsBound && null != serverMessenger) {
            try {
                serverMessenger.send(message);
            } catch (Exception e) {
                bind();
                e.printStackTrace();
            }
        } else {
            LogUtil.e(TAG, "send message in unbind service, retry it late");
            if (!mIsBound) {
            	LogUtil.printMainProcess("force bind 1 time");
                bind();
            }
        }
    }

    private static void receive(Message message) {
        // Get bundle
        Bundle bundle = message.getData();
        if (null != bundle) {
            // Receive normal message
            if (null != InAppApplication.getInstance().getInAppConnection()) {
                byte[] normalData = bundle.getByteArray(MessageDataEnum.NORMAL.getType());

                if (null != normalData && normalData.length != 0) {
                    try {
                        DownPacket downPacket = DownPacket.parseFrom(normalData);
                        //OfflineMsgHelper.showOfflineNotificaiton(InAppApplication.getInstance().getContext(), downPacket);
                        LogUtil.printMainProcess(TAG, "Receive NORMAL msg: " + downPacket);
                        InAppApplication.getInstance().getBizThread().addMessage(downPacket);
                    } catch (InvalidProtocolBufferMicroException e) {
                        LogUtil.printError(e);
                    } catch (RuntimeException e) {
                        LogUtil.e(TAG, "error", e);
                    }
                } else {
                   // LogUtil.printMainProcess(TAG, "Receive a message without normal data.");
                }
            }
            // Receive network change message
            if (null != InAppApplication.getInstance().getSession()) {
                byte[] networkData = bundle.getByteArray(MessageDataEnum.NETWORK_CHANGE.getType());
                if (null != networkData && networkData.length != 0) {
                    try {
                        NetworkChannelStatus networkChannelStatus =
                                NetworkChannelStatus.valueOf(new String(networkData));
                        LogUtil.printMainProcess(TAG, "Receive NETWORK_CHANGE msg: " + networkChannelStatus);
                        InAppApplication.getInstance().getSession().networkChannelStatusChanged(networkChannelStatus);

                    } catch (RuntimeException e) {
                        LogUtil.printError(e);
                    }
                } else {
                   // LogUtil.printMainProcess(TAG, "receive a message without NETWORK data.");
                }
            }
            
            if (null != InAppApplication.getInstance().getSession()) {
            	int seqStart = bundle.getInt(MessageDataEnum.SEQFETCH.getType());
                if (seqStart != 0) {
                    try {
                    	SeqGenerator.setSeqRange(seqStart, seqStart+ Constant.seqSize);
                        LogUtil.printMainProcess(TAG, "Receive seq msg: " + seqStart);
                     
                    } catch (RuntimeException e) {
                        LogUtil.printError(e);
                    }
                } else {
                    //LogUtil.printMainProcess(TAG, "receive a message without seq data.");
                }
            }
            
            if (null != InAppApplication.getInstance().getSession()) {
            	byte[] networkData = bundle.getByteArray(MessageDataEnum.CHANNELKEYRECEIVE.getType());
                if (null != networkData && networkData.length >10) {
                    try {
                    	String channelkey = new String(networkData);
                    	String olderChannelkey = InAppApplication.getInstance().getSession().getChannel().getChannelKey();
                    	if(!channelkey.equals(olderChannelkey))
                    	{
                    		InAppApplication.getInstance().getSession().getChannel().setChannelKey(channelkey);
                    		new Thread(new Runnable() {
                    			 
                    			@Override
                    			public void run() {
                    				
                            		InAppApplication.getInstance().getTransactionFlow().resend();
                    			}
                    		}).start();
                    		
                    	}
                        LogUtil.printMainProcess(TAG, "Receive channelKey msg: " + channelkey);
                     
                    } catch (RuntimeException e) {
                        LogUtil.printError(e);
                    }
                } else {
                   // LogUtil.printMainProcess(TAG, "receive a message without CHANNELKEYRECEIVE data.");
                }
            }
            
        } else {
            LogUtil.e(TAG, "Receive a message with nothing.");
        }
    }

    
   
    /*
    private static void receive(Message message) {
        // Get bundle
        Bundle bundle = message.getData();
        if (null != bundle) {
            // Receive normal message
            if (null != InAppApplication.getInstance().getInAppConnection()) {
                byte[] normalData = bundle.getByteArray(MessageDataEnum.NORMAL.getType());

                if (null != normalData && normalData.length != 0) {
                    try {
                        DownPacket downPacket = DownPacket.parseFrom(normalData);
                        LogUtil.printMainProcess(TAG, "Receive NORMAL msg: " + downPacket);
                        mMsgListener.receive(downPacket);
                    } catch (InvalidProtocolBufferMicroException e) {
                        LogUtil.fError(Thread.currentThread(), e);
                    } catch (RuntimeException e) {
                        LogUtil.e(TAG, "error", e);
                    }
                } else {
                    LogUtil.printMainProcess(TAG, "Receive a message without normal data.");
                }
            }
            // Receive network change message
            if (null != InAppApplication.getInstance().getSession()) {
                byte[] networkData = bundle.getByteArray(MessageDataEnum.NETWORK_CHANGE.getType());
                if (null != networkData && networkData.length != 0) {
                    try {
                        NetworkChannelStatus networkChannelStatus =
                                NetworkChannelStatus.valueOf(new String(networkData));
                        LogUtil.printMainProcess(TAG, "Receive NETWORK_CHANGE msg: " + networkChannelStatus);
                        InAppApplication.getInstance().getSession().networkChannelStatusChanged(networkChannelStatus);

                    } catch (RuntimeException e) {
                        LogUtil.fError(Thread.currentThread(), e);
                    }
                } else {
                    LogUtil.printMainProcess(TAG, "receive a message without NETWORK data.");
                }
            }
        } else {
            LogUtil.e(TAG, "Receive a message with nothing.");
        }
    }*/

    /**
     * Send text message to HiCore Service.
     * 
     * @param message
     * @param callback
     */
    // private void sendMessage(final LoginMessage message,
    // final IMessageCallback callback) {
    //
    // // get messageId
    // final int transactionId = message.hashCode();
    //
    // // Cache the message and the callback in memory.
    // InAppApplication.getInstance().getMessageCenter()
    // .cacheSendingMessage(transactionId, message, callback);
    //
    // }

}
