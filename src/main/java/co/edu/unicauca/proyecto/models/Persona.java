package co.edu.unicauca.proyecto.models;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tbl_persona")
public class Persona {
    @Id
    @Column(name = "perf_id", length = 50, nullable = false)
    private String perf_id;

    @Column(name = "perf_nombre", length = 100, nullable = false) 
    private String perf_nombre;

    @Column(name = "perf_correo", length = 100, nullable = false)
    private String perf_correo;
}
