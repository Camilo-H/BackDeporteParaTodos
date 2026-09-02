package co.edu.unicauca.deporteParaTodos.infraestructura.mappers;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Escenario;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.EscenarioDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.EscenarioEntidad;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoProcesableEntidadException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EscenarioMapperTest {

    private EscenarioEntidad entidadBase() {
        EscenarioEntidad e = new EscenarioEntidad();
        e.setId(1);
        e.setNombre("Piscina Olímpica");
        e.setDescripcion("Natación competitiva.");
        e.setNumTribunas(4);
        e.setDisponible(1);
        e.setEliminado(0);
        return e;
    }

    private Escenario escenarioBase() {
        Escenario e = new Escenario();
        e.setId(1);
        e.setNombre("Piscina Olímpica");
        e.setDescripcion("Natación competitiva.");
        e.setNumTribunas(4);
        e.setDisponible(true);
        e.setEliminado(0);
        return e;
    }

    private EscenarioDto dtoBase() {
        EscenarioDto dto = new EscenarioDto();
        dto.setId(1);
        dto.setNombre("Piscina Olímpica");
        dto.setDescripcion("Natación competitiva.");
        dto.setNumTribunas(4);
        dto.setDisponible(true);
        return dto;
    }

    // ── toDominio ──────────────────────────────────────────────────────────────

    @Test
    void toDominio_disponibleUno_retornaTrue_mapeaTodosCampos() {
        EscenarioEntidad entidad = entidadBase();
        entidad.setDisponible(1);

        Escenario resultado = EscenarioMapper.toDominio(entidad);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Piscina Olímpica", resultado.getNombre());
        assertEquals("Natación competitiva.", resultado.getDescripcion());
        assertEquals(4, resultado.getNumTribunas());
        assertTrue(resultado.isDisponible(), "disponible == 1 debe mapear a true");
        assertEquals(0, resultado.getEliminado());
    }

    @Test
    void toDominio_disponibleCero_retornaFalse() {
        EscenarioEntidad entidad = entidadBase();
        entidad.setDisponible(0);

        Escenario resultado = EscenarioMapper.toDominio(entidad);

        assertFalse(resultado.isDisponible(), "disponible == 0 debe mapear a false (no != 0)");
    }

    @Test
    void toDominio_entidadNula_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> EscenarioMapper.toDominio(null));
    }

    // ── toEntidad ──────────────────────────────────────────────────────────────

    @Test
    void toEntidad_disponibleTrue_mapea1_mapeaTodosCampos() {
        Escenario escenario = escenarioBase();
        escenario.setDisponible(true);

        EscenarioEntidad resultado = EscenarioMapper.toEntidad(escenario);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Piscina Olímpica", resultado.getNombre());
        assertEquals("Natación competitiva.", resultado.getDescripcion());
        assertEquals(4, resultado.getNumTribunas());
        assertEquals(1, resultado.getDisponible(), "disponible true debe mapear a 1");
        assertEquals(0, resultado.getEliminado());
    }

    @Test
    void toEntidad_disponibleFalse_mapea0() {
        Escenario escenario = escenarioBase();
        escenario.setDisponible(false);

        EscenarioEntidad resultado = EscenarioMapper.toEntidad(escenario);

        assertEquals(0, resultado.getDisponible(), "disponible false debe mapear a 0 (ternario ? 1 : 0)");
    }

    @Test
    void toEntidad_escenarioNulo_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> EscenarioMapper.toEntidad(null));
    }

    // ── toDto ──────────────────────────────────────────────────────────────────

    @Test
    void toDto_mapeaTodosLosCamposCorrectamente() {
        Escenario escenario = escenarioBase();

        EscenarioDto resultado = EscenarioMapper.toDto(escenario);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Piscina Olímpica", resultado.getNombre());
        assertEquals("Natación competitiva.", resultado.getDescripcion());
        assertEquals(Integer.valueOf(4), resultado.getNumTribunas());
        assertTrue(resultado.getDisponible());
    }

    @Test
    void toDto_escenarioNulo_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> EscenarioMapper.toDto(null));
    }

    // ── fromDto ────────────────────────────────────────────────────────────────

    @Test
    void fromDto_mapeaTodosLosCamposCorrectamente() {
        EscenarioDto dto = dtoBase();

        Escenario resultado = EscenarioMapper.fromDto(dto);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Piscina Olímpica", resultado.getNombre());
        assertEquals("Natación competitiva.", resultado.getDescripcion());
        assertEquals(4, resultado.getNumTribunas());
        assertTrue(resultado.isDisponible());
        assertEquals(0, resultado.getEliminado());
    }

    @Test
    void fromDto_camposNulos_aplicaDefaultsNullSafe() {
        EscenarioDto dto = new EscenarioDto();
        dto.setNombre("Coliseo");
        // numTribunas y disponible son null

        Escenario resultado = EscenarioMapper.fromDto(dto);

        assertEquals(0, resultado.getNumTribunas(), "numTribunas null debe defecto a 0");
        assertTrue(resultado.isDisponible(), "disponible null debe defecto a true");
        assertEquals(0, resultado.getEliminado(), "eliminado siempre 0 para registros nuevos");
    }

    @Test
    void fromDto_nulo_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> EscenarioMapper.fromDto(null));
    }
}
