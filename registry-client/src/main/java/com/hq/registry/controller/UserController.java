package com.hq.registry.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @program: spring-cloud-project
 * @author: Mr.Huang
 * @create: 2019-10-24 13:44
 **/
@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {

    @Value("${server.port}")
    private int port;

    @Value("${default.name}")
    private String defaultName;

    @Value("${default.age}")
    private int defaultAge;

    @RequestMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "huang") String name){
        System.out.println("---请求hello方法--");
        int i = new Random().nextInt(3000);
        System.out.println("客户端休眠时间:" + i);
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello user " + name + " ,i am from port:" + port;
    }

    @RequestMapping("/userInfo")
    public String userInfo(){
        return "hello user : " + defaultName + " , defaultAge:" + defaultAge;
    }

    @RequestMapping("/{id}")
    public List<String> user(@PathVariable String id){
        List<String> list = new ArrayList<>();
        list.add("huang "+id);
        list.add("huang "+id);
        list.add("huang "+id);
        return list;
    }
}
