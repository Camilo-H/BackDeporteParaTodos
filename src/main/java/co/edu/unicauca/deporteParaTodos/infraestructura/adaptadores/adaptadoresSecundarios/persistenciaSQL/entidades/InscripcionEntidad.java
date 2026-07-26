package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades;

import java.sql.Timestamp;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.GrupoId;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.InscripcionId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tbl_inscripcion")
@IdClass(value = InscripcionId.class)
public class InscripcionEntidad {

    @Column(name = "inscr_fechainscripcion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaInscripcion;

    @Column(name = "inscr_fechadesvinculacion", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaDesvinculacion;

    @Id
    @Column(name = "perf_id")
    private String alumnoId;

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

    @Column(name = "META_ELIMINADO")
    private int eliminado;

    public static InscripcionEntidad fabricarDeModelo(Inscripcion inscripcion) {
        try {
            InscripcionEntidad entidad = new InscripcionEntidad();
            entidad.setAlumnoId(inscripcion.getAlumnoId());
            entidad.setCategoria(inscripcion.getCategoria());
            entidad.setCurso(inscripcion.getCurso());
            entidad.setAnio(inscripcion.getAnio());
            entidad.setIterable(inscripcion.getIterable());
            entidad.setFechaInscripcion(inscripcion.getFechaInscripcion());
            entidad.setFechaDesvinculacion(inscripcion.getFechaDesvinculacion());
            entidad.setEliminado(0);
            return entidad;
        } catch (Exception e) {
            return null;
        }
    }
}