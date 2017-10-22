package com.my.common.exception;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 11:47 2017/7/4
 */
public class SystemException extends RuntimeException {
    private static final long serialVersionUID = 7769982372460790248L;

    public SystemException() {
    }

    protected SystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }
}
