
package com.my.common.exception;

/**
 *自定义逻辑异常
 */
public class CPBusinessException extends RuntimeException {

    private int code;

    public CPBusinessException() {
    }

    public CPBusinessException(String message) {
        super(message);
    }

    public CPBusinessException(String message, int code){
        super(message);
        this.code=code;
    }

    public CPBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public CPBusinessException(Throwable cause) {
        super(cause);
    }

    public static void throwIt(String msg) {
        throw new CPBusinessException(msg);
    }

    public static void throwIt(String msg,int code){
        throw new CPBusinessException(msg,code);
    }

    public static void throwIt(String msg, Throwable cause) {
        throw new CPBusinessException(msg, cause);
    }
    public static void throwIt(String param, String msg) {
        throw new CPBusinessException(msg);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
