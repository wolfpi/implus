package com.baidu.im.frame;

import java.util.TimerTask;

public class  IMCTransactionTimeout implements TimeoutCallback
{
	private int mTimeout = 300*1000;
	private GlobalTimer mGlobalTimer= GlobalTimerTasks.getInstance();
	private ITransactionTimeoutCallback mCallback = null;
	public IMCTransactionTimeout(ITransactionTimeoutCallback callback)
	{
		mCallback = callback;
	}
	public IMCTransactionTimeout(ITransactionTimeoutCallback callback,int timeout)
	{
		mCallback = callback;
		if(timeout > 0) {
			mTimeout = timeout;
		}
	}
	
	public void startCountDown()
	{
		stopCountDown();
		TimerTask task = new TimeoutTask(this);
		mGlobalTimer.addTask(this.hashCode(), task, mTimeout);
	}
	public void stopCountDown()
	{
		mGlobalTimer.removeTask(this.hashCode());
	}

	@Override
	public void timeoutCallback() {
		if(mCallback != null)
		{
			mCallback.onTimeOut();
		}
	}
}
