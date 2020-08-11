package com.itxiaoyuaiit.learn.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @AnnotationName MyAutowried
 * @Description 该注解主要用于修饰需要依赖注入的变量
 * @Author wuyuqing
 * @Date 2020/8/9 22:21
 * @Version 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAutowried {
    String value() default "";
}
