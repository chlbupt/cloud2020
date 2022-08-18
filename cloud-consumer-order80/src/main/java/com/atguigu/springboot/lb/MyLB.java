package com.atguigu.springboot.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements MyLoadBalancer{
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public int getAndIncrement()
    {
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            next = current >= Integer.MAX_VALUE ? 0 : current + 1;
        }while(!this.atomicInteger.compareAndSet(current, next));
        System.out.println("*****第几次访问，next次数：" + next);
        return next;
    }


    @Override
    public ServiceInstance instances(List<ServiceInstance> instance) {
        int index = getAndIncrement() % instance.size();
        return instance.get(index);
    }
}
