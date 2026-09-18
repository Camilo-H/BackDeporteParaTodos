package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IDeporteGateway;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Deporte;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.DeporteDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeporteServicioTest {

    @Mock
    private IDeporteGateway deporteGateway;

    @InjectMocks
    private DeporteServicio deporteServicio;

    private static final String NOMBRE = "Futbol";

    private Deporte deporteBase() {
        Deporte d = new Deporte();
        d.setNombre(NOMBRE);
        return d;
    }

    private DeporteDto dtoBase() {
        DeporteDto dto = new DeporteDto();
        dto.setNombre(NOMBRE);
        return dto;
    }

    // --- listaDeportes ---

    @Test
    void listaDeportes_conDeportes_retornaListaDtos() {
        when(deporteGateway.listaDeportes()).thenReturn(List.of(deporteBase()));

        List<DeporteDto> resultado = deporteServicio.listaDeportes();

        assertEquals(1, resultado.size());
        assertEquals(NOMBRE, resultado.get(0).getNombre());
        verify(deporteGateway).listaDeportes();
    }

    @Test
    void listaDeportes_listaVacia_lanzaListadoVacioExcepcion() {
        when(deporteGateway.listaDeportes()).thenReturn(Collections.emptyList());

        assertThrows(ListadoVacioExcepcion.class, () -> deporteServicio.listaDeportes());
    }

    // --- insertarDeporte ---

    @Test
    void insertarDeporte_noExiste_persiste_retornaDto() {
        when(deporteGateway.existeDeporte(NOMBRE)).thenReturn(false);
        when(deporteGateway.insertarDeporte(any(Deporte.class))).thenReturn(deporteBase());

        DeporteDto resultado = deporteServicio.insertarDeporte(dtoBase());

        assertNotNull(resultado);
        assertEquals(NOMBRE, resultado.getNombre());
        verify(deporteGateway).insertarDeporte(any(Deporte.class));
    }

    @Test
    void insertarDeporte_yaExiste_lanzaInsercionFallidaExepcion() {
        when(deporteGateway.existeDeporte(NOMBRE)).thenReturn(true);

        assertThrows(InsercionFallidaExepcion.class, () -> deporteServicio.insertarDeporte(dtoBase()));

        verify(deporteGateway, never()).insertarDeporte(any());
    }

    // --- obtenerDeportePorId ---

    @Test
    void obtenerDeportePorId_existe_retornaDeporte() {
        when(deporteGateway.obtenerDeportePorId(NOMBRE)).thenReturn(deporteBase());

        Deporte resultado = deporteServicio.obtenerDeportePorId(NOMBRE);

        assertNotNull(resultado);
        assertEquals(NOMBRE, resultado.getNombre());
    }

    @Test
    void obtenerDeportePorId_noExiste_lanzaNoExisteExcepcion() {
        when(deporteGateway.obtenerDeportePorId(NOMBRE)).thenReturn(null);

        assertThrows(NoExisteExcepcion.class, () -> deporteServicio.obtenerDeportePorId(NOMBRE));
    }

    // --- actualizarDeporte ---

    @Test
    void actualizarDeporte_existe_retornaDeporteActualizado() {
        when(deporteGateway.existeDeporte(NOMBRE)).thenReturn(true);
        when(deporteGateway.actualizarDeporte(any(Deporte.class))).thenReturn(deporteBase());

        Deporte resultado = deporteServicio.actualizarDeporte(NOMBRE, deporteBase());

        assertNotNull(resultado);
        assertEquals(NOMBRE, resultado.getNombre());
        verify(deporteGateway).actualizarDeporte(any(Deporte.class));
    }

    @Test
    void actualizarDeporte_noExiste_lanzaNoExisteExcepcion() {
        when(deporteGateway.existeDeporte(NOMBRE)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class,
                () -> deporteServicio.actualizarDeporte(NOMBRE, deporteBase()));

        verify(deporteGateway, never()).actualizarDeporte(any());
    }

    // --- eliminarDeporte ---

    @Test
    void eliminarDeporte_existe_retornaDeporteEliminado() {
        when(deporteGateway.existeDeporte(NOMBRE)).thenReturn(true);
        when(deporteGateway.eliminarDeporte(NOMBRE)).thenReturn(deporteBase());

        Deporte resultado = deporteServicio.eliminarDeporte(NOMBRE);

        assertNotNull(resultado);
        assertEquals(NOMBRE, resultado.getNombre());
        verify(deporteGateway).eliminarDeporte(NOMBRE);
    }

    @Test
    void eliminarDeporte_noExiste_lanzaNoExisteExcepcion() {
        when(deporteGateway.existeDeporte(NOMBRE)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class, () -> deporteServicio.eliminarDeporte(NOMBRE));

        verify(deporteGateway, never()).eliminarDeporte(any());
    }
}
