package com.line.backstage.enums;

import lombok.Getter;

/**
 * @author pc
 */
@Getter
public enum DataEnum {

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