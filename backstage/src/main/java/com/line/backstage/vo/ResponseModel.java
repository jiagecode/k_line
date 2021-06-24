package com.line.backstage.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author pc
 * 统一返回相应参数实体类
 */
@Data
@ApiModel("统一返回相应参数实体类")
public class ResponseModel<T> implements Serializable {

    private static final long serialVersionUID = -1241360949457314497L;

    @ApiModelProperty("返回实体")
    private T data;

    @ApiModelProperty("响应消息")
    private String message;

    @ApiModelProperty("响应码")
    private Integer code;

    public ResponseModel(T data) {
        this.data = data;
        this.code = ResultCode.SUCCESS.getCode();
        this.message = ResultCode.SUCCESS.getMessage();
    }

    public ResponseModel(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public ResponseModel(T data, ResultCode resultCode) {
        this.data = data;
        this.message = resultCode.getMessage();
        this.code = resultCode.getCode();
    }

    public ResponseModel(T data, Integer code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }
}
