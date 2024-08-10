package co.edu.unicauca.proyecto.models;

import java.sql.*;

import co.edu.unicauca.proyecto.models.enums.SexoUsers;
import co.edu.unicauca.proyecto.models.enums.TipoAlumno;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

//@MappedSuperclass
@Entity (name = "tbl_perfil")
@Inheritance(strategy = InheritanceType.JOINED)
public class PerfilEntity {
    @Id
    @Column(name = "perf_id", length = 50, nullable = false)
    private String perf_id;

    @Column(name = "perf_nombre", length = 100, nullable = false) 
    private String perf_nombre;

    @Column(name = "perf_correo", length = 100, nullable = false)
    private String perf_correo;

    @Column(name = "perf_imagen")
    private Blob perf_imagen;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "perf_tipoId", length = 50, nullable = false)
    private TipoAlumno  perf_tipo;
    
    @Enumerated(EnumType.STRING) 
    @Column(name = "perf_sexo", length = 10, nullable = false) 
    private SexoUsers perf_Sexo;
}
