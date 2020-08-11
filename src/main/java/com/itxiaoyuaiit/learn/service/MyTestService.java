package com.itxiaoyuaiit.learn.service;


import com.itxiaoyuaiit.learn.annotation.MyService;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MyTestService
 * @Description TODO
 * @Author wuyuqing
 * @Date 2020/8/12 0:54
 * @Version 1.0
 */
@MyService("myTestService")
public class MyTestService {

    public Map<String, Object> hello() {
        Map<String, Object> result = new HashMap<>();
        result.put("hello", "MySpringMVC");
        return result;
    }

}
