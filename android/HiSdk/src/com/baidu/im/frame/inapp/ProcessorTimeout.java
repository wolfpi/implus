/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.frame.inapp;

/**
 * @author zhaowei10
 * 
 */
public class ProcessorTimeout extends BaseTimeout {

	/**
     * 
     */
	public ProcessorTimeout() {

		// 超时
		setValue(10 * 1000);

		// 重试次数
		setTotalRetryTimes(2);

	}
}
