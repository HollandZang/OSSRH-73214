package com.holland.net.exception;

public enum NetExceptionEnum {
    EMPTY_BODY("Response body is empty!");

    public final String msg;

    NetExceptionEnum(String msg) {
        this.msg = msg;
    }

    public NetException e() {
        return new NetException(this.msg);
    }

    public static class NetException extends RuntimeException {
        public NetException(String message) {
            super(message);
        }
    }
}
