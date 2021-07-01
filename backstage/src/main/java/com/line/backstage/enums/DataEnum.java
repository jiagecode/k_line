package com.line.backstage.enums;

import lombok.Getter;

/**
 * @author pc
 */
@Getter
public enum DataEnum {

    LOGIN_STATE_EXPIRED(1000, "认证失败，请重新登陆"),

    USER_FORBID_FLAG(1, "已禁用"),
    USER_DEL_STATUS(1, "账号已主动注销"),
    USER_ERROR(1, "账号或密码错误"),
    FLAG_STATUS_INVALID(1, "数据有效"),
    FLAG_STATUS_VALID(0, "数据无效");

    /**
     * 请求响应码
     */
    private Integer code;

    /**
     * 请求响应说明
     */
    private String desc;

    DataEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}