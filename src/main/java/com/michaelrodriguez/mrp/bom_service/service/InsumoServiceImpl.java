package com.michaelrodriguez.mrp.bom_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.michaelrodriguez.mrp.bom_service.DTO.response.InsumoResponseDTO;
import com.michaelrodriguez.mrp.bom_service.entity.InsumoEntity;
import com.michaelrodriguez.mrp.bom_service.repository.InsumoRepository;
import com.michaelrodriguez.mrp.bom_service.mapper.InsumoMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para la consulta de Insumos.
 * 
 * Responsabilidades:
 * - Recuperar lista completa de insumos
 * - Transformar entidades a DTOs para respuesta
 * 
 * @author Michael Rodríguez
 * @version 1.0
 * @see IInsumoService
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class InsumoServiceImpl implements IInsumoService {

    private final InsumoRepository insumoRepository;
    private final InsumoMapper insumoMapper;

    /**
     * Obtiene la lista completa de todos los insumos del sistema.
     * 
     * Proceso:
     * 1. Consulta todos los insumos de la BD
     * 2. Mapea cada InsumoEntity a InsumoResponseDTO
     * 3. Retorna lista con información de nombre, unidad y símbolo
     * 
     * @return lista de InsumoResponseDTO con todos los insumos registrados
     */
    @Override
    public List<InsumoResponseDTO> getAllInsumos() {
        log.debug("Obteniendo lista completa de insumos");
        
        List<InsumoEntity> insumos = insumoRepository.findAll();
        log.info("Se encontraron {} insumos en el sistema", insumos.size());
        
        // Mapea todos los insumos a DTOs de respuesta usando el mapper inyectado
        return insumos.stream()
                .map(insumoMapper::mapToResponseDTO)
                .collect(Collectors.toList());
    }
}
