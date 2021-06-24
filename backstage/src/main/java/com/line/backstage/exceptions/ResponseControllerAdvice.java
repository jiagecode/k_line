package com.line.backstage.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.line.backstage.vo.ResponseModel;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局数据响应处理
 * 加上需要扫描的包
 * @author BaoBao
 */
@RestControllerAdvice(basePackages = {"com.earn.money.app.controller", "com.earn.money.admin.controller", "com.earn.money.public.controller"})
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 重写的这两个方法是用来在controller将数据进行返回前进行增强操作，
     * supports方法要返回为true才会执行beforeBodyWrite方法，
     * 所以如果有些情况不需要进行增强操作可以在supports方法里进行判断。
     * 对返回数据进行真正的操作还是在beforeBodyWrite方法中，
     * 我们可以直接在该方法里包装数据，这样就不需要每个接口都进行数据包装
     * 错误情况可包装，正常返回不用包装
     */

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果接口返回的类型本身就是 ResponseModel 那就没有必要进行额外的操作，返回false
        boolean result = methodParameter.getGenericParameterType().equals(ResponseModel.class);
        return !result;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        // String类型不能直接包装，所以要进行些特别的处理
        if (methodParameter.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在ResultVO里后，再转换为json字符串响应给前端
                return objectMapper.writeValueAsString(new ResponseModel<>(o));
            } catch (JsonProcessingException e) {

                throw new ApiException("返回String类型错误");
            }
        }

        // 将原本的数据包装在ResultVO里
        return new ResponseModel<>(o);
    }
}
