package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "tbl_perfil")
public class PerfilEntidad {
    @Id
    @Column(name = "perf_id", length = 50, nullable = false)
    private String perf_id;

    @Column(name = "perf_nombre", length = 100, nullable = false)
    private String perf_nombre;

    @Column(name = "perf_correo", length = 100, nullable = false)
    private String perfcorreo;

    @Column(name = "perf_imagen")
    private Integer perf_imagen;

    @Column(name = "perf_tipoid", length = 50, nullable = false)
    private String perf_tipo;

    @Column(name = "perf_sexo", length = 10, nullable = false)
    private String perf_Sexo;

    @Column(name ="META_ELIMINADO")
    private Integer eliminado;

    public static PerfilEntidad fabricarDeModelo(Perfil perfil, int eliminado){
        try{
            PerfilEntidad entidad = new PerfilEntidad();
            entidad.setEliminado(eliminado);
            entidad.setPerf_id(perfil.getId());
            entidad.setPerf_nombre(perfil.getNombre());
            entidad.setPerf_Sexo(perfil.getSexo());
            entidad.setPerf_tipo(perfil.getTipoId());
            entidad.setPerfcorreo(perfil.getCorreo());
            entidad.setPerf_imagen(perfil.getImagen());
            return entidad;
        }catch(Exception e){
            return null;
        }
    }
}
