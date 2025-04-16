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
    private String cat_titulo;
    
    @Column(name = "CUR_NOMBRE")
    private String cur_nombre;

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
               Objects.equals(cat_titulo, grupoId.cat_titulo) &&
               Objects.equals(cur_nombre, grupoId.cur_nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cat_titulo, cur_nombre, anio, iterable);
    }
}
