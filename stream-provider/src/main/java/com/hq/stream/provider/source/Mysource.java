package com.hq.stream.provider.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @program: spring-cloud-project
 * @description: 自定义output，这里要跟配置文件一致
 * @author: Mr.Huang
 * @create: 2019-11-04 15:41
 **/
public interface Mysource {

    @Output("myOutput")
    MessageChannel myOutput();
}
