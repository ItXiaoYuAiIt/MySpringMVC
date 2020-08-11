package com.itxiaoyuaiit.learn.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @AnnotationName MyQualifier
 * @Description 用于修饰依赖注入的变量
 * @Author wuyuqing
 * @Date 2020/8/9 22:29
 * @Version 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyQualifier {
    String value() default "";
}
