package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "tbl_programa")
public class ProgramaEntidad {
    @Id
    @Column(name = "prg_nombre", length = 200, nullable = false)
    private String prg_nombre;

    @ManyToOne
    @JoinColumn(name = "fac_nombre", referencedColumnName = "fac_nombre", nullable = false)
    private FacultadEntidad facultad;
}