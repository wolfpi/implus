package com.baidu.im.frame;

public class ProcessorResult {
    private ProcessorCode processorCode;
    private byte[] data;

    public ProcessorResult(ProcessorCode code) {
        this.processorCode = code;
    }

    public ProcessorResult(ProcessorCode code, byte[] data) {
        this.processorCode = code;
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public ProcessorCode getProcessorCode() {
        return processorCode;
    }

    public void setProcessorCode(ProcessorCode processorCode) {
        this.processorCode = processorCode;
    }

}
