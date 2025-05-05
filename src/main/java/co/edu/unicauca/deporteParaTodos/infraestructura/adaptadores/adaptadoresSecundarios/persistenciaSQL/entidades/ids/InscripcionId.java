package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InscripcionId implements Serializable{
        
    @Column(name = "CAT_TITULO")
    private String categoria;
    
    @Column(name = "CUR_NOMBRE")
    private String curso;

    @Column(name = "GRP_ANIO")
    private int anio;

    @Column(name = "GRP_ITERABLE")
    private int iterable;

    @Column(name = "perf_id")
    private String alumnoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InscripcionId inscripcionId = (InscripcionId) o;
        return anio == inscripcionId.anio && 
               iterable == inscripcionId.iterable && 
               Objects.equals(categoria, inscripcionId.categoria) &&
               Objects.equals(curso, inscripcionId.curso) &&
               Objects.equals(alumnoId, inscripcionId.alumnoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoria, curso, anio, iterable, alumnoId);
    }
}
