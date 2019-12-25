package com.hq.stream.consumer;

import com.hq.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * 消息分组: 将多个应用放在一个组内，可以保证同时只会被一个组内的一个消费者消费，可以避免重复消费
 *  只要把这些应用放置于同一个 “group” 中，就能够保证消息只会被其中一个应用消费一次。不同的组是可以消费的，
 *  同一个组内会发生竞争关系，只有其中一个可以消费
 *  group: group
 * 消息分区:
 *  一个或者多个生产者应用实例给多个消费者应用实例发送消息并确保相同特征的数据被同一消费者实例处理
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(Sink.class) //进行中转处理的时候需要注释掉，不然会出现绑定冲突
@Slf4j
public class StreamConsumerOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamConsumerOneApplication.class, args);
    }

    @StreamListener(Sink.INPUT)
    public void sink(User user){
        log.info("消费者one接收的数据: {}" , user.toString());
    }
}
