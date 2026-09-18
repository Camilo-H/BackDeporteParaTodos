package co.edu.unicauca.deporteParaTodos.infraestructura.mappers;

import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoProcesableEntidadException;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Deporte;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.DeporteDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.DeporteEntidad;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeporteMapperTest {

    private static final String NOMBRE = "Natacion";

    private DeporteEntidad entidadBase() {
        DeporteEntidad e = new DeporteEntidad();
        e.setNombre(NOMBRE);
        return e;
    }

    private Deporte modeloBase() {
        Deporte d = new Deporte();
        d.setNombre(NOMBRE);
        return d;
    }

    private DeporteDto dtoBase() {
        DeporteDto dto = new DeporteDto();
        dto.setNombre(NOMBRE);
        return dto;
    }

    // ── toDominio ─────────────────────────────────────────────────────────────

    @Test
    void toDominio_entidadValida_mapeaNombreCorrectamente() {
        Deporte resultado = DeporteMapper.toDominio(entidadBase());

        assertNotNull(resultado);
        assertEquals(NOMBRE, resultado.getNombre());
    }

    @Test
    void toDominio_entidadNula_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> DeporteMapper.toDominio(null));
    }

    // ── toEntidad ─────────────────────────────────────────────────────────────

    @Test
    void toEntidad_modeloValido_mapeaNombreCorrectamente() {
        DeporteEntidad resultado = DeporteMapper.toEntidad(modeloBase());

        assertNotNull(resultado);
        assertEquals(NOMBRE, resultado.getNombre());
    }

    @Test
    void toEntidad_modeloNulo_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> DeporteMapper.toEntidad(null));
    }

    // ── toDto ─────────────────────────────────────────────────────────────────

    @Test
    void toDto_modeloValido_mapeaNombreCorrectamente() {
        DeporteDto resultado = DeporteMapper.toDto(modeloBase());

        assertNotNull(resultado);
        assertEquals(NOMBRE, resultado.getNombre());
    }

    @Test
    void toDto_modeloNulo_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> DeporteMapper.toDto(null));
    }

    // ── fromDto ───────────────────────────────────────────────────────────────

    @Test
    void fromDto_dtoValido_mapeaNombreCorrectamente() {
        Deporte resultado = DeporteMapper.fromDto(dtoBase());

        assertNotNull(resultado);
        assertEquals(NOMBRE, resultado.getNombre());
    }

    @Test
    void fromDto_dtoNulo_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> DeporteMapper.fromDto(null));
    }
}
