package com.baidu.im.frame.inappCallback;

import com.baidu.im.frame.ProcessorResult;

public interface RegChannelCallback {
	void regChannelSuccess();
	void regChannelFail(ProcessorResult result);
}
