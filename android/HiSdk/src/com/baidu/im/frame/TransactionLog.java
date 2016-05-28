package com.baidu.im.frame;

public interface TransactionLog {

    final static String TIME = "time";
    final static String KEY = "key";
    final static String ACTION = "action";
    final static String RCODE = "rcode";
    final static String COST = "cost";
    final static String CLIENT = "client";
    final static String PROTOCOL = "protocol";

    final static String ID = "id";
    final static String NAME = "name";

    void startProcessor(String processorName);

    void endProcessor(String processorName, int seq, ProcessorResult processorResult);

    void transactionCallback(int transactionHashCode, ProcessorResult transactionResult);

    void transactionStart(int transactionHashCode);

}
