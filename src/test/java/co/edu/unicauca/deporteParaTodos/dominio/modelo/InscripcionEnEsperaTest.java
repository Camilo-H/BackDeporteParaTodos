package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InscripcionEnEsperaTest {

    @Test
    void constructorSinArgumentosYSetters_asignanValoresCorrectamente() {
        Timestamp ahora = Timestamp.from(Instant.now());

        InscripcionEnEspera inscripcion = new InscripcionEnEspera();
        inscripcion.setAlumnoId("alum1");
        inscripcion.setNombre("Juan Perez");
        inscripcion.setCorreo("juan@unicauca.edu.co");
        inscripcion.setFechaInscripcion(ahora);

        assertEquals("alum1", inscripcion.getAlumnoId());
        assertEquals("Juan Perez", inscripcion.getNombre());
        assertEquals("juan@unicauca.edu.co", inscripcion.getCorreo());
        assertEquals(ahora, inscripcion.getFechaInscripcion());
    }
}
