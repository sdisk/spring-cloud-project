package com.hq.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * config需要配置再bootstrap中，优先于application.yml加载
 * /actuator/bus-refresh/{destination}
 * 发送POST请求刷新微服务的配置 destination可以定义要刷新的应用，这可以做到部分刷新
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class RegistryClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistryClientApplication.class, args);
    }

}
