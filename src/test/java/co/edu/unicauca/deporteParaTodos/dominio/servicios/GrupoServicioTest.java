package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IGrupoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Grupo;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.GrupoDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoProcesableEntidadException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GrupoServicioTest {

    @Mock
    private IGrupoGateway grupoGateway;

    @InjectMocks
    private GrupoServicio grupoServicio;

    private static final String  CATEGORIA = "Recreativo";
    private static final String  CURSO     = "Natacion";
    private static final Integer ANIO      = 2025;
    private static final Integer ITERABLE  = 1;

    private Grupo grupoModelo() {
        Grupo g = new Grupo();
        g.setCategoria(CATEGORIA);
        g.setCurso(CURSO);
        g.setAnio(ANIO);
        g.setIterable(ITERABLE);
        g.setCupos(20);
        g.setImagenGrupo(1);
        g.setIdInstructor("INS001");
        g.setFechaCreacion(LocalDate.of(2025, 1, 15));
        g.setPeriodo(1);
        return g;
    }

    private GrupoDto grupoDto() {
        GrupoDto dto = new GrupoDto();
        dto.setCategoria(CATEGORIA);
        dto.setCurso(CURSO);
        dto.setAnio(ANIO);
        dto.setIterable(ITERABLE);
        dto.setCupos(20);
        dto.setImagenGrupo(1);
        dto.setFechaCreacion(LocalDate.of(2025, 1, 15));
        dto.setPeriodo(1);
        return dto;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // obtenerTodosGrupos
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void obtenerTodosGrupos_delegaAlGatewayYMapeaADtos() {
        when(grupoGateway.obtenerTodosGrupos()).thenReturn(List.of(grupoModelo(), grupoModelo()));

        List<GrupoDto> resultado = grupoServicio.obtenerTodosGrupos();

        assertEquals(2, resultado.size());
        verify(grupoGateway).obtenerTodosGrupos();
    }

    // ──────────────────────────────────────────────────────────────────────────
    // obtenerGruposDisponibles
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void obtenerGruposDisponibles_delegaAlGatewayYMapeaADtos() {
        when(grupoGateway.obtenerGruposDisponibles()).thenReturn(List.of(grupoModelo()));

        List<GrupoDto> resultado = grupoServicio.obtenerGruposDisponibles();

        assertEquals(1, resultado.size());
        verify(grupoGateway).obtenerGruposDisponibles();
    }

    // ──────────────────────────────────────────────────────────────────────────
    // obtenerGruposDeCurso
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void obtenerGruposDeCurso_delegaAlGatewayConParametros() {
        when(grupoGateway.obtenerGruposDeCurso(CATEGORIA, CURSO)).thenReturn(List.of(grupoModelo()));

        List<GrupoDto> resultado = grupoServicio.obtenerGruposDeCurso(CATEGORIA, CURSO);

        assertEquals(1, resultado.size());
        verify(grupoGateway).obtenerGruposDeCurso(CATEGORIA, CURSO);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // obtenerGruposInscripcionDisponible
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void obtenerGruposInscripcionDisponible_delegaAlGateway() {
        when(grupoGateway.obtenerGruposInscripcionDisponible()).thenReturn(List.of(grupoModelo()));

        List<GrupoDto> resultado = grupoServicio.obtenerGruposInscripcionDisponible();

        assertEquals(1, resultado.size());
        verify(grupoGateway).obtenerGruposInscripcionDisponible();
    }

    // ──────────────────────────────────────────────────────────────────────────
    // obtenerGruposInstructor
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void obtenerGruposInstructor_delegaAlGatewayConIdInstructor() {
        when(grupoGateway.obtenerGruposInstructor("INS001")).thenReturn(List.of(grupoModelo()));

        List<GrupoDto> resultado = grupoServicio.obtenerGruposInstructor("INS001");

        assertEquals(1, resultado.size());
        verify(grupoGateway).obtenerGruposInstructor("INS001");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // insertarGrupo
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void insertarGrupo_exitoso_convierteYDelegaAlGateway() {
        when(grupoGateway.insertarGrupo(any())).thenReturn(grupoModelo());

        GrupoDto resultado = grupoServicio.insertarGrupo(grupoDto());

        assertNotNull(resultado);
        assertEquals(CATEGORIA, resultado.getCategoria());
        verify(grupoGateway).insertarGrupo(any());
    }

    @Test
    void insertarGrupo_sinNullCheckDeConversion_pasaNullAlGateway_comportamientoActual() {
        // COMPORTAMIENTO CUESTIONABLE: insertarGrupo no verifica si Grupo.fabricarDeDto()
        // retorna null (a diferencia de actualizarGrupo que sí lo hace con NoProcesableEntidadException).
        // Si el DTO es nulo, la conversión falla silenciosamente y se pasa null al gateway.
        when(grupoGateway.insertarGrupo(nullable(Grupo.class))).thenReturn(grupoModelo());

        assertDoesNotThrow(() -> grupoServicio.insertarGrupo(null),
                "insertarGrupo no lanza NoProcesableEntidadException cuando la conversion falla");

        verify(grupoGateway).insertarGrupo(null);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // obtenerGrupoPorId
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void obtenerGrupoPorId_exitoso_retornaDtoMapeado() {
        when(grupoGateway.obtenerGrupoPorId(CATEGORIA, CURSO, ANIO, ITERABLE))
                .thenReturn(grupoModelo());

        GrupoDto resultado = grupoServicio.obtenerGrupoPorId(CATEGORIA, CURSO, ANIO, ITERABLE);

        assertNotNull(resultado);
        assertEquals(CATEGORIA, resultado.getCategoria());
        assertEquals(CURSO, resultado.getCurso());
    }

    @Test
    void obtenerGrupoPorId_grupoNulo_lanzaNoExisteExcepcion() {
        when(grupoGateway.obtenerGrupoPorId(CATEGORIA, CURSO, ANIO, ITERABLE))
                .thenReturn(null);

        assertThrows(NoExisteExcepcion.class,
                () -> grupoServicio.obtenerGrupoPorId(CATEGORIA, CURSO, ANIO, ITERABLE));
    }

    // ──────────────────────────────────────────────────────────────────────────
    // actualizarGrupo
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void actualizarGrupo_exitoso_actualizaCorrectamente() {
        when(grupoGateway.existeGrupo(CATEGORIA, CURSO, ANIO, ITERABLE)).thenReturn(true);
        when(grupoGateway.actualizarGrupo(eq(CATEGORIA), eq(CURSO), eq(ANIO), eq(ITERABLE), any()))
                .thenReturn(grupoModelo());

        GrupoDto resultado = grupoServicio.actualizarGrupo(CATEGORIA, CURSO, ANIO, ITERABLE, grupoDto());

        assertNotNull(resultado);
        assertEquals(CATEGORIA, resultado.getCategoria());
        verify(grupoGateway).actualizarGrupo(eq(CATEGORIA), eq(CURSO), eq(ANIO), eq(ITERABLE), any());
    }

    @Test
    void actualizarGrupo_noExiste_lanzaNoExisteExcepcion() {
        when(grupoGateway.existeGrupo(CATEGORIA, CURSO, ANIO, ITERABLE)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class,
                () -> grupoServicio.actualizarGrupo(CATEGORIA, CURSO, ANIO, ITERABLE, grupoDto()));

        verify(grupoGateway, never()).actualizarGrupo(any(), any(), any(), any(), any());
    }

    @Test
    void actualizarGrupo_conversionFalla_lanzaNoProcesableEntidadException() {
        // Grupo.fabricarDeDto(null) retorna null → el servicio lanza NoProcesableEntidadException.
        when(grupoGateway.existeGrupo(CATEGORIA, CURSO, ANIO, ITERABLE)).thenReturn(true);

        assertThrows(NoProcesableEntidadException.class,
                () -> grupoServicio.actualizarGrupo(CATEGORIA, CURSO, ANIO, ITERABLE, null));

        verify(grupoGateway, never()).actualizarGrupo(any(), any(), any(), any(), any());
    }

    // ──────────────────────────────────────────────────────────────────────────
    // eliminarGrupo
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void eliminarGrupo_exitoso_delegaAlGateway() {
        when(grupoGateway.existeGrupo(CATEGORIA, CURSO, ANIO, ITERABLE)).thenReturn(true);
        when(grupoGateway.eliminarGrupo(CATEGORIA, CURSO, ANIO, ITERABLE)).thenReturn(grupoModelo());

        GrupoDto resultado = grupoServicio.eliminarGrupo(CATEGORIA, CURSO, ANIO, ITERABLE);

        assertNotNull(resultado);
        verify(grupoGateway).eliminarGrupo(CATEGORIA, CURSO, ANIO, ITERABLE);
    }

    @Test
    void eliminarGrupo_noExiste_lanzaNoExisteExcepcion() {
        when(grupoGateway.existeGrupo(CATEGORIA, CURSO, ANIO, ITERABLE)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class,
                () -> grupoServicio.eliminarGrupo(CATEGORIA, CURSO, ANIO, ITERABLE));

        verify(grupoGateway, never()).eliminarGrupo(any(), any(), any(), any());
    }

    // ──────────────────────────────────────────────────────────────────────────
    // obtenerGrupo
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void obtenerGrupo_exitoso_retornaDtoMapeado() {
        when(grupoGateway.obtenerGrupo(CATEGORIA, CURSO, ANIO, ITERABLE))
                .thenReturn(grupoModelo());

        GrupoDto resultado = grupoServicio.obtenerGrupo(CATEGORIA, CURSO, ANIO, ITERABLE);

        assertNotNull(resultado);
        assertEquals(CATEGORIA, resultado.getCategoria());
    }

    @Test
    void obtenerGrupo_retornaNullSiGatewayDevuelveNull_comportamientoActual() {
        // COMPORTAMIENTO CUESTIONABLE: obtenerGrupo no protege contra resultado null
        // del gateway (a diferencia de obtenerGrupoPorId que lanza NoExisteExcepcion).
        // GrupoDto.fabricarDeModelo(null) captura la NPE y retorna null silenciosamente.
        when(grupoGateway.obtenerGrupo(CATEGORIA, CURSO, ANIO, ITERABLE))
                .thenReturn(null);

        GrupoDto resultado = grupoServicio.obtenerGrupo(CATEGORIA, CURSO, ANIO, ITERABLE);

        assertNull(resultado,
                "obtenerGrupo retorna null cuando el gateway devuelve null (inconsistente con obtenerGrupoPorId)");
    }
}
