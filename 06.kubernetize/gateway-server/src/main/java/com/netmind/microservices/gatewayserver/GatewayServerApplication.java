package com.netmind.microservices.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
//@OpenAPIDefinition(info = @Info(title = "API Gateway", version = "1.0", description = "Documentation API Gateway v1.0"))
public class GatewayServerApplication {

    public static void main(String[] args) {
        System.out.println("Initing GatewayServerApplication ...........");
        SpringApplication.run(GatewayServerApplication.class, args);
    }

}
