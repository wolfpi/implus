package com.baidu.im.sdk;



public interface IMessageProgressCallback extends IMessageCallback {

    /**
     * Start to deal with;
     */
    void onStart();

    /**
     * Operation over.
     */
    void onStop();

    /**
     * progress = x %, return x.
     * 
     * @return
     */
    void onProgress(ProgressEnum progress);
}
