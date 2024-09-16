package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.ids.GrupoId;
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
@Table(name="tbl_alerta")
@IdClass(value=GrupoId.class)
public class AlertaEntidad {
    
    @Id
    @Column(name = "GRP_NOMBRE")
    private String nombre;

    @Id
    @Column(name = "GRP_ANIO")
    private int anio;

    @Id
    @Column(name = "GRP_ITERABLE")
    private int iterable;
    
    @Column(name= "ALERT_ASUNTO")
    private String asunto;
    
    @Column(name="ALERT_DESCRIPCION")
    private String descripcion;
}
