package com.hq.registry.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @Value("${client.welcome.word}")
    private String welComeWord;

    @Value("${client.welcome.value}")
    private int welComeValue;

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

    @RequestMapping("/welcome")
    public String welcome(@RequestParam(value = "name", defaultValue = "huang") String name){
        return "hello user " + name + " ," + welComeWord + " (repeat " + welComeValue + " times) ,i am from port:" + port;
    }
}
