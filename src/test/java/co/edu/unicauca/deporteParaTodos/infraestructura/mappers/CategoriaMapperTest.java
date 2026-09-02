package co.edu.unicauca.deporteParaTodos.infraestructura.mappers;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.CategoriaDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CategoriaCursoEntidad;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoProcesableEntidadException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaMapperTest {

    private CategoriaCursoEntidad entidadBase() {
        CategoriaCursoEntidad e = new CategoriaCursoEntidad();
        e.setTitulo("Acuatico");
        e.setDescripcion("Deportes acuaticos");
        e.setCat_imagen(1);
        e.setEliminado(0);
        return e;
    }

    private Categoria categoriaBase() {
        Categoria c = new Categoria();
        c.setTitulo("Acuatico");
        c.setDescripcion("Deportes acuaticos");
        c.setImagen(1);
        c.setEliminado(0);
        return c;
    }

    private CategoriaDto dtoBase() {
        CategoriaDto dto = new CategoriaDto();
        dto.setTitulo("Acuatico");
        dto.setDescripcion("Deportes acuaticos");
        dto.setImagenId(1);
        return dto;
    }

    // ── toDominio ──────────────────────────────────────────────────────────────

    @Test
    void toDominio_mapeaTodosLosCamposCorrectamente() {
        CategoriaCursoEntidad entidad = entidadBase();
        entidad.setEliminado(1);

        Categoria resultado = CategoriaMapper.toDominio(entidad);

        assertNotNull(resultado);
        assertEquals("Acuatico", resultado.getTitulo());
        assertEquals("Deportes acuaticos", resultado.getDescripcion());
        assertEquals(1, resultado.getImagen());
        assertEquals(1, resultado.getEliminado());
    }

    @Test
    void toDominio_eliminadoCero_mapeaCorrectamente() {
        CategoriaCursoEntidad entidad = entidadBase();
        entidad.setEliminado(0);

        Categoria resultado = CategoriaMapper.toDominio(entidad);

        assertEquals(0, resultado.getEliminado());
    }

    @Test
    void toDominio_entidadNula_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> CategoriaMapper.toDominio(null));
    }

    // ── toEntidad ──────────────────────────────────────────────────────────────

    @Test
    void toEntidad_mapeaTodosLosCamposCorrectamente() {
        Categoria categoria = categoriaBase();
        categoria.setEliminado(0);

        CategoriaCursoEntidad resultado = CategoriaMapper.toEntidad(categoria);

        assertNotNull(resultado);
        assertEquals("Acuatico", resultado.getTitulo());
        assertEquals("Deportes acuaticos", resultado.getDescripcion());
        assertEquals(Integer.valueOf(1), resultado.getCat_imagen());
        assertEquals(0, resultado.getEliminado());
    }

    @Test
    void toEntidad_eliminadoCero_porDefectoCuandoVieneDeDtoConversion() {
        Categoria categoria = new Categoria();
        categoria.setTitulo("Acuatico");
        categoria.setDescripcion("Desc");
        categoria.setImagen(1);
        // eliminado no seteado: int default = 0

        CategoriaCursoEntidad resultado = CategoriaMapper.toEntidad(categoria);

        assertEquals(0, resultado.getEliminado());
    }

    @Test
    void toEntidad_categoriaNula_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> CategoriaMapper.toEntidad(null));
    }

    // ── toDto ──────────────────────────────────────────────────────────────────

    @Test
    void toDto_mapeaTodosLosCamposCorrectamente() {
        Categoria categoria = categoriaBase();

        CategoriaDto resultado = CategoriaMapper.toDto(categoria);

        assertNotNull(resultado);
        assertEquals("Acuatico", resultado.getTitulo());
        assertEquals("Deportes acuaticos", resultado.getDescripcion());
        assertEquals(Integer.valueOf(1), resultado.getImagenId());
    }

    @Test
    void toDto_categoriaNula_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> CategoriaMapper.toDto(null));
    }

    // ── fromDto ────────────────────────────────────────────────────────────────

    @Test
    void fromDto_mapeaTodosLosCamposCorrectamente() {
        CategoriaDto dto = dtoBase();

        Categoria resultado = CategoriaMapper.fromDto(dto);

        assertNotNull(resultado);
        assertEquals("Acuatico", resultado.getTitulo());
        assertEquals("Deportes acuaticos", resultado.getDescripcion());
        assertEquals(1, resultado.getImagen());
    }

    @Test
    void fromDto_nulo_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> CategoriaMapper.fromDto(null));
    }
}
