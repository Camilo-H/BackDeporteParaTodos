package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.GrupoId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tbl_grupo")
@IdClass(value = GrupoId.class)
public class GrupoEntidad {
    /*@EmbeddedId
    private GrupoId id;*/

    @Id
    @Column(name = "GRP_NOMBRE")
    private String nombre;

    @Id
    @Column(name = "GRP_ANIO")
    private int anio;

    @Id
    @Column(name = "GRP_ITERABLE")
    private int iterable;

    @ManyToOne
    @JoinColumn(name = "CUR_NOMBRE", referencedColumnName = "CUR_NOMBRE")
    //@JsonBackReference
    private CursoEntidad curso;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "GRP_IMAGEN", referencedColumnName = "IMG_ID")
    private ImagenEntidad imagenGrupo;

    @Column(name = "GRP_CUPOS")
    private int cupos;

    @Column(name = "GRP_ESTADO")
    private String estado;

    @Column(name = "GRP_FECHACREACION")
    private Date fechaCreacion;

    @Column(name = "GRP_FECHA_FINALIZACION")
    private Date fechaFinalizacion;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private List<HorarioEntidad> horarios = new ArrayList<>();

    @OneToMany(mappedBy = "grupoInscripcion", cascade = CascadeType.ALL,  fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<InscripcionEntidad> inscripciones = new ArrayList<>();

}
// fetch = FetchType.EAGER fetch = FetchType.LAZY,