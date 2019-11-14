package com.hq.gateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: spring-cloud-project
 * @description:
 * @author: Mr.Huang
 * @create: 2019-11-11 14:21
 **/
@RunWith(SpringJUnit4ClassRunner.class)
public class ReactorTests {

    /**
     * 已编程方式创建具有多次发射能力的Flux，元素案通过FluxSink API以同步或异步方式进行
     * method: create
     */
    @Test
    public void create(){
        Flux<String> flux = Flux.create((t) -> {
            t.next("first");
            t.next("second");
            t.complete();
        });
        flux.subscribe(System.out:: println);
    }

    /**
     * 以编程方式创建一个的Flux,通过consumer回调逐一生成信号
     * generate中next只能调1次
     */
    @Test
    public void generate(){

        Flux.generate(t->{
            t.next("generate");
            t.complete();
        }).subscribe(System.out:: println);
    }

    /**
     * 以提供的元素，创建一个Flux
     */
    @Test
    public void just(){
        Flux.just("just").subscribe(System.out::println);
        Flux.just("first","second","third").subscribe(System.out:: println);
    }

    @Test
    public void from(){

        Flux.from(Flux.just("first","second","third"))
                .subscribe(System.out::println);

        Flux.from(Mono.just("one"))
                .subscribe(System.out::println);
    }

    @Test
    public void fromArray(){
        Flux.fromArray(new String[]{"one", "two", "three"})
                .subscribe(System.out::println);
    }

    @Test
    public void fromIterable(){
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        Flux.fromIterable(()->
             list.iterator()
        ).subscribe(System.out::println);
    }

    @Test
    public void empty(){
        Flux.empty().subscribe(System.out::println);
    }

    /**
     * 创建一个Flux，它在订阅之后立即以指定的错误终止。
     */
    @Test
    public void error(){
        Flux.error(new RuntimeException()).subscribe(System.out::println);
    }
}
