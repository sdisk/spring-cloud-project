package com.hq.registry.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-project
 * @description: 添加一个接口，进行健康监控
 * @author: Mr.Huang
 * @create: 2019-11-08 15:29
 **/
@RestController
public class HealthController {

    @GetMapping("/health")
    public String health(){
        return "ok";
    }
}
