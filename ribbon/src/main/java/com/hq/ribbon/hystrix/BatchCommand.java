package com.hq.ribbon.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @program: spring-cloud-project
 * @description:
 * @author: Mr.Huang
 * @create: 2019-11-01 11:23
 **/
public class BatchCommand extends HystrixCommand<List<String>> {

    private RestTemplate restTemplate;
    private List<Long> ids ;

    public BatchCommand(String commandGroupKey, RestTemplate restTemplate, List<Long> ids) {
        super(HystrixCommandGroupKey.Factory.asKey(commandGroupKey));
        this.restTemplate = restTemplate;
        this.ids = ids;
    }

    @Override
    protected List<String>  run() throws Exception {
        System.out.println("发送请求。。参数: " + ids.toString() + Thread.currentThread().getName());
        String [] result = restTemplate.getForObject("http://registry-client/user/{1}", String[].class, StringUtils.join(ids, ","));
        return Arrays.asList(result);
    }
}
