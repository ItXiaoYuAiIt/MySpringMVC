package com.itxiaoyuaiit.learn.controller;

import com.itxiaoyuaiit.learn.annotation.MyAutowried;
import com.itxiaoyuaiit.learn.annotation.MyController;
import com.itxiaoyuaiit.learn.annotation.MyQualifier;
import com.itxiaoyuaiit.learn.annotation.MyRequestMapping;
import com.itxiaoyuaiit.learn.service.MyTestService;

/**
 * @ClassName MyTestController
 * @Description TODO
 * @Author wuyuqing
 * @Date 2020/8/12 0:53
 * @Version 1.0
 */
@MyController
@MyRequestMapping("/hello")
public class MyTestController {
    @MyAutowried
    @MyQualifier("myTestService")
    private MyTestService myTestService;

    @MyRequestMapping("/test")
    public Object hello(){
        return myTestService.hello();
    }
}
