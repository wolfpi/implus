package com.baidu.im.frame.outapp;

import java.io.IOException;

import com.baidu.im.constant.Constant.EChannelType;
import com.baidu.im.frame.INetworkChannelListener;
import com.baidu.im.frame.NetworkChannel;
import com.baidu.im.frame.NetworkChannel.NetworkChannelStatus;
import com.baidu.im.frame.SequenceDispatcher;
import com.baidu.im.frame.SequenceSender;
import com.baidu.im.frame.pb.EnumPacketType;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.utils.ConfigUtil;
import com.baidu.im.frame.utils.FileUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.NetworkUtil;
import com.baidu.im.frame.utils.NetworkUtil.NetworkType;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.frame.utils.ProtobufLogUtil;
import com.baidu.im.outapp.OutAppApplication;
import com.baidu.im.outapp.OutAppConfig;
import com.baidu.im.outapp.network.HiChannel;

/**
 * 网络层，用于发送和接收消息。封装了网络通道、重试、超时，网络超时会抛出异常。
 * 
 * @author zhaowei10
 * 
 */
public class NetworkLayer implements SequenceSender,OutMessagListener {

    public static final String TAG = "NetworkLayer";

    /**
     * Instance one of the channels.
     */
    private NetworkChannel networkChannel;
    private SequenceDispatcher sequenceDispatcher;
    private NetworkType networkType = NetworkType.broken;
    private InAppSender mInAppSender = null;

    /**
     * @throws IOException
     * 
     */
    public NetworkLayer(OutAppConfig outAppConfig, PreferenceUtil preference) throws IOException {

        if (preference == null)
            throw new RuntimeException("preference can not be null");

        String ip = ConfigUtil.getHostIp();
        int port = ConfigUtil.getPort();

        EChannelType channelType = ConfigUtil.getChannelType();
        if (ip == null || channelType == null)
            return;

        LogUtil.printMainProcess("start to initialize network channel. " + channelType.name() + "(" + ip + ":" + port
                + ")");
        switch (channelType) {
            case localMock:
                break;
            default:
                networkChannel = new HiChannel(outAppConfig, ip, port, preference, this);
                break;
        }
        sequenceDispatcher = new SequenceDispatcher();
    }

    public void Initialized(InAppSender inAppSender)
    {
    	mInAppSender = inAppSender;
        if (networkChannel != null) {

            networkChannel.setNetworkChannelListener(new INetworkChannelListener() {
                @Override
                public void onChanged(NetworkChannelStatus networkChannelStatus) {
                    try {
                        OutAppApplication.getInstance().getOutAppConnection().sendNetworkChange(getNetworkChannelStatus().name().getBytes(),null);
                    } catch (RuntimeException e) {
                        LogUtil.e(TAG, e);
                    }
                }
            });
        }
    }
    
    public NetworkChannelStatus getNetworkChannelStatus() {
    	if(networkChannel == null)
    	{
    		LogUtil.printIm(TAG, "networkchannel status can not be null");
    		return NetworkChannelStatus.Disconnected;
    	}else
    	{
    		return networkChannel.getNetworkChannelStatus();
    	}
    }

    public void reConnect() {
    	LogUtil.i(TAG, "Channel Reconnect");
    	if(networkChannel != null)
    	{
    		if(NetworkUtil.isNetworkConnected(OutAppApplication.getInstance().getContext()))
    		{
    			//networkChannel.networkChanged(0);
    			try {
    				Thread.sleep(10);
    			} catch (InterruptedException e) {
    				LogUtil.e(TAG, e);
    			}
    			networkChannel.networkChanged(1);
    		}else
    		{
    			networkChannel.networkChanged(0);
    		}
    		
    	}else
    	{
    		LogUtil.printIm(TAG, "Channel reconnect error");
    	}
    }

    public void send(UpPacket upPacket) throws IOException {
        if (upPacket != null) {
            LogUtil.printProtocol("发送-------->\r\n" + ProtobufLogUtil.print(upPacket) + "\r\nlen="
                    + upPacket.toByteArray().length);
            if (networkChannel != null) {
                networkChannel.send(upPacket);
            }
        }
    }

    public int getSeq() {
    	if(networkChannel != null)
    	{
    		return networkChannel.getSeq();
    	}
    	else
    		return 0;
    }

