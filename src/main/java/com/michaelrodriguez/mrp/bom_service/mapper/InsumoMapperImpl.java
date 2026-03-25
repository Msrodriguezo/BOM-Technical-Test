package com.michaelrodriguez.mrp.bom_service.mapper;

import org.springframework.stereotype.Component;

import com.michaelrodriguez.mrp.bom_service.DTO.response.InsumoResponseDTO;
import com.michaelrodriguez.mrp.bom_service.entity.InsumoEntity;

/**
 * Implementación del mapper para convertir entidades Insumo a DTOs de respuesta.
 * 
 * @author Michael Rodríguez
 * @version 1.0
 */
@Component
public class InsumoMapperImpl implements InsumoMapper {

    /**
     * Mapea una entidad InsumoEntity a InsumoResponseDTO.
     * 
     * Transforma:
     * - Datos básicos: id, nombre
     * - Información de la unidad de medida: nombre y símbolo
     * 
     * @param insumo la entidad InsumoEntity a mapear
     * @return InsumoResponseDTO completamente poblado
     */
    @Override
    public InsumoResponseDTO mapToResponseDTO(InsumoEntity insumo) {
        return InsumoResponseDTO.builder()
                .insumoId(insumo.getId())
                .insumo(insumo.getNombre())
                .cantidad(null) // La cantidad se asigna en ProductoInsumoEntity, no a nivel de Insumo
                .unidad(insumo.getUnidadDeMedida().getNombre())
                .simboloUnidad(insumo.getUnidadDeMedida().getSimbolo())
                .build();
    }
}
