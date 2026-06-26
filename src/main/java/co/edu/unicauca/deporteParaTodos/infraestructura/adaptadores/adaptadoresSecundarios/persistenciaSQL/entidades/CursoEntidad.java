package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.CursoId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tbl_curso")
@IdClass(value = CursoId.class)
public class CursoEntidad {
    @Id
    @Column(name = "CUR_NOMBRE")
    private String nombre;

    @Column(name = "dept_nombre")
    private String deporte;

    @Id
    @Column(name = "cat_titulo")
    private String categoriaCurso;

    @Column(name = "CUR_DESCRIPCION")
    private String descripcion;

    @Column(name = "CUR_IMAGEN")
    private Integer objImagen;

    @Column(name = "meta_eliminado")
    private Integer eliminado;

    @Column(name = "CUR_HORARIO")
    private String horario;

    public static CursoEntidad fabricarDeModelo(Curso curso){
        try{
            CursoEntidad entidad = new CursoEntidad();
            entidad.setCategoriaCurso(curso.getCategoriaCurso());
            entidad.setNombre(curso.getNombre());
            entidad.setDescripcion(curso.getDescripcion());
            entidad.setDeporte(curso.getDeporte());
            entidad.setObjImagen(curso.getImagenId());
            entidad.setHorario(curso.getHorario());
            return entidad;
        }catch(Exception e){
            return null;
        }
    }
}
