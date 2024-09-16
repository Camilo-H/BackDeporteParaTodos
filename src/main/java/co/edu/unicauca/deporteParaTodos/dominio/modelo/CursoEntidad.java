package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import java.sql.Blob;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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

    @Lob
    @Column(name="CUR_IMAGEN")
    private Blob imagen;
    
    @Column(name="CUR_DESCRIPCION")
    private String descripcion;
}
