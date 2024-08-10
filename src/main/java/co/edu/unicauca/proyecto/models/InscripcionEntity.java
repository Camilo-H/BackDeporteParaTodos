package co.edu.unicauca.proyecto.models;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

//import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table (name = "tbl_inscripcion")
public class InscripcionEntity {
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceInscripcion")
    //@SequenceGenerator (name = "sequenceInscripcion",  sequenceName = "INSCRIPCION_SEQUENCE", initialValue = 1,allocationSize = 1)
    @Column (name = "fecha_inscripcion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    //@DateTimeFormat(pattern = "dd/mm/yyyy")
    private Timestamp fecha_inscripcion;

    @Column (name = "fecha_desvinculacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    //@DateTimeFormat(pattern = "dd/mm/yyyy")
    private Timestamp fecha_desvinculacion;


    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "grp_nombre", referencedColumnName = "grp_nombre", nullable = false),
        @JoinColumn(name = "grp_anio", referencedColumnName = "grp_anio", nullable = false),
        @JoinColumn(name = "grp_iterable", referencedColumnName = "grp_iterable", nullable = false)
    })
    private GrupoEntity objgrupoInscripcion;

    @ManyToOne
    @JoinColumn(name = "perf_id", referencedColumnName = "perf_id", nullable = false)
    private AlumnoEntity objalumno;

}
