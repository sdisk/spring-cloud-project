package com.hq.stream.consumer;

import com.hq.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(Sink.class)
@Slf4j
public class StreamConsumerTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamConsumerTwoApplication.class, args);
    }

    @StreamListener(Sink.INPUT)
    public void sink(User user){
        log.info("消费者two接收的数据: {}" , user.toString());
    }

//    @StreamListener(Sink.INPUT)
//    public String sink(String msg){
//        log.info("消费者two接收的数据: {}" , msg);
//        return "receive msg :" + msg;
//    }
}
