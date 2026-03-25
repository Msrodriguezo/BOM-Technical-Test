package com.michaelrodriguez.mrp.bom_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.michaelrodriguez.mrp.bom_service.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
