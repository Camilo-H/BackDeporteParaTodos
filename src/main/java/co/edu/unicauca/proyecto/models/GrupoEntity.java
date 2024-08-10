package co.edu.unicauca.proyecto.models;

import jakarta.persistence.*;
import lombok.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table (name = "tbl_grupo")
public class GrupoEntity {
    
    @EmbeddedId
    private GrupoId grp_id;

    @Column (name = "grp_imagen", nullable = true)
    private Blob prg_image;

    @Column (name = "grp_cupos", nullable = false)
    private Number prg_cupos;

    @Column (name = "grp_estado", length = 1, nullable = false)
    private String grp_estado;

    //@DateTimeFormat(pattern = "dd/mm/yyyy")
    @Column (name = "grp_fechaCreacion", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date grp_fechaCreacion;

    //@DateTimeFormat(pattern = "dd/mm/yyyy")
    @Column (name = "grp_fechaCierre", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date grp_fechaCierre;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "objGrupo")
    private List<AlertaEntity> listaAlertas = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "objgrupoInscripcion")
    private List<InscripcionEntity> inscripciones = new ArrayList<>();

}
