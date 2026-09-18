package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICursoGateway;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IGrupoGateway;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IInscripcionGateway;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.CuposAgotadosExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.InscripcionesCerradasExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.LimiteCursosExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.EstadoInscripciones;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Grupo;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;

@ExtendWith(MockitoExtension.class)
class InscripcionServicioTest {

    @Mock
    private IInscripcionGateway gateway;

    @Mock
    private ICursoGateway cursoGateway;

    @Mock
    private IGrupoGateway grupoGateway;

    @InjectMocks
    private InscripcionServicio servicio;

    private Inscripcion datos;
    private Curso cursoAbierto;
    private Grupo grupoCon5Cupos;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(servicio, "limiteCursosAlumno", 3);
        datos = new Inscripcion("alum1", "cat1", "cur1", 2026, 1, null, null);
        cursoAbierto = new Curso();
        cursoAbierto.setEstadoInscripciones(EstadoInscripciones.ABIERTO);
        grupoCon5Cupos = new Grupo();
        grupoCon5Cupos.setCupos(5);
    }

    @Test
    void inscribir_conInscripcionesCerradas_lanzaExcepcion() {
        Curso cursoCerrado = new Curso();
        cursoCerrado.setEstadoInscripciones(EstadoInscripciones.CERRADO);
        when(cursoGateway.obtenerCurso("cat1", "cur1")).thenReturn(cursoCerrado);

        assertThrows(InscripcionesCerradasExcepcion.class, () -> servicio.inscribir(datos));
        verify(grupoGateway, never()).obtenerGrupoConLock(any(), any(), anyInt(), anyInt());
    }

    @Test
    void inscribir_conCuposNulos_lanzaCuposAgotadosExcepcion() {
        Grupo grupoSinCupos = new Grupo();
        grupoSinCupos.setCupos(null);
        when(cursoGateway.obtenerCurso("cat1", "cur1")).thenReturn(cursoAbierto);
        when(grupoGateway.obtenerGrupoConLock("cat1", "cur1", 2026, 1)).thenReturn(grupoSinCupos);

        assertThrows(CuposAgotadosExcepcion.class, () -> servicio.inscribir(datos));
        verify(gateway, never()).guardarInscripcion(any());
    }

    @Test
    void inscribir_conCuposAgotados_lanzaCuposAgotadosExcepcion() {
        when(cursoGateway.obtenerCurso("cat1", "cur1")).thenReturn(cursoAbierto);
        when(grupoGateway.obtenerGrupoConLock("cat1", "cur1", 2026, 1)).thenReturn(grupoCon5Cupos);
        when(gateway.contarInscripcionesActivasGrupo("cat1", "cur1", 2026, 1)).thenReturn(5L);

        assertThrows(CuposAgotadosExcepcion.class, () -> servicio.inscribir(datos));
        verify(gateway, never()).guardarInscripcion(any());
    }

    @Test
    void inscribir_conLimiteAlcanzado_lanzaLimiteCursosExcepcion() {
        when(cursoGateway.obtenerCurso("cat1", "cur1")).thenReturn(cursoAbierto);
        when(grupoGateway.obtenerGrupoConLock("cat1", "cur1", 2026, 1)).thenReturn(grupoCon5Cupos);
        when(gateway.contarInscripcionesActivasGrupo("cat1", "cur1", 2026, 1)).thenReturn(2L);
        when(gateway.contarCursosActivosAlumno("alum1")).thenReturn(3L);

        assertThrows(LimiteCursosExcepcion.class, () -> servicio.inscribir(datos));
        verify(gateway, never()).guardarInscripcion(any());
    }

    @Test
    void inscribir_nueva_exitosa_guarda_con_fechaInscripcion() {
        Inscripcion guardada = new Inscripcion("alum1", "cat1", "cur1", 2026, 1,
                Timestamp.from(Instant.now()), null);
        when(cursoGateway.obtenerCurso("cat1", "cur1")).thenReturn(cursoAbierto);
        when(grupoGateway.obtenerGrupoConLock("cat1", "cur1", 2026, 1)).thenReturn(grupoCon5Cupos);
        when(gateway.contarInscripcionesActivasGrupo("cat1", "cur1", 2026, 1)).thenReturn(2L);
        when(gateway.contarCursosActivosAlumno("alum1")).thenReturn(1L);
        when(gateway.existeInscripcion("alum1", "cat1", "cur1", 2026, 1)).thenReturn(false);
        when(gateway.guardarInscripcion(any())).thenReturn(guardada);

        Inscripcion resultado = servicio.inscribir(datos);

        assertNotNull(resultado);
        assertNotNull(resultado.getFechaInscripcion());
        verify(gateway).guardarInscripcion(any());
    }

    @Test
    void inscribir_existente_reactiva_fecha_desvinculacion_nula() {
        Timestamp ahora = Timestamp.from(Instant.now());
        Inscripcion existente = new Inscripcion("alum1", "cat1", "cur1", 2026, 1, ahora, ahora);
        Inscripcion reactivada = new Inscripcion("alum1", "cat1", "cur1", 2026, 1, ahora, null);
        when(cursoGateway.obtenerCurso("cat1", "cur1")).thenReturn(cursoAbierto);
        when(grupoGateway.obtenerGrupoConLock("cat1", "cur1", 2026, 1)).thenReturn(grupoCon5Cupos);
        when(gateway.contarInscripcionesActivasGrupo("cat1", "cur1", 2026, 1)).thenReturn(2L);
        when(gateway.contarCursosActivosAlumno("alum1")).thenReturn(1L);
        when(gateway.existeInscripcion("alum1", "cat1", "cur1", 2026, 1)).thenReturn(true);
        when(gateway.obtenerInscripcion("alum1", "cat1", "cur1", 2026, 1)).thenReturn(existente);
        when(gateway.guardarInscripcion(any())).thenReturn(reactivada);

        Inscripcion resultado = servicio.inscribir(datos);

        assertNull(resultado.getFechaDesvinculacion());
    }

    @Test
    void validarInscripcion_devuelve_resultado_del_gateway() {
        when(gateway.existeInscripcionActiva("alum1", "cat1", "cur1", 2026, 1)).thenReturn(true);
        assertTrue(servicio.validarInscripcion("alum1", "cat1", "cur1", 2026, 1));

        when(gateway.existeInscripcionActiva("alum1", "cat1", "cur1", 2026, 1)).thenReturn(false);
        assertFalse(servicio.validarInscripcion("alum1", "cat1", "cur1", 2026, 1));
    }

    @Test
    void desvincularInscripcion_noExiste_lanzaNoExisteExcepcion() {
        when(gateway.existeInscripcion("alum1", "cat1", "cur1", 2026, 1)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class,
                () -> servicio.desvincularInscripcion("alum1", "cat1", "cur1", 2026, 1));
        verify(gateway, never()).desvincularInscripcion(any(), any(), any(), anyInt(), anyInt());
    }
}
