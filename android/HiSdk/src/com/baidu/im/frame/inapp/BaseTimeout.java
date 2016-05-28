/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.frame.inapp;

import com.baidu.im.frame.Timeout;

/**
 * Timeout数据结构。
 * 
 * @author zhaowei10
 * 
 */
public class BaseTimeout implements Timeout {

	private long value;

	private long beginTime;

	private int totalRetryTime;

	private int leftRetryTime;

	/**
	 * @return the value
	 */
	@Override
	public long getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(long value) {
		this.value = value;
	}

	/**
	 * @return the beginTime
	 */
	public long getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime
	 *            the beginTime to set
	 */
	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return the totalRetryTime
	 */
	public int getTotalRetryTime() {
		return totalRetryTime;
	}

	/**
	 * @param totalRetryTime
	 *            the totalRetryTime to set
	 */
	public void setTotalRetryTimes(int totalRetryTime) {
		this.totalRetryTime = totalRetryTime;
		setLeftRetryTime(getTotalRetryTime());
	}

	public void retried() {
		leftRetryTime--;
	}

	/**
	 * @return the leftRetryTime
	 */
	@Override
	public int getLeftRetryTimes() {
		return leftRetryTime;
	}

	/**
	 * @param leftRetryTime
	 *            the leftRetryTime to set
	 */
	public void setLeftRetryTime(int leftRetryTime) {
		this.leftRetryTime = leftRetryTime;
	}

}
