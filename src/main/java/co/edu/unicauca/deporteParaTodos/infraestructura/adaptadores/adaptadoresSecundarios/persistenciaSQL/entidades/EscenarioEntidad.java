package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
@Table(name = "TBL_ESCENARIO")
public class EscenarioEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_entidad_escenario")
    @SequenceGenerator(name = "seq_entidad_escenario", sequenceName = "SEQ_ESC_ID", allocationSize = 1)
    @Column(name = "ESC_ID")
    private Integer id;

    @Column(name = "ESC_NOMBRE")
    private String nombre;

    @Column(name = "ESC_DESCRIPCION")
    private String descripcion;

    @Column(name = "ESC_NUM_TRIBUNAS")
    private int numTribunas;

    @Column(name = "ESC_DISPONIBLE")
    private int disponible;

    @Column(name = "META_ELIMINADO")
    private Integer eliminado;

}
