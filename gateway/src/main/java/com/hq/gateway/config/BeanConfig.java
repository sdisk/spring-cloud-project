package com.hq.gateway.config;

import com.hq.gateway.filter.RequestTimeGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 在最前端，启动一个netty server接受请求，
 * 然后通过Routes（每个Route由Predicate(等同于HandlerMapping)和Filter(等同于HandlerAdapter)）处理后通过Netty Client发给响应的微服务
 *
 *  path,host,method,header,cookie,query...
 *  @see org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory
 *
 *  - Header头部过滤器
 *  - Parameter请求参数过滤器
 *  - Path路径过滤器
 *  - Body请求（响应）体过滤器
 *  - Status状态过滤器
 *  - Session会话过滤器
 *  - Redirect重定向过滤器
 *  - Rrety重试过滤器
 *  - RateLimiter限流过滤器
 *  - Hystrix熔断器过滤器
 * @program: spring-cloud-project
 * @description:
 * @author: Mr.Huang
 * @create: 2019-11-04 18:13
 **/
@Configuration
public class BeanConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        String httpUri = "http://httpbin.org:80";
        return builder.routes()
//                .route(p -> p
//                    .path("/api_v1/**")
//                    .filters(f -> f.filter(new RequestTimeFilter())
//                        .addRequestHeader("X-Response-Default-Foo", "Default-Foo"))
//                    .uri(httpUri + "/get")
//                    .order(0)
//                    .id("customer_request_time_router")
//                    )
                .route(p -> p
                    .path("/get")
                    .filters(f -> f.addRequestHeader("token", "psd"))
                    .uri(httpUri))
                .route(p -> p
                    .host("*.hystrix.com")
                    .filters(f -> f
                        .hystrix(config -> config
                            .setName("myHystrix")
                            .setFallbackUri("forward:/fallback")))
                    .uri(httpUri))
                .build();

        //根据请求的路径是/get来判断，并且使用过滤器增加了一个header，然后将请求转发到 http://httpbin.org:80/get
        //请求的host中可以匹配 *.hystrix.com 的话就会重定向到 /fallback 地址 curl --dump-header - --header Host:www.hystrix.com http://localhost:8768/delay
    }

    @Bean
    public RequestTimeGatewayFilterFactory requestTimeGatewayFilterFactory(){
        return new RequestTimeGatewayFilterFactory();
    }

//    @Bean
//    public TokenFilter tokenFilter(){
//        return new TokenFilter();
//    }

//    @Bean
//    public HostAddrKeyResolver hostAddrKeyResolver(){
//        return new HostAddrKeyResolver();
//    }

    @Bean
    public UriKeyResolver uriKeyResolver(){
        return new UriKeyResolver();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
