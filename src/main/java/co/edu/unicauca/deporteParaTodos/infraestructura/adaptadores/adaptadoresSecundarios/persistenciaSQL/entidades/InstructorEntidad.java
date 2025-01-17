package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    @Column(name = "perf_id")
    private String idPerfil;

    @Column(name = "inst_codigo", unique = true, length = 20, nullable = false)
    private String inst_codigo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "perf_id", referencedColumnName = "perf_id")
    private PerfilEntidad perfil;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClaseEntidad> clases = new ArrayList<>();

}