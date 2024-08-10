package co.edu.unicauca.proyecto.models;

import java.util.*;
import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tbl_instructor")
//@PrimaryKeyJoinColumn(referencedColumnName = "perf_id")
public class InstructorEntity extends PerfilEntity {
    //@NaturalId
    @Column (name = "inst_codigo", unique = true, length = 20, nullable = false)
    private String inst_codigo;

    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "objgrupoInstructor")
    private List<ClaseEntity> listadeClases = new ArrayList<>();
    
}
