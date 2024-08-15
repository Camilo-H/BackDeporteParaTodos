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
@Table(name = "tbl_alumno")
public class AlumnoEntidad{
    
    @Id
    @Column (name = "alm_codigo", unique = true, length = 20, nullable = false)
    private String alm_codigo;
    
    @Column(name = "alm_tipo", length = 20, nullable = false)
    private String alm_tipo;
}
