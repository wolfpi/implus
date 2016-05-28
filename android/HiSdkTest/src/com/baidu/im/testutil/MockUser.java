/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.testutil;

/**
 * @author zhaowei10
 * 
 */
public class MockUser {

	public enum UserEnum {
		imrd_200("123456"),

		imrd_201("123456"),

		imrd_202("123456"),

		imrd_203("123456"),

		imrd_333("123456");

		private String password;

		private UserEnum(String password) {
			this.password = password;
		}

		public String getPassword() {
			return password;
		}
	}

}
