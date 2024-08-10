package co.edu.unicauca.proyecto.models;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "tbl_BandejaSalida")
public class BandejaSalidaEntity {
    @Id
    @Column(name = "bd_ids")
    private long bd_ids;
    
    /*     @OneToOne
    @MapsId
    @JoinColumn(name = "perf_id", nullable = false)
    private PerfilEntity perfil;*/

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "objBandejaSalida")
    private List<NotificacionEntity> notificacionesDeSalida = new ArrayList<>();

}
