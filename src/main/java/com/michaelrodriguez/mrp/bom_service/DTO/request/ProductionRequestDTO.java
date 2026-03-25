package com.michaelrodriguez.mrp.bom_service.DTO.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionRequestDTO {

    @NotNull(message = "El ID del producto es requerido")
    private Long productoId;

    @NotNull(message = "La cantidad a producir es requerida")
    @DecimalMin(value = "0.0001", inclusive = true, message = "La cantidad debe ser mayor a 0")
    private BigDecimal cantidad;
}
