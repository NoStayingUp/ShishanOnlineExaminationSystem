package com.feidian.exception;

/**
 * 业务异常
 */
public class WrongDataException extends BaseException {

    public WrongDataException() {
    }

    public WrongDataException(String msg) {
        super(msg);
    }

}
