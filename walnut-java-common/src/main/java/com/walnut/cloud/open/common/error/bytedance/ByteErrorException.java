package com.walnut.cloud.open.common.error.bytedance;


public class ByteErrorException extends Exception {

    private static final long serialVersionUID = -6357149550353160810L;

    private final ByteError error;

    private static final int DEFAULT_ERROR_CODE = -99;

    public ByteErrorException(String message) {
        this(ByteError.builder().errorCode(DEFAULT_ERROR_CODE).description(message).build());
    }

    public ByteErrorException(ByteError error) {
        super(error.toString());
        this.error = error;
    }

    public ByteErrorException(ByteError error, Throwable cause) {
        super(error.toString(), cause);
        this.error = error;
    }

    public ByteErrorException(Throwable cause) {
        super(cause.getMessage(), cause);
        this.error = ByteError.builder().errorCode(DEFAULT_ERROR_CODE).description(cause.getMessage()).build();
    }

    public ByteError getError() {
        return this.error;
    }
}
