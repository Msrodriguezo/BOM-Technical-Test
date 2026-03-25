package com.michaelrodriguez.mrp.bom_service.mapper;

import com.michaelrodriguez.mrp.bom_service.DTO.response.ProductionCalculationResponseDTO;
import com.michaelrodriguez.mrp.bom_service.entity.Producto;
import java.math.BigDecimal;

/**
 * Interfaz para mapear cálculos de producción a DTOs de respuesta.
 * 
 * Responsabilidades:
 * - Calcular y transformar insumos requeridos basándose en cantidad de producción
 * - Convertir entidades a formato de respuesta legible
 * 
 * @author Michael Rodríguez
 * @version 1.0
 */
public interface ProductionCalculationMapper {

    /**
     * Mapea el cálculo de insumos para una producción específica.
     * 
     * Algoritmo:
     * - Para cada insumo del producto: cantidad_requerida = cantidad_por_unidad * cantidad_a_producir
     * - Retorna un DTO con todos los insumos y sus cantidades totales
     * 
     * @param producto el producto cuya producción se calcula
     * @param cantidad cantidad de productos a producir
     * @return ProductionCalculationResponseDTO con el cálculo completo
     */
    ProductionCalculationResponseDTO mapToCalculationResponse(Producto producto, BigDecimal cantidad);
}
