package com.michaelrodriguez.mrp.bom_service.DTO.response;

import java.math.BigDecimal;
import java.util.List;

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
public class ProductionCalculationResponseDTO {

    private String producto;

    private BigDecimal cantidad;
    
    private List<InsumoCalculoDTO> insumos;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InsumoCalculoDTO {

        private String insumo;
        
        private BigDecimal requerido;

        private String unidad;

        private String simboloUnidad;
    }
}
