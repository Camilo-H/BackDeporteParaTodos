package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.PerfilDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.PerfilEntidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Perfil {

    private String id;

    private String nombre;

    private String correo;

    private Integer imagen;

    private String tipoId;

    private String sexo;

    private String rol;

    private String facultad;

    private String tipoAlumno;

    private String alm_codigo;

    public static Perfil fabricarDeEntidad(PerfilEntidad entidad){
        try{
            Perfil perfil = new Perfil();
            perfil.setId(entidad.getPerf_id());
            perfil.setNombre(entidad.getPerf_nombre());
            perfil.setTipoId(entidad.getPerf_tipo());
            perfil.setSexo(entidad.getPerf_Sexo());
            perfil.setCorreo(entidad.getPerfcorreo());
            perfil.setImagen(entidad.getPerf_imagen());
            perfil.setRol(null);
            perfil.setFacultad(null);
            perfil.setTipoAlumno(null);
            return perfil;
        }catch (Exception e){
            return null;
        }
    }

    public static Perfil fabricarDeDto(PerfilDto dto) {
        try {
            Perfil perfil = new Perfil();
            perfil.setId(dto.getId());
            perfil.setNombre(dto.getNombre());
            perfil.setCorreo(dto.getCorreo());
            perfil.setTipoId(dto.getTipoId());
            perfil.setSexo(dto.getSexo());
            perfil.setRol(dto.getRole());
            perfil.setFacultad(dto.getFacultad());
            perfil.setTipoAlumno(dto.getTipoAlumno());
            perfil.setAlm_codigo(dto.getAlumnoCodigo());
            perfil.setImagen(null);
            return perfil;
        } catch (Exception e) {
            return null;
        }
    }
}
