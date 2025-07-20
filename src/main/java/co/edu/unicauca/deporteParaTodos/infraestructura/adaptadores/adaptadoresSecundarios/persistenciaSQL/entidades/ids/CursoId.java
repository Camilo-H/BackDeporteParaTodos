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
@Setter
@Getter
public class CursoId implements Serializable{
    @Column(name = "CAT_TITULO")
    private String categoriaCurso;
    
    @Column(name = "CUR_NOMBRE")
    private String nombre;

    // Implementar equals y hashCode para que JPA funcione correctamente
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CursoId cursoId = (CursoId) o;
        return Objects.equals(categoriaCurso, cursoId.categoriaCurso) &&
               Objects.equals(nombre, cursoId.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoriaCurso, nombre);
    }
}
