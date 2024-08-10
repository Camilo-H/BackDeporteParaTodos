package co.edu.unicauca.proyecto.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tbl_programa")
public class ProgramaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequencePrograma")
	@SequenceGenerator(name = "SequencePrograma", sequenceName = "PROGRAMA_SEQUENCE", initialValue = 1,allocationSize = 1)
    @Column(name = "prg_nombre", length = 200, nullable = false)
    private String prg_nombre;

    @ManyToOne(cascade = { CascadeType.MERGE })
    @JoinColumn(name = "fac_nombre", nullable = false)
    private FacultadEntity objFacultad;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "tbl_programa_alumno", joinColumns = {@JoinColumn(name = "prg_nombre")}, inverseJoinColumns = {@JoinColumn(name = "perf_id")})
    private List<AlumnoEntity> estudiantes1 = new ArrayList<>();
}
