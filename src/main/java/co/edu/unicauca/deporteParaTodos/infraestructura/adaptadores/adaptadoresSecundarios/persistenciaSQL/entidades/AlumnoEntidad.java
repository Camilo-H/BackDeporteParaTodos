package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tbl_alumno")
public class AlumnoEntidad {

    @Id
    @Column(name = "perf_id")
    private String idPerfil;

    @Column(name = "ALM_CODIGO", unique = true, length = 20, nullable = false)
    private String alm_codigo;

    @ManyToOne
    @JoinColumn(name = "fac_nombre", referencedColumnName = "fac_nombre")
    private FacultadEntidad Facultad;

    @Column(name = "ALM_TIPO")
    private String tipoAlumno;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "perf_id", referencedColumnName = "perf_id")
    private PerfilEntidad perfil;
   
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<InscripcionEntidad> inscripciones = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tbl_asistencia", joinColumns = @JoinColumn(name = "PERF_ID", referencedColumnName = "perf_id"), inverseJoinColumns = @JoinColumn(name = "CLS_CODIGO", referencedColumnName = "CLS_CODIGO"))
    private List<ClaseEntidad> clases = new ArrayList<>();

}
