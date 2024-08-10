package co.edu.unicauca.proyecto.estudiantes.infraestructura.Entity;

import lombok.*;
import java.util.*;

import co.edu.unicauca.proyecto.models.ClaseEntity;
import co.edu.unicauca.proyecto.models.FacultadEntity;
import co.edu.unicauca.proyecto.models.InscripcionEntity;
import co.edu.unicauca.proyecto.models.PerfilEntity;
import co.edu.unicauca.proyecto.models.enums.TipoAlumno;
import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tbl_alumno")
//@PrimaryKeyJoinColumn(referencedColumnName = "perf_id")
public class AlumnoEntity extends PerfilEntity{
    
    //@NaturalId
    @Column (name = "alm_codigo", unique = true, length = 20, nullable = false)
    private String alm_codigo;
    
    @ManyToOne(cascade = { CascadeType.MERGE })
    @JoinColumn(name = "fac_nombre", nullable = false)
    private FacultadEntity objFacultad;

    @Enumerated(EnumType.STRING)
    @Column(name = "alm_tipo", length = 20, nullable = false)
    private TipoAlumno alm_tipo;
   
    @OneToMany(mappedBy = "objalumno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InscripcionEntity> inscripciones = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "tbl_alumno_clase",
        joinColumns = @JoinColumn(name = "perf_id", referencedColumnName = "perf_id"),
        inverseJoinColumns = @JoinColumn(name = "cls_codigo", referencedColumnName = "cls_codigo")
    )
    private List<ClaseEntity> AlumnosEnclase = new ArrayList<>();

}
