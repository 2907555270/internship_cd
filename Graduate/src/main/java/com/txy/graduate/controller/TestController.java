package com.txy.graduate.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("test")
public class TestController {

    //测试结果：任何JSON格式数据都可以映射为map
    @PostMapping("map")
    public Map<String,Object> test_map(@RequestBody Map<String,Object> map){
        return map;
    }
}
