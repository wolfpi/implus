package com.baidu.im.frame.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class HttpClientUtil {
    public static final String TAG = "HttpClientUtil";

    public static String[] GetResponse(String url, String cookieStr, String referer, int timeout) {
        return GetResponse(url, cookieStr, referer, "utf-8", timeout);
    }

    public static String[] GetResponse(String url, String cookieStr, String referer, String code, int timeout) {
    	
    	if(StringUtil.isStringInValid(url) || url.length() <= 4)
    		return null;
    	
        String[] result = new String[2];

        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeout);
        HttpConnectionParams.setSoTimeout(httpParameters, timeout);
        HttpProtocolParams.setVersion(httpParameters, HttpVersion.HTTP_1_1);
        DefaultHttpClient client = null;
        if (!url.startsWith("https")) {
            client = new DefaultHttpClient();
        } else {
            Scheme httpScheme = new Scheme("http", PlainSocketFactory.getSocketFactory(), 80);
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            // schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));
            schemeRegistry.register(httpScheme);
            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                SSLSocketFactory sf = new EasySSLSocketFactory(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                schemeRegistry.register(new Scheme("https", sf, 8443));
                schemeRegistry.register(new Scheme("https", sf, 443));
            } catch (KeyStoreException e) {
                LogUtil.e(TAG, e);
            } catch (NoSuchAlgorithmException e) {
                LogUtil.e(TAG, e);
            } catch (CertificateException e) {
                LogUtil.e(TAG, e);
            } catch (IOException e) {
                LogUtil.e(TAG, e);
            } catch (KeyManagementException e) {
                LogUtil.e(TAG, e);
            } catch (UnrecoverableKeyException e) {
                LogUtil.e(TAG, e);
            }

            ClientConnectionManager connManager = new ThreadSafeClientConnManager(httpParameters, schemeRegistry);
            client = new DefaultHttpClient(connManager, httpParameters);
        }

        client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.RFC_2965);
        HttpClientParams.setCookiePolicy(client.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
        String httpEntity = "";
        CookieStore cookieStore = new BasicCookieStore();
        cookieStore.clear();
        HttpGet getMethod = new HttpGet(url);
        if (referer != null && referer.length() != 0) {
            getMethod.setHeader("Referer", referer);
        }
        getMethod.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
        HashMap<String, String> cookieMap = StringToMap(cookieStr);
        for (String key : cookieMap.keySet()) {
            BasicClientCookie cookie = new BasicClientCookie(key, cookieMap.get(key));
            cookie.setVersion(0);
            cookie.setDomain("baidu.com");
            cookie.setPath("/");
            cookieStore.addCookie(cookie);
        }

        client.setCookieStore(cookieStore);
        try {
            HttpResponse response = client.execute(getMethod);
            httpEntity = EntityUtils.toString(response.getEntity(), code);
        } catch (ClientProtocolException e) {
            // String info = HiApplication.context
            // .getString(R.string.roam_net_error);
            // Utils.showToastInOtherThread(info);
            LogUtil.e(TAG, e);
        } catch (IOException e) {
            // String info = HiApplication.context
            // .getString(R.string.roam_net_error);
            // Utils.showToastInOtherThread(info);
            LogUtil.e(TAG, e);
        } catch (Exception e) {
            LogUtil.e(TAG, e);
        }

        List<Cookie> cookies = cookieStore.getCookies();
        HashMap<String, String> map = new HashMap<String, String>();
        for (Cookie cookie : cookies) {
            map.put(cookie.getName(), cookie.getValue());
        }
        result[1] = MapToString(map);
        getMethod.abort();

        if (httpEntity.length() == 0)
            result[0] = httpEntity;
        else {
            result[0] =
                    httpEntity.substring(httpEntity.indexOf("(") + 1, (httpEntity.lastIndexOf(")") == httpEntity
                            .length() - 1) ? (httpEntity.length() - 1) : httpEntity.length());
        }
        // result[0] = httpEntity.substring(httpEntity.indexOf("(") + 1, (httpEntity.lastIndexOf(")") ==
        // httpEntity.length() - 1) ? (httpEntity.length() - 1) : httpEntity.length());
        return result;
    }

    public static String[] GetResponseNormal(String url, String cookieStr, String referer, int timeout) {
    	
    	if(StringUtil.isStringInValid(url) || url.length() <= 4)
    		return null;
    	
        String[] result = new String[2];

        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeout);
        HttpConnectionParams.setSoTimeout(httpParameters, timeout);
        HttpProtocolParams.setVersion(httpParameters, HttpVersion.HTTP_1_1);
        DefaultHttpClient client = null;
        if (!url.startsWith("https")) {
            client = new DefaultHttpClient();
        } else {
            Scheme httpScheme = new Scheme("http", PlainSocketFactory.getSocketFactory(), 80);
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            // schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));
            schemeRegistry.register(httpScheme);
            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                SSLSocketFactory sf = new EasySSLSocketFactory(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                schemeRegistry.register(new Scheme("https", sf, 8443));
                schemeRegistry.register(new Scheme("https", sf, 443));
            } catch (KeyStoreException e) {
                LogUtil.e(TAG, e);
            } catch (NoSuchAlgorithmException e) {
                LogUtil.e(TAG, e);
            } catch (CertificateException e) {
                LogUtil.e(TAG, e);
            } catch (IOException e) {
                LogUtil.e(TAG, e);
            } catch (KeyManagementException e) {
                LogUtil.e(TAG, e);
            } catch (UnrecoverableKeyException e) {
                LogUtil.e(TAG, e);
            }
            ClientConnectionManager connManager = new ThreadSafeClientConnManager(httpParameters, schemeRegistry);
            client = new DefaultHttpClient(connManager, httpParameters);
        }

        client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.RFC_2965);
        HttpClientParams.setCookiePolicy(client.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
        String httpEntity = "";
        CookieStore cookieStore = new BasicCookieStore();

        HttpGet getMethod = new HttpGet(url);
        if (referer != null)
            getMethod.setHeader("Referer", referer);
        getMethod.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
        HashMap<String, String> cookieMap = StringToMap(cookieStr);
        for (String key : cookieMap.keySet()) {
            BasicClientCookie cookie = new BasicClientCookie(key, cookieMap.get(key));
            cookie.setVersion(0);
            cookie.setDomain("baidu.com");
            cookie.setPath("/");
            cookieStore.addCookie(cookie);
        }

        client.setCookieStore(cookieStore);
        try {
            HttpResponse response = client.execute(getMethod);
            httpEntity = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (ClientProtocolException e) {
            LogUtil.e(TAG, e);
        } catch (IOException e) {
            LogUtil.e(TAG, e);
        } catch (Exception e) {
            LogUtil.e(TAG, e);
        }

        List<Cookie> cookies = cookieStore.getCookies();
        HashMap<String, String> map = new HashMap<String, String>();
        for (Cookie cookie : cookies) {
            map.put(cookie.getName(), cookie.getValue());
        }
        result[1] = MapToString(map);
        getMethod.abort();
        result[0] = httpEntity;
        return result;
    }

    public static String MapToString(HashMap<String, String> map) {
        String result = "";

        if (map != null) {
            for (String key : map.keySet()) {
                result = result + "\"" + key + "\"=" + "\"" + map.get(key) + "\";";
            }
        }
        return result;
    }

    public static HashMap<String, String> StringToMap(String s) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtil.isStringInValid(s))
            return map;
        String[] keyValues = s.split("\";");
        for (int i = 0; i < keyValues.length; i++) {
            String[] keyValue = keyValues[i].substring(1).split("\"=\"");
            if (keyValue.length == 2) {
                map.put(keyValue[0], keyValue[1]);
            }
        }
        return map;
    }

    public static List<String> StringToCookie(String s) {
        List<String> result = new ArrayList<String>();
        HashMap<String, String> map = StringToMap(s);
        for (String key : map.keySet()) {
            result.add("" + key + "=" + map.get(key) + ";Path=/;Domain=.baidu.com;");
        }
        return result;
    }

    public static String StringToCookieString(String s) {
        String result = "";
        if(StringUtil.isStringInValid(s))
        	return result;
        HashMap<String, String> map = StringToMap(s);
        for (String key : map.keySet()) {
            result = result + "" + key + "=" + map.get(key) + ";";
        }
        if (result.length() > 0) {
            result = result + "Path=/;Domain=.baidu.com;";
        }
        return result;
    }

    public static String[] GetPostResponse(String url, HashMap<String, String> params, String cookieStr,
            String referer, int timeout) {
    	
    	if(StringUtil.isStringInValid(url))
    		return null;
    	
        String[] result = new String[2];

        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeout);
        HttpConnectionParams.setSoTimeout(httpParameters, timeout);
        DefaultHttpClient client = null;
        if (!url.startsWith("https")) {
            client = new DefaultHttpClient();
        } else {
            Scheme httpScheme = new Scheme("http", PlainSocketFactory.getSocketFactory(), 80);
            // Scheme httpScheme = new
            // Scheme("http",PlainSocketFactory.getSocketFactory(),8712);
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(httpScheme);
            // schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));
            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                SSLSocketFactory sf = new EasySSLSocketFactory(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                schemeRegistry.register(new Scheme("https", sf, 8443));
                schemeRegistry.register(new Scheme("https", sf, 443));
            } catch (KeyStoreException e) {
                LogUtil.e(TAG, e);
            } catch (NoSuchAlgorithmException e) {
                LogUtil.e(TAG, e);
            } catch (CertificateException e) {
                LogUtil.e(TAG, e);
            } catch (IOException e) {
                LogUtil.e(TAG, e);
            } catch (KeyManagementException e) {
                LogUtil.e(TAG, e);
            } catch (UnrecoverableKeyException e) {
                LogUtil.e(TAG, e);
            }

            ClientConnectionManager connManager = new ThreadSafeClientConnManager(httpParameters, schemeRegistry);
            client = new DefaultHttpClient(connManager, httpParameters);
        }

        client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.RFC_2965);
        HttpClientParams.setCookiePolicy(client.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
        String httpEntity = "";
        CookieStore cookieStore = new BasicCookieStore();

        HttpPost postMethod = new HttpPost(url);
        postMethod.setHeader("Referer", referer);
        postMethod.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);

        HashMap<String, String> cookieMap = StringToMap(cookieStr);
        for (String key : cookieMap.keySet()) {
            BasicClientCookie cookie = new BasicClientCookie(key, cookieMap.get(key));
            cookie.setVersion(0);
            cookie.setDomain("baidu.com");
            cookie.setPath("/");
            cookieStore.addCookie(cookie);
        }
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        Object[] keys = params.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            parameters.add(new BasicNameValuePair((String) keys[i], params.get(keys[i])));
        }
        UrlEncodedFormEntity formEntiry = null;
        try {
            formEntiry = new UrlEncodedFormEntity(parameters, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LogUtil.e(TAG, e);
        }
        postMethod.setEntity(formEntiry);
        client.setCookieStore(cookieStore);
        HttpResponse response = null;
        try {
            response = client.execute(postMethod);
            httpEntity = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (ClientProtocolException e) {
            // String info = HiApplication.context
            // .getString(R.string.roam_net_error);
            // Utils.showToastInOtherThread(info);
            LogUtil.e(TAG, e);
        } catch (IOException e) {
            // String info = HiApplication.context
            // .getString(R.string.roam_net_error);
            // Utils.showToastInOtherThread(info);
            LogUtil.e(TAG, e);
        } catch (Exception e) {
            LogUtil.e(TAG, e);
        }

        List<Cookie> cookies = cookieStore.getCookies();
        HashMap<String, String> map = new HashMap<String, String>();
        for (Cookie cookie : cookies) {
            map.put(cookie.getName(), cookie.getValue());
        }
        result[1] = MapToString(map);
        postMethod.abort();
        if (httpEntity.length() == 0)
            result[0] = httpEntity;
        else {
            result[0] =
                    httpEntity.substring(httpEntity.indexOf("(") == 1 ? 2 : 0,
                            (httpEntity.lastIndexOf(")") == httpEntity.length() - 1) ? (httpEntity.length() - 1)
                                    : httpEntity.length());
        }
        // result[0] = httpEntity.substring(httpEntity.indexOf("(") == 1 ? 2 : 0, (httpEntity.lastIndexOf(")") ==
        // httpEntity.length() - 1) ? (httpEntity.length() - 1) : httpEntity.length());
        return result;

    }

}
