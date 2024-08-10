package co.edu.unicauca.proyecto.models;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tbl_coordinador")
//@PrimaryKeyJoinColumn(referencedColumnName = "perf_id")
public class CoordinadorEntity  extends PerfilEntity{
    //@NaturalId
    @Column (name = "cor_codigo", unique = true, length = 20, nullable = false)
    private String cor_codigo;
    
}
