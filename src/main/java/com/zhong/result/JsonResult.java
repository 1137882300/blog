package com.zhong.result;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Created by cc on 2021/4/14
 */
@Slf4j
@Data
public class JsonResult<T> implements Serializable {

    public static final String SUCCESS_CODE = "200";
    public static final String ERROR_CODE = "500";
    public static final String SUCCESS_MESSAGE = "操作成功!";

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5748989360885116334L;





    private String code;

    private Boolean success;

    private String message;


    private T result;

    public JsonResult() {
        this(true, SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public JsonResult(boolean isSuccess, String code, String message) {
        this.success = isSuccess;
        this.code = code;
        this.message = message;
    }
    public static <T> JsonResult<T> success() {
        return new JsonResult<T>(true, SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public static <T> JsonResult<T> success(T data) {
        JsonResult<T> result = new JsonResult<T>(true, SUCCESS_CODE, SUCCESS_MESSAGE);
        result.setResult(data);
        return result;
    }

    public static <T> JsonResult<T> error(String message) {
        return new JsonResult<T>(false, ERROR_CODE, message);
    }

    public static <T> JsonResult<T> error(T data, String message) {
        JsonResult<T> result = new JsonResult<T>(false, ERROR_CODE, message);
        result.setResult(data);
        return result;
    }


    public static <T> JsonResult<T> error(String message, String code) {
        JsonResult<T> result = new JsonResult<T>(false, code, message);
        result.setCode(code);
        return result;
    }

    public String toBriefString() {
        return "JsonResult(code=" + this.getCode() + ", success=" + this.getSuccess() + ", message="
                + this.getMessage() + ")";
    }

}
