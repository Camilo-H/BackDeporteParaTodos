package co.edu.unicauca.deporteParaTodos.core.domain.models.ids;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;

public class AsistenciaId implements Serializable{
    @Column(name="PERF_ID")
    private String perfilId;

    @Column(name="CLS_CODIGO")
    private Integer claseCodigo;

    // Implementar equals y hashCode para que JPA funcione correctamente
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsistenciaId asistenciaId = (AsistenciaId) o;
        return Objects.equals(perfilId, asistenciaId.perfilId) && 
               claseCodigo == asistenciaId.claseCodigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(perfilId, claseCodigo);
    }
}
