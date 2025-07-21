package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import java.util.List;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.CategoriaDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CategoriaCursoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoProcesableEntidadException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Categoria {

    private String titulo;

    private String descripcion;

    private String rutaImagen;

    private int imagen;

    private List<Curso> cursos;

    static public Categoria fabricarDeEntidad(CategoriaCursoEntidad entidad){
        try{
            Categoria fabricado = new Categoria();
            fabricado.setTitulo(entidad.getTitulo());
            fabricado.setDescripcion(entidad.getDescripcion());
            fabricado.setImagen(entidad.getCat_imagen());
            return fabricado;
        }catch(Exception e){
            return null;
        }
    }

    static public Categoria fabricarDeDto(CategoriaDto dto){
        try{
            Categoria fabricado = new Categoria();
            fabricado.setTitulo(dto.getTitulo());
            fabricado.setDescripcion(dto.getDescripcion());
            fabricado.setImagen(dto.getImagenId());
            return fabricado;
        }catch(Exception e){
            return null;
        }
    }
}
