/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.frame;

/**
 * Timeout描述对象的接口
 * 
 * @author zhaowei10
 * 
 */
public interface Timeout {

	/**
	 * 获取超时时间
	 * 
	 * @return
	 */
	long getValue();

	/**
	 * 获取剩余的重试次数
	 * 
	 * @return the leftRetryTimes
	 */
	public int getLeftRetryTimes();

}
