package com.baidu.imc.impl.im.protocol.file;

import java.io.InputStream;
import java.util.Map;

import org.apache.http.cookie.Cookie;

import com.baidu.imc.impl.im.transaction.processor.BosHttpRequestCallback;

@SuppressWarnings("deprecation")
public class BossHttpRequest {
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_CONNECTION = "Connection";
    public static final String HEADER_HOST = "Host";
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_COOKIE = "Cookie";

    private String url;
    private Map<String, String> headersMap = null;
    private String method = "GET";
    private Map<String, String> postData = null;
    private InputStream postDataInputStream;
    // private OutputStream getDataOutputStream;
    private int postDataLen = 0;
    private boolean processRedirect = true;
    private int retryTimes = 1;
    private int timeout = 20000;
    private String proxyIp = null;
    private int proxyPort = 3128;
    private Cookie[] cookies;
    private BosHttpRequestCallback mCallback = null;

    public Cookie[] getCookies() {
        return cookies;
    }

    public void setCookies(Cookie[] cookies) {
        this.cookies = cookies;
    }

    public BossHttpRequest(String url) {
        this.url = url;
    }

    public BossHttpRequest(String url, Map<String, String> headersMap) {
        this.url = url;
        this.headersMap = headersMap;
    }

    public BossHttpRequest(String url, Map<String, String> headersMap, String method, Map<String, String> postData) {
        this.url = url;
        this.headersMap = headersMap;
        this.method = method;
        this.postData = postData;
    }

    public String getHeader(String name) {
        if (headersMap == null) {
            return null;
        } else {
            return headersMap.get(name);
        }
    }

    public Map<String, String> getHeadersMap() {
        return headersMap;
    }

    public void setHeadersMap(Map<String, String> headersMap) {
        this.headersMap = headersMap;
    }

    public Map<String, String> getPostData() {
        return postData;
    }

    public void setPostData(Map<String, String> postData) {
        this.postData = postData;
    }

    public boolean isProcessRedirect() {
        return processRedirect;
    }

    public void setProcessRedirect(boolean processRedirect) {
        this.processRedirect = processRedirect;
    }

    public String getProxyIp() {
        return proxyIp;
    }

    public void setProxyIp(String proxyIp) {
        this.proxyIp = proxyIp;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public InputStream getPostDataInputStream() {
        return postDataInputStream;
    }

    public void setPostDataInputStream(InputStream postDataInputStream) {
        this.postDataInputStream = postDataInputStream;
    }

    public int getPostDataLen() {
        return postDataLen;
    }

    public void setPostDataLen(int postDataLen) {
        this.postDataLen = postDataLen;
    }

    // public void setGetDataOutputStream(OutputStream getDataOutStream) {
    // this.getDataOutputStream = getDataOutStream;
    // }
    //
    // public OutputStream getGetDataOutputStream() {
    // return getDataOutputStream;
    // }

    public BosHttpRequestCallback getHttpRequestCallback() {
        return mCallback;
    }

    public void setHttpRequestCallback(BosHttpRequestCallback callback) {
        mCallback = callback;
    }
}
