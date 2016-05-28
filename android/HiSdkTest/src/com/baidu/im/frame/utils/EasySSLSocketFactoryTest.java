package com.baidu.im.frame.utils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.params.HttpParams;

import android.test.AndroidTestCase;

public class EasySSLSocketFactoryTest extends AndroidTestCase {

    public void notestEasySSLSocketFactory() {
        KeyStore trustStore;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            EasySSLSocketFactory easySSLSocketFactory = new EasySSLSocketFactory(trustStore);
            easySSLSocketFactory.isSecure(easySSLSocketFactory.createSocket());
            easySSLSocketFactory.hashCode();
            easySSLSocketFactory.equals(null);
            easySSLSocketFactory.connectSocket(null, "10.0.0.1", 8888, null, 8888, new HttpParams() {

                @Override
                public HttpParams setParameter(String arg0, Object arg1) {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public HttpParams setLongParameter(String arg0, long arg1) {
                    return null;
                }

                @Override
                public HttpParams setIntParameter(String arg0, int arg1) {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public HttpParams setDoubleParameter(String arg0, double arg1) {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public HttpParams setBooleanParameter(String arg0, boolean arg1) {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public boolean removeParameter(String arg0) {
                    // TODO Auto-generated method stub
                    return false;
                }

                @Override
                public boolean isParameterTrue(String arg0) {
                    // TODO Auto-generated method stub
                    return false;
                }

                @Override
                public boolean isParameterFalse(String arg0) {
                    // TODO Auto-generated method stub
                    return false;
                }

                @Override
                public Object getParameter(String arg0) {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public long getLongParameter(String arg0, long arg1) {
                    return 2000;
                }

                @Override
                public int getIntParameter(String arg0, int arg1) {
                    // TODO Auto-generated method stub
                    return 2000;
                }

                @Override
                public double getDoubleParameter(String arg0, double arg1) {
                    // TODO Auto-generated method stub
                    return 2000;
                }

                @Override
                public boolean getBooleanParameter(String arg0, boolean arg1) {
                    // TODO Auto-generated method stub
                    return false;
                }

                @Override
                public HttpParams copy() {
                    // TODO Auto-generated method stub
                    return null;
                }
            });
            easySSLSocketFactory.createSocket(null, "10.0.0.1", 8888, true);
            EasyX509TrustManager easyX509TrustManager = new EasyX509TrustManager(trustStore);
            easyX509TrustManager.checkClientTrusted(null, null);
            easyX509TrustManager.checkServerTrusted(null, null);
            easyX509TrustManager.getAcceptedIssuers();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CertificateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}