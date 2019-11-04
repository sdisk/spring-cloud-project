package com.hq.stream.consumer.bean;

import com.hq.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * @program: spring-cloud-project
 * @description:
 * @author: Mr.Huang
 * @create: 2019-11-04 16:08
 **/
@EnableBinding(Processor.class)
@Slf4j
public class ConvertService {

    @ServiceActivator(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public Object transform(User user){
        log.info("消息中转站: {}", user);
        return user.getUsername();
    }
}
