package com.baidu.im.frame.outapp;

import com.baidu.im.frame.BaseProcessor;
import com.baidu.im.frame.MessageResponser;
import com.baidu.im.outapp.OutAppApplication;

public  class OutAppBaseProcessor extends BaseProcessor {

    public OutAppBaseProcessor(MessageResponser msgListener) {
        super(OutAppApplication.getInstance().getNetworkLayer(), OutAppApplication.getInstance().getNetworkLayer()
                .getSequenceDispatcher(),msgListener);
    }
}
