package com.baidu.imc.impl.im.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.client.LocalResourceManager;
import com.baidu.imc.impl.im.message.BDHiFile;
import com.baidu.imc.impl.im.transaction.callback.IMTransactionFileDownloadCallback;
import com.baidu.imc.impl.im.transaction.callback.IMTransactionFileUploadCallback;

/**
 * 分配上下行文件请求，並且统一处理返回结果。
 * 
 * @author liubinzhe
 */
public class IMFileTransactionFlow {

    private static final String TAG = "FileTransactionFlow";
    private Map<BDHiFile, List<FileUploadEntry>> uploadFileMap =
            new ConcurrentHashMap<BDHiFile, List<FileUploadEntry>>();
    private Map<BDHiFile, List<FileDownloadEntry>> downloadFileMap =
            new ConcurrentHashMap<BDHiFile, List<FileDownloadEntry>>();

    private class FileUploadEntry {

        public BDHiFile hiFile;
        public IMTransactionFileUploadCallback callback;

        public FileUploadEntry(BDHiFile hiFile, IMTransactionFileUploadCallback callback) {

            this.hiFile = hiFile;
            this.callback = callback;
        }
    }

    private class FileDownloadEntry {

        public BDHiFile hiFile;
        public IMTransactionFileDownloadCallback callback;

        public FileDownloadEntry(BDHiFile hiFile, IMTransactionFileDownloadCallback callback) {
            this.hiFile = hiFile;
            this.callback = callback;
        }

    }

    /**
     * Put BDHiFile and IMTransactionFileUploadCallback into Map
     * 
     * @param bdHiFile
     * @param callback
     * 
     * @return true if it is already existed
     */
    private synchronized boolean putUploadFile(BDHiFile bdHiFile, IMTransactionFileUploadCallback callback) {
        if (!uploadFileMap.containsKey(bdHiFile)) {
            LogUtil.printIm(TAG, "[putUploadFile] Put a new file");
            List<FileUploadEntry> list = new ArrayList<FileUploadEntry>();
            FileUploadEntry entry = new FileUploadEntry(bdHiFile, callback);
            list.add(entry);
            uploadFileMap.put(bdHiFile, list);
            return true;
        } else {
            LogUtil.printIm(TAG, "[PutUploadFile] Put a already existed file");
            List<FileUploadEntry> list = uploadFileMap.get(bdHiFile);
            FileUploadEntry entry = new FileUploadEntry(bdHiFile, callback);
            if(list != null)
            	list.add(entry);
            return false;
        }
    }

    /**
     * fileUploadCallback when fileUpload finished
     * 
     * @param bdHiFile
     * @param callback
     */
    public void uploadFileCallback(BDHiFile bdHiFile, boolean result) {
        List<FileUploadEntry> entryList = uploadFileMap.remove(bdHiFile);
        if (null != entryList && !entryList.isEmpty()) {
            for (FileUploadEntry entry : entryList) {
                if (null != entry && null != entry.callback) {
                	entry.hiFile.setFid(bdHiFile.getFid());
                    entry.callback.onIMTransactionFileUploadCallback(entry.hiFile, result);
                }
            }
        }
    }

    /**
     * Put BDHiFile and IMTransactionFileDownloadCallback into Map
     * 
     * @param bdHiFile
     * @param callback
     * 
     * @return true if it is a new file.
     */
    private synchronized boolean putDownloadFile(BDHiFile bdHiFile, IMTransactionFileDownloadCallback callback) {
        if (!downloadFileMap.containsKey(bdHiFile)) {
            LogUtil.printIm(TAG, "[putDownloadFile] Put a new file.");
            List<FileDownloadEntry> list = new ArrayList<FileDownloadEntry>();
            FileDownloadEntry entry = new FileDownloadEntry(bdHiFile, callback);
            list.add(entry);
            downloadFileMap.put(bdHiFile, list);
            return true;
        } else {
            LogUtil.printIm(TAG, "[putDownloadFile] Put a already existed file.");
            List<FileDownloadEntry> list = downloadFileMap.get(bdHiFile);
            FileDownloadEntry entry = new FileDownloadEntry(bdHiFile, callback);
            list.add(entry);
            return false;
        }
    }

    /**
     * fileDownloadCallback when fileDownload finished
     * 
     * @param bdHiFile
     * @param callback
     */
    public void downloadFileCallback(BDHiFile bdHiFile, boolean result) {
        List<FileDownloadEntry> entryList = downloadFileMap.remove(bdHiFile);
        if (null != entryList && !entryList.isEmpty()) {
            for (FileDownloadEntry entry : entryList) {
                if (null != entry && null != entry.callback) {
                    entry.callback.onIMTransactionFileDownloadCallback(entry.hiFile, result);
                }
            }
        }

    }

    /**
     * Upload a local file(do not need to validate parameters)
     * 
     * @param localResourceManager
     * @param bosHost
     * @param hiFile
     * @param callback
     */
    public void uploadFile(LocalResourceManager localResourceManager, String bosHost, BDHiFile hiFile,
            IMTransactionFileUploadCallback callback) {
        LogUtil.printIm(TAG, "[uploadFile] file:" + (null != hiFile ? hiFile.toString() : "") + " host:"
                + (null != bosHost ? bosHost : ""));
        if (putUploadFile(hiFile, callback)) {
            IMTransactionStart transaction = new IMUploadFileTransaction(localResourceManager, bosHost, hiFile, this);
            try {
                transaction.startWorkFlow();
            } catch (Exception e) {
                LogUtil.printImE(TAG, "[uploadFile]", e);
                uploadFileCallback(hiFile, false);
            }
        }
    }

    /**
     * Download a file from server(do not need to validate parameters)
     * 
     * @param localResourceManager
     * @param bosHost
     * @param fid
     * @param callback
     */
    public void downloadFile(LocalResourceManager localResourceManager, String bosHost, BDHiFile bdHiFile,
            IMTransactionFileDownloadCallback callback) {
        LogUtil.printIm(TAG, "[downloadFile]  file:" + (null != bdHiFile ? bdHiFile.toString() : "") + " host:"
                + (null != bosHost ? bosHost : ""));
        if (putDownloadFile(bdHiFile, callback)) {
            IMTransactionStart transaction =
                    new IMDownloadFileTransaction(localResourceManager, bosHost, bdHiFile, this);
            try {
                transaction.startWorkFlow();
            } catch (Exception e) {
                LogUtil.printImE(TAG, "[downloadFile]", e);
                downloadFileCallback(bdHiFile, false);
            }
        }
    }
}
