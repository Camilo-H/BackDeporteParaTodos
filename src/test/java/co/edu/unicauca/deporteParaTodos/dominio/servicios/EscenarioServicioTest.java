package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IEscenarioGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Escenario;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.EscenarioDto;
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

    private EscenarioDto dtoBase() {
        EscenarioDto dto = new EscenarioDto();
        dto.setNombre(NOMBRE);
        dto.setDescripcion("Natación.");
        dto.setNumTribunas(0);
        dto.setDisponible(true);
        return dto;
    }

    // --- listarEscenarios ---

    @Test
    void listarEscenarios_exitoso_retornaListaDtos() {
        when(escenarioGateway.listarEscenarios()).thenReturn(List.of(escenarioActivo()));

        List<EscenarioDto> resultado = escenarioServicio.listarEscenarios();

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
    void obtenerEscenario_exitoso_retornaDto() {
        when(escenarioGateway.existeEscenario(ID)).thenReturn(true);
        when(escenarioGateway.obtenerEscenario(ID)).thenReturn(escenarioActivo());

        EscenarioDto resultado = escenarioServicio.obtenerEscenario(ID);

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
    void insertarEscenario_exitoso_retornaDto() {
        when(escenarioGateway.existeEscenarioPorNombre(NOMBRE)).thenReturn(false);
        when(escenarioGateway.insertarEscenario(any())).thenReturn(escenarioActivo());

        EscenarioDto resultado = escenarioServicio.insertarEscenario(dtoBase());

        assertNotNull(resultado);
        assertEquals(NOMBRE, resultado.getNombre());
        verify(escenarioGateway).insertarEscenario(any());
    }

    @Test
    void insertarEscenario_yaExiste_lanzaYaExisteElementoExcepcion() {
        when(escenarioGateway.existeEscenarioPorNombre(NOMBRE)).thenReturn(true);

        assertThrows(YaExisteElementoExcepcion.class, () -> escenarioServicio.insertarEscenario(dtoBase()));

        verify(escenarioGateway, never()).insertarEscenario(any());
    }

    // --- actualizarEscenario ---

    @Test
    void actualizarEscenario_exitoso_retornaDto() {
        Escenario actualizado = escenarioActivo();
        actualizado.setDescripcion("Nueva descripción");

        when(escenarioGateway.existeEscenario(ID)).thenReturn(true);
        when(escenarioGateway.obtenerEscenario(ID)).thenReturn(escenarioActivo());
        when(escenarioGateway.actualizarEscenario(eq(ID), any())).thenReturn(actualizado);

        EscenarioDto resultado = escenarioServicio.actualizarEscenario(ID, dtoBase());

        assertNotNull(resultado);
        assertEquals(NOMBRE, resultado.getNombre());
        verify(escenarioGateway).actualizarEscenario(eq(ID), any());
    }

    @Test
    void actualizarEscenario_noExiste_lanzaNoExisteExcepcion() {
        when(escenarioGateway.existeEscenario(ID)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class, () -> escenarioServicio.actualizarEscenario(ID, dtoBase()));

        verify(escenarioGateway, never()).actualizarEscenario(any(), any());
    }

    @Test
    void actualizarEscenario_nombreDuplicado_lanzaYaExisteElementoExcepcion() {
        EscenarioDto dtoNuevoNombre = dtoBase();
        dtoNuevoNombre.setNombre("Coliseo Cubierto Universitario");

        Escenario actual = escenarioActivo(); // tiene nombre "Piscina Olímpica"

        when(escenarioGateway.existeEscenario(ID)).thenReturn(true);
        when(escenarioGateway.obtenerEscenario(ID)).thenReturn(actual);
        when(escenarioGateway.existeEscenarioPorNombre("Coliseo Cubierto Universitario")).thenReturn(true);

        assertThrows(YaExisteElementoExcepcion.class,
            () -> escenarioServicio.actualizarEscenario(ID, dtoNuevoNombre));

        verify(escenarioGateway, never()).actualizarEscenario(any(), any());
    }

    // --- eliminarEscenario ---

    @Test
    void eliminarEscenario_exitoso_retornaDto() {
        Escenario eliminado = escenarioEliminado();

        when(escenarioGateway.existeEscenario(ID)).thenReturn(true);
        when(escenarioGateway.obtenerEscenario(ID)).thenReturn(escenarioActivo());
        when(escenarioGateway.eliminarEscenario(ID)).thenReturn(eliminado);

        EscenarioDto resultado = escenarioServicio.eliminarEscenario(ID);

        assertNotNull(resultado);
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

        assertThrows(YaExisteElementoExcepcion.class, () -> escenarioServicio.eliminarEscenario(ID));

        verify(escenarioGateway, never()).eliminarEscenario(any());
    }
}
