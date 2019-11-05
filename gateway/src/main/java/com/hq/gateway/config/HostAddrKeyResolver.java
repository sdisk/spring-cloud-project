package com.hq.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 使用redis来存储KeyResolver，有效期很短
 * request_rate_limiter.{/user}.timestamp
 * 存储的是当前时间的秒数，也就是System.currentTimeMillis() / 1000或者Instant.now().getEpochSecond()
 * request_rate_limiter.{/user}.tokens
 * 存储的是当前这秒钟的对应的可用的令牌数量
 *
 * @program: spring-cloud-project
 * @description: 根据hostname进行限流
 * @author: Mr.Huang
 * @create: 2019-11-05 14:44
 * @see  org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter
 **/
public class HostAddrKeyResolver implements KeyResolver {
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }
}
