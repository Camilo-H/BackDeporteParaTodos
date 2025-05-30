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

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;

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

    @Column(name = "cat_imagen", nullable = false)
    private Integer cat_imagen;

    @Column(name = "meta_eliminado")
    private int eliminado;

    public static CategoriaCursoEntidad fabricarDeModelo(Categoria modelo, int eliminado){
        try{
            CategoriaCursoEntidad entidad = new CategoriaCursoEntidad();
            entidad.setTitulo(modelo.getTitulo());
            entidad.setDescripcion(modelo.getDescripcion());
            entidad.setCat_imagen(modelo.getImagen());
            entidad.setEliminado(eliminado);
            return entidad;
        }catch(Exception e){
            return null;
        }
    }
}
