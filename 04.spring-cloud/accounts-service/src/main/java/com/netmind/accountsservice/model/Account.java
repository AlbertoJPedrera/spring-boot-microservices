package com.netmind.accountsservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "accounts")
@Schema(name = "account", description = "Account model")
@XmlRootElement
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(0)
    @Schema(name = "Account ID", example = "1", required = false)
    private Long id;

    @NotBlank(message = "Debe tener valor")
    @NotNull
    @Size(min = 3, max = 50)
    @Schema(name = "Account type", example = "Personal", required = true)
    private String type;

    @DateTimeFormat
    @NotNull
    @Schema(name = "Opening date", example = "2023-10-01", required = true)
    Date openingDate;

    @Min(0)
    @Schema(name = "Current balance", example = "10245.67", required = true)
    private int balance;

    @Min(1)
    @Schema(name = "Customer id", example = "34", required = true)
    private Long ownerId;

    @Transient
    Customer owner;


}
