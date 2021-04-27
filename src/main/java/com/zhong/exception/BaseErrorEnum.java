package com.zhong.exception;

/**
 * Created by cc on 2021/4/14
 */

public interface BaseErrorEnum {

    /**
     * 错误码
     */
    String getCode();

    /**
     * 错误信息
     */
    String getMessage();

    /**
     * 临时构建一个BaseErrorEnum对象
     */
    default BaseErrorEnum of(String code, String message) {
        return new BaseErrorEnum() {
            @Override
            public String getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return message;
            }
        };
    }

}















