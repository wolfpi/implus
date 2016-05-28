package com.baidu.imc.impl.im.callback;

import java.util.LinkedList;
import java.util.List;

import com.baidu.imc.callback.PageableResult;
import com.baidu.imc.message.IMMessage;

public class PageableResultImpl implements PageableResult<IMMessage> {
    private int total = 0;
    private List<IMMessage> list = new LinkedList<IMMessage>();

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<IMMessage> getList() {
        return list;
    }

    public void setList(List<IMMessage> list) {
        this.list = list;
    }

    public void addMessageLast(IMMessage message) {
    	if(message != null)
        ((LinkedList<IMMessage>) list).addLast(message);
    }

    public void addMessageFirst(IMMessage message) {
    	if(message != null)
        ((LinkedList<IMMessage>) list).addFirst(message);
    }

    public void addMessage(IMMessage message) {
    	if(message != null)
        list.add(message);
    }

}
