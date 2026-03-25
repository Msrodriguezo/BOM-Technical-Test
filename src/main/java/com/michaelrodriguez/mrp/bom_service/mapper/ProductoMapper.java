package com.michaelrodriguez.mrp.bom_service.mapper;

import com.michaelrodriguez.mrp.bom_service.DTO.response.ProductoResponseDTO;
import com.michaelrodriguez.mrp.bom_service.entity.Producto;

/**
 * Interfaz para mapear entidades de Producto a DTOs de respuesta.
 * 
 * Responsabilidades:
 * - Convertir entidades JPA (Producto) a DTOs de respuesta
 * - Manejar transformaciones de datos complejas (relaciones ManyToMany)
 * 
 * @author Michael Rodríguez
 * @version 1.0
 */
public interface ProductoMapper {

    /**
     * Mapea una entidad Producto a ProductoResponseDTO.
     * 
     * Incluye:
     * - Información básica del producto (id, nombre)
     * - Lista completa de insumos (BOM) con sus detalles
     * 
     * @param producto la entidad Producto a mapear
     * @return ProductoResponseDTO con datos del producto e insumos
     */
    ProductoResponseDTO mapToResponseDTO(Producto producto);
}
