package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CursoDto {
    //TODO: agregar restricciones
    private String nombre;

    private String deporte;

    private String categoriaCurso;

    private String descripcion;

    private Integer idImagen;

    private Integer eliminado;

    public static CursoDto fabricarDeModelo(Curso curso){
        try{
            CursoDto dto = new CursoDto();
            dto.setCategoriaCurso(curso.getCategoriaCurso());
            dto.setNombre(curso.getNombre());
            dto.setDescripcion(curso.getDescripcion());
            dto.setIdImagen(curso.getImagenId());
            dto.setDeporte(curso.getDeporte());
            return dto;
        }catch(Exception e){
            return null;
        }
    }

}
