package com.baidu.im.mock.pb;

import android.content.Context;

import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;

public class DownPacketCenter {
	
	private Context mContext = null;
	
	public DownPacketCenter(Context context)
	{
		mContext = context;
	}
	public DownPacket getDownPacketAppLogin()
	{
		return DownPacketAppLogin.getSuccess(mContext);
	}

}
