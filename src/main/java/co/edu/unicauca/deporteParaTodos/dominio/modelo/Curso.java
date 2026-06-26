package co.edu.unicauca.deporteParaTodos.dominio.modelo;


import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.CursoDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CursoEntidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Curso {

    private String nombre;

    private String deporte;

    private String categoriaCurso;

    private String descripcion;

    private Integer imagenId;

    private EstadoCurso estadoCurso;

    public static Curso fabricarDeEntidad(CursoEntidad entidad){
        try{
            Curso fabricado = new Curso();
            fabricado.setNombre(entidad.getNombre());
            fabricado.setCategoriaCurso(entidad.getCategoriaCurso());
            fabricado.setDescripcion(entidad.getDescripcion());
            fabricado.setDeporte(entidad.getDeporte());
            fabricado.setImagenId(entidad.getObjImagen());
            fabricado.setEstadoCurso(
                Integer.valueOf(1).equals(entidad.getEliminado()) ? EstadoCurso.INACTIVO : EstadoCurso.ACTIVO
            );
            return fabricado;
        }catch(Exception e){
            return null;
        }
    }

    public static Curso fabricarDeDto(CursoDto dto){
        try{
            Curso fabricado = new Curso();
            fabricado.setCategoriaCurso(dto.getCategoriaCurso());
            fabricado.setNombre(dto.getNombre());
            fabricado.setDescripcion(dto.getDescripcion());
            fabricado.setDeporte(dto.getDeporte());
            fabricado.setImagenId(dto.getIdImagen());
            return fabricado;
        }catch(Exception e){
            return null;
        }
    }
}