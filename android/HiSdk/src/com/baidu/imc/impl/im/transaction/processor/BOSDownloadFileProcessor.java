package com.baidu.imc.impl.im.transaction.processor;

import android.text.TextUtils;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.callback.ResultCallback;
import com.baidu.imc.client.LocalResourceManager;
import com.baidu.imc.impl.im.client.BDHiLocalResourceManager;
import com.baidu.imc.impl.im.client.ResourceManager;
import com.baidu.imc.impl.im.protocol.file.BossHttpRequest;
import com.baidu.imc.impl.im.protocol.file.HttpResult;
import com.baidu.imc.impl.im.transaction.processor.callback.BosDownloadFileCallback;
import com.baidu.imc.impl.im.transaction.request.BOSDownloadFileRequest;
import com.baidu.imc.impl.im.transaction.response.BOSDownloadFileResponse;
import com.baidu.imc.impl.im.util.FileName;

public class BOSDownloadFileProcessor implements IMProcessorStart, BosHttpRequestCallback, ResultCallback<String> {

    private static final String TAG = "DownloadFileProcessor";

    private BOSDownloadFileRequest request;
    private BOSDownloadFileResponse response;
    private BossHttpRequest mHttpRequest = null;
    private BosDownloadFileCallback mCallback = null;

    public BOSDownloadFileProcessor(BOSDownloadFileRequest request, BosDownloadFileCallback callback) {
        this.request = request;
        this.mCallback = callback;
    }

    public String getProcessorName() {
        return TAG;
    }

    @Override
    public void startWorkFlow() {
        if (null != request) {
            mHttpRequest = request.createRequest();
            if (null != mHttpRequest) {
                mHttpRequest.setHttpRequestCallback(this);
                BosService.sendHttpRequest(mHttpRequest);
                return;
            } else {
                LogUtil.printIm(getProcessorName(), "Can not get httpRequest.");
            }
        } else {
            LogUtil.printIm(getProcessorName(), "Can not get request.");
        }
        if (mCallback != null) {
            mCallback.onBosDownloadFileCallback(response);
        }
    }

    public BOSDownloadFileResponse getResponse() {
        return response;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBosCallbackResult(HttpResult result) {
        if (null != result && null != result.getHttpEntity()) {
            LocalResourceManager localResourceManager;
            if (null != ResourceManager.getInstance().getLocalResourceManager()) {
                localResourceManager = ResourceManager.getInstance().getLocalResourceManager();
            } else {
                localResourceManager = ResourceManager.getInstance().getDefaultLocalResourceManager();
            }
            if (result.getStatusCode() == 200 && result.getHttpEntity().getContentLength() > -1
                    && !TextUtils.isEmpty(request.getFid()) && !TextUtils.isEmpty(request.getMD5())) {
                try {
                    LogUtil.printIm(getProcessorName(), "Try to save file locally.");
                    if (localResourceManager instanceof BDHiLocalResourceManager) {
                        String fileName = FileName.getFileName(request.getFid(), request.getMD5());
                        ((BDHiLocalResourceManager) localResourceManager).saveFile(result.getHttpEntity().getContent(),
                                fileName, this);
                    } else {
                        localResourceManager.saveFile(result.getHttpEntity().getContent(), this);
                    }
                    return;
                } catch (Exception e) {
                    LogUtil.printImE(getProcessorName(), "Save file error.", e);
                }
            } else {
                LogUtil.printIm(getProcessorName(), "StatusCode or contentLength error.");
            }
        } else {
            LogUtil.printIm(getProcessorName(), "Can not get httpResult or httpEntity.");
        }
        if (mCallback != null) {
            response = new BOSDownloadFileResponse(null);
            mCallback.onBosDownloadFileCallback(response);
        }
    }

    @Override
    public void result(String filePathAndName, Throwable e) {
        LogUtil.printIm(getProcessorName(), "Successfully save file. " + filePathAndName);
        response = new BOSDownloadFileResponse(filePathAndName);
        if (mCallback != null) {
            mCallback.onBosDownloadFileCallback(response);
        }
    }
}
