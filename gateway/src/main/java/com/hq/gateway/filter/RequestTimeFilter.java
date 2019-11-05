package com.hq.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @program: spring-cloud-project
 * @description: 自定义过滤器,记录请求时间
 * @author: Mr.Huang
 * @create: 2019-11-05 13:48
 **/
@Slf4j
public class RequestTimeFilter implements GatewayFilter, Ordered {

    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());
        return chain.filter(exchange).then(
                Mono.fromRunnable(()->{
                    Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
                    if (startTime != null){
                        log.info(exchange.getRequest().getURI().getRawPath() + ":" + (System.currentTimeMillis() - startTime) + "ms");
                    }
                }));
    }

    //过滤器优先级，值越小优先级越高
    @Override
    public int getOrder() {
        return 0;
    }
}
