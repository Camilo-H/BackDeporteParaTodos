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
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="tbl_coordinador")
public class CoordinadorEntidad {
    
    @Id
    @Column(name="PERF_ID")
    private String idPerfil;

    @Column(name="COR_CODIGO")
    private String codigo;
}