package com.baidu.imc.impl.im.transaction.callback;

import com.baidu.imc.impl.im.message.BDHiFile;

public interface IMTransactionFileDownloadCallback {
    public void onIMTransactionFileDownloadCallback(BDHiFile bdhiFile, boolean result);
}
