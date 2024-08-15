package co.edu.unicauca.deporteParaTodos.core.domain.models;

import java.sql.Blob;
import java.sql.Clob;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "tbl_categoria_curso")
public class CategoriaCursoEntidad {
    @Id
    @Column (name = "cat_titulo", length = 100, nullable = false)
    private String cat_titulo;

    @Column(name = "cat_descripcion", length = 1000, nullable = false)
    private Clob cat_descripcion;

    @Column (name = "cat_imagen", nullable = true)
    private Blob cat_imagen;
}
