package com.hq.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * 当使用Hystrix Board来监控Spring Cloud Zuul构建的API网关时，
 * Thread Pool信息会一直处于Loading状态。这是由于Zuul默认会使用信号量来实现隔离，
 * 只有通过Hystrix配置把隔离机制改成为线程池的方式才能够得以展示
 *
 * Eureka客户端，途中的即服务提供者，主要处理服务的注册和发现。客户端服务通过注册和参数配置的方式，
 * 嵌入在客户端应用程序的代码中。在应用程序启动时，Eureka客户端向服务注册中心注册自身提供的服务，
 * 并周期性的发送心跳来更新它的服务租约。同时，他也能从服务端查询当前注册的服务信息并把它们缓存到
 * 本地并周期行的刷新服务状态
 * @EnableDiscoveryClient 将使用META-INF/spring.factories查找DiscoveryClient接口的 implementations，包括Eureka, Consul和Zookeeper
 * ServiceRegistry 用于服务注册，DiscoveryClient用于服务发现和负载均衡
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableHystrixDashboard
@EnableCircuitBreaker //开启断路器功能
public class RegistryClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistryClientApplication.class, args);
    }
}
