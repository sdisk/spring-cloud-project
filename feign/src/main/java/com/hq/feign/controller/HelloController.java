package com.hq.feign.controller;

import com.hq.feign.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-project
 * @author: Mr.Huang
 * @create: 2019-10-24 15:38
 **/
@RestController
@RequestMapping("/user")
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping(value = "/hello")
    public String hello(@RequestParam(value = "name") String name){
        return helloService.userHello(name);
    }
}
