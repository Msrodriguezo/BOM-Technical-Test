package com.michaelrodriguez.mrp.bom_service.service;

import com.michaelrodriguez.mrp.bom_service.DTO.request.InsumoRequestDTO;
import com.michaelrodriguez.mrp.bom_service.DTO.request.ProductoRequestDTO;
import com.michaelrodriguez.mrp.bom_service.DTO.response.ProductoResponseDTO;
import java.util.List;

/**
 * Interfaz de servicio para la gestión de Productos.
 * 
 * Define el contrato para todas las operaciones relacionadas con productos,
 * incluyendo crear, obtener y agregar insumos al Bill of Materials.
 * 
 * @author Michael Rodríguez
 * @version 1.0
 */
public interface IProductoService {

    /**
     * Crea un nuevo producto en el sistema.
     * 
     * @param requestDTO datos del producto a crear
     * @return el producto creado con su información
     * @throws com.michaelrodriguez.mrp.bom_service.exception.DuplicateResourceException 
     *         si el producto ya existe
     */
    ProductoResponseDTO createProducto(ProductoRequestDTO requestDTO);

    /**
     * Obtiene la lista completa de todos los productos registrados.
     * 
     * @return lista de todos los productos con sus insumos (BOM)
     */
    List<ProductoResponseDTO> getAllProductos();

    /**
     * Obtiene un producto por su ID.
     * 
     * @param productId el ID único del producto
     * @return datos del producto incluyendo su BOM
     * @throws com.michaelrodriguez.mrp.bom_service.exception.ResourceNotFoundException 
     *         si el producto no existe
     */
    ProductoResponseDTO getProducto(Long productId);

    /**
     * Agrega un insumo al Bill of Materials de un producto.
     * 
     * Si el insumo no existe, se crea automáticamente.
     * 
     * @param productId el ID del producto
     * @param requestDTO datos del insumo a agregar
     * @return el producto actualizado con el nuevo insumo
     * @throws com.michaelrodriguez.mrp.bom_service.exception.ResourceNotFoundException 
     *         si el producto o unidad de medida no existen
     */
    ProductoResponseDTO addInsumoToProducto(Long productId, InsumoRequestDTO requestDTO);
}
