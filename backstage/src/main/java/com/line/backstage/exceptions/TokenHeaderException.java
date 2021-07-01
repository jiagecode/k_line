package com.line.backstage.exceptions;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 认证请求头缺失异常
 */
public class TokenHeaderException extends AuthenticationException {

    public TokenHeaderException() {
        super();
    }

    public TokenHeaderException(String msg) {
        super(msg);
    }

    public TokenHeaderException(Throwable cause) {
        super(cause);
    }

    public TokenHeaderException(Throwable cause, String msg) {
        super(msg, cause);
    }

}
