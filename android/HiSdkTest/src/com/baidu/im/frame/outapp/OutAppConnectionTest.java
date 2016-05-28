package com.baidu.im.frame.outapp;

import android.test.AndroidTestCase;

public class OutAppConnectionTest extends AndroidTestCase {

    public void testOutAppConnection() {

    	OutAppConnection oc = new OutAppConnection(null,null,null);
    	oc.send(null);
    	oc.getBinder();
    	oc.getSeqDisp();
    	oc.onUnbind();
    	oc.send(0, 0,null);
    	oc.sendNetworkChange(null, null);
    }
}
