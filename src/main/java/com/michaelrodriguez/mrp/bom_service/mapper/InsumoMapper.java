package com.michaelrodriguez.mrp.bom_service.mapper;

import com.michaelrodriguez.mrp.bom_service.DTO.response.InsumoResponseDTO;
import com.michaelrodriguez.mrp.bom_service.entity.InsumoEntity;

/**
 * Interfaz para mapear entidades de Insumo a DTOs de respuesta.
 * 
 * Responsabilidades:
 * - Convertir entidades JPA (InsumoEntity) a DTOs de respuesta
 * - Manejar la transformación de información de unidades de medida
 * 
 * @author Michael Rodríguez
 * @version 1.0
 */
public interface InsumoMapper {

    /**
     * Mapea una entidad InsumoEntity a InsumoResponseDTO.
     * 
     * Incluye:
     * - Información básica del insumo (id, nombre)
     * - Información de la unidad de medida (nombre y símbolo)
     * 
     * @param insumo la entidad InsumoEntity a mapear
     * @return InsumoResponseDTO con datos del insumo y su unidad
     */
    InsumoResponseDTO mapToResponseDTO(InsumoEntity insumo);
}
