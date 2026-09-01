package co.edu.unicauca.deporteParaTodos.infraestructura.mappers;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.CategoriaDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CategoriaCursoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoProcesableEntidadException;

public class CategoriaMapper {

    public static Categoria toDominio(CategoriaCursoEntidad entidad) {
        try {
            Categoria fabricado = new Categoria();
            fabricado.setTitulo(entidad.getTitulo());
            fabricado.setDescripcion(entidad.getDescripcion());
            fabricado.setImagen(entidad.getCat_imagen());
            fabricado.setEliminado(entidad.getEliminado());
            return fabricado;
        } catch (Exception e) {
            return null;
        }
    }

    public static CategoriaCursoEntidad toEntidad(Categoria categoria) {
        try {
            CategoriaCursoEntidad entidad = new CategoriaCursoEntidad();
            entidad.setTitulo(categoria.getTitulo());
            entidad.setDescripcion(categoria.getDescripcion());
            entidad.setCat_imagen(categoria.getImagen());
            entidad.setEliminado(categoria.getEliminado());
            return entidad;
        } catch (Exception e) {
            return null;
        }
    }

    public static CategoriaDto toDto(Categoria categoria) {
        try {
            CategoriaDto dto = new CategoriaDto();
            dto.setTitulo(categoria.getTitulo());
            dto.setDescripcion(categoria.getDescripcion());
            dto.setImagenId(categoria.getImagen());
            return dto;
        } catch (Exception e) {
            return null;
        }
    }

    public static Categoria fromDto(CategoriaDto dto) {
        try {
            Categoria fabricado = new Categoria();
            fabricado.setTitulo(dto.getTitulo());
            fabricado.setDescripcion(dto.getDescripcion());
            fabricado.setImagen(dto.getImagenId());
            return fabricado;
        } catch (Exception e) {
            throw new NoProcesableEntidadException("No fue posible convertir CategoriaDto a dominio: " + e.getMessage());
        }
    }
}
