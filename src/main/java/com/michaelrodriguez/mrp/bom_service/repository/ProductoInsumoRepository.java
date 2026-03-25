package com.michaelrodriguez.mrp.bom_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.michaelrodriguez.mrp.bom_service.entity.ProductoInsumoEntity;

@Repository
public interface ProductoInsumoRepository extends JpaRepository<ProductoInsumoEntity, Long> {
}
