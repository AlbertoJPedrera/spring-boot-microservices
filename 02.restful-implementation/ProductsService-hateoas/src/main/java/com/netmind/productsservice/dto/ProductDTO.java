package com.netmind.productsservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.netmind.productsservice.constraints.SerialNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(Include.NON_NULL)
@Schema(name = "Product", description = "Modelo producto")
@XmlRootElement
public class ProductDTO extends RepresentationModel<ProductDTO> {
    @Min(0)
    @Schema(name = "Product ID", example = "1", required = false)
    private Long id;

    @NotBlank(message = "Debe tener valor")
    @NotNull
    @Size(min = 3, max = 50)
    @Schema(name = "Product name", example = "Product 1", required = true)
    private String name;

    @NotNull
    @NotBlank(message = "Debe tener valor con formato ddd-ddd-dddd")
    @SerialNumber(message = "{serial.format}")
    @Schema(name = "Product serial number", example = "111-222-3333", required = true)
    private String serial;
}
