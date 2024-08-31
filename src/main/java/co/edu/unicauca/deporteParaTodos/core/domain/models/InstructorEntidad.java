package co.edu.unicauca.deporteParaTodos.core.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tbl_instructor")
public class InstructorEntidad {
    @Id
    @Column (name = "inst_codigo", unique = true, length = 20, nullable = false)
    private String inst_codigo;
}