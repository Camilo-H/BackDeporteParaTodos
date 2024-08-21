package co.edu.unicauca.deporteParaTodos.core.domain.models.ids;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class GrupoId implements Serializable{
    @Column(name = "GRP_NOMBRE")
    private String nombre;

    @Column(name = "GRP_ANIO")
    private int anio;

    @Column(name = "GRP_ITERABLE")
    private int iterable;

    // Implementar equals y hashCode para que JPA funcione correctamente
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrupoId grupoId = (GrupoId) o;
        return anio == grupoId.anio && 
               iterable == grupoId.iterable && 
               Objects.equals(nombre, grupoId.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, anio, iterable);
    }
}
