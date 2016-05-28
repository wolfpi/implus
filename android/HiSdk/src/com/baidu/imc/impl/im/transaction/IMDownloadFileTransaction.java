package com.baidu.imc.impl.im.transaction;

import android.text.TextUtils;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.client.LocalResourceManager;
import com.baidu.imc.impl.im.message.BDHiFile;
import com.baidu.imc.impl.im.transaction.processor.BOSDownloadFileProcessor;
import com.baidu.imc.impl.im.transaction.processor.IMFileGetDownloadSignProcessor;
import com.baidu.imc.impl.im.transaction.processor.callback.BosDownloadFileCallback;
import com.baidu.imc.impl.im.transaction.processor.callback.FileGetDownloadSignCallback;
import com.baidu.imc.impl.im.transaction.request.BOSDownloadFileRequest;
import com.baidu.imc.impl.im.transaction.request.IMFileGetDownloadSignRequest;
import com.baidu.imc.impl.im.transaction.response.BOSDownloadFileResponse;
import com.baidu.imc.impl.im.transaction.response.IMFileGetDownloadSignResponse;

/**
 * 负责文件下行。
 * 
 * @author liubinzhe
 */
public class IMDownloadFileTransaction implements IMTransactionStart, FileGetDownloadSignCallback,
        BosDownloadFileCallback {

    private static final String TAG = "DownloadFile";
    // private LocalResourceManager localResourceManager;
    private String bosHost;
    private BDHiFile bdhiFile;
    private IMFileTransactionFlow transactionFlow;

    public IMDownloadFileTransaction(LocalResourceManager localResouceManager, String bosHost, BDHiFile bdhiFile,
            IMFileTransactionFlow transactionFlow) {
        // this.localResourceManager = localResouceManager;
        this.bosHost = bosHost;
        this.bdhiFile = bdhiFile;
        this.transactionFlow = transactionFlow;
    }

    @Override
    public void startWorkFlow() throws Exception {
        LogUtil.printIm(getThreadName(), "IMDownloadFileTransaction start: " + this.hashCode());
        if (!TextUtils.isEmpty(bosHost) && null != bdhiFile && !TextUtils.isEmpty(bdhiFile.getFid())
                && !TextUtils.isEmpty(bdhiFile.getMD5())) {
            // 1. request download sign
            LogUtil.printIm(getThreadName(), "Send GetDownloadSignRequest.");
            IMFileGetDownloadSignRequest getDownloadSignRequest =
                    new IMFileGetDownloadSignRequest(bdhiFile.getFid(), bosHost, bdhiFile.getMD5());
            IMFileGetDownloadSignProcessor getDownloadSignProcessor =
                    new IMFileGetDownloadSignProcessor(getDownloadSignRequest, this);
            getDownloadSignProcessor.startWorkFlow();
        } else {
            LogUtil.printIm(getThreadName(), "Param error.");
            if (null != transactionFlow) {
                transactionFlow.downloadFileCallback(bdhiFile, false);
            } else {
                LogUtil.printIm(getThreadName(), "Can not get callback.");
            }
        }
    }

    public String getThreadName() {
        return TAG;
    }

    @Override
    public void onFileGetDownloadSignCallback(IMFileGetDownloadSignResponse getDownloadSignResponse) {
        if (!TextUtils.isEmpty(bosHost) && null != bdhiFile && !TextUtils.isEmpty(bdhiFile.getMD5())
                && null != getDownloadSignResponse && getDownloadSignResponse.getErrCode() == 0
                && !TextUtils.isEmpty(getDownloadSignResponse.getDownloadUrl())
                && !TextUtils.isEmpty(getDownloadSignResponse.getFid())
                && !TextUtils.isEmpty(getDownloadSignResponse.getSign())) {
            // 2. download file
            LogUtil.printIm(getThreadName(), "Send DownloadFileRequest. host:" + bosHost);
            BOSDownloadFileRequest downloadFileRequest =
                    new BOSDownloadFileRequest(bosHost, bdhiFile.getMD5(), getDownloadSignResponse.getFid(),
                            getDownloadSignResponse.getDownloadUrl(), getDownloadSignResponse.getSign()/* , fos */);
            BOSDownloadFileProcessor downloadFileProcessor = new BOSDownloadFileProcessor(downloadFileRequest, this);
            downloadFileProcessor.startWorkFlow();
            return;
        } else {
            // Server error. Can not get download sign
            LogUtil.printIm(getThreadName(), "Server Error. Can not get download sign.");
        }
        if (transactionFlow != null) {
            transactionFlow.downloadFileCallback(bdhiFile, false);
        }
    }

    @Override
    public void onBosDownloadFileCallback(BOSDownloadFileResponse downloadFileResponse) {

        if (null != downloadFileResponse && downloadFileResponse.isFileDownloaded()) {
            if (null != bdhiFile) {
                bdhiFile.setLocaleFilePath(downloadFileResponse.getLocalFilePath());
            }
            LogUtil.printIm(getThreadName(), "Download file successed. filePath:" + bdhiFile.getLocaleFilePath());
            if (null != transactionFlow) {
                transactionFlow.downloadFileCallback(bdhiFile, true);
            }
        } else {
            LogUtil.printIm(getThreadName(), "BOSServer error. Can not download file.");
            if (transactionFlow != null) {
                transactionFlow.downloadFileCallback(bdhiFile, false);
            }
        }

    }

    @Override
    public String toString() {
        return "IMDownloadFileTransaction [bosHost=" + bosHost + ", bdhiFile=" + bdhiFile + "]";
    }

}
