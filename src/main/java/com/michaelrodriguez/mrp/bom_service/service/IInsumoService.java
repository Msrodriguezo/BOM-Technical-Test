package com.michaelrodriguez.mrp.bom_service.service;

import com.michaelrodriguez.mrp.bom_service.DTO.response.InsumoResponseDTO;
import java.util.List;

/**
 * Interfaz de servicio para la consulta de Insumos.
 * 
 * Define el contrato para operaciones de lectura relacionadas con insumos.
 * 
 * @author Michael Rodríguez
 * @version 1.0
 */
public interface IInsumoService {

    /**
     * Obtiene la lista completa de todos los insumos registrados en el sistema.
     * 
     * @return lista de todos los insumos con información de unidad de medida
     */
    List<InsumoResponseDTO> getAllInsumos();
}
