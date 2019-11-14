package com.hq.gateway.ping;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @program: spring-cloud-project
 * @description: 定时检测，可设置频率，通过isAlive()方法来判断实例的状态，从而把该服务剔除，
 * 后续正常启动后重新加入，但是宕机到下次检测这段时间中，访问的时候还是可能报错
 * @author: Mr.Huang
 * @create: 2019-11-08 15:33
 **/
@Component
@Slf4j
public class HealthMonitor implements IPing {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean isAlive(Server server) {

        String url = "http://" + server.getId() + "/health";
        try {
            ResponseEntity<String> responseEntity =  restTemplate.getForEntity(url, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK){
                log.info("ping: url={}, success and response is {}", url, responseEntity.getBody());
                return true;
            }
            log.error("ping: url={}, failed and response is {}", url, responseEntity.getBody());
            return false;
        } catch (Exception e){
            log.error("ping: url={}, error ", url);
            return false;
        }
    }
}
