package com.michaelrodriguez.mrp.bom_service.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.michaelrodriguez.mrp.bom_service.DTO.response.ProductionCalculationResponseDTO;
import com.michaelrodriguez.mrp.bom_service.service.IProductionCalculationService;

/**
 * Controlador REST para el cálculo de insumos en el proceso de producción.
 * 
 * Responsabilidades:
 * - Calcular la cantidad total de insumos necesarios para producir
 *   una cantidad específica de un producto
 * - Aplicar la fórmula: Cantidad Total = Cantidad por Unidad × Cantidad a Producir
 * 
 * Todos los endpoints están bajo el path base /api/produccion
 * Los contratos de los endpoints (documentación Swagger) están definidos en {@link IProductionController}
 * 
 * Retorna la cantidad necesaria de cada insumo para producir 100 unidades del producto.
 * 
 * @author Michael Steven Rodríguez Ortiz
 * @version 1.0
 * @see IProductionController
 * @see ProductionCalculationService
 */
@RestController
@RequestMapping("/api/produccion")
@Validated
@RequiredArgsConstructor
public class ProductionController implements IProductionController {

    /** Inyección de dependencia del servicio de cálculo de insumos para producción */
    private final IProductionCalculationService productionCalculationService;

    /**
     * Calcula la cantidad total de insumos necesarios para producir una cantidad específica.
     * 
     * Algoritmo:
     * 1. Obtiene el producto y su lista de insumos (BOM)
     * 2. Para cada insumo:
     *    - Multiplica la cantidad requerida por unidad × cantidad a producir
     *    - Registra el resultado con el nombre del insumo y unidad de medida
     * 3. Retorna un resumen con todos los insumos y cantidades necesarias
     * 
     * @param productId ID único del producto del cual se calcularán los insumos
     * @param quantity Cantidad de productos a fabricar (debe ser > 0)
     * @return ResponseEntity con status 200 OK y el cálculo detallado de insumos requeridos
     */
    @Override
    public ResponseEntity<ProductionCalculationResponseDTO> calcularInsumos(
            Long productId,
            BigDecimal cantidad) {

        // Solicita al servicio el cálculo de insumos para la cantidad especificada
        ProductionCalculationResponseDTO response = productionCalculationService
                .calcularInsumosProduccion(productId, cantidad);

        // Retorna HTTP 200 OK con el resultado del cálculo
        return ResponseEntity.ok(response);
    }
}
