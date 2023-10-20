package com.netmind.productsservice.model;

import com.netmind.productsservice.constraints.SerialNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(0)
    private Long id;

    @Column
    @NotBlank(message = "Debe tener valor")
    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @Column
    @NotNull
    @NotBlank(message = "Debe tener valor con formato ddd-ddd-dddd")
    @SerialNumber(message = "{serial.format}")
    private String serial;
}
