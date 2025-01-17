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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tbl_facultad")
public class FacultadEntidad {
    @Id
    @Column(name = "fac_nombre")
    private String nombre;

    @OneToMany(mappedBy = "Facultad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlumnoEntidad> alumnos = new ArrayList<>();

    @OneToMany(mappedBy = "facultad", cascade =  CascadeType.ALL, orphanRemoval = true)
    private List<ProgramaEntidad> programas = new ArrayList<>();
}