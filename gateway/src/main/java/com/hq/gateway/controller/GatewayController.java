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

    /**
     * 访问 http://localhost:8768/REGISTRY-CLIENT/user/hello
     * 这里的服务名需要大写，这是因为eureka服务注册中心的规则，会把所有的服务id转为大写
     * 可以设置 locator.lower-case-service-id: true 就可以不用大写了
     * http://localhost:8768/registry-client/user/hello
     * 可以看到自动实现了负载均衡，GateWay是通过服务注册中心的服务名/接口实现的负载均衡
     *
     *  first: hello user huang ,i am from port:8762
     *
     *  second: hello user huang ,i am from port:8763
     *
     *  但是如果一个服务停掉，gateway还是会对这个服务进行轮询，还是会去访问已经停掉的服务
     */
}
