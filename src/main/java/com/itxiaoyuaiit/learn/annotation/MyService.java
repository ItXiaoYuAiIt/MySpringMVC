package com.itxiaoyuaiit.learn.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @AnnotationName MyService
 * @Description 用于修饰服务层
 * @Author wuyuqing
 * @Date 2020/8/9 22:33
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyService {
    String value() default "";
}
