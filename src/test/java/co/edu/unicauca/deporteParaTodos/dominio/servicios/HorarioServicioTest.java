package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IHorarioGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Horario;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.HorarioDto;
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
class HorarioServicioTest {

    @Mock
    private IHorarioGateway horarioGateway;

    @InjectMocks
    private HorarioServicio horarioServicio;

    private static final Integer ID = 1;
    private static final String CAT = "Natación";
    private static final String CUR = "Natación Adultos";
    private static final int ANIO = 2025;
    private static final int ITERABLE = 1;

    private Horario horarioActivo() {
        Horario h = new Horario();
        h.setId(ID);
        h.setCategoria(CAT);
        h.setCurso(CUR);
        h.setAnio(ANIO);
        h.setIterable(ITERABLE);
        h.setDia("Lunes");
        h.setHoraInicio("08:00");
        h.setHoraFin("10:00");
        h.setEscenario("Piscina");
        h.setEliminado(0);
        return h;
    }

    private Horario horarioEliminado() {
        Horario h = horarioActivo();
        h.setEliminado(1);
        return h;
    }

    private HorarioDto dtoBase() {
        HorarioDto dto = new HorarioDto();
        dto.setCategoria(CAT);
        dto.setCurso(CUR);
        dto.setAnio(ANIO);
        dto.setIterable(ITERABLE);
        dto.setDia("Lunes");
        dto.setHoraInicio("08:00");
        dto.setHoraFin("10:00");
        dto.setEscenario("Piscina");
        return dto;
    }

    // --- listarHorariosPorGrupo ---

    @Test
    void listarHorariosPorGrupo_exitoso_retornaListaDtos() {
        when(horarioGateway.listarHorariosPorGrupo(CAT, CUR, ANIO, ITERABLE))
                .thenReturn(List.of(horarioActivo()));

        List<HorarioDto> resultado = horarioServicio.listarHorariosPorGrupo(CAT, CUR, ANIO, ITERABLE);

        assertEquals(1, resultado.size());
        assertEquals("Lunes", resultado.get(0).getDia());
        verify(horarioGateway).listarHorariosPorGrupo(CAT, CUR, ANIO, ITERABLE);
    }

    @Test
    void listarHorariosPorGrupo_sinRegistros_lanzaListadoVacioExcepcion() {
        when(horarioGateway.listarHorariosPorGrupo(CAT, CUR, ANIO, ITERABLE))
                .thenReturn(Collections.emptyList());

        assertThrows(ListadoVacioExcepcion.class,
                () -> horarioServicio.listarHorariosPorGrupo(CAT, CUR, ANIO, ITERABLE));
    }

    // --- obtenerHorario ---

    @Test
    void obtenerHorario_exitoso_retornaDto() {
        when(horarioGateway.existeHorario(ID)).thenReturn(true);
        when(horarioGateway.obtenerHorario(ID)).thenReturn(horarioActivo());

        HorarioDto resultado = horarioServicio.obtenerHorario(ID);

        assertNotNull(resultado);
        assertEquals(ID, resultado.getId());
        assertEquals("Lunes", resultado.getDia());
    }

    @Test
    void obtenerHorario_noExiste_lanzaNoExisteExcepcion() {
        when(horarioGateway.existeHorario(ID)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class, () -> horarioServicio.obtenerHorario(ID));

        verify(horarioGateway, never()).obtenerHorario(any());
    }

    // --- insertarHorario ---

    @Test
    void insertarHorario_exitoso_retornaDto() {
        when(horarioGateway.insertarHorario(any())).thenReturn(horarioActivo());

        HorarioDto resultado = horarioServicio.insertarHorario(dtoBase());

        assertNotNull(resultado);
        assertEquals("Lunes", resultado.getDia());
        verify(horarioGateway).insertarHorario(any());
    }

    // --- actualizarHorario ---

    @Test
    void actualizarHorario_exitoso_retornaDto() {
        when(horarioGateway.existeHorario(ID)).thenReturn(true);
        when(horarioGateway.actualizarHorario(eq(ID), any())).thenReturn(horarioActivo());

        HorarioDto resultado = horarioServicio.actualizarHorario(ID, dtoBase());

        assertNotNull(resultado);
        assertEquals(ID, resultado.getId());
        verify(horarioGateway).actualizarHorario(eq(ID), any());
    }

    @Test
    void actualizarHorario_noExiste_lanzaNoExisteExcepcion() {
        when(horarioGateway.existeHorario(ID)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class, () -> horarioServicio.actualizarHorario(ID, dtoBase()));

        verify(horarioGateway, never()).actualizarHorario(any(), any());
    }

    // --- eliminarHorario ---

    @Test
    void eliminarHorario_exitoso_retornaDto() {
        when(horarioGateway.existeHorario(ID)).thenReturn(true);
        when(horarioGateway.obtenerHorario(ID)).thenReturn(horarioActivo());
        when(horarioGateway.eliminarHorario(ID)).thenReturn(horarioEliminado());

        HorarioDto resultado = horarioServicio.eliminarHorario(ID);

        assertNotNull(resultado);
        verify(horarioGateway).eliminarHorario(ID);
    }

    @Test
    void eliminarHorario_noExiste_lanzaNoExisteExcepcion() {
        when(horarioGateway.existeHorario(ID)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class, () -> horarioServicio.eliminarHorario(ID));

        verify(horarioGateway, never()).eliminarHorario(any());
    }

    @Test
    void eliminarHorario_yaEliminado_lanzaYaExisteElementoExcepcion() {
        when(horarioGateway.existeHorario(ID)).thenReturn(true);
        when(horarioGateway.obtenerHorario(ID)).thenReturn(horarioEliminado());

        assertThrows(YaExisteElementoExcepcion.class, () -> horarioServicio.eliminarHorario(ID));

        verify(horarioGateway, never()).eliminarHorario(any());
    }
}
