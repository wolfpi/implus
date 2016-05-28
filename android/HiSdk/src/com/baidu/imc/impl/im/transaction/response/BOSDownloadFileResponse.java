package com.baidu.imc.impl.im.transaction.response;

import java.io.File;

import android.text.TextUtils;

import com.baidu.im.frame.utils.LogUtil;

public class BOSDownloadFileResponse extends BOSBaseResponse {

    private static final String TAG = "DownloadFileResponse";
    private boolean isFileDownloaded = false;
    private String localeFilePath;

    public BOSDownloadFileResponse(String localeFilePath/* , int statusCode, long length, InputStream content */) {
        super(0, 0, null);
        this.localeFilePath = localeFilePath;
        // if (statusCode > -1 && length > -1) {
        createResponse();
        // }
    }

    @Override
    public void createResponse() {
        LogUtil.printIm(getResponseName(), "StatusCode:" + statusCode);
        // if (null != outputStream) {
        // try {
        // byte[] buffer = new byte[BUFFER_SIZE];
        // int readBytes = 0;
        // long currentLength = 0;
        // long readLength = length < 0 ? BUFFER_SIZE : (length - currentLength);
        // if (readLength > BUFFER_SIZE) {
        // readLength = BUFFER_SIZE;
        // }
        //
        // while ((readBytes = content.read(buffer, 0, (int) readLength)) >= 0) {
        // outputStream.write(buffer, 0, readBytes);
        // currentLength += readBytes;
        // if (currentLength >= length) {
        // break;
        // }
        //
        // readLength = length < 0 ? BUFFER_SIZE : (length - currentLength);
        // if (readLength > BUFFER_SIZE) {
        // readLength = BUFFER_SIZE;
        // }
        // }
        // isFileDownloaded = true;
        // } catch (IOException e) {
        // LogUtil.printImE(getResponseName(), "Can not read inputStream.", e);
        // }
        // } else {
        // LogUtil.printIm(getResponseName(), "Can not get outputStream.");
        // }
        if (!TextUtils.isEmpty(localeFilePath) && new File(localeFilePath).exists()) {
            isFileDownloaded = true;
        } else {
            isFileDownloaded = false;
        }
        LogUtil.printIm(getResponseName(), "Result:" + isFileDownloaded);
    }

    @Override
    public String getResponseName() {
        return TAG;
    }

    public boolean isFileDownloaded() {
        return isFileDownloaded;
    }

    public String getLocalFilePath() {
        return localeFilePath;
    }
}