    public SequenceDispatcher getSequenceDispatcher() {
        return sequenceDispatcher;
    }

    public void heartbeat() {
    	if(networkChannel != null)
    		networkChannel.heartbeat();
    }

    /**
     * 接收消息。
     * 
     * @param downPacket
     */
    
    //static int send = 0;
    @Override
    public void onReceive( DownPacket downPacket) {

        if (downPacket == null || downPacket.getBizPackage() == null) {
            return;
        }
        LogUtil.printProtocol("收包<--------\r\n" + ProtobufLogUtil.print(downPacket));

        /*
        if(++send == 8)
        {
        	OutAppApplication.getInstance().getTransactionFlow().outAppSetAppOffline(downPacket.getAppId());
        	return ;
        }
        if (EnumPacketType.NOTIFYCATION == downPacket.getBizPackage().getPacketType()) {
        	
        	if(send > 12)
             OutAppApplication.getInstance().getTransactionFlow().outAppShowOffLineMessage(downPacket);
        	return;
        }*/
        
        if (sequenceDispatcher.dispatch(downPacket)) {
            return;
        } else {
        	 if(mInAppSender != null)
             {
          	   mInAppSender.send(downPacket);
             }
             else
             {
          	   LogUtil.printIm(TAG, "Inappsender can not be null");
             }
        	 
            if (EnumPacketType.NOTIFYCATION == downPacket.getBizPackage().getPacketType()) {
            	if(OutAppApplication.getInstance().getRouter().getClientHandler(downPacket.getAppId()) == null) 
            			//|| !AppStatusUtil.isRunning(OutAppApplication.getInstance().getContext()))
            	{
            		OutAppApplication.getInstance().getTransactionFlow().outAppShowOffLineMessage(downPacket);
            	}
            }
          
        }
    }

    public void destroy() {
        if (networkChannel != null) {
            networkChannel.close();
        }
    }

    public void saveProtocolFile(DownPacket downPacket) {
    	
    	if(downPacket == null)
    		return ;
        FileUtil.saveBytesToFileInSdkFolder("downPacket_" + "_seq(" + downPacket.getSeq() + ")_channelCode("
                + downPacket.getChannelCode() + ")_bizCode(" + downPacket.getBizPackage().getBizCode() + ")",
                downPacket.toByteArray());

        FileUtil.saveBytesToFileInSdkFolder("downPacket_" + "_seq(" + downPacket.getSeq() + ")_channelCode("
                + downPacket.getChannelCode() + ")_bizCode(" + downPacket.getBizPackage().getBizCode() + ")_Context",
                ProtobufLogUtil.print(downPacket).getBytes());
    }

    NetworkChannel getNetworkChannel() {
        return networkChannel;
    }

    void setNetworkChannel(NetworkChannel networkChannel) {
        this.networkChannel = networkChannel;
    }

    public void networkChanged() {
    	LogUtil.i(TAG, "networkchanged is called");
        try {
            NetworkType currentNetworkType = NetworkUtil.getNetworkType(OutAppApplication.getInstance().getContext());

            switch (currentNetworkType) {
                case broken:
                    // 如果有网络变为无网络，则调用底层接口
                    if (networkType != currentNetworkType) {
                        networkChannel.networkChanged(0);
                    }
                    break;

                default:
                    // 如果之前无网络,则通知网络可以用
                    if (networkType == NetworkType.broken) {
                        networkChannel.networkChanged(1);
                    } else if (networkType != currentNetworkType) {
                        // 如果有网络，但是网络类型改变，则需要通知重连
                        networkChannel.networkChanged(0);
                        Thread.sleep(10);
                        networkChannel.networkChanged(1);
                    }
                    break;
            }
            networkType = currentNetworkType;
            if( currentNetworkType != NetworkType.broken ) {
            	networkChannel.networkChanged(1);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "networkChanged error.", e);
        }
    }

    public void dumpChannel() {
    	if(networkChannel != null)
    		networkChannel.dump();
    }

	@Override
	public void sendReconnect() {
		networkChanged();
	}

	@Override
	public void onChannelKeyRecv(String channelKey) {
		if(mInAppSender != null)
		{
			mInAppSender.sendChannelKey(channelKey.getBytes(), null);
		}
	}
}
