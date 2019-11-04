package com.hq.stream.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * SCS中消息的发布和消费有4个组件
 * 1.source
 *  当一个服务准备发布消息时，它将使用一个source来发布消息，source会将发送的java对象序列化并发布到channel
 * 2.channel
 *  通道是队列的一种抽象，通道始终与目标队列名称关联，我们可以通过修改配置文件来更改队列名称，而不需要修改代码
 * 3.binder
 *  通过定义绑定器作为中间层，实现了应用程序与消息中间件细节之间的隔离
 * 4.sink
 *  服务通过sink从队列中接收消息，并进行反序列化
 *
 *  默认广播,可以通过分组和分区来定义由哪一个消费者来消费，避免重复消费
 *  分组是一个队列有多个实例，同一个组的消费者分别进行消费
 *  分区是一个或者多个生产者给多个消费者发送消费确保相同特征的数据被统一消费者实例进行处理，
 *  生成了多个队列，每个队列绑定了一个消费者，生产者会有区别的进行发送到哪一个队列中
 *
 * SCS中自带支持的常用消息类型转换：
 *
 * Json与POJO的互相转换；
 * Json与org.springframework.tuple.Tuple的相互转换；
 * Object与byte[]的相互转换，Object需要可序列化；
 * String与byte[]的互相转换；
 * Object向纯文本转换，Object需要实现toString()方法
 */

@SpringBootApplication
@EnableDiscoveryClient
public class StreamProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamProviderApplication.class, args);
    }

}
