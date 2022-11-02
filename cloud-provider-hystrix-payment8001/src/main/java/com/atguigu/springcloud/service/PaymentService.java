package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfoOK(Integer id) {
        return "线程池:  " + Thread.currentThread().getName() + "  paymentInfoOK,id:  " + id + "\t"
                + "O(∩_∩)O哈哈~";
    }

    @HystrixCommand(fallbackMethod="paymentInfoTimeoutHandler", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfoTimeout(Integer id){
//        int i = 10 /0;
        int second = 5000;

        try {
            TimeUnit.MICROSECONDS.sleep(second);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_timeout,ID: " + id + "\t";
    }

    public String paymentInfoTimeoutHandler(Integer id){
        return "线程池： " + Thread.currentThread().getName() + " paymentInfo_timeoutHandler系统繁忙或者运行报错，请稍后再试，id： " + id + "\t" + "/(ㄒoㄒ)/~~";
    }

    @HystrixCommand(fallbackMethod="paymentCircuitBreakerFallback", commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled", value="true"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="10000"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="60"),
    })
    public String paymentCircuitBreaker(Integer id){
        if (id < 0){
            throw new RuntimeException("******id 不能负数");
        }
        String number = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号：" + number;
    }

    public String paymentCircuitBreakerFallback(Integer id){
        return Thread.currentThread().getName() + "\t" + "id 不能负数或超时或自身错误，请稍后再试，o(╥﹏╥)o id: " + id;
    }

}
