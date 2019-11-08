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
 * @description:
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
                log.debug("ping: url={}, success and response is {}", url, responseEntity.getBody());
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
