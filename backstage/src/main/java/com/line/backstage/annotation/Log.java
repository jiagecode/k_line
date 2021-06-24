package com.line.backstage.annotation;

import java.lang.annotation.*;

/**
 * 在Controller方法上加入改注解会自动记录日志
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
}
