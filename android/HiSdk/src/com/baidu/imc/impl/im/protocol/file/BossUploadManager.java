package com.baidu.imc.impl.im.protocol.file;


public class BossUploadManager {
    // public BossUploadManager() {
    // }
    //
    // private void setFileInfo(BDHiFile file, String ID, String fileName, int fileSize, String localeFilePath,
    // String md5, String url) {
    // file.setFileName(fileName);
    // file.setFileSize(fileSize);
    // file.setID(ID);
    // file.setLocaleFilePath(localeFilePath);
    // file.setMD5(md5);
    // file.setURL(url);
    // }
    //
    // private BDHiFile getCustomFile(String ID, String fileName, int fileSize, String localeFilePath, String md5,
    // String url) {
    // BDHiFile file = new BDHiFile();
    //
    // this.setFileInfo(file, ID, fileName, fileSize, localeFilePath, md5, url);
    //
    // return file;
    // }
    //
    // public void uploadFile(File fileInfo, ProgressListener<BDHiFile> progressListener) {
    // // 判断文件合法性
    // InputStream fin = ResourceManager.getLocalResourceManager().getFileInputStream(fileInfo.getAbsolutePath());
    // if (fin == null) {
    // progressListener.onError(400, "file not exist");
    // return;
    // }
    // int fileLength = 0;
    //
    // progressListener.onProgress(0.1f);
    // try {
    // // 获取文件签名
    // MessageDigest sha256Digest = MessageDigest.getInstance("SHA-256");
    // MessageDigest md5Digest = MessageDigest.getInstance("MD5");
    // byte[] buffer = new byte[1024];
    // int readBytes;
    // try {
    // while ((readBytes = fin.read(buffer, 0, 1024)) >= 0) {
    // if (readBytes > 0) {
    // fileLength += readBytes;
    // sha256Digest.update(buffer, 0, readBytes);
    // md5Digest.update(buffer, 0, readBytes);
    // }
    // }
    // } finally {
    // fin.close();
    // }
    //
    // String sha256 = HexUtil.hex(sha256Digest.digest());
    // String md5 = HexUtil.hex(md5Digest.digest());
    //
    // progressListener.onProgress(0.2f);
    //
    // GetUploadSignReq reqBuilder = new  GetUploadSignReq();  // migrate from builder
    // reqBuilder.addReqList(GetUploadSignReqItem.newBuilder().setBossHost("10.105.97.15").setBmd5(sha256)
    // .setFileName(fileInfo.getName()).setFileSize((int) fileLength).setFileType(EFileType.FILE_CUSTOM)
    // .setMd5(md5));
    //
    // final CountDownLatch lock = new CountDownLatch(1);
    // final GetUploadSignRsp getUploadSignRspBuilder = new  GetUploadSignRsp();  // migrate from builder
    // FileProtocol.getUploadSign(reqBuilder, new IMessageResultCallback() {
    //
    // @Override
    // public void onSuccess(String description, byte[] data) {
    // try {
    // getUploadSignRspBuilder.mergeFrom(data);
    // } catch (Exception e) {
    //
    // }
    //
    // lock.countDown();
    // }
    //
    // @Override
    // public void onFail(int errorCode) {
    // lock.countDown();
    // }
    //
    // });
    //
    // lock.await(5000, TimeUnit.SECONDS);
    // if (getUploadSignRspBuilder.getRspListCount() == 0) {
    // progressListener.onError(500, "get upload sign failed");
    // return;
    // }
    // GetUploadSignRspItem rspItem = getUploadSignRspBuilder.getRspList(0);
    // if (rspItem.getExist()) {
    // BDHiFile file =
    // this.getCustomFile(rspItem.getFid(), fileInfo.getName(), fileLength,
    // fileInfo.getAbsolutePath(), md5, "imp://" + rspItem.getFid());
    // progressListener.onSuccess(file);
    // return;
    // }
    //
    // progressListener.onProgress(0.4f);
    //
    // // 上传到boss
    // fin = ResourceManager.getLocalResourceManager().getFileInputStream(fileInfo.getAbsolutePath());
    // try {
    // boolean result =
    // FileProtocol.uploadFileToBOSS("10.105.97.15", rspItem.getUploadUrl(), rspItem.getSign(),
    // sha256, fin, (int) fileLength);
    // if (!result) {
    // progressListener.onError(500, "upload to BOSS failed");
    // return;
    // }
    // } finally {
    // try {
    // fin.close();
    // } catch (Exception e) {
    //
    // }
    // }
    //
    // progressListener.onProgress(0.9f);
    //
    // // 上报成功
    // final UploadSuccessRsp uploadSuccessRspBuilder = new  UploadSuccessRsp();  // migrate from builder
    // final Result result = new Result();
    // final CountDownLatch uploadSuccessLock = new CountDownLatch(1);
    // FileProtocol.uploadSuccess(rspItem.getFid(), new IMessageResultCallback() {
    //
    // @Override
    // public void onSuccess(String description, byte[] data) {
    // try {
    // uploadSuccessRspBuilder.mergeFrom(data);
    // result.isSuccess = true;
    // } catch (Exception e) {
    // result.isSuccess = false;
    // }
    //
    // uploadSuccessLock.countDown();
    // }
    //
    // @Override
    // public void onFail(int errorCode) {
    // result.isSuccess = false;
    // uploadSuccessLock.countDown();
    // }
    //
    // });
    // uploadSuccessLock.await(5000, TimeUnit.SECONDS);
    // if (!result.isSuccess) {
    // progressListener.onError(500, "get upload sign failed");
    // return;
    // }
    //
    // BDHiFile file =
    // this.getCustomFile(rspItem.getFid(), fileInfo.getName(), fileLength, fileInfo.getAbsolutePath(),
    // md5, "imp://" + rspItem.getFid());
    //
    // progressListener.onSuccess(file);
    // } catch (Exception e) {
    // progressListener.onError(500, e.getMessage());
    // return;
    // }
    // }
    //
    // private static class Result {
    // public boolean isSuccess = false;
    // }
}
