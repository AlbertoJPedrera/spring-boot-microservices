package com.netmind.microservices.ordersservice.proxy;

import com.netmind.microservices.ordersservice.config.LoadBalancerConfiguration;
import com.netmind.microservices.ordersservice.model.ProductBean;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(name = "products-service", url = "${products-service.base-url}")
@FeignClient(name = "products-service") //for eureka and load balancer
public interface ProductsServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "${products-service.path}")
    public ProductBean getProduct(@PathVariable Long id);

}