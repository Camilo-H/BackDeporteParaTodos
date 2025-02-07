package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "tbl_curso")
public class CursoEntidad {
    @Id
    @Column(name = "CUR_NOMBRE")
    private String nombre;

    // Relación con DeporteEntidad
    @ManyToOne
    @JoinColumn(name = "DEPT_NOMBRE", referencedColumnName = "dept_nombre")
    private DeporteEntidad deporte;

    // Relación con CategoriaCursoEntidad
    @ManyToOne
    @JoinColumn(name = "CAT_TITULO", referencedColumnName = "cat_titulo")
    @JsonBackReference
    private CategoriaCursoEntidad categoriaCurso;

    @Column(name = "CUR_DESCRIPCION")
    private String descripcion;

    // relacion con entidad imagen //join column debe ser emplementado en la tabla
    // que contiene la columna de clave foranea
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CUR_IMAGEN", referencedColumnName = "IMG_ID")
    private ImagenEntidad objImagen;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<GrupoEntidad> grupos= new ArrayList<>();
}
