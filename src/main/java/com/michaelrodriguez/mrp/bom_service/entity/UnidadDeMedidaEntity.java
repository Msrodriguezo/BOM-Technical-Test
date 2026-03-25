package com.michaelrodriguez.mrp.bom_service.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "unidades_de_medida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnidadDeMedidaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Representa el nombre de la unidad (ej. "GRAMOS", "LITROS")
    @Column(name = "nombre", nullable = false, unique = true, length = 50)
    private String nombre;

    // Representa el símbolo (ej. "g", "L")
    @Column(name = "simbolo", nullable = false, length = 10)
    private String simbolo;

    // Representa la categoría (ej. "Peso", "Volumen")
    @Column(name = "categoria", nullable = false, length = 50)
    private String categoria;

    // Relación bidireccional hacia los insumos que usan esta unidad de medida
    @OneToMany(mappedBy = "unidadDeMedida")
    @Builder.Default
    private List<InsumoEntity> insumos = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnidadDeMedidaEntity)) return false;
        UnidadDeMedidaEntity unidad = (UnidadDeMedidaEntity) o;
        return id != null && id.equals(unidad.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}