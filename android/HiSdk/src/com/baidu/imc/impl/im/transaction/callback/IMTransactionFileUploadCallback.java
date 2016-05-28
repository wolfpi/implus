package com.baidu.imc.impl.im.transaction.callback;

import com.baidu.imc.impl.im.message.BDHiFile;

public interface IMTransactionFileUploadCallback {
    public void onIMTransactionFileUploadCallback(BDHiFile bdhiFile,boolean result);
}
