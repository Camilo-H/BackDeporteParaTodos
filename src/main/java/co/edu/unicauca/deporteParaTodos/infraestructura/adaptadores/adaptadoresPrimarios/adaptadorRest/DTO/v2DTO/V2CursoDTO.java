package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CursoEntidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class V2CursoDTO {
    
    private String nombre;

    private String deporte;

    private String categoriaCurso;

    private String descripcion;

    private Integer idImagen;

    private Integer eliminado;

    public static V2CursoDTO fromEntity(CursoEntidad entidad){
        V2CursoDTO dto;
        try{
            dto = new V2CursoDTO(entidad.getNombre(), entidad.getDeporte(), entidad.getCategoriaCurso(), entidad.getDescripcion(), entidad.getObjImagen(), entidad.getEliminado());
            return dto;
        }catch (Exception e){
            return null;
        }
    }
}
