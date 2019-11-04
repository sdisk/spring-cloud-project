package com.hq.stream.provider.service.impl;

import com.hq.stream.provider.service.SendService;
import com.hq.stream.provider.source.Mysource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;

/**
 * @program: spring-cloud-project
 * @description:
 * @author: Mr.Huang
 * @create: 2019-11-04 15:45
 **/
@EnableBinding(Mysource.class)
public class SendServiceImpl implements SendService {

    @Autowired
    private Mysource mysource;

    @Override
    public void sendMsg(String msg) {
        mysource.myOutput()
                .send(MessageBuilder.withPayload(msg).build());
    }
}
