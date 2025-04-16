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

    @Column(name = "dept_nombre")
    private String deporte;

    @Column(name = "cat_titulo")
    private String categoriaCurso;

    @Column(name = "CUR_DESCRIPCION")
    private String descripcion;

    @Column(name = "CUR_IMAGEN")
    private Integer objImagen;

    @Column(name = "meta_eliminado")
    private Integer eliminado;

    //@OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@JsonManagedReference
    //private List<GrupoEntidad> grupos= new ArrayList<>();
}
