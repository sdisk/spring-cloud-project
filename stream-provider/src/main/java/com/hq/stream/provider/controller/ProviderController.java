package com.hq.stream.provider.controller;

import com.hq.stream.provider.bean.SimpleSourceBean;
import com.hq.stream.provider.service.SendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-project
 * @description:
 * @author: Mr.Huang
 * @create: 2019-11-04 14:02
 **/
@RestController
@Slf4j
public class ProviderController {

    @Autowired
    private SimpleSourceBean simpleSourceBean;

    @Autowired
    private SendService sendService;

    @GetMapping("/provider/{msg}")
    public String provider(@PathVariable("msg") String msg){
        log.info("----provider---");
        simpleSourceBean.publish(msg);
        log.info("source发送消息");
        return "provider: " + msg;
    }

    @GetMapping("/send/{msg}")
    public String send(@PathVariable("msg") String msg){
        log.info("----send---");
        sendService.sendMsg(msg);
        log.info("source发送消息");
        return "provider: " + msg;
    }
}
