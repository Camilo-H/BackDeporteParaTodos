package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tbl_categoria_curso")
public class CategoriaCursoEntidad {
    @Id
    @Column(name = "cat_titulo", length = 100, nullable = false)
    private String titulo;

    @Column(name = "cat_descripcion", length = 1000, nullable = false)
    private String descripcion;

    @Column(name = "cat_url_imagen", length = 1000, nullable = true)
    private String rutaImagen;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cat_imagen", referencedColumnName = "IMG_ID")
    private ImagenEntidad objImagenCategoria;

    // Relación uno a muchos con CursoEntidad
    @OneToMany(mappedBy = "categoriaCurso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<CursoEntidad> cursos = new ArrayList<>();

}
