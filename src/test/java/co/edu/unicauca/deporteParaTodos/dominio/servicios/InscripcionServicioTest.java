package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IInscripcionGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;

@ExtendWith(MockitoExtension.class)
class InscripcionServicioTest {

    @Mock
    private IInscripcionGateway gateway;

    @InjectMocks
    private InscripcionServicio servicio;

    @Test
    void inscribir_nueva_guarda_con_fechaInscripcion() {
        Inscripcion datos = new Inscripcion("alum1", "cat1", "cur1", 2026, 1, null, null);
        Inscripcion guardada = new Inscripcion("alum1", "cat1", "cur1", 2026, 1, Timestamp.from(Instant.now()), null);
        when(gateway.existeInscripcion("alum1", "cat1", "cur1", 2026, 1)).thenReturn(false);
        when(gateway.guardarInscripcion(any())).thenReturn(guardada);

        Inscripcion resultado = servicio.inscribir(datos);

        assertNotNull(resultado);
        assertNotNull(resultado.getFechaInscripcion());
        verify(gateway).guardarInscripcion(any());
    }

    @Test
    void inscribir_existente_reactiva_poniendo_fechaDesvinculacion_nula() {
        Timestamp ahora = Timestamp.from(Instant.now());
        Inscripcion datos = new Inscripcion("alum1", "cat1", "cur1", 2026, 1, null, null);
        Inscripcion existente = new Inscripcion("alum1", "cat1", "cur1", 2026, 1, ahora, ahora);
        Inscripcion reactivada = new Inscripcion("alum1", "cat1", "cur1", 2026, 1, ahora, null);
        when(gateway.existeInscripcion("alum1", "cat1", "cur1", 2026, 1)).thenReturn(true);
        when(gateway.obtenerInscripcion("alum1", "cat1", "cur1", 2026, 1)).thenReturn(existente);
        when(gateway.guardarInscripcion(any())).thenReturn(reactivada);

        Inscripcion resultado = servicio.inscribir(datos);

        assertNull(resultado.getFechaDesvinculacion());
    }

    @Test
    void validarInscripcion_activa_devuelve_true() {
        when(gateway.existeInscripcionActiva("alum1", "cat1", "cur1", 2026, 1)).thenReturn(true);

        boolean resultado = servicio.validarInscripcion("alum1", "cat1", "cur1", 2026, 1);

        assertTrue(resultado);
    }

    @Test
    void validarInscripcion_inactiva_devuelve_false() {
        when(gateway.existeInscripcionActiva("alum1", "cat1", "cur1", 2026, 1)).thenReturn(false);

        boolean resultado = servicio.validarInscripcion("alum1", "cat1", "cur1", 2026, 1);

        assertFalse(resultado);
    }

    @Test
    void desvincularInscripcion_exitoso_devuelve_inscripcion_con_fechaDesvinculacion() {
        Timestamp ahora = Timestamp.from(Instant.now());
        Inscripcion desvinculada = new Inscripcion("alum1", "cat1", "cur1", 2026, 1, ahora, ahora);
        when(gateway.existeInscripcion("alum1", "cat1", "cur1", 2026, 1)).thenReturn(true);
        when(gateway.desvincularInscripcion("alum1", "cat1", "cur1", 2026, 1)).thenReturn(desvinculada);

        Inscripcion resultado = servicio.desvincularInscripcion("alum1", "cat1", "cur1", 2026, 1);

        assertNotNull(resultado);
        assertNotNull(resultado.getFechaDesvinculacion());
    }

    @Test
    void desvincularInscripcion_noExiste_lanzaNoExisteExcepcion() {
        when(gateway.existeInscripcion("alum1", "cat1", "cur1", 2026, 1)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class,
                () -> servicio.desvincularInscripcion("alum1", "cat1", "cur1", 2026, 1));
        verify(gateway, never()).desvincularInscripcion(any(), any(), any(), anyInt(), anyInt());
    }
}
