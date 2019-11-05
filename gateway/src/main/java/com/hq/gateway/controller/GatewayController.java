package com.hq.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @program: spring-cloud-project
 * @description:
 * @author: Mr.Huang
 * @create: 2019-11-05 09:55
 **/
@RestController
@Slf4j
public class GatewayController {

    /**
     * Mono是一个Reactive Stream,对外输出一个fallback字符串
     * @return
     */
    @RequestMapping("/fallback")
    public Mono<String> fallback(){
        log.error("hystrix fallback");
        return Mono.just("hystrix fallback");
    }

    @RequestMapping("/test")
    public Mono<String> test(){
        return Mono.just("success");
    }
}
