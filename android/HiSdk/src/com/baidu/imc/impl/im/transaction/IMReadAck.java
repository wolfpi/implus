package com.baidu.imc.impl.im.transaction;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.imc.impl.im.store.IMsgStore;
import com.baidu.imc.message.IMInboxEntry;
import com.baidu.imc.type.AddresseeType;

/**
 * 进入会话后，每十秒发送一次readAck
 */
public class IMReadAck {

    private static final long READ_ACK_INTERVAL = 10 * 1000;
    private static final long READ_ACK_DELAY = 300;
    private static final String READ_ACK_THREAD_NAME = "ReadAckThread";

    private ScheduledExecutorService readAckService;
    private String addresserID; // 自己的ID
    private AddresseeType addresseeType;
    private String addresseeID; // 对方的ID
    private IMessageResultCallback callback;
    private IMsgStore msgStore;
    private IMTransactionFlow transactionFlow;

    private class ReadAckThreadFactory implements ThreadFactory {

        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, READ_ACK_THREAD_NAME);
            thread.setDaemon(true);
            return thread;
        }

    }

    public void start(String addresserID, AddresseeType addresseeType, String addresseeID, IMsgStore msgStore,
            IMTransactionFlow transactionFlow, IMessageResultCallback callback) {
        LogUtil.printIm(READ_ACK_THREAD_NAME, "Start ReadAck. AddresserID:" + addresserID + " AddresseeType:"
                + addresseeType + " AddresseeID:" + addresseeID);
        if (null != this.readAckService) {
            this.readAckService.shutdown();
        }
        this.readAckService = Executors.newSingleThreadScheduledExecutor(new ReadAckThreadFactory());
        this.addresserID = addresserID;
        this.addresseeType = addresseeType;
        this.addresseeID = addresseeID;
        this.msgStore = msgStore;
        this.transactionFlow = transactionFlow;
        this.callback = callback;
        readAckService.scheduleAtFixedRate(new ReadAckThread(), READ_ACK_INTERVAL, READ_ACK_INTERVAL,
                TimeUnit.MILLISECONDS);
    }

    public void stop() {
        LogUtil.printIm(READ_ACK_THREAD_NAME, "Stop ReadAck. AddresserID:" + addresserID + " AddresseeType:"
                + addresseeType + " AddresseeID:" + addresseeID);
        if (null != readAckService) {
            readAckService.schedule(new ReadAckThread(), READ_ACK_DELAY, TimeUnit.MILLISECONDS);
            readAckService.shutdown();
        }
    }

    private class ReadAckThread implements Runnable {

        @Override
        public void run() {
            if (null != transactionFlow && null != addresseeType && null != addresseeID && addresseeID.length() > 0) {
                IMInboxEntry entry = msgStore.clearUnread(addresseeType, addresseeID);
                if (null != entry) {
                    LogUtil.printIm(READ_ACK_THREAD_NAME, "Send ReadAck. AddresserID:" + addresserID
                            + " AddresseeType:" + addresseeType + " AddresseeID:" + addresseeID);
                    transactionFlow.readAck(addresseeType, addresserID, addresseeID, entry, callback);
                } else {
                    LogUtil.printIm(READ_ACK_THREAD_NAME, "Do not need ReadAck. AddresserID:" + addresserID
                            + " AddresseeType:" + addresseeType + " AddresseeID:" + addresseeID);
                }
            }
        }

    }
}
