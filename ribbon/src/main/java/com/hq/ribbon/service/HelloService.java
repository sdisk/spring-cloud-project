package com.hq.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @program: spring-cloud-project
 * @author: Mr.Huang
 * @create: 2019-10-24 15:09
 **/
@Service
@Slf4j
public class HelloService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * HystrixCommand 的fallbackMenthod定义熔断方法
     * @param name
     * @return
     */
    @HystrixCommand(fallbackMethod = "helloError")
    public String helloUser(String name){
        return restTemplate.getForObject("http://registry-client/user/hello?name=" + name, String.class);
    }

    public String helloError(String name){
        log.error("helloUser方法访问错误，进入helloError熔断方法");
        return "hello user " + name + ", sorry, occur error!";
    }
}
