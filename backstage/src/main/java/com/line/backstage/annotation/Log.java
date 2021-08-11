package com.line.backstage.annotation;

import java.lang.annotation.*;

/**
 * 自动记录日志
 *
 * @author pc
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 描述. 执行
     */
    String description() default "";

    /**
     * 用户类型 ‘1’-管理员 0-用户
     * @return
     */
    String userType() default "";
}
