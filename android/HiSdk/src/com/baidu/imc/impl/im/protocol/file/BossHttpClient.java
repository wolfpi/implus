package com.baidu.imc.impl.im.protocol.file;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.baidu.im.frame.utils.LogUtil;

@SuppressWarnings("deprecation")
public class BossHttpClient {
    public static final String TAG = "BossHttpClient";

    public static HttpResult sendRequest(BossHttpRequest request) throws IOException {
        DefaultHttpClient client = new DefaultHttpClient();

        HttpRequestBase method = null;
        try {
            if (request.getMethod().equals(BOSConstant.POST)) {
                method = new HttpPost(request.getUrl());
            } else if (request.getMethod().equals(BOSConstant.GET)) {
                method = new HttpGet(request.getUrl());
            } else if (request.getMethod().equals(BOSConstant.PUT)) {
                method = new HttpPut(request.getUrl());
            }

            Map<String, String> headersMap = request.getHeadersMap();
            if (headersMap != null) {
                Iterator<String> iter = headersMap.keySet().iterator();
                while (iter.hasNext()) {
                    String name = iter.next();
                    String value = headersMap.get(name);
                    method.setHeader(name, value);
                }
            }

            HttpEntity reqHttpEntiry = null;
            if (request.getPostDataInputStream() != null && request.getPostDataLen() > 0) {
                reqHttpEntiry = new InputStreamEntity(request.getPostDataInputStream(), request.getPostDataLen());
            }
            Map<String, String> postData = request.getPostData();
            if (postData != null) {
                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : postData.entrySet()) {
                    parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                try {
                    reqHttpEntiry = new UrlEncodedFormEntity(parameters, BOSConstant.UTF_8);
                } catch (UnsupportedEncodingException e) {
                    LogUtil.e(TAG, e);
                }
            }
            if (request.getMethod().equals(BOSConstant.POST)) {
                HttpPost postMethod = (HttpPost) method;
                if (reqHttpEntiry != null) {
                    postMethod.setEntity(reqHttpEntiry);
                }
            } else if (request.getMethod().equals(BOSConstant.PUT)) {
                HttpPut putMethod = (HttpPut) method;
                if (reqHttpEntiry != null) {
                    putMethod.setEntity(reqHttpEntiry);
                }
            }

            HttpResponse response = null;
            for (int attempt = 0; response == null && attempt < request.getRetryTimes(); attempt++) {
                try {
                    // execute the method.
                    response = client.execute(method);
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }

            HttpResult result = new HttpResult();

            if (response == null) {
                result.setStatusCode(400);
                return result;
            }
            result.setHttpEntity(response.getEntity());
            result.setStatusCode(response.getStatusLine().getStatusCode());
            return result;
        } finally {
            // try {
            // if (method != null)
            // method.abort();
            // } catch (Exception e) {
            // }
        }
    }
}
