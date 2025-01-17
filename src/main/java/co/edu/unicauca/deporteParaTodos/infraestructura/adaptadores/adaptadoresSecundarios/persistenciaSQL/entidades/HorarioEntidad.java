package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tbl_horario")
public class HorarioEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_entidad_horario")
    @SequenceGenerator(name = "seq_entidad_horario", sequenceName = "SEQ_ID_HORARIO", allocationSize = 1)
    @Column(name = "hr_id")
    private Integer id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "GRP_NOMBRE", referencedColumnName = "GRP_NOMBRE"),
            @JoinColumn(name = "GRP_ANIO", referencedColumnName = "GRP_ANIO"),
            @JoinColumn(name = "GRP_ITERABLE", referencedColumnName = "GRP_ITERABLE") })
    private GrupoEntidad grupo;

    @Column(name = "hr_dia")
    private String dia;

    @Column(name = "hr_horainicio")
    private String horaInicio;

    @Column(name = "hr_horafin")
    private String horaFin;

    @Column(name = "hr_escenario")
    private String escenario;
}
