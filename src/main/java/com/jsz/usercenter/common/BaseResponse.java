package com.jsz.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Title: BaseResponse
 * @Author jsz
 * @Package com.jsz.usercenter.common
 */

@Data
public class BaseResponse<T> implements Serializable {
    private int code;

    private String msg;

    private T data;

    private String description;

    public BaseResponse(int code, T data,String msg, String description) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.description = description;
    }

    public BaseResponse(int code,T data,String msg) {
        this(code,data,msg,"");
    }

    public BaseResponse(int code, T data) {
        this(code,data,"","");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(),null,errorCode.getMsg(),errorCode.getDescription());

    }

}
