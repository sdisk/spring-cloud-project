package com.hq.ribbon.controller;

import com.hq.ribbon.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-project
 * @author: Mr.Huang
 * @create: 2019-10-24 15:08
 **/
@RestController
@RequestMapping("/user")
public class HelloController {


    @Autowired
    private HelloService helloService;

    @GetMapping(value = "/hello")
    public String hello(@RequestParam String name){
        return helloService.helloUser(name);
    }
}
