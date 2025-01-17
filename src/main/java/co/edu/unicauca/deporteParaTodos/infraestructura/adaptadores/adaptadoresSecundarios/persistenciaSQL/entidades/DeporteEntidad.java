package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "tbl_deporte")
public class DeporteEntidad {

    @Id
    @Column(name="dept_nombre")
    private String nombre;

    // Relación con CursoEntidad
    @OneToMany(mappedBy = "deporte", cascade = CascadeType.ALL)
    private List<CursoEntidad> cursos = new ArrayList<>();
}
