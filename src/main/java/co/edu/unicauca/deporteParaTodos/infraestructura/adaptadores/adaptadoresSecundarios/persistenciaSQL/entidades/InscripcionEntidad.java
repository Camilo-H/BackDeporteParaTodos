package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.JoinColumns;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tbl_inscripcion")
public class InscripcionEntidad {
    @Id
    @Column(name = "inscr_fechainscripcion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaInscripcion;

    @Column(name = "inscr_fechadesvinculacion", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaDesvinculacion;

    @Column(name = "fecha_desvinculacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaDesvinculado;

    @ManyToOne
    @JoinColumn(name = "perf_id", referencedColumnName = "perf_id")
    private AlumnoEntidad alumno;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "GRP_NOMBRE", referencedColumnName = "GRP_NOMBRE"),
            @JoinColumn(name = "GRP_ANIO", referencedColumnName = "GRP_ANIO"),
            @JoinColumn(name = "GRP_ITERABLE", referencedColumnName = "GRP_ITERABLE") })
    private GrupoEntidad grupoInscripcion;

}