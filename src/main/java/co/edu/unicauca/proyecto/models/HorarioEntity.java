package co.edu.unicauca.proyecto.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table (name = "tbl_horario")
public class HorarioEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "sequenceHorario")
    @SequenceGenerator(name = "sequenceHorario", sequenceName = "HORARIO_SEQUENCE", initialValue = 1, allocationSize = 1)
    @Column (name = "hr_id", nullable = false)
    private Number hr_id;
    
    @Column (name = "hr_dia", length = 50, nullable = false)
    private String hr_dia;

    @Column (name = "hr_horaInicio", length = 8, nullable = false)
    private String hr_horaInicio;

    @Column (name = "hr_horaFin", length = 8, nullable = true)
    private String hr_horaFin;

    @Column (name = "hr_escenario", length = 100, nullable = false)
    private String hr_escenario;
    
    




}
