package com.hq.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Feign 采用的是基于接口的注解
 * Feign 整合了Ribbon
 *
 * hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds	断路器的超时时间需要大于ribbon的超时时间，不然不会触发重试。
 * ribbon.ConnectTimeout	请求连接的超时时间
 * ribbon.ReadTimeout	请求处理的超时时间
 * ribbon.OkToRetryOnAllOperations	是否对所有操作请求都进行重试,这里有个问题，对GET请求能保证幂等性的接口可以进行重试，但是非GET请求不能进行重试，建议设置为false
 * ribbon.MaxAutoRetriesNextServer	重试负载均衡其他的实例最大重试次数，不包括首次server
 * ribbon.MaxAutoRetries	同一台实例最大重试次数，不包括首次调用
 *
 * 请求总次数为：
 * （MaxAutoRetries+1）*（MaxAutoRetriesNextServer+1）
 * hystrix超时时间的计算：（MaxAutoRetries+1）*（MaxAutoRetriesNextServer+1） * （ConnectTimeout+ReadTimeout）
 * 当ribbon超时后且hystrix没有超时，就会使用到重试机制
 *
 * @see org.springframework.cloud.openfeign.ribbon.FeignLoadBalancer
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class, args);
    }

}
