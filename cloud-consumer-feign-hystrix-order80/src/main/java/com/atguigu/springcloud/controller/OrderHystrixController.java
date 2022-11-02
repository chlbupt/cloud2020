package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("consumer")
@Slf4j
@DefaultProperties(defaultFallback = "paymentGlobalFallbackMethod")// hystrix 全局fallback方法
public class OrderHystrixController {
    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfoOK(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfoOk(id);
        return result;
    }

    @GetMapping("/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod="paymentTimeoutFallbackMethod", commandProperties = {
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="1500")
//    })
    @HystrixCommand
    public String paymentInfoTimeout(@PathVariable("id") Integer id){
        int age = 10 / 0;
        String result = paymentHystrixService.paymentInfoTimeout(id);
        return result;
    }

    public String paymentTimeoutFallbackMethod(@PathVariable("id") Integer id){
        return "我是消费者80，对方支付系统繁忙请10秒后再试或者自己运行出错检查自己，/(ㄒoㄒ)/~~";
    }

    public String paymentGlobalFallbackMethod() {
        return "Global异常处理信息，请稍后再试，/(ㄒoㄒ)/~~";
    }
}