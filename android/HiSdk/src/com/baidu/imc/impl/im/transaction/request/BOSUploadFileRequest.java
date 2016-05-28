package com.baidu.imc.impl.im.transaction.request;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.text.TextUtils;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.exception.InitializationException;
import com.baidu.imc.impl.im.protocol.file.BOSConstant;
import com.baidu.imc.impl.im.protocol.file.BossHttpRequest;

public class BOSUploadFileRequest extends BOSBaseRequest {

    private static final String TAG = "UploadFileRequest";

    private String bosHost;
    private String uploadUrl;
    private String sign;
    private String bmd5;
    private InputStream fileIn;
    private int fileLength;

    public BOSUploadFileRequest(String bosHost, String uploadUrl, String sign, String bmd5, InputStream fileIn,
            int fileLength) {

        if (TextUtils.isEmpty(bosHost) || TextUtils.isEmpty(uploadUrl) || TextUtils.isEmpty(sign)
                || TextUtils.isEmpty(bmd5) || fileLength <= 0 || fileIn == null) {
            throw new InitializationException();
        }

        this.bosHost = bosHost;
        this.uploadUrl = uploadUrl;
        this.sign = sign;
        this.bmd5 = bmd5;
        this.fileIn = fileIn;
        this.fileLength = fileLength;
    }

    @Override
    public String getRequestName() {
        return TAG;
    }

    @Override
    public BossHttpRequest createRequest() {
        LogUtil.printIm(getRequestName(), "BosHost:" + bosHost + " UploadUrl:" + uploadUrl + " Sign:" + sign + " Bmd5"
                + bmd5 + " FileLength:" + fileLength);
        Map<String, String> headersMap = new HashMap<String, String>();
        headersMap.put(BOSConstant.HEADER_HOST, bosHost);
        headersMap.put(BOSConstant.HEADER_AUTHORIZATION, sign);
        headersMap.put(BOSConstant.HEADER_X_BCE_CONTENT_SHA256, bmd5);
        String date = getUTCTimeString(sign);
        LogUtil.printIm(getRequestName(), BOSBaseRequest.format2 + date);
        headersMap.put(BOSConstant.HEADER_X_BCE_DATE, date);
        BossHttpRequest httpRequest = new BossHttpRequest(uploadUrl, headersMap, BOSConstant.PUT, null);
        httpRequest.setPostDataInputStream(fileIn);
        httpRequest.setPostDataLen(fileLength);
        return httpRequest;
    }
}
