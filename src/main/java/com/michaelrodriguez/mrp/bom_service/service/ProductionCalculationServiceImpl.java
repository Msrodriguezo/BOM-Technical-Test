package com.michaelrodriguez.mrp.bom_service.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.michaelrodriguez.mrp.bom_service.DTO.response.ProductionCalculationResponseDTO;
import com.michaelrodriguez.mrp.bom_service.entity.Producto;
import com.michaelrodriguez.mrp.bom_service.exception.ResourceNotFoundException;
import com.michaelrodriguez.mrp.bom_service.repository.ProductoRepository;
import com.michaelrodriguez.mrp.bom_service.mapper.ProductionCalculationMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementación del servicio para el cálculo de insumos en producción.
 * 
 * Responsabilidades:
 * - Calcular la cantidad total de insumos necesarios para una producción
 * - Aplicar la fórmula: cantidad_requerida = cantidad_por_unidad × cantidad_a_producir
 * - Validar que el producto existe antes del cálculo
 * 
 * @author Michael Rodríguez
 * @version 1.0
 * @see IProductionCalculationService
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductionCalculationServiceImpl implements IProductionCalculationService {

    private final ProductoRepository productoRepository;
    private final ProductionCalculationMapper productionCalculationMapper;

    /**
     * Calcula la cantidad total de insumos necesarios para producir una cantidad específica.
     * 
     * Algoritmo:
     * 1. Obtiene el producto por ID
     * 2. Para cada insumo del BOM:
     *    - Multiplica cantidad_insumo_por_unidad × cantidad_a_producir
     * 3. Retorna un DTO con todos los insumos y sus cantidades totales
     * 
     * Ejemplo:
     * - Producto: Zapato (necesita 2 Cueros por zapato)
     * - Cantidad a producir: 100 zapatos
     * - Resultado: Necesita 200 Cueros
     * 
     * @param productoId el ID del producto a producir
     * @param cantidad cantidad de unidades de producto a fabricar
     * @return ProductionCalculationResponseDTO con el cálculo detallado
     * @throws ResourceNotFoundException si el producto no existe
     */
    @Override
    public ProductionCalculationResponseDTO calcularInsumosProduccion(Long productoId, BigDecimal cantidad) {
        log.debug("Iniciando cálculo de insumos para producto ID: {}, cantidad: {}", productoId, cantidad);
        
        // Obtener producto y validar existencia
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> {
                    log.warn("Producto no encontrado para cálculo de producción ID: {}", productoId);
                    return new ResourceNotFoundException("Producto no encontrado con ID: " + productoId);
                });

        log.info("Calculando insumos para: {}, cantidad: {}", producto.getNombre(), cantidad);
        
        // Delegar cálculo al mapper (separación de responsabilidades)
        ProductionCalculationResponseDTO resultado = 
            productionCalculationMapper.mapToCalculationResponse(producto, cantidad);
        
        log.debug("Cálculo completado. Insumos requeridos: {}", resultado.getInsumos().size());
        
        return resultado;
    }
}
