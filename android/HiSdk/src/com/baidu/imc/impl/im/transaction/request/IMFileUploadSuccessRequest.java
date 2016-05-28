package com.baidu.imc.impl.im.transaction.request;

import java.util.List;

import android.text.TextUtils;

import com.baidu.im.frame.pb.ProUploadSuccess.UploadSuccessReq;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.imc.exception.InitializationException;
import com.baidu.imc.impl.im.protocol.MethodNameEnum;
import com.baidu.imc.impl.im.protocol.ServiceNameEnum;

public class IMFileUploadSuccessRequest extends IMBaseRequest {

    private static final String TAG = "UploadFileSuccessRequest";

    private List<String> fidList;

    public IMFileUploadSuccessRequest(List<String> fidList) {
        if (null == fidList || fidList.isEmpty()) {
            throw new InitializationException();
        }
        this.fidList = fidList;
    }

    @Override
    public BinaryMessage createRequest() {
        LogUtil.printIm(getRequestName(), fidList.toString());
        UploadSuccessReq uploadSuccessReqBuilder = new  UploadSuccessReq();  // migrate from builder
        for (String fid : fidList) {
            if (!TextUtils.isEmpty(fid)) {
                uploadSuccessReqBuilder.addFid(fid);
            } else {
                LogUtil.printIm(getRequestName(), "Fid Error.");
            }
        }
        if (uploadSuccessReqBuilder.getFidCount() > 0) {
            BinaryMessage binaryMessage = new BinaryMessage();
            binaryMessage.setServiceName(ServiceNameEnum.IM_PLUS_FILE.getName());
            binaryMessage.setMethodName(MethodNameEnum.UPLOAD_SUCCESS.getName());
            binaryMessage.setData(uploadSuccessReqBuilder.toByteArray());
            return binaryMessage;
        } else {
            LogUtil.printIm(getRequestName(), "Can not get fids.");
            return null;
        }
    }

    @Override
    public String getRequestName() {
        return TAG;
    }
}
