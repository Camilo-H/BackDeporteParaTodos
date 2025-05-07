package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.PerfilEntidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class V2InstructorDTO {
    private String id;
    private String nombre;
    private String correo;
    private String sexo;

    public static V2InstructorDTO factoryFromPerfil(PerfilEntidad perfil){
        try{
            V2InstructorDTO dto = new V2InstructorDTO();
            dto.setId(perfil.getPerf_id());
            dto.setNombre(perfil.getPerf_nombre());
            dto.setCorreo(perfil.getPerfcorreo());
            dto.setSexo(perfil.getPerf_Sexo());
            return dto;
        }catch(Exception e){
            return null;
        }
    }
}
