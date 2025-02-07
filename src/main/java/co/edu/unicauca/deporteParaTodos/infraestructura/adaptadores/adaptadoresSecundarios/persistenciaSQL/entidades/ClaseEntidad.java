package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
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
@Table(name = "tbl_clase")
public class ClaseEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_entidad_clase")
    @SequenceGenerator(name = "seq_entidad_clase", sequenceName = "SEQ_ID_CLASE", allocationSize = 1)
    @Column(name = "CLS_CODIGO")
    private Integer codigo;

    @ManyToOne
    @JoinColumn(name = "perf_id", referencedColumnName = "perf_id")
    private InstructorEntidad instructor;

    @Column(name = "CLS_FECHA")
    private Date fecha;

    @Column(name = "CLS_HORA_INICIO")
    private Timestamp horaInicio;

    @Column(name = "CLS_HORA_FIN")
    private Timestamp horaFin;

    @Column(name = "CLS_OBSERVACION")
    private String observacion;

    @ManyToMany(mappedBy = "clases")
    private List<AlumnoEntidad> alumnos = new ArrayList<>();

}
