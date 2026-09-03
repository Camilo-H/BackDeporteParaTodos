package co.edu.unicauca.deporteParaTodos.infraestructura.mappers;

import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoProcesableEntidadException;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.InscripcionDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.InscripcionEntidad;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class InscripcionMapperTest {

    private static final String ALUMNO_ID  = "alum1";
    private static final String CATEGORIA  = "cat1";
    private static final String CURSO      = "cur1";
    private static final int    ANIO       = 2026;
    private static final int    ITERABLE   = 1;
    private static final Timestamp AHORA   = Timestamp.from(Instant.now());

    private InscripcionEntidad entidadBase() {
        InscripcionEntidad e = new InscripcionEntidad();
        e.setAlumnoId(ALUMNO_ID);
        e.setCategoria(CATEGORIA);
        e.setCurso(CURSO);
        e.setAnio(ANIO);
        e.setIterable(ITERABLE);
        e.setFechaInscripcion(AHORA);
        e.setFechaDesvinculacion(null);
        e.setEliminado(0);
        return e;
    }

    private Inscripcion inscripcionBase() {
        return new Inscripcion(ALUMNO_ID, CATEGORIA, CURSO, ANIO, ITERABLE, AHORA, null);
    }

    // ────────── toDominio ──────────

    @Test
    void toDominio_entidadNula_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> InscripcionMapper.toDominio(null));
    }

    @Test
    void toDominio_mapeaTodosLosCamposDeDominio() {
        Inscripcion resultado = InscripcionMapper.toDominio(entidadBase());
        assertEquals(ALUMNO_ID, resultado.getAlumnoId());
        assertEquals(CATEGORIA, resultado.getCategoria());
        assertEquals(CURSO,     resultado.getCurso());
        assertEquals(ANIO,      resultado.getAnio());
        assertEquals(ITERABLE,  resultado.getIterable());
        assertEquals(AHORA,     resultado.getFechaInscripcion());
    }

    @Test
    void toDominio_conFechaDesvinculacionNula_retornaFechaDesvinculacionNula() {
        InscripcionEntidad entidad = entidadBase();
        entidad.setFechaDesvinculacion(null);
        assertNull(InscripcionMapper.toDominio(entidad).getFechaDesvinculacion());
    }

    // ────────── toEntidad ──────────

    @Test
    void toEntidad_inscripcionNula_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> InscripcionMapper.toEntidad(null));
    }

    @Test
    void toEntidad_mapeaTodosLosCampos() {
        InscripcionEntidad resultado = InscripcionMapper.toEntidad(inscripcionBase());
        assertEquals(ALUMNO_ID, resultado.getAlumnoId());
        assertEquals(CATEGORIA, resultado.getCategoria());
        assertEquals(CURSO,     resultado.getCurso());
        assertEquals(ANIO,      resultado.getAnio());
        assertEquals(ITERABLE,  resultado.getIterable());
        assertEquals(AHORA,     resultado.getFechaInscripcion());
    }

    @Test
    void toEntidad_hardcodeoEliminadoCero() {
        InscripcionEntidad resultado = InscripcionMapper.toEntidad(inscripcionBase());
        assertEquals(0, resultado.getEliminado());
    }

    // ────────── toDto ──────────

    @Test
    void toDto_inscripcionNula_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> InscripcionMapper.toDto(null));
    }

    @Test
    void toDto_mapeaTodosLosCampos() {
        InscripcionDto resultado = InscripcionMapper.toDto(inscripcionBase());
        assertEquals(ALUMNO_ID, resultado.getAlumnoId());
        assertEquals(CATEGORIA, resultado.getCategoria());
        assertEquals(CURSO,     resultado.getCurso());
        assertEquals(ANIO,      resultado.getAnio());
        assertEquals(ITERABLE,  resultado.getIterable());
        assertEquals(AHORA,     resultado.getFechaInscripcion());
    }

    @Test
    void toDto_conFechasNulas_retornaFechasNulas() {
        Inscripcion inscripcion = new Inscripcion(ALUMNO_ID, CATEGORIA, CURSO, ANIO, ITERABLE, null, null);
        InscripcionDto resultado = InscripcionMapper.toDto(inscripcion);
        assertNull(resultado.getFechaInscripcion());
        assertNull(resultado.getFechaDesvinculacion());
    }

    // ────────── fromDto ──────────

    @Test
    void fromDto_dtoNulo_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> InscripcionMapper.fromDto(null));
    }

    @Test
    void fromDto_mapeaTodosLosCampos() {
        InscripcionDto dto = new InscripcionDto(ALUMNO_ID, CATEGORIA, CURSO, ANIO, ITERABLE, AHORA, null);
        Inscripcion resultado = InscripcionMapper.fromDto(dto);
        assertEquals(ALUMNO_ID, resultado.getAlumnoId());
        assertEquals(CATEGORIA, resultado.getCategoria());
        assertEquals(CURSO,     resultado.getCurso());
        assertEquals(ANIO,      resultado.getAnio());
        assertEquals(ITERABLE,  resultado.getIterable());
        assertEquals(AHORA,     resultado.getFechaInscripcion());
    }

    @Test
    void fromDto_conFechasNulas_retornaFechasNulas() {
        InscripcionDto dto = new InscripcionDto(ALUMNO_ID, CATEGORIA, CURSO, ANIO, ITERABLE, null, null);
        Inscripcion resultado = InscripcionMapper.fromDto(dto);
        assertNull(resultado.getFechaInscripcion());
        assertNull(resultado.getFechaDesvinculacion());
    }
}
