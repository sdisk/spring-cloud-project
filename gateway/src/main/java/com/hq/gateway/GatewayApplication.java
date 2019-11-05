package com.hq.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * start NettyWebServer
 * 网关作为系统的入口，通常有以下作用:
 *  - 协议装换，路由转发
 *  - 流量聚合，对流量进行监控，日志输出
 *  - 作为系统的前端工程，对流量进行控制，有限流的作用
 *  - 作为系统的前端边界，外部流量只能通过网关才能访问系统
 *  - 可以在网关层做权限的判断
 *  - 可以在网关层做缓存
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
