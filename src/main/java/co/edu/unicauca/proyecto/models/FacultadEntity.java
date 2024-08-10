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
@Table(name = "tbl_facultad")
public class FacultadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceFacultad")
	@SequenceGenerator(name = "SequenceFacultad", sequenceName = "FACULTAD_SEQUENCE", initialValue = 1,allocationSize = 1)
    @Column (name ="fac_nombre", length = 200, nullable = false)
    private String fac_nombre; 

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "objFacultad")
    private List<ProgramaEntity> listaProgramas = new ArrayList<>();

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "objFacultad")
    private List<AlumnoEntity> listaAlumnos = new ArrayList<>();
}
