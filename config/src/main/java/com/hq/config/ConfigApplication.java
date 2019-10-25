package com.hq.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * config项目启动后，可以直接访问http://localhost:8880/spring.rabbitmq.host/application
 * 可以看到有返回值
 *
 * http请求地址和资源文件映射如下:
 *
 * /{application}/{profile}[/{label}]
 * /{application}-{profile}.yml
 * /{label}/{application}-{profile}.yml
 * /{application}-{profile}.properties
 * /{label}/{application}-{profile}.properties
 *
 * {application}, which maps to spring.application.name on the client side.
 * {profile}, which maps to spring.profiles.active on the client (comma-separated list).
 * {label}, which is a server side feature labelling a "versioned" set of config files.
 *
 * 当服务实例比较多的时候，也可以考虑将配置中心做成集群，来实现配置中心的高可用
 */

@SpringBootApplication
@EnableConfigServer
public class ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }

}
