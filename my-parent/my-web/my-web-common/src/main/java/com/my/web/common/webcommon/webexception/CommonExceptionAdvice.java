package com.my.web.common.webcommon.webexception;

import com.my.biz.common.interactive.SingleResponse;
import com.my.biz.common.interactive.ViewInfo;
import com.my.common.enumer.Code;
import com.my.common.exception.CPBusinessException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 自定义全局异常
 * Created by 96230 on 2017/5/29.
 */
@ControllerAdvice
@ResponseBody
public class CommonExceptionAdvice {
    private static Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);
    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CPBusinessException.class)
    public ViewInfo handleServiceException(CPBusinessException e) {
        logger.error("业务逻辑异常", e);
        return ViewInfo.from(new SingleResponse().failure(e.getMessage(),e.getCode()));
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ViewInfo runtimeException(RuntimeException e) {
        logger.error("业务逻辑异常", e);
        return ViewInfo.from(new SingleResponse().failure(Code.INTERNALERROR.getMessage(),Code.INTERNALERROR.getCode()));
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ViewInfo runtimeException(Exception e) {
        logger.error("业务逻辑异常", e);
        return ViewInfo.from(new SingleResponse().failure(Code.INTERNALERROR.getMessage(),Code.INTERNALERROR.getCode()));
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AuthenticationException.class)
    public ViewInfo runtimeException(AuthenticationException e) {
        logger.error("业务逻辑异常", e);
        return ViewInfo.from(new SingleResponse().failure(Code.ACCOUNTORPASSERROR.getMessage(),Code.ACCOUNTORPASSERROR.getCode()));
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DisabledAccountException.class)
    public ViewInfo runtimeException(DisabledAccountException e) {
        logger.error("业务逻辑异常", e);
        return ViewInfo.from(new SingleResponse().failure(Code.ACCOUNTLOCK.getMessage(),Code.ACCOUNTLOCK.getCode()));
    }

}
