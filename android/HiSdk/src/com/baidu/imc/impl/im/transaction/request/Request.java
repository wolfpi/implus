package com.baidu.imc.impl.im.transaction.request;

public interface Request<T> {

    public T createRequest();

    public String getRequestName();
}
