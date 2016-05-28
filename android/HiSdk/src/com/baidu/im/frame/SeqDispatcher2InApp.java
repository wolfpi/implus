package com.baidu.im.frame;

import java.util.HashMap;
import java.util.Map;

import com.baidu.im.frame.utils.LogUtil;

import android.os.Messenger;

public class SeqDispatcher2InApp {
	private Map<String,Messenger> mDispatcher = new HashMap<String,Messenger>();
	
	public void addSeqDispatch(int seq, Messenger msger)
	{
		LogUtil.printMainProcess("Seq is added to Disp ......" + seq);
		if(msger != null) {
		    mDispatcher.put(String.valueOf(seq), msger);
		}
	}
	
	public Messenger getMessenger(int seq)
	{
		Messenger  msger = mDispatcher.get(String.valueOf(seq));
		LogUtil.printMainProcess("Seq is fetched to Disp ......" + seq);
		mDispatcher.remove(String.valueOf(seq));
		return msger;
	}

}
