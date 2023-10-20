package com.netmind.microservices.productsservice.model;

import com.netmind.microservices.productsservice.constraints.SerialNumber;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(0)
    private Long id;

    @Column
    @NotBlank
    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @Column
    @NotBlank(message = "{serial.not.blank}")
    @NotNull
    @SerialNumber(message = "{serial.format}")
    private String serial;

    @Column
    private Integer price;
}
