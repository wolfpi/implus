package com.baidu.imc.impl.im.protocol.file;


public class BossDownloadManager {

    // public BossDownloadManager() {
    //
    // }
    //
    // public void downloadFile(final BDHiFile file, ProgressListener<BDHiFile> progressListener) {
    // try {
    // final CountDownLatch lock = new CountDownLatch(1);
    // final GetDownloadSignRsp getDownloadSignRspBuilder = new  GetDownloadSignRsp();  // migrate from builder
    // // 获取签名
    // FileProtocol.getDownloadSign(file.getID(), new IMessageResultCallback() {
    //
    // @Override
    // public void onSuccess(String description, byte[] data) {
    // try {
    // getDownloadSignRspBuilder.mergeFrom(data);
    // } catch (Exception e) {
    //
    // }
    // lock.countDown();
    // }
    //
    // @Override
    // public void onFail(int errorCode) {
    // lock.countDown();
    // }
    //
    // });
    // lock.await(5000, TimeUnit.SECONDS);
    // if (getDownloadSignRspBuilder == null || getDownloadSignRspBuilder.getRspListCount() <= 0) {
    // progressListener.onError(500, "get download sign failed");
    // return;
    // }
    //
    // // 下载内容
    // final CountDownLatch saveLock = new CountDownLatch(1);
    // final Result saveResult = new Result();
    //
    // PipedInputStream pis = new PipedInputStream();
    // PipedOutputStream pos = new PipedOutputStream();
    // pis.connect(pos);
    // ResourceManager.getLocalResourceManager().saveFile(pis, new ResultCallback<String>() {
    //
    // @Override
    // public void result(String result, Throwable arg1) {
    // if (null != result && result.length() > 0) {
    // saveResult.isSuccess = true;
    // ((BDHiFile) file).setLocaleFilePath(result);
    // saveLock.countDown();
    // } else {
    // saveLock.countDown();
    // }
    // }
    // });
    //
    // boolean downloadResult =
    // FileProtocol.downloadFileFromBOSS("10.105.97.15", getDownloadSignRspBuilder.getRspList(0)
    // .getDownloadUrl(), getDownloadSignRspBuilder.getRspList(0).getSign(), pos);
    // if (!downloadResult) {
    // progressListener.onError(500, "download failed");
    // return;
    // }
    // saveLock.await(5, TimeUnit.SECONDS);
    // if (!saveResult.isSuccess) {
    // progressListener.onError(500, "save file failed");
    // return;
    // }
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
