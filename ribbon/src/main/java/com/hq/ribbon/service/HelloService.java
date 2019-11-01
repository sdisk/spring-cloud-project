package com.hq.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

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
     * HystrixCommandAspect完成对HystrixCommand的包裹处理
     * HystrixCommand 的fallbackMenthod定义熔断方法
     * 超时时间限制
     * 线程池里数量默认为10个，舱壁模式，线程隔离
     *  ObservableExecutionMode.EAGER 热执行（默认）  ObservableExecutionMode.LAZY 冷执行
     * @param name
     * @return
     */
    @HystrixCommand(fallbackMethod = "helloError", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")})
    public Future<String> helloUser(String name){
        System.out.println("-----helloUser()-----");
        System.out.println(Thread.currentThread().getName());
        Future<String> future = new AsyncResult<String>() {
            @Override
            public String invoke() {
                return restTemplate.getForObject("http://registry-client/user/hello?name=" + name, String.class);
            }
        };
        return future;
    }

    /**
     * getFallback()降级逻辑.以下四种情况将触发getFallback调用：
     * 　　　　(1):run()方法抛出非HystrixBadRequestException异常。
     * 　　　　(2):run()方法调用超时
     * 　　　　(3):熔断器开启拦截调用
     * 　　　　(4):线程池/队列/信号量是否跑满
     */
    public String helloError(String name){
        log.error("helloUser方法访问错误，进入helloError熔断方法");
        return "hello user " + name + ", sorry, occur error!";
    }

    @HystrixCommand(threadPoolKey = "loginUser", threadPoolProperties = {@HystrixProperty(
            name = "coreSize", value = "2"), @HystrixProperty(name = "maxQueueSize", value = "10")}, fallbackMethod = "loginUserError")
    public String loginUser(String username){
        log.info("----进入loginUser----");
        System.out.println("threadInfo: "+ Thread.currentThread().getName());
        return "success";
    }
    @HystrixCommand(threadPoolKey = "getUserInfo", threadPoolProperties = {@HystrixProperty(
            name = "coreSize", value = "1"), @HystrixProperty(name = "maxQueueSize", value = "20")}, fallbackMethod = "getUserInfoError")
    public String getUserInfo(String username){
        log.info("----进入getUserInfo----");
        System.out.println("threadInfo: "+ Thread.currentThread().getName());
        return "success";
    }

    public String loginUserError(String name){
        log.error("loginUser方法访问错误，进入loginUserError熔断方法");
        return "sorry, occur error!";
    }

    public String getUserInfoError(String name){
        log.error("getUserInfo方法访问错误，进入getUserInfoError熔断方法");
        return "sorry, occur error!";
    }
//    @CacheRemove()
//    @CacheKey
    @CacheResult //默认情况下方法的所有参数都将作为缓存的key
    @HystrixCommand
    public String testAnnotationCache(String username){
        return restTemplate.getForObject("http://registry-client/user/hello?name=" + username, String.class);
    }

    /**
     *  Hystrix特性:
     *   1.请求熔断: 当Hystrix Command请求后端服务失败数量超过一定比例（默认50%），断路器切换到开路状态（Open），
     *   这时所有请求会直接请求失败，断路器保证在开路状态一段时间后（默认5秒），自动切换到半开路状态（Half-Open），
     *   这时会判断下一次请求的返回情况，如果请求成功，断路器会切换到闭路状态（Closed），否则切换到开路状态（Open）
     *   2.服务降级: Fallback方法直接返回一个默认值或者缓存的值，告知后面的请求服务不可用
     *   3.依赖隔离: 在Hystrix中，主要通过线程池来实现资源隔离，通常在使用的时候我们会根据调用的远程服务划分出多个
     *   线程池。比如说，一个服务调用两个服务，你两个服务都用一个线程池，那么如果一个服务卡在哪里，资源没被释放
     *   请求再过来，导致后面的请求都卡在这里
     *   4.请求缓存: 如果一个请求过一个资源，后面的请求再次请求这个资源，就可以直接把第一次的结果直接返回
     *   5.请求合并: 如果对一个服务要调用 N 次，比如数据库查询，可以把多个请求合并为一个请求，发送一次就返回所有结果，提高效率
     *
     *  在同一用户请求的上下文中，相同依赖服务的返回数据始终保持一致。
     *  在当次请求内对同一个依赖进行重复调用，只会真实调用一次。在当次请求内数据可以保证一致
     *
     */
}
