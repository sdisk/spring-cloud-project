package com.hq.ribbon.hystrix;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: spring-cloud-project
 * @description: 请求合并处理
 * @author: Mr.Huang
 * @create: 2019-11-01 09:40
 **/
public class BatchCollapser extends HystrixCollapser<List<String>, String, Long> {

    private Long id;

    private RestTemplate restTemplate;

    //在200ms内进行请求合并，不在的话，会放入下一个200ms周期，导致请求延迟响应（根据具体的业务来设置时间）
    public BatchCollapser(RestTemplate restTemplate, Long id) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("batchCollapserKey"))
        .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter()
        .withTimerDelayInMilliseconds(200)));
        this.id = id;
        this.restTemplate = restTemplate;
    }


    @Override
    public Long getRequestArgument() {
        return id;
    }
    //创建命令请求合并
    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Long>> collapsedRequests) {
        List<Long> ids = new ArrayList<>(collapsedRequests.size());
        ids.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        BatchCommand command = new BatchCommand("huang", restTemplate, ids);
        return command;
    }

    @Override
    protected void mapResponseToRequests(List<String> batchResponse, Collection<CollapsedRequest<String, Long>> collapsedRequests) {
        System.out.println("分批批量请求结果....");
        int count = 0;
        for (CollapsedRequest<String, Long> request : collapsedRequests){
            String response = batchResponse.get(count++);
            request.setResponse(response);
        }
    }
}
