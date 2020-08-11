package com.itxiaoyuaiit.learn.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @AnnotationName MyRequestMapping
 * @Description 用于修饰路由地址映射
 * @Author wuyuqing
 * @Date 2020/8/9 22:30
 * @Version 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyRequestMapping {
    String value() default "";
}
