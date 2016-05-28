package com.baidu.imc.impl.im.transaction.response;

import java.util.concurrent.atomic.AtomicBoolean;

import com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignRsp;
import com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignRspItem;
import com.baidu.im.frame.utils.LogUtil;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

public class IMFileGetUploadSignResponse extends IMBaseResponse {

    private static final String TAG = "UploadFileGetSignResponse";

    private String fid;
    private String sign;
    private String uploadUrl;
    private String bmd5;
    private AtomicBoolean exist = new AtomicBoolean(false);

    public IMFileGetUploadSignResponse(String description, byte[] data, int errCode, String bmd5) {
        super(description, data, errCode);
        if (errCode == 0 && null != getData()) {
            createResponse();
        }
        this.bmd5 = bmd5;
    }

    @Override
    public void createResponse() {
        LogUtil.printIm(getResponseName(), "Code:" + errCode);
        try {
            GetUploadSignRsp getUploadSignRspBuilder = new GetUploadSignRsp(); // migrate from builder
            getUploadSignRspBuilder.mergeFrom(data);

            if (getUploadSignRspBuilder.getRspListCount() > 0 && null != getUploadSignRspBuilder.getRspList(0)) {
                GetUploadSignRspItem rspItem = getUploadSignRspBuilder.getRspList(0);
                exist.set(rspItem.getExist());
                fid = rspItem.getFid();
                sign = rspItem.getSign();
                uploadUrl = rspItem.getUploadUrl();
                LogUtil.printIm(getResponseName(), this.toString());
            }
        } catch (InvalidProtocolBufferMicroException e) {
            LogUtil.printImE(getResponseName(), e);
        }
    }

    @Override
    public String getResponseName() {
        return TAG;
    }

    public String getFid() {
        return fid;
    }

    public String getSign() {
        return sign;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public boolean isExist() {
        return exist.get();
    }

    public String getBmd5() {
        return bmd5;
    }

    @Override
    public String toString() {
        return "IMFileGetUploadSignResponse [fid=" + fid + ", sign=" + sign + ", uploadUrl=" + uploadUrl + ", exist="
                + exist + "]";
    }

}
