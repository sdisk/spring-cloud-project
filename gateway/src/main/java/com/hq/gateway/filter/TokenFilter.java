package com.hq.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @program: spring-cloud-project
 * @description: 自定义全局过滤器
 * @author: Mr.Huang
 * @create: 2019-11-05 14:30
 **/
@Slf4j
public class TokenFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //MultiValueMap<String, String> getQueryParams()
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if (token == null || token.isEmpty()){
            log.error("token is empty");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
