package com.jsz.usercenter.exception;

import com.jsz.usercenter.common.BaseResponse;
import com.jsz.usercenter.common.ErrorCode;
import com.jsz.usercenter.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Title: GlobalExceptionHandler
 * @Author jsz
 * @Package com.jsz.usercenter.exception
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        log.error("runtimeException" + e.getMessage(), e);
        return  ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());

    }

    @ExceptionHandler(RuntimeException.class)
    private BaseResponse runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException" + e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"");
    }
}
