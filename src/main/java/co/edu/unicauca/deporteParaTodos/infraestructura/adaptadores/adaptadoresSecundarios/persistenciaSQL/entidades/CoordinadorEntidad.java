package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tbl_coordinador")
public class CoordinadorEntidad {

    @Id
    @Column(name = "perf_id")
    private String idPerfil;

    @Column(name = "COR_CODIGO", unique = true, length = 20, nullable = false)
    private String coor_codigo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "perf_id", referencedColumnName = "perf_id")
    private PerfilEntidad perfil;
}