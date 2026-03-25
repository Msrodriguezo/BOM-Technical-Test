package com.michaelrodriguez.mrp.bom_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.michaelrodriguez.mrp.bom_service.entity.UnidadDeMedidaEntity;

@Repository
public interface UnidadDeMedidaRepository extends JpaRepository<UnidadDeMedidaEntity, Long> {
    Optional<UnidadDeMedidaEntity> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
