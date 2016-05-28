package com.baidu.im.frame.inapp;

import java.io.IOException;

import android.os.RemoteException;

import com.baidu.im.frame.NetworkChannel.NetworkChannelStatus;
import com.baidu.im.frame.NotifyListener;
import com.baidu.im.frame.SequenceDispatcher;
import com.baidu.im.frame.SequenceSender;
import com.baidu.im.frame.pb.EnumPacketType;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.frame.utils.ProtobufLogUtil;
import com.baidu.im.inapp.transaction.message.processor.ReceiveMessageProsessor;

/**
 * In-app部分与out-app部分的接口类。
 * 
 * @author zhaowei10
 * 
 */
public class InAppConnection implements SequenceSender, MessageListener {

    public static final String TAG = "InAppConnection";
    private OutAppServiceBinding outAppServiceBinding;
    private SequenceDispatcher sequenceDispatcher;
    private NotifyListener		mNotifyListener;
    private PreferenceUtil		mPref = null;

    public void initialize(PreferenceUtil pref) {
        LogUtil.printMainProcess("InAppClient initializing...");

        mPref = pref;
        outAppServiceBinding = new OutAppServiceBinding(this);
        outAppServiceBinding.initialize(mPref);

        sequenceDispatcher = new SequenceDispatcher();
        mNotifyListener    = new ReceiveMessageProsessor(mPref);  
    }

    public void receive(DownPacket downPacket) {
    	
    	LogUtil.printMainProcess(TAG, "receive a good Packet downPacket............>>>>>>>>>>>");
        
    	if (downPacket == null || downPacket.getBizPackage() == null) {
            LogUtil.printMainProcess(TAG, "receive a null downPacket");
            return;
        }
        int packetType = 0;
        if(downPacket.getBizPackage() != null){
            packetType = downPacket.getBizPackage().getPacketType(); // prevent null pointer exception
        }else{
            LogUtil.e(TAG, "BizPackage is Null!");
        }
        if (EnumPacketType.NOTIFYCATION == packetType) {
        	if(mNotifyListener != null)
        	{
        		mNotifyListener.receiveNotify(downPacket);
        	}
        	LogUtil.printMainProcess( "N packet......>>>>>");
            return;
        } else if (sequenceDispatcher.dispatch(downPacket)) {
        	LogUtil.printMainProcess( "downPacket is dispatched......>>>>>");
            return;
        }

        LogUtil.printMainProcess(TAG, "Receive a unknown downPacket = \n" + ProtobufLogUtil.print(downPacket));

    }

    /**
     * Send to out-app.
     * 
     * @param upPacket
     * @throws IOException
     * @throws RemoteException
     */
    public void send(UpPacket upPacket) throws RemoteException {
        outAppServiceBinding.send(upPacket);
    }
    
    public void sendAlive()
    {
    	try {
			outAppServiceBinding.sendAlive();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    }
    
    /*
    public void sendBind(String apiKey,int seq)
    {
    	try {
			//outAppServiceBinding.sendApiKey(apiKey, seq);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    }*/

    public SequenceDispatcher getSequenceDispatcher() {
        return sequenceDispatcher;
    }

    public void destroy() {
    }

	@Override
	public NetworkChannelStatus getNetworkChannelStatus() {
		return null;
	}

	@Override
	public void sendReconnect() {
		outAppServiceBinding.sendReconnect();
	}

}
