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
////CAT_TITULO, CUR_NOMBRE, GRP_ANIO, GRP_ITERABLE
    @Id
    @Column(name = "CAT_TITULO")
    private String categoria;

    @Id
    @Column(name = "CUR_NOMBRE")
    private String curso;

    @Id
    @Column(name = "GRP_ANIO")
    private int anio;

    @Id
    @Column(name = "GRP_ITERABLE")
    private int iterable;

    @Column(name = "GRP_IMAGEN")
    private Integer imagenGrupo;

    @Column(name = "GRP_CUPOS")
    private int cupos;

    //@Column(name = "GRP_ESTADO")
    //private String estado;

    @Column(name = "GRP_FECHACREACION")
    private Date fechaCreacion;

    @Column(name = "GRP_FECHA_FINALIZACION")
    private Date fechaFinalizacion;

    @Column(name = "META_ELIMINADO")
    private Integer eliminado;
}
// fetch = FetchType.EAGER fetch = FetchType.LAZY,