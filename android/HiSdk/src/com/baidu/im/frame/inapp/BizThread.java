package com.baidu.im.frame.inapp;

import java.util.LinkedList;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.utils.LogUtil;

public class BizThread implements Runnable{
	
	private  LinkedList<DownPacket>  mServerMessages = new LinkedList<DownPacket>();
	//private  Object  mEvent = new Object();
	private static MessageListener mMsgListener = null;
    private final static String TAG = "BizThread";
	private  boolean   mStop = false;
	private boolean    mRunning = false;
	
	@Override
	public void run() {
		LogUtil.i(TAG, "bizthead is running");
		mStop = false;
		while(mStop ==false) {
			int size = 0;
			synchronized(this) {
				size = mServerMessages.size();
				if(size >0) {
					DownPacket msg = null;
					  {
						msg = mServerMessages.getFirst();
						mServerMessages.removeFirst();
					  }
					  LogUtil.i(TAG, "processing receiving downpacket");
					  receive(msg);
					}
				else {
					try {
						 synchronized(this) {
							 LogUtil.i(TAG, "wait for downpacket");
							 wait();
							 LogUtil.i(TAG, "start process downpacket");
						 }
					} catch (InterruptedException e) {
						e.printStackTrace();
						return ;
					}
				}
			}
		}
		mRunning = false;
	}
	
	public void setListener(MessageListener listener)
	{
		LogUtil.i(TAG, "set listener is set");
	    mMsgListener = listener;
	    if(!mRunning)
	    {
	    	new Thread(this).start();
	    	mRunning = true;
	    }
	}
	
	public void addMessage(DownPacket downPacket) {
		if(downPacket == null)
			return;
		synchronized(this) {
			if(!mRunning)
			{
				 new Thread(this).start();
				 mRunning = true;
			}
			mServerMessages.addLast(downPacket);
			notify();
		}
	}
	
	public void stop() {
		synchronized(this) {
			mStop = true;
			notify();
			mServerMessages.clear();
		}
	}
	
    private static void receive(DownPacket downPacket) {
    	{
             LogUtil.printMainProcess(TAG, "Receive NORMAL msg: " + downPacket);
             if(mMsgListener != null)
                mMsgListener.receive(downPacket);
         }
           
    }
	
    public boolean isRunning() {
    	return mRunning;   
    }
	
}
