package co.edu.unicauca.deporteParaTodos.core.domain.models;

import java.sql.Blob;
import java.sql.Date;

import co.edu.unicauca.deporteParaTodos.core.domain.models.ids.GrupoId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Lob;
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
@Table(name="tbl_grupo")
@IdClass(value = GrupoId.class)
public class GrupoEntidad{
    @Id
    @Column(name = "GRP_NOMBRE")
    private String nombre;

    @Id
    @Column(name = "GRP_ANIO")
    private int anio;

    @Id
    @Column(name = "GRP_ITERABLE")
    private int iterable;

    @Column(name = "CUR_NOMBRE")
    private String nombreCurso;

    @Lob
    @Column(name = "GRP_IMAGEN")
    private Blob imagen;

    @Column(name = "GRP_CUPOS")
    private int cupos;

    @Column(name = "GRP_ESTADO")
    private String estado;

    @Column(name = "GRP_FECHACREACION")
    private Date fechaCreacion;

    @Column(name = "GRP_FECHA_FINALIZACION")
    private Date fechaFinalizacion;
}
