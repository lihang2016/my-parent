package com.my.common.exception;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 11:19 2017/7/4
 */
public class AppException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AppException() {
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
