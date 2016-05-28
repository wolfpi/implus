package com.baidu.im.frame.inapp;

import com.baidu.im.frame.BaseProcessor;
import com.baidu.im.frame.MessageResponser;

public class InAppBaseProcessor extends BaseProcessor {

    public InAppBaseProcessor(MessageResponser msgListener) {
        super(InAppApplication.getInstance().getInAppConnection(), InAppApplication.getInstance().getInAppConnection()
                .getSequenceDispatcher(),  msgListener);
    }
}
