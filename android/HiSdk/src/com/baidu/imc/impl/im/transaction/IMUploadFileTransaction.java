package com.baidu.imc.impl.im.transaction;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.client.LocalResourceManager;
import com.baidu.imc.impl.im.message.BDHiFile;
import com.baidu.imc.impl.im.transaction.processor.BOSUploadFileProcessor;
import com.baidu.imc.impl.im.transaction.processor.IMFileGetUploadSignProcessor;
import com.baidu.imc.impl.im.transaction.processor.IMFileUploadSuccessProccessor;
import com.baidu.imc.impl.im.transaction.processor.callback.BosUploadFileCallback;
import com.baidu.imc.impl.im.transaction.processor.callback.FileGetUploadSignCallback;
import com.baidu.imc.impl.im.transaction.processor.callback.FileUploadSuccessCallback;
import com.baidu.imc.impl.im.transaction.request.BOSUploadFileRequest;
import com.baidu.imc.impl.im.transaction.request.IMFileGetUploadSignRequest;
import com.baidu.imc.impl.im.transaction.request.IMFileUploadSuccessRequest;
import com.baidu.imc.impl.im.transaction.response.BOSUploadFileResponse;
import com.baidu.imc.impl.im.transaction.response.IMFileGetUploadSignResponse;
import com.baidu.imc.impl.im.transaction.response.IMFileUploadSuccessResponse;
import com.baidu.imc.impl.im.util.FileName;
import com.baidu.imc.impl.im.util.FileUtil;

/**
 * 处理文件的上行。
 * 
 * @author liubinzhe
 */
