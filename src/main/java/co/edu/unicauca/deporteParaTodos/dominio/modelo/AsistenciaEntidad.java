package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.ids.AsistenciaId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@Table(name="tbl_asistencia")
@IdClass(value = AsistenciaId.class)
public class AsistenciaEntidad {
    @Id
    @Column(name="PERF_ID")
    private String perfilId;

    @Id
    @Column(name="CLS_CODIGO")
    private Integer claseCodigo;
}
