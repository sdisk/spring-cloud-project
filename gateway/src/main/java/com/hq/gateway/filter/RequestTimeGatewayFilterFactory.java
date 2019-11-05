package com.hq.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @program: spring-cloud-project
 * @description: 自定义过滤器工厂
 * @author: Mr.Huang
 * @create: 2019-11-05 14:03
 * @see AbstractGatewayFilterFactory
 * @see org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory
 **/
@Slf4j
public class RequestTimeGatewayFilterFactory extends AbstractGatewayFilterFactory<RequestTimeGatewayFilterFactory.Config> {

    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";
    /**
     * 定义可以在yml中声明的属性变量
     */
    private static final String KEY = "withParam";

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(KEY);
    }

    public RequestTimeGatewayFilterFactory() {
        // 这里需要将自定义的config传过去，否则会报告ClassCastException
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());
            return chain.filter(exchange).then(
                    Mono.fromRunnable(()->{
                        Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
                        if (startTime != null){
                            StringBuilder sb = new StringBuilder(exchange.getRequest().getURI().getRawPath())
                                    .append(": ").append(System.currentTimeMillis() - startTime)
                                    .append("ms");
                            if (config.isWithParam()){
                                sb.append(" params:").append(exchange.getRequest().getQueryParams());
                            }
                            log.info(sb.toString());
                        }
                    })
            );
        };
    }

    public static class Config{
        private boolean withParam;

        public boolean isWithParam() {
            return withParam;
        }

        public void setWithParam(boolean withParam) {
            this.withParam = withParam;
        }
    }

}
