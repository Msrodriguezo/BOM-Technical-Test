package com.michaelrodriguez.mrp.bom_service.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import com.michaelrodriguez.mrp.bom_service.DTO.response.ProductionCalculationResponseDTO;

@Tag(name = "Producción", description = "Cálculo de insumos para producción")
public interface IProductionController {

    @GetMapping("/calcular")
    @Operation(summary = "Calcular insumos para producción", 
            description = "Calcula la cantidad total de insumos necesarios para producir una cantidad específica de productos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cálculo de insumos realizado exitosamente", 
                    content = @Content(schema = @Schema(implementation = ProductionCalculationResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    ResponseEntity<ProductionCalculationResponseDTO> calcularInsumos(
            @RequestParam
            @NotNull(message = "El productId es requerido")
            Long productId,

            @RequestParam
            @NotNull(message = "La cantidad es requerida")
            @DecimalMin(value = "0.0001", inclusive = true, message = "La cantidad debe ser mayor a 0")
            BigDecimal cantidad);
}
