package com.baidu.im.inapp.transaction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import android.annotation.SuppressLint;

import com.baidu.im.frame.BizTransaction;
import com.baidu.im.frame.ITransResend;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.sdk.IMessageCallback;

public class TransactionResend implements ITransResend {

    static final String TAG = "Transaction Resend";

    class ResendData {
        public BizTransaction mBiz = null;
        public IMessageCallback mCallback = null;
        public int tranId = 0;
    }

    @SuppressLint("UseSparseArrays")
    private Map<Integer, ResendData> transMap = new HashMap<Integer, ResendData>();
    private LinkedList<ResendData> mResendList = new LinkedList<ResendData>();

    public void addTransaction(int tranId, BizTransaction tran, IMessageCallback callback) {
        ResendData rData = new ResendData();
        rData.mBiz = tran;
        rData.mCallback = callback;
        rData.tranId = tranId;

        removeTransaction(tranId);
        synchronized (TransactionResend.class) {
            transMap.put(tranId, rData);
            mResendList.add(rData);
        }
    }

    public void removeTransaction(int tranId) {
        synchronized (TransactionResend.class) {
            if (transMap.containsKey(tranId)) {
                transMap.remove(tranId);
                removeTrans(tranId);
            }
        }
    }

    private void removeTrans(int tranId) {
        for (Iterator<ResendData> i = mResendList.iterator(); i.hasNext();) {
            ResendData rd = (ResendData) i.next();
            if (rd.tranId == tranId) {
                i.remove();
            }
        }
    }

    public void resendAll() {
        if (transMap.size() > 0) {
            LogUtil.e(TAG, String.format("resend size is:%d,%d", transMap.size(), mResendList.size()));
            synchronized (TransactionResend.class) {
                LinkedList<ResendData> copy = new LinkedList<ResendData>(mResendList);
                mResendList.clear();
                for (Iterator<ResendData> i = copy.iterator(); i.hasNext();) {
                    ResendData rd = (ResendData) i.next();
                    rd.mBiz.startWorkFlow(rd.mCallback);
                }
                LogUtil.e(TAG, "resend all after connect");
            }
        }
    }
    
    public void clearall() {
    	if (transMap.size() > 0) {
    		synchronized (TransactionResend.class) {
    			transMap.clear();
    			mResendList.clear();
    		}
    	}
    }
}
