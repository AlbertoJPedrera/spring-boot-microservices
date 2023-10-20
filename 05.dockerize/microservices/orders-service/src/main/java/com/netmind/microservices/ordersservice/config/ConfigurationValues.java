package com.netmind.microservices.ordersservice.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("orders-service")

@Getter @Setter @ToString
public class ConfigurationValues {
    private int max;
    private int min;
}
