package co.edu.unicauca.deporteParaTodos.infraestructura.mappers;

import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoProcesableEntidadException;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Facultad;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Programa;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.FacultadEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ProgramaEntidad;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgramaMapperTest {

    private static final String NOMBRE_PROGRAMA = "Ingeniería de Sistemas";
    private static final String NOMBRE_FACULTAD = "Facultad de Ingeniería";

    private ProgramaEntidad entidadBase() {
        ProgramaEntidad e = new ProgramaEntidad();
        e.setPrg_nombre(NOMBRE_PROGRAMA);
        FacultadEntidad fe = new FacultadEntidad();
        fe.setNombre(NOMBRE_FACULTAD);
        e.setFacultad(fe);
        return e;
    }

    private Programa modeloBase() {
        Programa p = new Programa();
        p.setPrg_nombre(NOMBRE_PROGRAMA);
        Facultad f = new Facultad();
        f.setNombre(NOMBRE_FACULTAD);
        p.setFacultad(f);
        return p;
    }

    // ── toDominio ─────────────────────────────────────────────────────────────

    @Test
    void toDominio_entidadValida_mapeaTodosLosCampos() {
        Programa resultado = ProgramaMapper.toDominio(entidadBase());

        assertNotNull(resultado);
        assertEquals(NOMBRE_PROGRAMA, resultado.getPrg_nombre());
        assertNotNull(resultado.getFacultad());
        assertEquals(NOMBRE_FACULTAD, resultado.getFacultad().getNombre());
    }

    @Test
    void toDominio_entidadNula_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> ProgramaMapper.toDominio(null));
    }

    @Test
    void toDominio_facultadNula_facultadModeloEsNula() {
        ProgramaEntidad entidad = new ProgramaEntidad();
        entidad.setPrg_nombre(NOMBRE_PROGRAMA);
        entidad.setFacultad(null);

        Programa resultado = ProgramaMapper.toDominio(entidad);

        assertNotNull(resultado);
        assertEquals(NOMBRE_PROGRAMA, resultado.getPrg_nombre());
        assertNull(resultado.getFacultad());
    }

    // ── toEntidad ─────────────────────────────────────────────────────────────

    @Test
    void toEntidad_modeloValido_mapeaTodosLosCampos() {
        ProgramaEntidad resultado = ProgramaMapper.toEntidad(modeloBase());

        assertNotNull(resultado);
        assertEquals(NOMBRE_PROGRAMA, resultado.getPrg_nombre());
        assertNotNull(resultado.getFacultad());
        assertEquals(NOMBRE_FACULTAD, resultado.getFacultad().getNombre());
    }

    @Test
    void toEntidad_modeloNulo_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> ProgramaMapper.toEntidad(null));
    }

    @Test
    void toEntidad_facultadNula_facultadEntidadEsNula() {
        Programa modelo = new Programa();
        modelo.setPrg_nombre(NOMBRE_PROGRAMA);
        modelo.setFacultad(null);

        ProgramaEntidad resultado = ProgramaMapper.toEntidad(modelo);

        assertNotNull(resultado);
        assertEquals(NOMBRE_PROGRAMA, resultado.getPrg_nombre());
        assertNull(resultado.getFacultad());
    }
}
