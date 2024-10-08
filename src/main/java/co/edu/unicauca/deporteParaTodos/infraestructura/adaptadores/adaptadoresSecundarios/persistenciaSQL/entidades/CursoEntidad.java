package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name="tbl_curso")
public class CursoEntidad {
    @Id
    @Column(name="CUR_NOMBRE")
    private String nombre;

    @Column(name="DEPT_NOMBRE")
    private String nombreDeporte;

    @Column(name="CAT_TITULO")
    private String tituloCategoria;

    //relacion de uno a uno
    @OneToOne(cascade = CascadeType.ALL)
    //join column debe ser emplementado en la tabla que contiene la columna de clave foranea
    @JoinColumn(name="CUR_IMAGEN", referencedColumnName = "IMG_ID")
    private ImagenEntidad objImagen;
    
    @Column(name="CUR_DESCRIPCION")
    private String descripcion;
}
