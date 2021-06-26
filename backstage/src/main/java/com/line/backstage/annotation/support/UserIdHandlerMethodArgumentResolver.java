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
 * userId注解
 * @author pc
 * 接口说明：
 * supportsParameter：用于判定是否需要处理该参数分解，返回true为需要，并会去调用下面的方法resolveArgument。
 * resolveArgument：真正用于处理参数分解的方法，返回的Object就是controller方法上的形参对象。
 */
public class UserIdHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 获取header中的key
     */
    public static final String LOGIN_TOKEN_KEY = "Authorization";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {

        Class<?> class1 = methodParameter.getParameterType();
        // class1.isAssignableFrom(Class<?>2) 判定此 Class1 对象所表示的类或接口与指定的 Class<?>2 参数所表示的类或接口是否相同，或是否是其超类或超接口。如果是则返回 true；否则返回 false。
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
