package com.jsz.usercenter.exception;

import com.jsz.usercenter.common.ErrorCode;

/**
 * @Title: BusinessException
 * @Author jsz
 * @Package com.jsz.usercenter.exception
 */
public class BusinessException extends RuntimeException {
    private final int  code;
    private final String  description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }
    public BusinessException(ErrorCode errorCode,String description) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
