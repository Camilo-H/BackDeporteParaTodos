package co.edu.unicauca.deporteParaTodos.core.domain.models;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name="tbl_inscripcion")
public class InscripcionEntidad {
    @Id
    @Column (name = "inscr_fechainscripcion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaInscripcion;

    @Column (name = "inscr_fechadesvinculacion", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaDesvinculacion;

    @Column(name = "grp_nombre", nullable = false)
    private String nombre;

    @Column(name = "grp_anio", nullable= false)
    private int anio;

    @Column(name = "grp_iterable", nullable = false)
    private int iterable;

}