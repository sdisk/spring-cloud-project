package com.hq.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * config需要配置再bootstrap中，优先于application.yml加载
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class RegistryClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistryClientApplication.class, args);
    }

}
