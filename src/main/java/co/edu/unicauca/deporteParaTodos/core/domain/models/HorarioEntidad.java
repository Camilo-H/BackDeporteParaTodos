package co.edu.unicauca.deporteParaTodos.core.domain.models;

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
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="tbl_horario")
public class HorarioEntidad {

    @Id
    @Column(name="hr_id")
    private Integer id;

    @Column(name="grp_nombre")
    private String grupoNombre;

    @Column(name="grp_anio")
    private String grupoAnio;

    @Column(name="grp_iterable")
    private String grupoIterable;

    @Column(name="hr_dia")
    private String dia;

    @Column(name="hr_horainicio")
    private String horaInicio;

    @Column(name="hr_horafin")
    private String horaFin;

    @Column(name="hr_escenario")
    private String escenario;
}
