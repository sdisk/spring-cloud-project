package com.hq.ribbon.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

/**
 * @program: spring-cloud-project
 * @description:  使用Hystrix来实现
 * @author: Mr.Huang
 * @create: 2019-10-31 09:59
 **/
public class HelloServiceCommand extends HystrixCommand<String> {

    private RestTemplate restTemplate;

    public HelloServiceCommand(String commandGroupKey, RestTemplate restTemplate) {
        // 根据commandGroupKey进行线程隔离
        super(HystrixCommandGroupKey.Factory.asKey(commandGroupKey));
        this.restTemplate = restTemplate;
    }

    //服务调用
    @Override
    protected String run(){
        System.out.println("------执行的是HystrixCommand的run方法-------");
        System.out.println(Thread.currentThread().getName());
        return restTemplate.getForObject("http://registry-client/user/hello", String.class);
    }

    @Override
    protected String getFallback() {
        System.out.println("------getFallback-------");
        return "occur error";
    }

    @Override
    protected String getCacheKey() {
        //一般动态的取缓存key，比如userId
        return "test";
    }
}
