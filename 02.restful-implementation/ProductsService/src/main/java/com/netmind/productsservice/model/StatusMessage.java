package com.netmind.productsservice.model;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StatusMessage {
    private Integer status;
    private String message;

}