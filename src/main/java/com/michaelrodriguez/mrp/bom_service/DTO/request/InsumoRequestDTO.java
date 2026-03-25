package com.michaelrodriguez.mrp.bom_service.DTO.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class InsumoRequestDTO {

    @NotBlank(message = "El nombre del insumo es requerido")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    private String insumo;

    @NotNull(message = "La cantidad es requerida")
    @DecimalMin(value = "0.0001", inclusive = true, message = "La cantidad debe ser mayor a 0")
    private BigDecimal cantidad;

    @NotNull(message = "El ID de la unidad de medida es requerido")
    private Long unidadId;
}
