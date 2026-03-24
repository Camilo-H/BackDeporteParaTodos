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

    @Column(name = "META_ELIMINADO")
    private int eliminado;

    //@OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<ClaseEntidad> clases = new ArrayList<>();
    // ✅ Relación con TBL_PERFIL usando la misma columna perf_id
    @OneToOne
    @JoinColumn(name = "perf_id", insertable = false, updatable = false)
    private PerfilEntidad perfil;

}