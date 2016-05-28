package com.baidu.imc.impl.im.protocol.file;

import org.apache.http.HttpEntity;

@SuppressWarnings("deprecation")
public class HttpResult {
	public static final String HEADER_USER_AGENT = "User-Agent" ;
	public static final String HEADER_ACCEPT = "Accept" ;
	public static final String HEADER_CONNECTION = "Connection" ;
	public static final String HEADER_HOST = "Host" ;
	public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding" ;
	public static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language" ;
	public static final String HEADER_CONTENT_TYPE = "Content-Type" ;
	public static final String HEADER_COOKIE = "Cookie" ;
	public static final String HEADER_SET_COOKIE = "Set-Cookie" ;
	
	private int statusCode ;
    private HttpEntity httpEntity;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public HttpEntity getHttpEntity() {
		return httpEntity;
	}
	public void setHttpEntity(HttpEntity httpEntity) {
		this.httpEntity = httpEntity;
	}
}
