package com.baidu.hi.sdk.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import android.text.TextUtils;

@SuppressWarnings("deprecation")
public class HttpUtil {

    public static String getHttpResponse(String url) {

        if (TextUtils.isEmpty(url)) {
            return null;
        }

        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
        HttpConnectionParams.setSoTimeout(httpParameters, 10000);
        HttpProtocolParams.setVersion(httpParameters, HttpVersion.HTTP_1_1);
        DefaultHttpClient client = null;
        if (!url.startsWith("https")) {
            client = new DefaultHttpClient();
        } else {
            Scheme httpScheme = new Scheme("http", PlainSocketFactory.getSocketFactory(), 80);
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(httpScheme);
            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                SSLSocketFactory sf = new EasySSLSocketFactory(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                schemeRegistry.register(new Scheme("https", sf, 8443));
                schemeRegistry.register(new Scheme("https", sf, 443));
            } catch (KeyStoreException e) {
                return null;
            } catch (NoSuchAlgorithmException e) {
                return null;
            } catch (CertificateException e) {
                return null;
            } catch (IOException e) {
                return null;
            } catch (KeyManagementException e) {
                return null;
            } catch (UnrecoverableKeyException e) {
                return null;
            }

            ClientConnectionManager connManager = new ThreadSafeClientConnManager(httpParameters, schemeRegistry);
            client = new DefaultHttpClient(connManager, httpParameters);
        }

        HttpGet getMethod = new HttpGet(url);

        try {
            HttpResponse response = client.execute(getMethod);
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "utf-8");
            } else {
                return null;
            }
        } catch (ClientProtocolException e) {
            return null;
        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            getMethod.abort();
        }
    }

    public static String[] getPostResponse(String url, Map<String, String> params, int timeout) {
        try {

            String[] result = new String[3];

            // timeout
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeout);
            HttpConnectionParams.setSoTimeout(httpParameters, timeout);

            // client
            DefaultHttpClient client = null;
            if (!url.startsWith("https")) {
                client = new DefaultHttpClient();
            } else {
                SchemeRegistry schemeRegistry = new SchemeRegistry();
                schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                // schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                SSLSocketFactory sf = new EasySSLSocketFactory(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                schemeRegistry.register(new Scheme("https", sf, 8443));
                schemeRegistry.register(new Scheme("https", sf, 443));

                ClientConnectionManager connManager = new ThreadSafeClientConnManager(httpParameters, schemeRegistry);
                client = new DefaultHttpClient(connManager, httpParameters);
            }

            // params
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            Object[] keys = params.keySet().toArray();
            for (int i = 0; i < keys.length; i++) {
                parameters.add(new BasicNameValuePair((String) keys[i], params.get(keys[i])));
            }
            UrlEncodedFormEntity formEntiry = null;
            try {
                formEntiry = new UrlEncodedFormEntity(parameters, "utf-8");
            } catch (UnsupportedEncodingException e) {
                return null;
            }

            // post method
            HttpPost postMethod = new HttpPost(url);
            postMethod.setEntity(formEntiry);

            // execute
            HttpResponse response = client.execute(postMethod);
            String httpEntity = EntityUtils.toString(response.getEntity(), "utf-8");
            postMethod.abort();
            result[0] = Integer.toString(response.getStatusLine().getStatusCode());
            result[1] = response.getStatusLine().getReasonPhrase();
            // if (httpEntity.length() == 0) {
            result[2] = httpEntity;
            // } else {
            // result[2] =
            // httpEntity.substring(httpEntity.indexOf("(") == 1 ? 2 : 0,
            // (httpEntity.lastIndexOf(")") == httpEntity.length() - 1) ? (httpEntity.length() - 1)
            // : httpEntity.length());
            // }
            return result;
        } catch (Exception e) {
            return null;
        }

    }
}
