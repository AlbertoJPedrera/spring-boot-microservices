package com.netmind.productsservice.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@XmlRootElement
public class StatusMessage {
    private Integer status;
    private String message;

}