package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name="tbl_clase")
public class ClaseEntidad {
    
    @Id
    @Column(name="CLS_CODIGO")
    private Integer codigo;
    
    @Column(name="PERF_ID")
    private String idInstructor;
    
    @Column(name="CLS_FECHA")
    private Date fecha;

    @Column(name="CLS_HORA_INICIO")
    private Timestamp horaInicio;

    @Column(name="CLS_HORA_FIN")
    private Timestamp horaFin;

    @Column(name="CLS_OBSERVACION")
    private String observacion;
}
