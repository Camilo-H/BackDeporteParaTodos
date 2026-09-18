package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Deporte;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.DeporteEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IDeporteRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeporteGatewayTest {

    @Mock
    private IDeporteRepositorio repoDeporte;

    @InjectMocks
    private DeporteGateway deporteGateway;

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

    // --- listaDeportes ---

    @Test
    void listaDeportes_retornaListaMapeada() {
        when(repoDeporte.findAll()).thenReturn(List.of(entidadBase()));

        List<Deporte> resultado = deporteGateway.listaDeportes();

        assertEquals(1, resultado.size());
        assertEquals(NOMBRE, resultado.get(0).getNombre());
    }

    // --- existeDeporte ---

    @Test
    void existeDeporte_existente_retornaTrue() {
        when(repoDeporte.existsById(NOMBRE)).thenReturn(true);

        assertTrue(deporteGateway.existeDeporte(NOMBRE));
    }

    @Test
    void existeDeporte_noExistente_retornaFalse() {
        when(repoDeporte.existsById(NOMBRE)).thenReturn(false);

        assertFalse(deporteGateway.existeDeporte(NOMBRE));
    }

    // --- insertarDeporte ---

    @Test
    void insertarDeporte_guardaYRetornaMapeado() {
        when(repoDeporte.save(any(DeporteEntidad.class))).thenReturn(entidadBase());

        Deporte resultado = deporteGateway.insertarDeporte(modeloBase());

        assertNotNull(resultado);
        assertEquals(NOMBRE, resultado.getNombre());
        verify(repoDeporte).save(any(DeporteEntidad.class));
    }

    // --- obtenerDeportePorId ---

    @Test
    void obtenerDeportePorId_encontrado_retornaDeporte() {
        when(repoDeporte.findById(NOMBRE)).thenReturn(Optional.of(entidadBase()));

        Deporte resultado = deporteGateway.obtenerDeportePorId(NOMBRE);

        assertNotNull(resultado);
        assertEquals(NOMBRE, resultado.getNombre());
    }

    @Test
    void obtenerDeportePorId_noEncontrado_retornaNull() {
        when(repoDeporte.findById(NOMBRE)).thenReturn(Optional.empty());

        assertNull(deporteGateway.obtenerDeportePorId(NOMBRE));
    }

    // --- eliminarDeporte ---

    @Test
    void eliminarDeporte_encontrado_eliminaYRetornaDeporte() {
        when(repoDeporte.findById(NOMBRE)).thenReturn(Optional.of(entidadBase()));
        doNothing().when(repoDeporte).deleteById(NOMBRE);

        Deporte resultado = deporteGateway.eliminarDeporte(NOMBRE);

        assertNotNull(resultado);
        assertEquals(NOMBRE, resultado.getNombre());
        verify(repoDeporte).deleteById(NOMBRE);
    }

    @Test
    void eliminarDeporte_noEncontrado_retornaNull() {
        when(repoDeporte.findById(NOMBRE)).thenReturn(Optional.empty());

        assertNull(deporteGateway.eliminarDeporte(NOMBRE));

        verify(repoDeporte, never()).deleteById(any());
    }
}
