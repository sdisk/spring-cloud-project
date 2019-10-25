package com.hq.registry.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-project
 * @author: Mr.Huang
 * @create: 2019-10-24 13:44
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${server.port}")
    private int port;

    @RequestMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "huang") String name){
        return "hello user " + name + " ,i am from port:" + port;
    }
}
