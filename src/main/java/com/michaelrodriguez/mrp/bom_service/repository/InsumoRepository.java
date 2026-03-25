package com.michaelrodriguez.mrp.bom_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.michaelrodriguez.mrp.bom_service.entity.InsumoEntity;

@Repository
public interface InsumoRepository extends JpaRepository<InsumoEntity, Long> {
    Optional<InsumoEntity> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
