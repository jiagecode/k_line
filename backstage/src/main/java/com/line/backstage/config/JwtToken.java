package com.line.backstage.config;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 自定义token
 * @author scxfsc
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
