package co.edu.unicauca.deporteParaTodos.dominio.modelo;

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
@Table (name = "tbl_categoria_curso")
public class CategoriaCursoEntidad {
    @Id
    @Column (name = "cat_titulo", length = 100, nullable = false)
    private String titulo;

    @Column(name = "cat_descripcion", length = 1000, nullable = false)
    private String descripcion;

    @Lob
    @Column (name = "cat_imagen", nullable = true)
    private byte[] imagen;
}
