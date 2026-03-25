package com.michaelrodriguez.mrp.bom_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "insumos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsumoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidad_medida_id", nullable = false)
    private UnidadDeMedidaEntity unidadDeMedida;
    
    /*
    * Sobrescribimos equals y hashCode solo con el ID para evitar problemas de rendimiento
    * y ciclos infinitos en colecciones JPA. (Evitar @Data de Lombok en entidades)
    */ 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InsumoEntity)) return false;
        InsumoEntity insumo = (InsumoEntity) o;
        return id != null && id.equals(insumo.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
