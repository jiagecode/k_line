package com.line.backstage.exceptions;

import com.line.backstage.vo.ResultCode;
import lombok.Getter;

/**
 * 针对继承的异常封装响应码和响应信息
 * @author BaoBao
 */
@Getter
public class ApiException extends RuntimeException{

    private static final long serialVersionUID = 3455708526465670030L;

    public int code;
    public String msg;

    /**
     * 指定响应码和其它响应信息
     * @param msg
     */
    public ApiException(String msg){

        this(ResultCode.ERROR.getCode(), msg);
    }

    /**
     * 其它响应码和响应信息
     * @param code
     * @param msg
     */
    public ApiException(int code, String msg){

        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
