package com.baidu.imc.impl.im.transaction.request;

import android.text.TextUtils;

import com.baidu.im.frame.pb.ProGetDownloadSign.GetDownloadSignReq;
import com.baidu.im.frame.pb.ProGetDownloadSign.GetDownloadSignReqItem;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.StringUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.imc.exception.InitializationException;
import com.baidu.imc.impl.im.protocol.MethodNameEnum;
import com.baidu.imc.impl.im.protocol.ServiceNameEnum;

public class IMFileGetDownloadSignRequest extends IMBaseRequest {

    private static final String TAG = "DownloadFileGetSignRequest";
    private String fid;
    private String host;
    private String md5;

    public IMFileGetDownloadSignRequest(String fid, String host, String md5) {
        if (StringUtil.isStringInValid(fid))
            throw new InitializationException();
        this.fid = fid;
        this.host = host;
        this.md5 = md5;
    }

    @Override
    public BinaryMessage createRequest() {
        LogUtil.printIm(getRequestName(), "fid:" + fid + " host:" + host + " md5:" + md5);
        BinaryMessage binaryMessage = null;
        if (!TextUtils.isEmpty(fid) && !TextUtils.isEmpty(host) && !TextUtils.isEmpty(md5)) {
            binaryMessage = new BinaryMessage();
            binaryMessage.setServiceName(ServiceNameEnum.IM_PLUS_FILE.getName());
            binaryMessage.setMethodName(MethodNameEnum.GET_DOWNLOAD_SIGN.getName());

            GetDownloadSignReqItem getDownloadSignReqItemBuilder = new  GetDownloadSignReqItem();  // migrate from builder
            getDownloadSignReqItemBuilder.setFileId(fid);
            getDownloadSignReqItemBuilder.setBossHost(host);
            getDownloadSignReqItemBuilder.setMd5(md5);

            GetDownloadSignReq getDownloadSignReqBuilder = new  GetDownloadSignReq();  // migrate from builder
            getDownloadSignReqBuilder.addReqList(getDownloadSignReqItemBuilder);

            binaryMessage.setData(getDownloadSignReqBuilder.toByteArray());
        }
        return binaryMessage;
    }

    @Override
    public String getRequestName() {
        return TAG;
    }

}
