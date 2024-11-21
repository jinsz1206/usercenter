package com.jsz.usercenter.utils;

import com.jsz.usercenter.common.BaseResponse;
import com.jsz.usercenter.common.ErrorCode;

/**
 * @Title: ResultUtils
 * @Author jsz
 * @Package com.jsz.usercenter.utils
 */
public class ResultUtils {
    /**
     *  成功
     * @param data
     * @return
     * @param <T>
     */
    public static <T>BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "success");
    }

    /**
     * 失败
     * @param errorCode
     * @return
     * @param <T>
     */
    public static <T>BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }



    public static BaseResponse error(int code, String message,String description) {
        return new BaseResponse(code, null, message, description);
    }

    public static BaseResponse error(ErrorCode errorCode, String message,String description) {
        return new BaseResponse(errorCode.getCode(), null, message, description);
    }

    public static BaseResponse error(ErrorCode errorCode,String description) {
        return new BaseResponse(errorCode.getCode(), null, description);
    }


}
