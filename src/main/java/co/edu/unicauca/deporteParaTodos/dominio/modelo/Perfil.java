package co.edu.unicauca.deporteParaTodos.dominio.modelo;

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
            return perfil;
        }catch (Exception e){
            return null;
        }
    }
}
