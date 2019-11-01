package com.hq.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 服务注册
 * 每个服务单元向服务注册中心登记自己提供的服务，包括服务的主机端口灯信息，服务中心按照服务名分类服务清单
 * 同时以心跳检测的方式去监测注册清单中的服务是否可用，若不可用需要从清单中剔除，以达到排除故障服务的效果
 *
 * 服务发现
 * 服务间的调用不通过指定具体的实例地址来实现，而是通过服务名发起请求调用实现，服务调用放通过服务名从注册中心的
 * 服务清单中获取服务实例，通过指定的负载均衡策略取出一个服务实例来进行服务调用
 * Eureka服务端支持集群模式部署，当集群中有分片发生故障的时候，Eureka会自动转入自我保护模式。它允许在分片发生故障
 * 的时候继续提供服务的发现和注册，当故障分配恢复时，集群中的其他分片会把他们的状态再次同步回来。集群中的的
 * 不同服务注册中心通过异步模式互相复制各自的状态，这也意味着在给定的时间点每个实例关于所有服务的状态可能存在不一致的现象
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class RegistryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistryServerApplication.class, args);
    }

}
