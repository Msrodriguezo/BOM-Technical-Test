package com.michaelrodriguez.mrp.bom_service.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    // Relación bidireccional hacia la tabla intermedia del BOM
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductoInsumoEntity> insumos = new ArrayList<>();

    public void addInsumo(ProductoInsumoEntity producto) {
        insumos.add(producto);
        producto.setProducto(this);
    }

    public void removeInsumo(ProductoInsumoEntity producto) {
        insumos.remove(producto);
        producto.setProducto(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        Producto producto = (Producto) o;
        return id != null && id.equals(producto.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}