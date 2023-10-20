package com.netmind.productsservice.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.netmind.productsservice.constraints.SerialNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JacksonXmlRootElement
@Schema(name = "Product", description = "Modelo de producto")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(0)
    @Schema(name = "Product Id", example = "1", required = false)
    private Long id;

    @Column
    @NotBlank(message = "El nombre debe tener valor")
    @NonNull
    @Size(min = 3, max = 50)
    @Schema(name = "Product name", example = "Product 1", required = true)
    private String name;

    @Column
    @SerialNumber(message = "{serial.format}")
    @Schema(name = "Product serial number", example = "111-222-3333", required = true)
    private String serial;
}
