package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PerfilDto {
    private String id;

    private String nombre;

    private String correo;

    private String tipoId;

    private String sexo;

    private String role;

    //Este dato no existe para el modelo, solo para intercambiar con el front
    private String facultad;
    //Este dato no exite para el modelo, solo para intercambiar con el front
    private String tipoAlumno;

    public static PerfilDto fabricarDeModelo(Perfil perfil){
        try{
            PerfilDto dto = new PerfilDto();
            dto.setNombre(perfil.getNombre());
            dto.setId(perfil.getId());
            dto.setCorreo(perfil.getCorreo());
            dto.setRole(perfil.getRol());
            dto.setSexo(perfil.getSexo());
            dto.setTipoId(perfil.getTipoId());
            dto.setFacultad(perfil.getFacultad());
            dto.setTipoAlumno(perfil.getTipoAlumno());
            return dto;
        }catch(Exception e){
            return null;
        }
    }
}
