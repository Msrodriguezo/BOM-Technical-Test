package com.michaelrodriguez.mrp.bom_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.michaelrodriguez.mrp.bom_service.DTO.request.InsumoRequestDTO;
import com.michaelrodriguez.mrp.bom_service.DTO.request.ProductoRequestDTO;
import com.michaelrodriguez.mrp.bom_service.DTO.response.ProductoResponseDTO;
import com.michaelrodriguez.mrp.bom_service.service.IProductoService;
import java.util.List;

/**
 * Controlador REST para la gestión de Productos y su Bill of Materials (BOM).
 * 
 * Responsabilidades:
 * - Crear nuevos productos
 * - Recuperar información detallada de productos incluyendo su lista de insumos
 * - Agregar insumos a un producto existente
 * 
 * Todos los endpoints están bajo el path base /api/products
 * Los contratos de los endpoints (documentación Swagger) están definidos en {@link IProductoController}
 * 
 * @author Michael Steven Rodríguez Ortiz
 * @version 1.0
 * @see IProductoController
 * @see ProductoService
 */
@RestController
@RequestMapping("/api/productos")
@Validated
@RequiredArgsConstructor
public class ProductoController implements IProductoController {

    /** Inyección de dependencia del servicio de productos */
    private final IProductoService productoService;

    /**
     * Crea un nuevo producto en el sistema.
     * 
     * @param requestDTO DTO con los datos del producto a crear (debe contener el nombre)
     * @return ResponseEntity con status 201 CREATED y el producto creado en el body
     */
    @Override
    public ResponseEntity<ProductoResponseDTO> createProducto(@Valid ProductoRequestDTO requestDTO) {
        ProductoResponseDTO response = productoService.createProducto(requestDTO);
        // Retorna HTTP 201 Created para indicar que el recurso fue creado exitosamente
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Obtiene la lista completa de todos los productos registrados.
     * 
     * @return ResponseEntity con status 200 OK y la lista de productos,
     *         o 204 No Content si no existen productos
     */
    @Override
    public ResponseEntity<List<ProductoResponseDTO>> getAllProductos() {
        List<ProductoResponseDTO> productos = productoService.getAllProductos();
        
        if (productos.isEmpty()) {
            // Si no hay productos, retorna 204 No Content
            return ResponseEntity.noContent().build();
        }
        
        // Retorna HTTP 200 OK con la lista de productos
        return ResponseEntity.ok(productos);
    }

    /**
     * Recupera los detalles de un producto por su ID.
     * 
     * Incluye:
     * - Información básica del producto
     * - Lista completa de insumos (BOM) asociados al producto
     * - Cantidad de insumo necesario por unidad de producto
     * 
     * @param productId ID único del producto a recuperar
     * @return ResponseEntity con status 200 OK y el producto con su BOM
     */
    @Override
    public ResponseEntity<ProductoResponseDTO> getProducto(Long productId) {
        ProductoResponseDTO response = productoService.getProducto(productId);
        // Retorna HTTP 200 OK con los datos del producto
        return ResponseEntity.ok(response);
    }

    /**
     * Agrega un insumo al Bill of Materials de un producto existente.
     * 
     * Si el insumo no existe, se crea automáticamente antes de asociarlo al producto.
     * 
     * @param productId ID único del producto al que se agregará el insumo
     * @param requestDTO DTO con los datos del insumo (nombre, cantidad y unidad de medida)
     * @return ResponseEntity con status 200 OK y el producto actualizado con el nuevo insumo
     */
    @Override
    public ResponseEntity<ProductoResponseDTO> addInsumoToProducto(
            Long productId,
            @Valid InsumoRequestDTO requestDTO) {
        ProductoResponseDTO response = productoService.addInsumoToProducto(productId, requestDTO);
        // Retorna HTTP 200 OK con el producto actualizado e incluyendo el nuevo insumo en su BOM
        return ResponseEntity.ok(response);
    }
}
