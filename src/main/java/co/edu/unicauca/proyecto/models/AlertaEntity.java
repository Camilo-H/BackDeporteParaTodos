package co.edu.unicauca.proyecto.models;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table (name = "tbl_alerta")
public class AlertaEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "sequenceAlerta")
    @SequenceGenerator(name = "sequenceAlerta", sequenceName = "ALERTA_SEQUENCE", initialValue = 1,allocationSize = 1)
    @Column (name = "alert_asunto", length = 1000, nullable = false)    
    private String alert_asunto;

    @Column (name = "alert_descripcion", length = 1000, nullable = false)
    private String alert_descripcion;

    @OneToOne(optional = false)
    @JoinColumns({
        @JoinColumn(name = "grp_nombre", referencedColumnName = "grp_nombre", nullable = false),
        @JoinColumn(name = "grp_anio", referencedColumnName = "grp_anio", nullable = false),
        @JoinColumn(name = "grp_iterable", referencedColumnName = "grp_iterable", nullable = false)
    })
    private GrupoEntity objGrupo;

}
