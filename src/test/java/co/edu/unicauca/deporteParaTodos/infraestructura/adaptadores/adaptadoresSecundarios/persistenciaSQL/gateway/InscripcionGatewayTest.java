package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.InscripcionEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IInscripcionRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InscripcionGatewayTest {

    @Mock
    private IInscripcionRepositorio repoInscrp;

    @InjectMocks
    private InscripcionGateway gateway;

    private static final String    ALUMNO_ID = "alum1";
    private static final String    CATEGORIA = "cat1";
    private static final String    CURSO     = "cur1";
    private static final int       ANIO      = 2026;
    private static final int       ITERABLE  = 1;
    private static final Timestamp AHORA     = Timestamp.from(Instant.now());

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

    @Test
    void existeInscripcion_returnTrue() {
        when(repoInscrp.existsById(any())).thenReturn(true);
        assertTrue(gateway.existeInscripcion(ALUMNO_ID, CATEGORIA, CURSO, ANIO, ITERABLE));
    }

    @Test
    void existeInscripcionActiva_returnTrue() {
        when(repoInscrp.existeInscripcionActiva(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(true);
        assertTrue(gateway.existeInscripcionActiva(ALUMNO_ID, CATEGORIA, CURSO, ANIO, ITERABLE));
    }

    @Test
    void obtenerInscripcion_existente_retornaInscripcion() {
        when(repoInscrp.findById(any())).thenReturn(Optional.of(entidadBase()));

        Inscripcion resultado = gateway.obtenerInscripcion(ALUMNO_ID, CATEGORIA, CURSO, ANIO, ITERABLE);

        assertNotNull(resultado);
        assertEquals(ALUMNO_ID, resultado.getAlumnoId());
        assertEquals(CATEGORIA, resultado.getCategoria());
    }

    @Test
    void obtenerInscripcion_noExiste_lanzaNoExisteExcepcion() {
        when(repoInscrp.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoExisteExcepcion.class,
                () -> gateway.obtenerInscripcion(ALUMNO_ID, CATEGORIA, CURSO, ANIO, ITERABLE));
    }

    @Test
    void guardarInscripcion_retornaInscripcionGuardada() {
        when(repoInscrp.save(any(InscripcionEntidad.class))).thenReturn(entidadBase());

        Inscripcion input = new Inscripcion(ALUMNO_ID, CATEGORIA, CURSO, ANIO, ITERABLE, AHORA, null);
        Inscripcion resultado = gateway.guardarInscripcion(input);

        assertNotNull(resultado);
        assertEquals(ALUMNO_ID, resultado.getAlumnoId());
        assertEquals(CATEGORIA, resultado.getCategoria());
        verify(repoInscrp).save(any(InscripcionEntidad.class));
    }

    @Test
    void desvincularInscripcion_existente_setFechaDesvinculacionYGuarda() {
        when(repoInscrp.findById(any())).thenReturn(Optional.of(entidadBase()));
        when(repoInscrp.save(any(InscripcionEntidad.class))).thenAnswer(inv -> inv.getArgument(0));

        Inscripcion resultado = gateway.desvincularInscripcion(ALUMNO_ID, CATEGORIA, CURSO, ANIO, ITERABLE);

        assertNotNull(resultado);
        assertNotNull(resultado.getFechaDesvinculacion());
        verify(repoInscrp).save(any(InscripcionEntidad.class));
    }

    @Test
    void desvincularInscripcion_noExiste_lanzaNoExisteExcepcion() {
        when(repoInscrp.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoExisteExcepcion.class,
                () -> gateway.desvincularInscripcion(ALUMNO_ID, CATEGORIA, CURSO, ANIO, ITERABLE));
        verify(repoInscrp, never()).save(any());
    }
}
