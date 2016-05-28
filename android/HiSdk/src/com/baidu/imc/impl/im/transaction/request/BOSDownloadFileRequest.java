package com.baidu.imc.impl.im.transaction.request;

import java.util.HashMap;
import java.util.Map;

import android.text.TextUtils;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.exception.InitializationException;
import com.baidu.imc.impl.im.protocol.file.BOSConstant;
import com.baidu.imc.impl.im.protocol.file.BossHttpRequest;

public class BOSDownloadFileRequest extends BOSBaseRequest {

    private static final String TAG = "DownloadFileRequest";

    private String bosHost;
    private String md5;
    private String fid;
    private String downloadUrl;
    private String sign;

    // private OutputStream fileOut;

    public BOSDownloadFileRequest(String bosHost, String md5, String fid, String downloadUrl, String sign) {

        if (TextUtils.isEmpty(bosHost) || TextUtils.isEmpty(md5) || TextUtils.isEmpty(fid)
                || TextUtils.isEmpty(downloadUrl) || TextUtils.isEmpty(sign)/* || fileOut == null */) {
            throw new InitializationException();
        }
        this.bosHost = bosHost;
        this.md5 = md5;
        this.fid = fid;
        this.downloadUrl = downloadUrl;
        this.sign = sign;
        // this.fileOut = fileOut;
    }

    @Override
    public String getRequestName() {
        return TAG;
    }

    @Override
    public BossHttpRequest createRequest() {
        LogUtil.printIm(getRequestName(), "Fid:" + fid + " downloadUrl:" + downloadUrl + " sign:" + sign + " host:"
                + bosHost);
        Map<String, String> headersMap = new HashMap<String, String>();
        headersMap = new HashMap<String, String>();
        headersMap.put(BOSConstant.HEADER_HOST, bosHost);
        headersMap.put(BOSConstant.HEADER_AUTHORIZATION, sign);
        String date = getUTCTimeString(sign);
        LogUtil.printIm(getRequestName(), BOSBaseRequest.format2 + date);
        headersMap.put(BOSConstant.HEADER_X_BCE_DATE, date);
        BossHttpRequest httpRequest = new BossHttpRequest(downloadUrl, headersMap);
        // httpRequest.setGetDataOutputStream(fileOut);
        return httpRequest;
    }

    public String getMD5() {
        return md5;
    }

    public String getFid() {
        return fid;
    }
}
