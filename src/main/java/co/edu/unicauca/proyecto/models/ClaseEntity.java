package co.edu.unicauca.proyecto.models;
import jakarta.persistence.*;
import lombok.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table (name = "tbl_clase")
public class ClaseEntity {
    @Id
    @Column (name = "cls_codigo", nullable = false)
    private Number cls_codigo;

    @Column (name = "cls_fecha", nullable = false)
    private Date cls_fecha;

    @Column (name = "cls_horaInicio", nullable = false)
    private Timestamp cls_horaInicio;

    @Column (name = "cls_horaFin", nullable = true)
    private Timestamp cls_horaFin;

    @Column (name = "cls_observación", length = 1000, nullable = true)
    private String cls_observacion;

    @ManyToOne
    @JoinColumn (name = "perf_id", referencedColumnName = "perf_id", nullable = false)
    private InstructorEntity objgrupoInstructor;

    @ManyToMany(mappedBy = "AlumnosEnclase")
    private List<AlumnoEntity> alumnos = new ArrayList<>();


    
}
