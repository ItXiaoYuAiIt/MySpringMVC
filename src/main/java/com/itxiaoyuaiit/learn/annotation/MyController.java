package com.itxiaoyuaiit.learn.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @AnnotationName MyController
 * @Description 用于修饰控制层类
 * @Author wuyuqing
 * @Date 2020/8/9 22:27
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyController {
    String value() default "";
}
