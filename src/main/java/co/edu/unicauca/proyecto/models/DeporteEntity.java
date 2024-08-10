package co.edu.unicauca.proyecto.models;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table (name = "tbl_deporte")

public class DeporteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceDeporte")
	@SequenceGenerator(name = "SequenceDeporte", sequenceName = "DEPORTE_SEQUENCE", initialValue = 1,allocationSize = 1)
    @Column (name = "dept_nombre", length = 100, nullable = false)    
    private String dept_nombre;

    @OneToMany(mappedBy = "objDeporte")
    private List<CursoEntity> listaCursos = new ArrayList<>();

}
