package com.hq.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @program: spring-cloud-project
 * @description:
 * @author: Mr.Huang
 * @create: 2019-11-01 14:19
 **/
@Service
@Slf4j
public class BatchService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * scope = GLOBAL 合并作用域，默认是REQUEST，就是不会跨越多个请求会话的，
     * 只在当前用户请求中合并多次请求为批处理请求。这里改成GLOBAL，就是可以跨越request context，合并不同用户的请求为一次批处理请求。
     * timerDelayInMilliseconds 合并请求的窗口期， 建议尽量设置的小一点，默认是10ms，如果并发量不大的话，其实也没有必要使用HystrixCollapser来处理
     * maxRequestsInBatch 在窗口期合并的最多请求数，默认不限
     *
     * 批处理方法返回的集合大小，一定要跟请求参数的集合大小一致
     *
     *  这里设置timerDelayInMilliseconds为3s，可以看到不同的客户端请求进来，每个请求都会延迟3s时间再请求，大大加大了延迟性
     */
    @HystrixCollapser(batchMethod = "getUserInfo", scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL, collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "3000"),
            @HystrixProperty(name = "maxRequestsInBatch", value = "100")
    })
    public Future<String> batchGetUserInfo(Long id){ //批处理方法的参数类型要一致，一个使用基本类型一个使用包装型也不行
        return null;
    }

    @HystrixCommand(fallbackMethod = "getUserInfoError")
    public List<String> getUserInfo(List<Long> ids){
        log.debug("---getUserInfo---");
        log.info("合并操作线程 --> {} --> params --> {}", Thread.currentThread().getName(), ids);
        String [] result = restTemplate.getForObject("http://registry-client/user/{1}", String[].class, StringUtils.join(ids, ","));
        return Arrays.asList(result);
    }

    public List<String> getUserInfoError(List<Long> ids){
       log.error("---getUserInfo Error---");
       return Collections.emptyList();
    }
}
