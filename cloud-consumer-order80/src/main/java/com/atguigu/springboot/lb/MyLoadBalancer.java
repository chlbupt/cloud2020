package com.atguigu.springboot.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface MyLoadBalancer {
    ServiceInstance instances(List<ServiceInstance> instance);
}
