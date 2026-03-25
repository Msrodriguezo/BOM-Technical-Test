package com.michaelrodriguez.mrp.bom_service.DTO.response;

import java.math.BigDecimal;

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
public class InsumoResponseDTO {

    private Long insumoId;

    private String insumo;
    
    private BigDecimal cantidad;

    private String unidad;

    private String simboloUnidad;
}
