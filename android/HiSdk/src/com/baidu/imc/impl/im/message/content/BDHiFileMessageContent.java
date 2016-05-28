package com.baidu.imc.impl.im.message.content;

import java.io.File;

import android.text.TextUtils;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.MD5Util;
import com.baidu.imc.client.RemoteResourceManager;
import com.baidu.imc.impl.im.client.BDHiRemoteResourceManager;
import com.baidu.imc.impl.im.client.ResourceManager;
import com.baidu.imc.impl.im.message.BDHiFile;
import com.baidu.imc.impl.im.transaction.callback.IMTransactionFileDownloadCallback;
import com.baidu.imc.impl.im.util.FileName;
import com.baidu.imc.impl.im.util.FileUtil;
import com.baidu.imc.listener.ProgressListener;
import com.baidu.imc.message.content.FileMessageContent;

public class BDHiFileMessageContent extends BDHiIMessageContent implements FileMessageContent {

    private String fileName;
    private String filePath;

    private long fileSize;
    private String fileMD5;

    private boolean fileUploaded;

    private String fid;
    private String fileURL;

    private String fileID;

    /**
     *
     * <b>文件内容[接收]</b>
     * 
     * @since 1.0
     * @author WuBin
     *
     */
    @Override
    public String getFileName() {
        return fileName;
    }

    /**
     * <b>文件大小</b>
     * 
     * @since 1.0
     * 
     * @return 文件大小
     */
    @Override
    public long getFileSize() {
        if (fileSize == 0) {
            if (null != filePath && filePath.length() > 0) {
                File file = new File(filePath);
                if (file.exists()) {
                    fileSize = file.length();
                }
            }
        }
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     *
     * <b>获取消息附件的MD5</b>
     * <p>
     * 当本地不存在时返回null
     * </p>
     *
     * @since 1.0
     * 
     * @return 消息的本地路径
     */
    public String getFileMD5() {
        if (TextUtils.isEmpty(fileMD5)) {
            if (!TextUtils.isEmpty(filePath) && new File(filePath).exists()) {
                fileMD5 = MD5Util.getFileMD5(new File(filePath));
            }
        }
        return fileMD5;
    }

    public void setFileMD5(String fileMD5) {
        this.fileMD5 = fileMD5;
    }

    /**
     *
     * <b>获取消息附件的本地路径</b>
     * <p>
     * 当本地不存在时返回null
     * </p>
     *
     * @since 1.0
     * 
     * @return 消息的本地路径
     */
    @Override
    public String getLocalResource() {
        if (null != filePath && filePath.length() > 0 && new File(filePath).exists()) {
            return filePath;
        }
        String fid = getFid();
        String md5 = getFileMD5();
        if (!TextUtils.isEmpty(fid) && !TextUtils.isEmpty(md5)) {
            String tmpFileName = FileName.getFileName(fid, md5);
            String tmpFilePath = FileUtil.getTmpFileAbsolutePath(tmpFileName);
            if (!TextUtils.isEmpty(tmpFilePath) && new File(tmpFilePath).exists()) {
                return tmpFilePath;
            }
        }
        return null;
    }

    /**
     * <b>文件是否已经上传到服务器</b>
     * <p>
     * 接收到的文件/发送成功：true<br>
     * 发送中的: false
     * </p>
     * 
     * 
     * @return
     */
    @Override
    public boolean isFileUploaded() {
        return fileUploaded;
    }

    public void setFileUploaded(boolean fileUploaded) {
        this.fileUploaded = fileUploaded;
    }

    /**
     *
     * <b>获取消息资源</b>
     * <p>
     * 当指定资源在本地不存在时，将从服务器端下载指定资源，如果某次下载时服务器端返回代码为404时，将保留该代码，下次请求时 直接返回 404 错误
     * </p>
     * <b>错误码</b>
     * <ul>
     * <li>404 : File Not Found ， 下次获取该资源时将直接返回该错误码</li>
     * <li>408 : Request Timeout ， 下次获取该资源时将会继续从服务器端获取</li>
     * </ul>
     *
     * @since 1.0
     *
     * @param progressListener 进度监听器，成功时返回文件的本地路径
     */
    @Override
    public void loadResource(final ProgressListener<String> progressListener) {
    	
    	try{
        if (null != ResourceManager.getInstance()) {
            RemoteResourceManager remoteResourceManager;
            if (null != ResourceManager.getInstance().getRemoteResourceManager()) {
                remoteResourceManager = ResourceManager.getInstance().getRemoteResourceManager();
            } else {
                remoteResourceManager = ResourceManager.getInstance().getDefaultRemoteRemoteManager();
            }
            if (remoteResourceManager instanceof BDHiRemoteResourceManager) {
                BDHiFile bdHiFile = new BDHiFile();
                bdHiFile.setFid(fid);
                bdHiFile.setMD5(fileMD5);
                bdHiFile.setURL(fileURL);
                ((BDHiRemoteResourceManager) remoteResourceManager).downloadFile(bdHiFile,
                        new IMTransactionFileDownloadCallback() {

                            @Override
                            public void onIMTransactionFileDownloadCallback(BDHiFile bdhiFile, boolean result) {
                                if (result) {
                                    if (progressListener != null && bdhiFile != null
                                            && !TextUtils.isEmpty(bdhiFile.getLocaleFilePath())) {
                                        BDHiFileMessageContent.this.setFilePath(bdhiFile.getLocaleFilePath());
                                        progressListener.onSuccess(bdhiFile.getLocaleFilePath());
                                    }
                                } else {
                                    if (progressListener != null) {
                                        progressListener.onError(-1, null);
                                    }
                                }
                            }
                        });
            } else {
                ProgressListener<String> downloadProgressListener = new ProgressListener<String>() {

                    @Override
                    public void onError(int code, String message) {
                        if (null != progressListener) {
                            progressListener.onError(code, message);
                        }
                    }

                    @Override
                    public void onProgress(float progress) {
                        if (null != progressListener) {
                            progressListener.onProgress(progress);
                        }
                    }

                    @Override
                    public void onSuccess(String result) {
                        setFilePath(result);
                        if (null != progressListener) {
                            progressListener.onSuccess(result);
                        }
                    }
                };
                remoteResourceManager.downloadFile(this.fileURL, downloadProgressListener);
            }
        }
    	} catch(Exception e)
    	{
    		LogUtil.printError("load resource", e);
    	}
    }

    /**
     * <b>设置文件上传监听器</b>
     * <p>
     * 特别说明：如果设置时文件已经上传成功，则立刻调用ProgressListener的onSuccess方法
     * </p>
     * 
     * @param progressListener 成功返回上传成功的文件URL
     */
    @Override
    public void setFileUploadListener(ProgressListener<String> progressListener) {
    }

    /**
     * <b>设置文件名[可选]</b>
     * 
     * @param fileName 文件名
     * 
     * @since 1.0
     */
    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * <b>设置文件路径[必须]</b>
     * 
     * <p>
     * 设置具体文件时，需要先使用LocalResourceManager将文件保存到本地临时资源库
     * </p>
     * 
     * @param filePath 文件本地资源库路径
     * 
     * @since 1.0
     */
    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    @Override
    public String toString() {
        return "BDHiFileMessageContent [fileName=" + fileName + ", filePath=" + filePath + ", fileSize=" + fileSize
                + ", fileMD5=" + fileMD5 + ", fileUploaded=" + fileUploaded + ", fid=" + fid + ", fileURL=" + fileURL
                + ", fileID=" + fileID + "]";
    }

}