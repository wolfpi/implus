package com.baidu.im.frame;

import java.util.TimerTask;
import com.baidu.im.frame.utils.LogUtil;

class TimeoutTask extends TimerTask {
	
	final static String TAG = "TimeoutTask";
	private TimeoutCallback	mCallback = null;
	
	public TimeoutTask(TimeoutCallback listener)
	{
		this.mCallback = listener;
	}   
	
	@Override
    public void run() {  
		LogUtil.e(TAG, "Timeout is called.");
		this.cancel();
		try {
			if(mCallback != null) {
			mCallback.timeoutCallback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }  
} ;