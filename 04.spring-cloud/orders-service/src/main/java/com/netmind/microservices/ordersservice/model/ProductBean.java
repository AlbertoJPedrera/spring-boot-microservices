package com.netmind.microservices.ordersservice.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductBean {
    private Long id;
    private String name;
    private String serial;
    private Integer price;
}
