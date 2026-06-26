package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.EstadoCurso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CursoDto {
    @NotBlank(message = "{curso.nombre.blank}")
    private String nombre;

    @NotBlank(message = "{curso.deporte.blank}")
    private String deporte;

    @NotBlank(message = "{curso.categoria.blank}")
    private String categoriaCurso;

    @NotBlank(message = "{curso.descripcion.blank}")
    private String descripcion;

    @NotNull(message = "{curso.imagen.null}")
    private Integer idImagen;

    private EstadoCurso estadoCurso;

    private String horario;

    public static CursoDto fabricarDeModelo(Curso curso){
        try{
            CursoDto dto = new CursoDto();
            dto.setCategoriaCurso(curso.getCategoriaCurso());
            dto.setNombre(curso.getNombre());
            dto.setDescripcion(curso.getDescripcion());
            dto.setIdImagen(curso.getImagenId());
            dto.setDeporte(curso.getDeporte());
            dto.setEstadoCurso(curso.getEstadoCurso());
            dto.setHorario(curso.getHorario());
            return dto;
        }catch(Exception e){
            return null;
        }
    }

}
