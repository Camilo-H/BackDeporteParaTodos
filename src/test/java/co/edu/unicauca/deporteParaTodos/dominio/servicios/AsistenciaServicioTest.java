package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IAsistenciaGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Asistencia;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.YaExisteElementoExcepcion;

@ExtendWith(MockitoExtension.class)
class AsistenciaServicioTest {

    @Mock
    private IAsistenciaGateway asistenciaGateway;

    @Mock
    private co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IAlumnoGateway alumnoGateway;

    @Mock
    private co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IClaseGateway claseGateway;

    @InjectMocks
    private AsistenciaServicio servicio;

    @Test
    void eliminarAsistencia_exitoso_devuelve_asistencia_eliminada() {
        Asistencia activa = new Asistencia("perf1", 10, 0);
        Asistencia eliminada = new Asistencia("perf1", 10, 1);
        when(asistenciaGateway.existeAsistencia("perf1", 10)).thenReturn(true);
        when(asistenciaGateway.obtenerAsistencia("perf1", 10)).thenReturn(Optional.of(activa));
        when(asistenciaGateway.eliminarAsistencia("perf1", 10L)).thenReturn(eliminada);

        Asistencia resultado = servicio.eliminarAsistencia("perf1", 10L);

        assertNotNull(resultado);
        assertEquals(1, resultado.getEliminado());
        verify(asistenciaGateway).eliminarAsistencia("perf1", 10L);
    }

    @Test
    void eliminarAsistencia_noExiste_lanzaNoExisteExcepcion() {
        when(asistenciaGateway.existeAsistencia("perf1", 10)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class,
                () -> servicio.eliminarAsistencia("perf1", 10L));
        verify(asistenciaGateway, never()).eliminarAsistencia(any(), any());
    }

    @Test
    void eliminarAsistencia_yaEliminada_lanzaYaExisteElementoExcepcion() {
        Asistencia yaEliminada = new Asistencia("perf1", 10, 1);
        when(asistenciaGateway.existeAsistencia("perf1", 10)).thenReturn(true);
        when(asistenciaGateway.obtenerAsistencia("perf1", 10)).thenReturn(Optional.of(yaEliminada));

        assertThrows(YaExisteElementoExcepcion.class,
                () -> servicio.eliminarAsistencia("perf1", 10L));
        verify(asistenciaGateway, never()).eliminarAsistencia(any(), any());
    }
}
