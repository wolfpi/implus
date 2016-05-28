package com.baidu.imc.impl.im.transaction.response;

import com.baidu.im.frame.pb.ProGetDownloadSign.GetDownloadSignRsp;
import com.baidu.im.frame.pb.ProGetDownloadSign.GetDownloadSignRspItem;
import com.baidu.im.frame.utils.LogUtil;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

public class IMFileGetDownloadSignResponse extends IMBaseResponse {

    private static final String TAG = "DownloadFileGetSignResponse";

    private String downloadUrl;
    private String fid;
    private String sign;

    public IMFileGetDownloadSignResponse(String description, byte[] data, int errCode) {
        super(description, data, errCode);
        if (errCode == 0 && null != getData()) {
            createResponse();
        }
    }

    @Override
    public void createResponse() {
        LogUtil.printIm(getResponseName(), "Code:" + errCode);
        try {
            GetDownloadSignRsp getDownloadSignRspBuilder = new GetDownloadSignRsp(); // migrate from builder
            getDownloadSignRspBuilder.mergeFrom(data);
            if (getDownloadSignRspBuilder.getRspListCount() > 0 && null != getDownloadSignRspBuilder.getRspList(0)) {
                GetDownloadSignRspItem rspItem = getDownloadSignRspBuilder.getRspList(0);
                downloadUrl = rspItem.getDownloadUrl();
                fid = rspItem.getFileId();
                sign = rspItem.getSign();
            }
            LogUtil.printIm(getResponseName(), "DownloadUrl:" + downloadUrl + " fid:" + fid + " sign:" + sign);
        } catch (InvalidProtocolBufferMicroException e) {
            LogUtil.printImE(getResponseName(), e);
        }
    }

    @Override
    public String getResponseName() {
        return TAG;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getFid() {
        return fid;
    }

    public String getSign() {
        return sign;
    }

}
