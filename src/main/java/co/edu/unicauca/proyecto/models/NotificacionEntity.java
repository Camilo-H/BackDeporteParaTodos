package co.edu.unicauca.proyecto.models;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table (name = "tbl_notificacion")
public class NotificacionEntity {
    @Id
    @Column (name = "ntf_identificador", nullable = false)
    private long ntf_identificador;

    @Column (name = "ntf_emisor", length = 50, nullable = false)
    private String ntf_emisor;

    @Column (name = "ntf_asunto", length = 100, nullable = false)
    private String ntf_asunto;

    @Column (name = "ntf_descripcion", length = 1000, nullable = false)
    private String ntf_descripcion;

    @Column (name = "ntf_fechaEnvio", nullable = false)
    private Date ntf_fechaEnvio;

    @Column (name = "ntf_destinatarios", length = 500, nullable = false)
    private String ntf_destinatarios;

    @ManyToOne
    @JoinColumn(name = "perf_id")
    private BandejaSalidaEntity objBandejaSalida;
    
}
