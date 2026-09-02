package co.edu.unicauca.deporteParaTodos.infraestructura.mappers;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Horario;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.HorarioDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.HorarioEntidad;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoProcesableEntidadException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HorarioMapperTest {

    private HorarioEntidad entidadBase() {
        HorarioEntidad e = new HorarioEntidad();
        e.setId(1);
        e.setCategoria("Natación");
        e.setCurso("Natación Adultos");
        e.setAnio(2025);
        e.setIterable(1);
        e.setDia("Lunes");
        e.setHoraInicio("08:00");
        e.setHoraFin("10:00");
        e.setEscenario("Piscina");
        e.setEliminado(0);
        return e;
    }

    private Horario horarioBase() {
        Horario h = new Horario();
        h.setId(1);
        h.setCategoria("Natación");
        h.setCurso("Natación Adultos");
        h.setAnio(2025);
        h.setIterable(1);
        h.setDia("Lunes");
        h.setHoraInicio("08:00");
        h.setHoraFin("10:00");
        h.setEscenario("Piscina");
        h.setEliminado(0);
        return h;
    }

    private HorarioDto dtoBase() {
        HorarioDto dto = new HorarioDto();
        dto.setId(1);
        dto.setCategoria("Natación");
        dto.setCurso("Natación Adultos");
        dto.setAnio(2025);
        dto.setIterable(1);
        dto.setDia("Lunes");
        dto.setHoraInicio("08:00");
        dto.setHoraFin("10:00");
        dto.setEscenario("Piscina");
        return dto;
    }

    // ── toDominio ──────────────────────────────────────────────────────────────

    @Test
    void toDominio_mapeaTodosCampos() {
        Horario resultado = HorarioMapper.toDominio(entidadBase());

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Natación", resultado.getCategoria());
        assertEquals("Natación Adultos", resultado.getCurso());
        assertEquals(2025, resultado.getAnio());
        assertEquals(1, resultado.getIterable());
        assertEquals("Lunes", resultado.getDia());
        assertEquals("08:00", resultado.getHoraInicio());
        assertEquals("10:00", resultado.getHoraFin());
        assertEquals("Piscina", resultado.getEscenario());
        assertEquals(0, resultado.getEliminado());
    }

    @Test
    void toDominio_entidadNula_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> HorarioMapper.toDominio(null));
    }

    // ── toEntidad ──────────────────────────────────────────────────────────────

    @Test
    void toEntidad_mapeaTodosLosCampos() {
        HorarioEntidad resultado = HorarioMapper.toEntidad(horarioBase());

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Natación", resultado.getCategoria());
        assertEquals("Natación Adultos", resultado.getCurso());
        assertEquals(2025, resultado.getAnio());
        assertEquals(1, resultado.getIterable());
        assertEquals("Lunes", resultado.getDia());
        assertEquals("08:00", resultado.getHoraInicio());
        assertEquals("10:00", resultado.getHoraFin());
        assertEquals("Piscina", resultado.getEscenario());
        assertEquals(Integer.valueOf(0), resultado.getEliminado());
    }

    @Test
    void toEntidad_horarioNulo_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> HorarioMapper.toEntidad(null));
    }

    // ── toDto ──────────────────────────────────────────────────────────────────

    @Test
    void toDto_mapeaCamposSinEliminado() {
        Horario horario = horarioBase();

        HorarioDto resultado = HorarioMapper.toDto(horario);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Natación", resultado.getCategoria());
        assertEquals("Natación Adultos", resultado.getCurso());
        assertEquals(Integer.valueOf(2025), resultado.getAnio());
        assertEquals(Integer.valueOf(1), resultado.getIterable());
        assertEquals("Lunes", resultado.getDia());
        assertEquals("08:00", resultado.getHoraInicio());
        assertEquals("10:00", resultado.getHoraFin());
        assertEquals("Piscina", resultado.getEscenario());
        // eliminado no forma parte de HorarioDto
    }

    @Test
    void toDto_horarioNulo_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> HorarioMapper.toDto(null));
    }

    // ── fromDto ────────────────────────────────────────────────────────────────

    @Test
    void fromDto_mapeaTodosLosCampos() {
        Horario resultado = HorarioMapper.fromDto(dtoBase());

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Natación", resultado.getCategoria());
        assertEquals("Natación Adultos", resultado.getCurso());
        assertEquals(2025, resultado.getAnio());
        assertEquals(1, resultado.getIterable());
        assertEquals("Lunes", resultado.getDia());
        assertEquals("08:00", resultado.getHoraInicio());
        assertEquals("10:00", resultado.getHoraFin());
        assertEquals("Piscina", resultado.getEscenario());
    }

    @Test
    void fromDto_anioNulo_usaDefault0() {
        HorarioDto dto = dtoBase();
        dto.setAnio(null);

        Horario resultado = HorarioMapper.fromDto(dto);

        assertEquals(0, resultado.getAnio(), "anio null debe defecto a 0");
    }

    @Test
    void fromDto_iterableNulo_usaDefault0() {
        HorarioDto dto = dtoBase();
        dto.setIterable(null);

        Horario resultado = HorarioMapper.fromDto(dto);

        assertEquals(0, resultado.getIterable(), "iterable null debe defecto a 0");
    }

    @Test
    void fromDto_dtoNulo_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> HorarioMapper.fromDto(null));
    }
}
