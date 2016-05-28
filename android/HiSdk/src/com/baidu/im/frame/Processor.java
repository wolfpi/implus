package com.baidu.im.frame;

import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;

/**
 * 最小的业务处理者，处理某一个具体的业务点。
 * 
 * @author zhaowei10
 * 
 */
public interface Processor {
	
	 final static String TIME = "time";
	 final static String KEY = "key";
	 final static String ACTION = "action";
	 final static String RCODE = "rcode";
	 final static String COST = "cost";
	 final static String CLIENT = "client";
	 final static String PROTOCOL = "protocol";
	 final static String ID = "id";
	 final static String NAME = "name";
	    

    Timeout getTimeout();

    boolean send(UpPacket upPacket);
    void    sendReconnect();
}
