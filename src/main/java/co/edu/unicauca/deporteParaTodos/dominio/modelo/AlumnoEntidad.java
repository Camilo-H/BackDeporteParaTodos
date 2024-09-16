package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class AlumnoEntidad{
    
    @Id
    @Column(name="PERF_ID")
    private String idPerfil;
    
    @Column(name="ALM_CODIGO")
    private String codigoAlumno;

    @Column(name="FAC_NOMBRE")
    private String nombreFacultad;

    @Column(name="ALM_TIPO")
    private String tipoAlumno;
}
