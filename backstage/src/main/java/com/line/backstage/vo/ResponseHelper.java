package com.line.backstage.vo;


import java.io.Serializable;

/**
 * 统一返回相应参数
 * @author pc
 */
public class ResponseHelper<T> implements Serializable {

    /**
     * 操作成功默认： code + msg
     * @param model
     * @param <T>
     * @return
     */
    public static <T> ResponseModel<T> success(T model) {
        return successWith(model, ResultCode.SUCCESS);
    }

    /**
     * 作成功自定义错误： code + msg
     * @param t
     * @param resultCode
     * @param <T>
     * @return
     */
    public static <T> ResponseModel<T> successWith(T t, ResultCode resultCode) {
        return new ResponseModel<T>(t, resultCode);
    }

    /**
     * 操作失败默认： code + msg
     * @param model
     * @param <T>
     * @return
     */
    public static <T> ResponseModel<T> failed(T model) {
        return failedWith(model, ResultCode.ERROR);
    }

    /**
     * 操作失败自定义错误： code + msg
     * @param t
     * @param resultCode
     * @param <T>
     * @return
     */
    public static <T> ResponseModel<T> failedWith(T t, ResultCode resultCode) {
        return new ResponseModel<>(t, resultCode);
    }

    /**
     * 操作失败自定义错误： code + msg
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseModel<T> failedWith(String message) {
        return new ResponseModel<>(ResultCode.VALIDATE_FAILED.getCode(), message);
    }
}
