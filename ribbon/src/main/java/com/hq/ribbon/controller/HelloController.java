package com.hq.ribbon.controller;

import com.hq.ribbon.hystrix.BatchCollapser;
import com.hq.ribbon.hystrix.HelloServiceCommand;
import com.hq.ribbon.hystrix.HelloServiceObserveCommand;
import com.hq.ribbon.service.BatchService;
import com.hq.ribbon.service.HelloService;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @program: spring-cloud-project
 * @author: Mr.Huang
 * @create: 2019-10-24 15:08
 **/
@RestController
@RequestMapping("/user")
public class HelloController {

    @Autowired
    private HelloService helloService;

    @Autowired
    private BatchService batchService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/hello")
    public String hello(@RequestParam String name) {
        //异步请求，多个请求同时不会阻塞
        Future<String> future = helloService.helloUser(name);
        try {
            return future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/helloCommand")
    public String helloCommand(){
        HelloServiceCommand command = new HelloServiceCommand("hello", restTemplate);

         Future<String> queue = command.queue();
        try {
            return queue.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/helloConsumer")
    public String helloConsumer(){
        List<String> list = new ArrayList<>();
        HelloServiceObserveCommand command = new HelloServiceObserveCommand("hello", restTemplate);
        //热执行
        //不管你事件有没有注册完(onCompleted()，onError，onNext这三个事件注册)，就去执行我的业务方法即(HystrixObservableCommand实现类中的construct()方法)
        Observable<String> observable = command.observe();
        //冷执行
        //先进行事件监听方法注册完成后，才执行业务方法
        //Observable<String> observable = command.toObservable();
        try {
            Thread.sleep(4000); //可以明显看出冷执行和热执行的区别
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        observable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("把所有请求汇总完毕");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                System.out.println("结果出来了");
                list.add(s);
            }
        });
        return list.toString();
        //[hello user huang ,i am from port:8762, hello user huang ,i am from port:8762]
    }

    @GetMapping("loginUser/{username}")
    public String loginUser(@PathVariable String username){
        return helloService.loginUser(username);
    }
    @GetMapping("userInfo/{username}")
    public String getUserInfo(@PathVariable String username){
        return helloService.getUserInfo(username);
    }

    /**
     * 每次请求来之前都必须HystrixRequestContext.initializeContext();进行初始化，
     * 每请求一次controller就会走一次filter，上下文又会初始化一次，前面缓存的就失效了，又得重新来。
     * 所以这个缓存比较鸡肋，如果客户端发送多次请求，还是会多次请求依赖服务
     *
     */
    @GetMapping("testCache")
    public String testCache(){
        //缓存实现
        HystrixRequestContext.initializeContext();

        HelloServiceCommand command = new HelloServiceCommand("hello", restTemplate);

        Future<String> queue = command.queue();

        HelloServiceCommand command1 = new HelloServiceCommand("hello", restTemplate);

        Future<String> queue1 = command1.queue();
        try {
            String result = queue.get();
            String result1 = queue1.get();
            //清除缓存
            HystrixCommandKey key = HystrixCommandKey.Factory.asKey("HelloServiceCommand");
            HystrixRequestCache.getInstance(key,  HystrixConcurrencyStrategyDefault.getInstance()).clear("test");
            HelloServiceCommand command2 = new HelloServiceCommand("hello", restTemplate);
            //再次请求，缓存失效，重新请求方法，这样这个方法一共会请求2服务次
            String result2 = command2.execute();
            System.out.println("result2:"+ result2);
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 也可以通过注解来实现缓存
     */
    @GetMapping("testAnnotationCache")
    public String testAnnotationCache(){
        //缓存实现
        HystrixRequestContext.initializeContext();
        String username = "admin";
        helloService.testAnnotationCache(username);
        helloService.testAnnotationCache(username);
        helloService.testAnnotationCache(username + "1");
        return "success";
    }
    /**
     * 请求合并需要异步请求，因为如果用同步，是一个请求完成后，另外的请求才能继续执行，不能进行合并
     */
    @GetMapping("testCollapser")
    public String testCollapser() throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        BatchCollapser collapser1 = new BatchCollapser(restTemplate, 1L);
        BatchCollapser collapser2 = new BatchCollapser(restTemplate, 2L);
        BatchCollapser collapser3 = new BatchCollapser(restTemplate, 3L);
        Future<String>  future1 = collapser1.queue();
        Future<String>  future2 = collapser2.queue();
        String result1 = future1.get();
        String result2 = future2.get();
        //睡眠1s，超多请求合并的200ms时间，进入下一个周期
        Thread.sleep(1000);

        Future<String>  future3 = collapser3.queue();
        String result3 = future3.get();
        System.out.println("result1: " + result1);
        System.out.println("result2: " + result2);
        System.out.println("result3: " + result3);
        context.close();
        return "success";
    }

    @GetMapping("testCollapser2")
    public String testCollapser2() throws ExecutionException, InterruptedException {

        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        Future<String>  future1 = batchService.batchGetUserInfo(1L);
        Future<String>  future2 = batchService.batchGetUserInfo(2L);

        String result1 = future1.get();
        String result2 = future2.get();
        System.out.println("result1: " + result1);
        System.out.println("result2: " + result2);
        context.close();
        return "success";
    }
}
