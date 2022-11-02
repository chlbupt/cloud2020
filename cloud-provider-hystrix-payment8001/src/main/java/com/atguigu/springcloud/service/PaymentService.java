package com.atguigu.springcloud.service;

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

}
