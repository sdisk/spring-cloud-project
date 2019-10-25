package com.hq.feign.service;

import com.hq.feign.hystric.HelloServiceHystric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: spring-cloud-project
 * @author: Mr.Huang
 * @create: 2019-10-24 15:36
 **/
@FeignClient(value = "registry-client", fallback = HelloServiceHystric.class)
public interface HelloService {

    @GetMapping(value = "/user/hello")
    String userHello(@RequestParam(value = "name") String name);
}
