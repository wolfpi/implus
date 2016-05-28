/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.frame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.utils.LogUtil;

/**
 * 封装了收到协议分发执行的逻辑。
 * 
 * @author zhaowei10
 * 
 */
public class SequenceDispatcher {

    public static final String TAG = "SequenceDispatcher";

    private List<SequenceListener> list = Collections.synchronizedList(new ArrayList<SequenceListener>());

    public void register(SequenceListener sequenceListener) {
    	if(sequenceListener != null)
            list.add(sequenceListener);
    }

    public void unRegister(SequenceListener sequenceListener) {
    	if(sequenceListener != null) {
    	    list.remove(sequenceListener);
    	}
    }

    public final boolean dispatch(DownPacket downPacket) {

    	if(downPacket == null)
    		return false;
        final int size = list.size();

        for (int i = 0; i < size; i++) {

            try {
                if (list.get(i).getListenSeq() == downPacket.getSeq()) {
                    if (list.get(i).onReceive(downPacket)) {
                        return true;
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                LogUtil.e(TAG, "Sequence listener has been removed. (" + i + "/" + size + ")");
            } catch (RuntimeException e1) {
                LogUtil.e(TAG, "Error when loop sequence listener.", e1);
            }
        }

        return false;
    }
}
