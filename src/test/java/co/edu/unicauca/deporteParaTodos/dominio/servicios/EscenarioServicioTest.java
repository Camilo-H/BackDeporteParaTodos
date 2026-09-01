package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IEscenarioGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Escenario;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.YaExisteElementoExcepcion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EscenarioServicioTest {

    @Mock
    private IEscenarioGateway escenarioGateway;

    @InjectMocks
    private EscenarioServicio escenarioServicio;

    private static final Integer ID = 1;
    private static final String NOMBRE = "Piscina Olímpica";

    private Escenario escenarioActivo() {
        Escenario e = new Escenario();
        e.setId(ID);
        e.setNombre(NOMBRE);
        e.setDescripcion("Natación.");
        e.setNumTribunas(0);
        e.setDisponible(true);
        e.setEliminado(0);
        return e;
    }

    private Escenario escenarioEliminado() {
        Escenario e = escenarioActivo();
        e.setEliminado(1);
        return e;
    }

    // --- listarEscenarios ---

    @Test
    void listarEscenarios_exitoso_retornaListaEscenarios() {
        when(escenarioGateway.listarEscenarios()).thenReturn(List.of(escenarioActivo()));

        List<Escenario> resultado = escenarioServicio.listarEscenarios();

        assertEquals(1, resultado.size());
        assertEquals(NOMBRE, resultado.get(0).getNombre());
        verify(escenarioGateway).listarEscenarios();
    }

    @Test
    void listarEscenarios_sinRegistros_lanzaListadoVacioExcepcion() {
        when(escenarioGateway.listarEscenarios()).thenReturn(Collections.emptyList());

        assertThrows(ListadoVacioExcepcion.class, () -> escenarioServicio.listarEscenarios());
    }

    // --- obtenerEscenario ---

    @Test
    void obtenerEscenario_exitoso_retornaEscenario() {
        when(escenarioGateway.existeEscenario(ID)).thenReturn(true);
        when(escenarioGateway.obtenerEscenario(ID)).thenReturn(escenarioActivo());

        Escenario resultado = escenarioServicio.obtenerEscenario(ID);

        assertNotNull(resultado);
        assertEquals(NOMBRE, resultado.getNombre());
        assertEquals(ID, resultado.getId());
    }

    @Test
    void obtenerEscenario_noExiste_lanzaNoExisteExcepcion() {
        when(escenarioGateway.existeEscenario(ID)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class, () -> escenarioServicio.obtenerEscenario(ID));

        verify(escenarioGateway, never()).obtenerEscenario(any());
    }

    // --- insertarEscenario ---

    @Test
    void insertarEscenario_exitoso_retornaEscenario() {
        when(escenarioGateway.existeEscenarioPorNombre(NOMBRE)).thenReturn(false);
        when(escenarioGateway.insertarEscenario(any())).thenReturn(escenarioActivo());

        Escenario resultado = escenarioServicio.insertarEscenario(escenarioActivo());

        assertNotNull(resultado);
        assertEquals(NOMBRE, resultado.getNombre());
        verify(escenarioGateway).insertarEscenario(any());
    }

    @Test
    void insertarEscenario_yaExiste_lanzaYaExisteElementoExcepcion() {
        when(escenarioGateway.existeEscenarioPorNombre(NOMBRE)).thenReturn(true);

        assertThrows(YaExisteElementoExcepcion.class,
                () -> escenarioServicio.insertarEscenario(escenarioActivo()));

        verify(escenarioGateway, never()).insertarEscenario(any());
    }

    // --- actualizarEscenario ---

    @Test
    void actualizarEscenario_exitoso_retornaEscenario() {
        Escenario actualizado = escenarioActivo();
        actualizado.setDescripcion("Nueva descripción");

        when(escenarioGateway.existeEscenario(ID)).thenReturn(true);
        when(escenarioGateway.obtenerEscenario(ID)).thenReturn(escenarioActivo());
        when(escenarioGateway.actualizarEscenario(eq(ID), any())).thenReturn(actualizado);

        Escenario resultado = escenarioServicio.actualizarEscenario(ID, escenarioActivo());

        assertNotNull(resultado);
        assertEquals(NOMBRE, resultado.getNombre());
        verify(escenarioGateway).actualizarEscenario(eq(ID), any());
    }

    @Test
    void actualizarEscenario_noExiste_lanzaNoExisteExcepcion() {
        when(escenarioGateway.existeEscenario(ID)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class,
                () -> escenarioServicio.actualizarEscenario(ID, escenarioActivo()));

        verify(escenarioGateway, never()).actualizarEscenario(any(), any());
    }

    @Test
    void actualizarEscenario_nombreDuplicado_lanzaYaExisteElementoExcepcion() {
        Escenario datosNuevoNombre = escenarioActivo();
        datosNuevoNombre.setNombre("Coliseo Cubierto Universitario");

        Escenario actual = escenarioActivo(); // nombre "Piscina Olímpica"

        when(escenarioGateway.existeEscenario(ID)).thenReturn(true);
        when(escenarioGateway.obtenerEscenario(ID)).thenReturn(actual);
        when(escenarioGateway.existeEscenarioPorNombre("Coliseo Cubierto Universitario")).thenReturn(true);

        assertThrows(YaExisteElementoExcepcion.class,
                () -> escenarioServicio.actualizarEscenario(ID, datosNuevoNombre));

        verify(escenarioGateway, never()).actualizarEscenario(any(), any());
    }

    // --- eliminarEscenario ---

    @Test
    void eliminarEscenario_exitoso_retornaEscenarioEliminado() {
        Escenario eliminado = escenarioEliminado();

        when(escenarioGateway.existeEscenario(ID)).thenReturn(true);
        when(escenarioGateway.obtenerEscenario(ID)).thenReturn(escenarioActivo());
        when(escenarioGateway.eliminarEscenario(ID)).thenReturn(eliminado);

        Escenario resultado = escenarioServicio.eliminarEscenario(ID);

        assertNotNull(resultado);
        assertEquals(1, resultado.getEliminado());
        verify(escenarioGateway).eliminarEscenario(ID);
    }

    @Test
    void eliminarEscenario_noExiste_lanzaNoExisteExcepcion() {
        when(escenarioGateway.existeEscenario(ID)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class, () -> escenarioServicio.eliminarEscenario(ID));

        verify(escenarioGateway, never()).eliminarEscenario(any());
    }

    @Test
    void eliminarEscenario_yaEstaEliminado_lanzaYaExisteElementoExcepcion() {
        when(escenarioGateway.existeEscenario(ID)).thenReturn(true);
        when(escenarioGateway.obtenerEscenario(ID)).thenReturn(escenarioEliminado());

        assertThrows(YaExisteElementoExcepcion.class,
                () -> escenarioServicio.eliminarEscenario(ID));

        verify(escenarioGateway, never()).eliminarEscenario(any());
    }
}
