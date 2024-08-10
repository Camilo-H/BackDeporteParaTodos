package co.edu.unicauca.proyecto.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table (name = "tbl_curso")
public class CursoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceCurso")
	@SequenceGenerator(name = "SequenceCurso", sequenceName = "CURSO_SEQUENCE", initialValue = 1,allocationSize = 1)
    @Column(name = "cur_nombre", length = 100, nullable = false)
    private String cur_nombre;

    @Column (name = "cur_imagen", nullable = true)
    private String cur_imagen;

    @Column (name = "cur_descripcion", length = 1000, nullable = false)
    private String cur_descripcion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dept_nombre", referencedColumnName = "dept_nombre", nullable = false)
    private DeporteEntity objDeporte;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cat_titulo", referencedColumnName = "cat_titulo", nullable = false)
    private CategoriaCursoEntity objCategoria;
    
}
