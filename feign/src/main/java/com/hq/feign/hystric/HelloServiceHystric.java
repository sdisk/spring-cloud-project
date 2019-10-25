package com.hq.feign.hystric;

import com.hq.feign.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @program: spring-cloud-project
 * @author: Mr.Huang
 * @create: 2019-10-24 16:25
 **/
@Slf4j
@Component
public class HelloServiceHystric implements HelloService {
    @Override
    public String userHello(String name) {
        log.info("userHello，进入熔断方法");
        return "hello user " + name + ", sorry, occur error!";
    }
}
