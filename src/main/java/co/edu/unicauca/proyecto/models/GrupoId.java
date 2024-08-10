package co.edu.unicauca.proyecto.models;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Embeddable
public class GrupoId implements Serializable{

    @Column (name = "grp_nombre", length = 100, nullable = false)
    private String grp_nombre;

    @Column (name = "grp_anio", nullable = false)
    private Number grp_anio;

    @Column (name = "grp_iterable", nullable = false)
    private Number grp_iterable;
}
