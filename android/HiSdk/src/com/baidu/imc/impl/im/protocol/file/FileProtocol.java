package com.baidu.imc.impl.im.protocol.file;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.baidu.im.frame.pb.ProGetDownloadSign.GetDownloadSignReq;
import com.baidu.im.frame.pb.ProGetDownloadSign.GetDownloadSignReqItem;
import com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignReq;
import com.baidu.im.frame.pb.ProUploadSuccess.UploadSuccessReq;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.ChannelSdk;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.imc.impl.im.protocol.MethodNameEnum;
import com.baidu.imc.impl.im.protocol.ServiceNameEnum;

public class FileProtocol {
    public static void getUploadSign(GetUploadSignReq getUploadSignReq, final IMessageResultCallback callback) {
        BinaryMessage binaryMessage = new BinaryMessage();
        binaryMessage.setServiceName(ServiceNameEnum.IM_PLUS_FILE.getName());
        binaryMessage.setMethodName(MethodNameEnum.GET_UPLOAD_SIGN.getName());
        binaryMessage.setData(getUploadSignReq.toByteArray());
        ChannelSdk.send(binaryMessage, callback);
    }

    public static void uploadSuccess(String fid, final IMessageResultCallback callback) {
        BinaryMessage binaryMessage = new BinaryMessage();
        binaryMessage.setServiceName(ServiceNameEnum.IM_PLUS_FILE.getName());
        binaryMessage.setMethodName(MethodNameEnum.UPLOAD_SUCCESS.getName());
        binaryMessage.setData(new UploadSuccessReq().addFid(fid).toByteArray()); // migrate from builder
        ChannelSdk.send(binaryMessage, callback);
    }

    public static void getDownloadSign(String fid, final IMessageResultCallback callback) {
        BinaryMessage binaryMessage = new BinaryMessage();
        binaryMessage.setServiceName(ServiceNameEnum.IM_PLUS_FILE.getName());
        binaryMessage.setMethodName(MethodNameEnum.GET_DOWNLOAD_SIGN.getName());

        GetDownloadSignReq downloadSignBuilder = new GetDownloadSignReq(); // migrate from builder
        downloadSignBuilder.addReqList(new GetDownloadSignReqItem().setBossHost(BOS_HOST).setFileId(fid)); // migrate
        // from builder

        binaryMessage.setData(new GetDownloadSignReq()
                .addReqList(new GetDownloadSignReqItem().setFileId(fid)).toByteArray()); // migrate from builder
        ChannelSdk.send(binaryMessage, callback);
    }

    public static boolean uploadFileToBOSS(String bossHost, String uploadUrl, String sign, String fileSha256,
            InputStream fileIn, int fileLength) {
        Map<String, String> headersMap = new HashMap<String, String>();
        headersMap.put(HEADER_HOST, BOS_HOST);
        headersMap.put(HEADER_AUTHORIZATION, sign);
        headersMap.put(HEADER_X_BCE_CONTENT_SHA256, fileSha256);
        headersMap.put(HEADER_DATE, new SimpleDateFormat(BOS_DATE_FORMAT, Locale.ROOT).format(new Date()));
        BossHttpRequest httpRequest = new BossHttpRequest(uploadUrl, headersMap, "PUT", null);
        httpRequest.setPostDataInputStream(fileIn);
        httpRequest.setPostDataLen(fileLength);
        try {
            HttpResult httpResult = BossHttpClient.sendRequest(httpRequest);

            return httpResult.getStatusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean downloadFileFromBOSS(String bossHost, String downloadUrl, String sign, OutputStream fileOut) {
        Map<String, String> headersMap = new HashMap<String, String>();
        headersMap = new HashMap<String, String>();
        headersMap.put(HEADER_HOST, BOS_HOST);
        headersMap.put(HEADER_AUTHORIZATION, sign);
        headersMap.put(HEADER_DATE, new SimpleDateFormat(BOS_DATE_FORMAT, Locale.ROOT).format(new Date()));
        BossHttpRequest httpRequest = new BossHttpRequest(downloadUrl, headersMap);
        try {
            HttpResult httpResult = BossHttpClient.sendRequest(httpRequest);

            if (httpResult.getHttpEntity() == null) {
                return false;
            }

            @SuppressWarnings("deprecation")
            long length = httpResult.getHttpEntity().getContentLength();
            @SuppressWarnings("deprecation")
            InputStream in = httpResult.getHttpEntity().getContent();
            byte[] buffer = new byte[BUFFER_SIZE];
            int readBytes = 0;
            long currentLength = 0;
            long readLength = length < 0 ? BUFFER_SIZE : (length - currentLength);
            if (readLength > BUFFER_SIZE)
                readLength = BUFFER_SIZE;

            while ((readBytes = in.read(buffer, 0, (int) readLength)) >= 0) {
                fileOut.write(buffer, 0, readBytes);
                currentLength += readBytes;
                if (currentLength >= length) {
                    break;
                }

                readLength = length < 0 ? BUFFER_SIZE : (length - currentLength);
                if (readLength > BUFFER_SIZE)
                    readLength = BUFFER_SIZE;
            }
            return httpResult.getStatusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    private static final String BOS_HOST = "10.105.97.15";
    private static final String BOS_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";

    private static final String HEADER_HOST = "Host";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_DATE = "Date";
    private static final String HEADER_X_BCE_CONTENT_SHA256 = "x-bce-content-sha256";

    private static final int BUFFER_SIZE = 50*1024;
}
