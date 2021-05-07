package com.zhong.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by cc on 2021/4/14
 */
public class BizException extends RuntimeException {

    private static final String ATTR_CODE = "errorCode";
    private static final String ATTR_DESC = "errorDesc";

    private static final String ATTR_CLASS = "className";

    public BizException(BaseErrorEnum baseErrorEnum) {
        super(new JSONObject().fluentPut(ATTR_CODE, baseErrorEnum.getCode())
                .fluentPut(ATTR_DESC, baseErrorEnum.getMessage())
                .fluentPut(ATTR_CLASS, BizException.class.getName())
                .toJSONString());
    }

    public <T> BizException(T errorCode, String message) {
        super(new JSONObject().fluentPut(ATTR_CODE, errorCode).fluentPut(ATTR_DESC, message)
                .fluentPut(ATTR_CLASS, BizException.class.getName())
                .toJSONString());
    }

    public BizException(BaseErrorEnum baseErrorEnum, Throwable cause) {
        super(new JSONObject().fluentPut(ATTR_CODE, baseErrorEnum.getCode())
                .fluentPut(ATTR_DESC, baseErrorEnum.getMessage())
                .fluentPut(ATTR_CLASS, BizException.class.getName())
                .toJSONString(), cause);
    }

    public String extractCode() {
        return JSON.parseObject(super.getMessage()).getString(ATTR_CODE);
    }

    public String extractMessage() {
        return JSON.parseObject(super.getMessage()).getString(ATTR_DESC);
    }
}
