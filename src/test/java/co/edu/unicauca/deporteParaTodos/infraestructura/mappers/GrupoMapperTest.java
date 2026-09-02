package co.edu.unicauca.deporteParaTodos.infraestructura.mappers;

import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoProcesableEntidadException;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Grupo;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.GrupoDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.GrupoEntidad;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GrupoMapperTest {

    private static final String    CATEGORIA = "Recreativo";
    private static final String    CURSO     = "Natacion";
    private static final LocalDate FECHA     = LocalDate.of(2025, 1, 15);

    private GrupoEntidad entidadBase() {
        return new GrupoEntidad(CATEGORIA, CURSO, 2025, 1, 1, 20, "INS001", FECHA, null, null, null, 1, 0);
    }

    private Grupo grupoBase() {
        Grupo g = new Grupo();
        g.setCategoria(CATEGORIA);
        g.setCurso(CURSO);
        g.setAnio(2025);
        g.setIterable(1);
        g.setImagenGrupo(1);
        g.setCupos(20);
        g.setIdInstructor("INS001");
        g.setFechaCreacion(FECHA);
        g.setPeriodo(1);
        return g;
    }

    // ────────── toDominio ──────────

    @Test
    void toDominio_entidadValida_mapeaCamposCorrectamente() {
        Grupo resultado = GrupoMapper.toDominio(entidadBase());
        assertEquals(CATEGORIA, resultado.getCategoria());
        assertEquals(CURSO, resultado.getCurso());
        assertEquals(2025, resultado.getAnio());
        assertEquals(1, resultado.getIterable());
        assertEquals(1, resultado.getPeriodo());
        assertEquals(FECHA, resultado.getFechaCreacion());
    }

    @Test
    void toDominio_entidadNula_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> GrupoMapper.toDominio(null));
    }

    @Test
    void toDominio_preservaIdInstructor() {
        Grupo resultado = GrupoMapper.toDominio(entidadBase());
        assertEquals("INS001", resultado.getIdInstructor());
    }

    // ────────── toEntidad ──────────

    @Test
    void toEntidad_grupoValido_mapeaCamposCorrectamente() {
        GrupoEntidad resultado = GrupoMapper.toEntidad(grupoBase());
        assertEquals(CATEGORIA, resultado.getCategoria());
        assertEquals(CURSO, resultado.getCurso());
        assertEquals(20, resultado.getCupos());
    }

    @Test
    void toEntidad_eliminadoSiempreEs0() {
        GrupoEntidad resultado = GrupoMapper.toEntidad(grupoBase());
        assertEquals(0, resultado.getEliminado());
    }

    @Test
    void toEntidad_grupoNulo_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> GrupoMapper.toEntidad(null));
    }

    // ────────── toDto ──────────

    @Test
    void toDto_grupoValido_mapeaCamposCorrectamente() {
        GrupoDto resultado = GrupoMapper.toDto(grupoBase());
        assertEquals(CATEGORIA, resultado.getCategoria());
        assertEquals(CURSO, resultado.getCurso());
        assertEquals(1, resultado.getPeriodo());
        assertEquals(FECHA, resultado.getFechaCreacion());
    }

    @Test
    void toDto_grupoNulo_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> GrupoMapper.toDto(null));
    }

    // ────────── fromDto ──────────

    @Test
    void fromDto_dtoValido_mapeaCamposCorrectamente() {
        GrupoDto dto = new GrupoDto();
        dto.setCategoria(CATEGORIA);
        dto.setCurso(CURSO);
        dto.setAnio(2025);
        dto.setIterable(1);
        dto.setImagenGrupo(1);
        dto.setCupos(20);
        dto.setFechaCreacion(FECHA);
        dto.setPeriodo(1);

        Grupo resultado = GrupoMapper.fromDto(dto);

        assertEquals(CATEGORIA, resultado.getCategoria());
        assertEquals(CURSO, resultado.getCurso());
        assertEquals(1, resultado.getPeriodo());
    }

    @Test
    void fromDto_periodoNulo_usaValorCero() {
        GrupoDto dto = new GrupoDto();
        dto.setCategoria(CATEGORIA);
        dto.setCurso(CURSO);
        dto.setImagenGrupo(1);
        dto.setCupos(20);
        dto.setPeriodo(null);

        Grupo resultado = GrupoMapper.fromDto(dto);

        assertEquals(0, resultado.getPeriodo());
    }

    @Test
    void fromDto_periodoExplicito_usaValorProvisto() {
        GrupoDto dto = new GrupoDto();
        dto.setCategoria(CATEGORIA);
        dto.setCurso(CURSO);
        dto.setImagenGrupo(1);
        dto.setCupos(20);
        dto.setPeriodo(2);

        Grupo resultado = GrupoMapper.fromDto(dto);

        assertEquals(2, resultado.getPeriodo());
    }

    @Test
    void fromDto_dtoNulo_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> GrupoMapper.fromDto(null));
    }
}
