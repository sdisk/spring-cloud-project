package com.hq.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: spring-cloud-project
 * @description:
 * @author: Mr.Huang
 * @create: 2019-10-24 17:35
 **/
@Component
@Slf4j
public class AccessFilter extends ZuulFilter {

    /**
     * filterType: 过滤器的类型
     * pre: 路由之前
     * routing: 路由之中
     * post: 路由之后
     * error: 发送错误调用
     *
     *  同时也支持static，返回静态的响应，详情见StaticResponseFilter
     *  通过调用FilterProcessor.runFilters(type)创建或添加并运行任何filterType
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的顺序，多个过滤器的先后
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     *  过滤器的具体逻辑: 权限,安全，日志等
     *  shouldFilter == true 执行run方法
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run(){
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("%s >>> %s", request.getMethod(), request.getRequestURL().toString());
        Object accessToken = request.getParameter("token");
        if (null == accessToken){
            log.error("token is empty!");
            ctx.setSendZuulResponse(false);
            //unAuthenticate
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty!");
            } catch (Exception e){
                return null;
            }
        }
        log.info("通过{}过滤器", AccessFilter.class);
        return null;
    }
}
