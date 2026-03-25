package com.michaelrodriguez.mrp.bom_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.michaelrodriguez.mrp.bom_service.DTO.request.InsumoRequestDTO;
import com.michaelrodriguez.mrp.bom_service.DTO.request.ProductoRequestDTO;
import com.michaelrodriguez.mrp.bom_service.DTO.response.ProductoResponseDTO;
import java.util.List;

@Tag(name = "Productos", description = "Gestión de productos y su BOM (Bill of Materials)")
public interface IProductoController {

    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente", 
                    content = @Content(schema = @Schema(implementation = ProductoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "El producto ya existe")
    })
    ResponseEntity<ProductoResponseDTO> createProducto(@Valid @RequestBody ProductoRequestDTO requestDTO);

    @GetMapping
    @Operation(summary = "Obtener todos los productos", 
            description = "Obtiene la lista completa de todos los productos registrados en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente", 
                    content = @Content(schema = @Schema(implementation = ProductoResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "No hay productos registrados")
    })
    ResponseEntity<List<ProductoResponseDTO>> getAllProductos();

    @GetMapping("/{productId}")
    @Operation(summary = "Obtener un producto", description = "Obtiene los detalles de un producto incluyendo su BOM")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado", 
                    content = @Content(schema = @Schema(implementation = ProductoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    ResponseEntity<ProductoResponseDTO> getProducto(@PathVariable Long productId);

    @PostMapping("/{productId}/insumos")
    @Operation(summary = "Agregar insumo a un producto", 
            description = "Agrega un insumo a la lista de Bill of Materials de un producto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Insumo agregado exitosamente", 
                    content = @Content(schema = @Schema(implementation = ProductoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Producto o unidad de medida no encontrados")
    })
    ResponseEntity<ProductoResponseDTO> addInsumoToProducto(
            @PathVariable Long productId,
            @Valid @RequestBody InsumoRequestDTO requestDTO);
}
