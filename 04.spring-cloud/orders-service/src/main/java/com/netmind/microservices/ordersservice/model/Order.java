package com.netmind.microservices.ordersservice.model;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "orders")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(0)
    private Long id;

    @Column
    @NotNull
    @Min(1)
    private Long product;

    @Column    
    @Size(min = 3, max = 50)
    private String description;

    @Column
    private Integer quantity;

    @Column
    private Integer finalprice;

}
