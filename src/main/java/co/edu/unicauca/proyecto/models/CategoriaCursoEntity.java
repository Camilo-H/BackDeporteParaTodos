package co.edu.unicauca.proyecto.models;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table (name = "tbl_categoria_curso")
public class CategoriaCursoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceCategoria")
	@SequenceGenerator(name = "SequenceCategoria", sequenceName = "CATEGORIA_SEQUENCE", initialValue = 1,allocationSize = 1)
    @Column (name = "cat_titulo", length = 100, nullable = false)
    private String cat_titulo;

    @Column(name = "cat_descripcion", length = 1000, nullable = false)
    private String cat_descripcion;

    @Column (name = "cat_imagen", nullable = true)
    private Blob cat_imagen;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "objCategoria")
    private List<CursoEntity> listaCursos = new ArrayList<>();
    
}
