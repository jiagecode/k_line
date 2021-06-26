package com.line.backstage.vo;

import lombok.Getter;

/**
 * @author BaoBao
 */
@Getter
public enum ResultCode {

    // 请求信息
    SUCCESS(10000, "操作成功"),
    FAILED(10001, "响应失败"),
    VALIDATE_FAILED(10002, "参数校验失败"),

    // 系统信息
    ERROR(50000, "未知错误"),
    INDEX_OUT_OF_BOUNDS(50001, "下标越界");
    /**
     * 请求响应码
     */
    private Integer code;

    /**
     * 请求响应说明
     */
    private String message;

    ResultCode(int code, String message){
        this.code = code;
        this.message = message;
    }
}
