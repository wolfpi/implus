package com.baidu.im.frame.utils;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

/**
 * @author zhaowei10
 * 
 */
public class SignatureUtil {

    public static final String TAG = "SignatureUtil";

    private static PackageInfo localPackageInfo;

    public static String getSecureKey(Context context) {
    	if(context == null)
    		return null;
        String packageName = context.getPackageName();
        try {
            String sha1 = getSha1(context, packageName);
            return sha1 + ";" + packageName;
        } catch (RuntimeException e) {
            LogUtil.e(TAG, e);
            return "" + ";" + packageName;
        }
    }

    static String getSha1(Context context, String packageName) {
    	if(context == null || packageName ==  null)
    		return null;
        String str = "";
        try {
            if (localPackageInfo == null) {
                localPackageInfo = context.getPackageManager().getPackageInfo(packageName, 64);
            }
            Signature[] arrayOfSignature = localPackageInfo.signatures;
            CertificateFactory localCertificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate localX509Certificate =
                    (X509Certificate) localCertificateFactory.generateCertificate(new ByteArrayInputStream(
                            arrayOfSignature[0].toByteArray()));
            str = getSha1ByCer(localX509Certificate);
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            LogUtil.e(TAG, localNameNotFoundException);
        } catch (CertificateException localCertificateException) {
            LogUtil.e(TAG, localCertificateException);
        }
        StringBuffer localStringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            localStringBuffer.append(str.charAt(i));
            if ((i <= 0) || (i % 2 != 1) || (i >= str.length() - 1))
                continue;
            localStringBuffer.append(":");
        }
        return localStringBuffer.toString();
    }

    static String getSha1ByCer(X509Certificate paramX509Certificate) {
        if (paramX509Certificate != null) {
            byte[] arrayOfByte = null;
            try {
                MessageDigest localMessageDigest = MessageDigest.getInstance("SHA1");
                arrayOfByte = localMessageDigest.digest(paramX509Certificate.getEncoded());
            } catch (NoSuchAlgorithmException e1) {
                LogUtil.e(TAG, e1);
            } catch (CertificateEncodingException e2) {
                LogUtil.e(TAG, e2);
            }

            if (arrayOfByte != null) {
                return convertToSha1(arrayOfByte);
            }
        }
        return "";
    }

    static String convertToSha1(byte[] paramArrayOfByte) {
        if (paramArrayOfByte != null) {

            char[] arrayOfChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
            StringBuilder localStringBuilder = new StringBuilder(paramArrayOfByte.length * 2);
            for (int i = 0; i < paramArrayOfByte.length; i++) {
                localStringBuilder.append(arrayOfChar[((paramArrayOfByte[i] & 0xF0) >> 4)]);
                localStringBuilder.append(arrayOfChar[(paramArrayOfByte[i] & 0xF)]);
            }
            return localStringBuilder.toString();
        }
        return "";
    }

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }
}
