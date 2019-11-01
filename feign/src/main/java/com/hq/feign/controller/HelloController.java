package com.hq.feign.controller;

import com.hq.feign.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: spring-cloud-project
 * @author: Mr.Huang
 * @create: 2019-10-24 15:38
 **/
@RestController
@RequestMapping("/user")
@Slf4j
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping(value = "/hello")
    public String hello(@RequestParam(value = "name") String name){
        log.info("调用hello接口get");
        return helloService.userHello(name);
    }
    @PostMapping(value = "/hello")
    public String helloPost(){
        log.info("调用hello接口post");
        return helloService.userHelloPost("huang");
    }
}
