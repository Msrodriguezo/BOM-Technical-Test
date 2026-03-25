package com.michaelrodriguez.mrp.bom_service.mapper;

import org.springframework.stereotype.Component;

import com.michaelrodriguez.mrp.bom_service.DTO.response.ProductoResponseDTO;
import com.michaelrodriguez.mrp.bom_service.DTO.response.InsumoResponseDTO;
import com.michaelrodriguez.mrp.bom_service.entity.Producto;

/**
 * Implementación del mapper para convertir entidades Producto a DTOs de respuesta.
 * 
 * @author Michael Rodríguez
 * @version 1.0
 */
@Component
public class ProductoMapperImpl implements ProductoMapper {

    /**
     * Mapea una entidad Producto a ProductoResponseDTO.
     * 
     * Transforma:
     * - Datos básicos: id, nombre
     * - Lista de ProductoInsumoEntity a lista de InsumoResponseDTO
     * - Extrae información de unidades de medida
     * 
     * @param producto la entidad Producto a mapear
     * @return ProductoResponseDTO completamente poblado
     */
    @Override
    public ProductoResponseDTO mapToResponseDTO(Producto producto) {
        return ProductoResponseDTO.builder()
                .id(producto.getId())
                .name(producto.getNombre())
                .insumos(producto.getInsumos().stream()
                        .map(productoInsumo -> InsumoResponseDTO.builder()
                                .insumoId(productoInsumo.getInsumo().getId())
                                .insumo(productoInsumo.getInsumo().getNombre())
                                .cantidad(productoInsumo.getCantidad())
                                .unidad(productoInsumo.getInsumo().getUnidadDeMedida().getNombre())
                                .simboloUnidad(productoInsumo.getInsumo().getUnidadDeMedida().getSimbolo())
                                .build())
                        .toList())
                .build();
    }
}
