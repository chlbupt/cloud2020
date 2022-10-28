package com.atguigu.springcloud.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfoOK(Integer id) {
        return "线程池:  " + Thread.currentThread().getName() + "  paymentInfoOK,id:  " + id + "\t"
                + "O(∩_∩)O哈哈~";
    }

    public String paymentInfo_timeout(Integer id){
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_timeout,ID: " + id + "\t";
    }
}
