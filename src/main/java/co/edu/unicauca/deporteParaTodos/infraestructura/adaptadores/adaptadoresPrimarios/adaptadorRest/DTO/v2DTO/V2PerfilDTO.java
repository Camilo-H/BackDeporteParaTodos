package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ImagenEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.PerfilEntidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class V2PerfilDTO{

    private String id;

    private String nombre;

    private String correo;

    private String tipoId;

    private String sexo;

    private String facultad;

    private String tipoAlumno;

    private String role;

    //public static V2ImagenDTO fabricaFromImagenEntidad(ImagenEntidad entidad){
    public static V2PerfilDTO fabricaFromPerfilEntidad(PerfilEntidad entidad){
        try{
            V2PerfilDTO dto = new V2PerfilDTO();
            dto.setId(entidad.getPerf_id());
            dto.setCorreo(entidad.getPerfcorreo());
            dto.setNombre(entidad.getPerf_nombre());
            dto.setSexo(entidad.getPerf_Sexo());
            dto.setTipoId(entidad.getPerf_tipo());
            return dto;
        }catch(Exception ex){
            return null;
        }
    }
}