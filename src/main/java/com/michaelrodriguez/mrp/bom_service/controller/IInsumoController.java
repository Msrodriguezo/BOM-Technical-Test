package com.michaelrodriguez.mrp.bom_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.michaelrodriguez.mrp.bom_service.DTO.response.InsumoResponseDTO;
import java.util.List;

@Tag(name = "Insumos", description = "Consulta y gestión de insumos")
public interface IInsumoController {

    @GetMapping
    @Operation(summary = "Obtener todos los insumos", 
            description = "Obtiene la lista completa de todos los insumos almacenados en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de insumos obtenida exitosamente", 
                    content = @Content(schema = @Schema(implementation = InsumoResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "No hay insumos registrados")
    })
    ResponseEntity<List<InsumoResponseDTO>> getAllInsumos();
}
