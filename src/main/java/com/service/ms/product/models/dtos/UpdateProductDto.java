package com.service.ms.product.models.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductDto {

    @NotNull(message = "El campo name no debe ser nulo")
    @NotBlank(message = "El campo name no debe estar vacio")
    @Size(min = 3, max = 50, message = "El campo name debe tener entre 3 y 50 caracteres")
    private String name;

    @Size(max = 500, message = "El campo description no debe exceder los 500 caracteres")
    private String description;

    @NotNull(message = "El campo price no debe ser nulo")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener como máximo 10 dígitos enteros y 2 decimales")
    private BigDecimal price;

    @NotNull(message = "El campo categoryId no debe ser nulo")
    @Positive(message = "El categoryId debe ser un número positivo")
    private Long categoryId;

    @NotNull(message = "El campo status no debe ser nulo")
    private Boolean status;
}
