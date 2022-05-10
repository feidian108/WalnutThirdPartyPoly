package com.walnut.cloud.open.common.error.bytedance;


public class ByteRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 4881698471192264412L;

    public ByteRuntimeException(Throwable e) {
        super(e);
    }

    public ByteRuntimeException(String msg) {
        super(msg);
    }

    public ByteRuntimeException(String msg, Throwable e) {
        super(msg, e);
    }
}
