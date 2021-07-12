package com.line.backstage.annotation.support;

import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.shiro.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 注解
 * @author pc
 */
public class UserIdHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String LOGIN_TOKEN_KEY = "Authorization";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {

        Class<?> class1 = methodParameter.getParameterType();
        return methodParameter.hasParameterAnnotation(LoginUserId.class) && class1.isAssignableFrom(String.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {

        String token = nativeWebRequest.getHeader(LOGIN_TOKEN_KEY);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        if (methodParameter.getParameterType().isAssignableFrom(String.class)) {
            String userId = JwtUtil.getUserId(token);
            return userId;
        }
        return null;
    }
}
