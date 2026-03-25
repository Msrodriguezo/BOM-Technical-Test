package com.michaelrodriguez.mrp.bom_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.michaelrodriguez.mrp.bom_service.DTO.response.InsumoResponseDTO;
import com.michaelrodriguez.mrp.bom_service.service.IInsumoService;
import java.util.List;

/**
 * Controlador REST para la consulta de Insumos.
 * 
 * Responsabilidades:
 * - Recuperar información de insumos almacenados
 * - Listar todos los insumos del sistema
 * 
 * Todos los endpoints están bajo el path base /api/insumos
 * Los contratos de los endpoints (documentación Swagger) están definidos en {@link IInsumoController}
 * 
 * @author Michael Steven Rodríguez Ortiz
 * @version 1.0
 * @see IInsumoController
 * @see IInsumoService
 */
@RestController
@RequestMapping("/api/insumos")
@Validated
@RequiredArgsConstructor
public class InsumoController implements IInsumoController {

    /** Inyección de dependencia del servicio de insumos */
    private final IInsumoService insumoService;

    /**
     * Obtiene la lista completa de todos los insumos registrados en el sistema.
     * 
     * @return ResponseEntity con status 200 OK y la lista de insumos,
     *         o 204 No Content si no existen insumos
     */
    @Override
    public ResponseEntity<List<InsumoResponseDTO>> getAllInsumos() {
        List<InsumoResponseDTO> insumos = insumoService.getAllInsumos();
        
        if (insumos.isEmpty()) {
            // Si no hay insumos, retorna 204 No Content
            return ResponseEntity.noContent().build();
        }
        
        // Retorna HTTP 200 OK con la lista de insumos
        return ResponseEntity.ok(insumos);
    }
}