public class IMUploadFileTransaction implements IMTransactionStart, FileGetUploadSignCallback, BosUploadFileCallback,
        FileUploadSuccessCallback {

    private static final String TAG = "UploadFile";

    private LocalResourceManager localResourceManager;
    private String bosHost;
    private BDHiFile bdhiFile = null;
    private IMFileTransactionFlow transactionFlow = null;

    public IMUploadFileTransaction(LocalResourceManager localResourceManager, String bosHost, BDHiFile hiFile,
            IMFileTransactionFlow transactionFlow) {
        this.localResourceManager = localResourceManager;
        this.bosHost = bosHost;
        this.bdhiFile = hiFile;
        this.transactionFlow = transactionFlow;
    }

    @Override
    public void startWorkFlow() throws Exception {
        if (!TextUtils.isEmpty(bosHost) && null != bdhiFile && !TextUtils.isEmpty(bdhiFile.getLocaleFilePath())
                && !TextUtils.isEmpty(bdhiFile.getFileType()) && !TextUtils.isEmpty(bdhiFile.getMD5())) {
            // 01. request upload sign
            // Send GetUploadSign
            LogUtil.printIm(getThreadName(), "Send GetUploadSign.");
            IMFileGetUploadSignRequest uploadSignRequest =
                    new IMFileGetUploadSignRequest(bosHost, bdhiFile, bdhiFile.getLocaleFilePath(), bdhiFile.getFileType());
            IMFileGetUploadSignProcessor uploadSignProcessor =
                    new IMFileGetUploadSignProcessor(uploadSignRequest, this);
            uploadSignProcessor.startWorkFlow();
            //bdhiFile.setMD5(uploadSignRequest.getMd5());
            //bdhiFile.setLocaleFilePath(uploadSignRequest.getFileName());
        } else {
            // Param error
            LogUtil.printIm(getThreadName(), "Param error.");
            if (transactionFlow != null) {
                transactionFlow.uploadFileCallback(bdhiFile, false);
            }
        }
    }

    public String getThreadName() {
        return TAG;
    }

    @Override
    public void onFileGetUploadSignCallback(IMFileGetUploadSignResponse uploadSignResponse) {
        // 02. if it is fast-uploaded, it do not need to upload again.
        if (null != uploadSignResponse && uploadSignResponse.getErrCode() == 0) {
            LogUtil.printIm(getThreadName(), "Get GetUploadSign succeed." + uploadSignResponse.toString());
            if (!uploadSignResponse.isExist()) {
                // it is not existed
                LogUtil.printIm(getThreadName(), "It is not existed in server.");
                if (null != localResourceManager && !TextUtils.isEmpty(bosHost) && null != bdhiFile
                        && !TextUtils.isEmpty(bdhiFile.getLocaleFilePath())
                        && !TextUtils.isEmpty(uploadSignResponse.getFid())
                        && !TextUtils.isEmpty(uploadSignResponse.getUploadUrl())
                        && !TextUtils.isEmpty(uploadSignResponse.getSign())
                        && !TextUtils.isEmpty(uploadSignResponse.getBmd5())) {
                    LogUtil.printIm(getThreadName(), "Try to upload local file.");
                    LogUtil.printIm(bdhiFile.getLocaleFilePath());
                    InputStream fis = localResourceManager.getFileInputStream(bdhiFile.getLocaleFilePath());
                    long fileLength = bdhiFile.getFileSize();
                    if (null != fis) {
                        String fid = uploadSignResponse.getFid();
                        String url = uploadSignResponse.getUploadUrl();
                        String sign = uploadSignResponse.getSign();
                        String bmd5 = uploadSignResponse.getBmd5();
                        bdhiFile.setFid(fid);
                        LogUtil.printIm(getThreadName(), "Send UploadFile. fid:" + fid + " url:" + url + " sign:"
                                + sign + " bmd5:" + bmd5 + " length:" + fileLength);
                        // save fid
                        // 03. upload file
                        BOSUploadFileRequest uploadFileRequest =
                                new BOSUploadFileRequest(bosHost, url, sign, bmd5, fis, (int) fileLength);
                        BOSUploadFileProcessor uploadFileProcessor =
                                new BOSUploadFileProcessor(uploadFileRequest, this);
                        uploadFileProcessor.startWorkFlow();
                        return;
                    } else {
                        LogUtil.printIm(getThreadName(), "Can not get file input stream.");
                    }
                } else {
                    LogUtil.printIm(getThreadName(), "Server error. Get upload sign failed.");
                }
            } else if (!TextUtils.isEmpty(uploadSignResponse.getFid())) {
                // it is already existed and has fid
                if (transactionFlow != null) {
                    LogUtil.printIm(getThreadName(), "It is already existed." + uploadSignResponse.getFid());
                    bdhiFile.setFid(uploadSignResponse.getFid());
                    transactionFlow.uploadFileCallback(bdhiFile, true);
                }
                return;
            } else {
                // it is already existed but has no fid
                LogUtil.printIm(getThreadName(), "Server error. It is already existed but has no fid.");
            }
        } else {
            LogUtil.printIm(getThreadName(), "Server error. Get upload sign failed."
                    + (null != uploadSignResponse ? uploadSignResponse.getErrCode() : -1));
        }
        if (transactionFlow != null) {
            transactionFlow.uploadFileCallback(bdhiFile, false);
        }
    }

    @Override
    public void onBosUploadFileCallback(BOSUploadFileResponse uploadFileResponse) {
        if (null != uploadFileResponse && uploadFileResponse.getStatusCode() == 200) {
            LogUtil.printIm(getThreadName(), "BOS File upload succeed.");
            List<String> fidList = new ArrayList<String>();
            if((bdhiFile.getFid() != null) && (fidList!= null))
            	fidList.add(bdhiFile.getFid());
            IMFileUploadSuccessRequest uploadSuccessRequest = new IMFileUploadSuccessRequest(fidList);
            IMFileUploadSuccessProccessor uploadSuccessProccessor =
                    new IMFileUploadSuccessProccessor(uploadSuccessRequest, this);
            try {
                LogUtil.printIm(getThreadName(), "Send UploadSuccess.");
                uploadSuccessProccessor.startWorkFlow();
                return;
            } catch (Exception e) {
                LogUtil.printImE(getThreadName(), "IMFileUploadSuccessProccessor. Can not set it uploadSuccess.", e);
            }
        } else {
            LogUtil.printIm(getThreadName(), "BOS Server error. Can not upload file.");
        }
        if (transactionFlow != null) {
            transactionFlow.uploadFileCallback(bdhiFile, false);
        }
    }

    @Override
    public void onFileUploadSuccessCallback(IMFileUploadSuccessResponse uploadSuccessResponse) {
        if (null != uploadSuccessResponse && uploadSuccessResponse.getErrCode() == 0) {
            LogUtil.printIm(getThreadName(), "UploadSuccess succeed.");
            if (!TextUtils.isEmpty(bdhiFile.getLocaleFilePath()) && !TextUtils.isEmpty(bdhiFile.getFid())
                    && !TextUtils.isEmpty(bdhiFile.getMD5())) {
                if (!FileUtil.isInTmpFileDir(bdhiFile.getLocaleFilePath())) {
                    LogUtil.printIm(getThreadName(), "UploadSuccess succeed. copyIntoTmpFileDir");
                    FileUtil.copyIntoTmpFileDir(bdhiFile.getLocaleFilePath(),
                            FileName.getFileName(bdhiFile.getFid(), bdhiFile.getMD5()));
                } else {
                    LogUtil.printIm(getThreadName(), "UploadSuccess succeed. rename");
                    FileUtil.rename(bdhiFile.getLocaleFilePath(),
                            FileName.getFileName(bdhiFile.getFid(), bdhiFile.getMD5()));
                }
            }
            if (transactionFlow != null) {
                transactionFlow.uploadFileCallback(bdhiFile, true);
            }
        } else {
            LogUtil.printIm(getThreadName(), "UploadSuccess failed.");
            if (transactionFlow != null) {
                transactionFlow.uploadFileCallback(bdhiFile, false);
            }
        }
    }
}
