package com.hq.stream.provider.bean;

import com.hq.model.entity.User;
import com.hq.stream.provider.source.Mysource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @program: spring-cloud-project
 * @description:
 * @author: Mr.Huang
 * @create: 2019-11-04 11:00
 **/
@Component
@EnableBinding(Source.class)
@Slf4j
public class SimpleSourceBean {

    /**
     * output
     */
    @Autowired
    private Source source;

    public void publish(String msg){
        User user = new User();
        user.setId(1);
        user.setUsername(msg);
        user.setEmail("123456789@qq.com");
        for(int i=0;i<10;i++){
            source.output()
                    .send(MessageBuilder.withPayload(user).build());
        }
    }
}
