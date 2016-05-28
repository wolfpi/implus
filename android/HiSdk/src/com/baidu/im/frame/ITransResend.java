package com.baidu.im.frame;

import com.baidu.im.sdk.IMessageCallback;

public interface ITransResend {
	public void addTransaction(int tranId, BizTransaction tran,IMessageCallback callback);
	public void removeTransaction(int tranId);
}
