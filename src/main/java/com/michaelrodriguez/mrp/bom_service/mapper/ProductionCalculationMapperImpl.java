package com.michaelrodriguez.mrp.bom_service.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.michaelrodriguez.mrp.bom_service.DTO.response.ProductionCalculationResponseDTO;
import com.michaelrodriguez.mrp.bom_service.DTO.response.ProductionCalculationResponseDTO.InsumoCalculoDTO;
import com.michaelrodriguez.mrp.bom_service.entity.Producto;

/**
 * Implementación del mapper para cálculos de producción.
 * 
 * @author Michael Rodríguez
 * @version 1.0
 */
@Component
public class ProductionCalculationMapperImpl implements ProductionCalculationMapper {

    /**
     * Mapea el cálculo de insumos requeridos para una cantidad específica de producción.
     * 
     * Procesa:
     * 1. Itera sobre los insumos del producto
     * 2. Multiplica cada cantidad de insumo * cantidad a producir
     * 3. Extrae información de unidades de medida
     * 4. Construye la respuesta con todos los cálculos
     * 
     * @param producto el producto cuya producción se calcula
     * @param quantity cantidad de unidades de producto a producir
     * @return ProductionCalculationResponseDTO con cálculos detallados
     */
    @Override
    public ProductionCalculationResponseDTO mapToCalculationResponse(Producto producto, BigDecimal cantidad) {
        // Calcular insumos requeridos multiplicando cantidad de insumo * cantidad a producir
        List<InsumoCalculoDTO> insumos = producto.getInsumos().stream()
                .map(productoInsumo -> {
                    // Cantidad requerida = cantidad de insumo por unidad * cantidad a producir
                    BigDecimal cantidadRequerida = productoInsumo.getCantidad().multiply(cantidad);

                    return InsumoCalculoDTO.builder()
                            .insumo(productoInsumo.getInsumo().getNombre())
                            .requerido(cantidadRequerida)
                            .unidad(productoInsumo.getInsumo().getUnidadDeMedida().getNombre())
                            .simboloUnidad(productoInsumo.getInsumo().getUnidadDeMedida().getSimbolo())
                            .build();
                })
                .toList();

        // Construir respuesta con nombre del producto, cantidad y lista de insumos calculados
        return ProductionCalculationResponseDTO.builder()
                .producto(producto.getNombre())
                .cantidad(cantidad)
                .insumos(insumos)
                .build();
    }
}
