package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@Embeddable
public class GrupoId implements Serializable{
    //CAT_TITULO, CUR_NOMBRE, GRP_ANIO, GRP_ITERABLE
    @Column(name = "CAT_TITULO")
    private String categoria;
    
    @Column(name = "CUR_NOMBRE")
    private String curso;

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
               Objects.equals(categoria, grupoId.categoria) &&
               Objects.equals(curso, grupoId.curso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoria, curso, anio, iterable);
    }
}
