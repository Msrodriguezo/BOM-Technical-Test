package com.michaelrodriguez.mrp.bom_service.service;

import java.math.BigDecimal;

import com.michaelrodriguez.mrp.bom_service.DTO.response.ProductionCalculationResponseDTO;

/**
 * Interfaz de servicio para el cálculo de insumos en producción.
 * 
 * Define el contrato para las operaciones de cálculo de materiales
 * necesarios para la producción basadas en cantidad.
 * 
 * @author Michael Rodríguez
 * @version 1.0
 */
public interface IProductionCalculationService {

    /**
     * Calcula la cantidad total de insumos necesarios para producir una cantidad específica.
     * 
     * Algoritmo:
     * - Para cada insumo del producto: cantidad_requerida = cantidad_por_unidad × cantidad_a_producir
     * - Retorna un DTO con todos los insumos y sus cantidades totales
     * 
     * @param productoId el ID del producto cuya producción se calcula
     * @param cantidad la cantidad de unidades de producto a producir
     * @return el cálculo detallado de insumos requeridos
     * @throws com.michaelrodriguez.mrp.bom_service.exception.ResourceNotFoundException 
     *         si el producto no existe
     */
    ProductionCalculationResponseDTO calcularInsumosProduccion(Long productoId, BigDecimal cantidad);
}
