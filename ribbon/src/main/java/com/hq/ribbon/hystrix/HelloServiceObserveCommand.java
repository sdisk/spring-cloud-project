package com.hq.ribbon.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

/**
 * @program: spring-cloud-project
 * @description:
 * @author: Mr.Huang
 * @create: 2019-10-31 11:26
 **/
public class HelloServiceObserveCommand extends HystrixObservableCommand<String> {

    private RestTemplate restTemplate;

    public HelloServiceObserveCommand(String commandGroupKey, RestTemplate restTemplate){
        super(HystrixCommandGroupKey.Factory.asKey(commandGroupKey));
        this.restTemplate = restTemplate;
    }

    /**
     * Observable 被观察者，被观察者是被listener观察者进行
     */
    @Override
    protected Observable<String> construct() {
        //观察者订阅网络请求事件
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
               try {
                   if (!subscriber.isUnsubscribed()){
                       System.out.println("方法开始执行");
                       String result = restTemplate.getForObject("http://registry-client/user/hello", String.class);
                       //这个方法监听方法，是传递结果的，请求多次的结果通过它返回去汇总
                       subscriber.onNext(result);
                       String result1 = restTemplate.getForObject("http://registry-client/user/hello", String.class);
                       //这个方法是监听方法，传递结果
                       subscriber.onNext(result1);
                       subscriber.onCompleted();
                   }
               } catch (Exception e){
                   subscriber.onError(e);
               }
            }
        });
    }

    @Override
    protected Observable<String> resumeWithFallback() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try{
                    if (!subscriber.isUnsubscribed()){
                        subscriber.onNext("occur error");
                        subscriber.onCompleted();
                    }
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }
}
