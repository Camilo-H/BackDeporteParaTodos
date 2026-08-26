package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IAlumnoGateway;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IPerfilGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Alumno;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.AlumnoDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlumnoServicioTest {

    @Mock private IAlumnoGateway alumnoGateway;
    @Mock private IPerfilGateway perfilGateway;
    @InjectMocks private AlumnoServicio alumnoServicio;

    private static final String  ID       = "12345678";
    private static final String  CATEGORIA = "Recreativo";
    private static final String  CURSO    = "Natacion";
    private static final Integer ANIO     = 2025;
    private static final Integer ITERABLE = 1;

    private Alumno alumnoModelo() {
        Perfil perfil = new Perfil();
        perfil.setId(ID);
        perfil.setNombre("Juan Test");
        perfil.setCorreo("juan@unicauca.edu.co");

        Alumno alumno = new Alumno();
        alumno.setPerfil(perfil);
        alumno.setAlm_codigo("2025-REC-001");
        alumno.setTipoAlumno("Estudiante");
        return alumno;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // obtenerAlumnos
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void obtenerAlumnos_listaNoVacia_retornaLista() {
        when(alumnoGateway.obtenerAlumnos()).thenReturn(List.of(alumnoModelo()));

        List<Alumno> resultado = alumnoServicio.obtenerAlumnos();

        assertEquals(1, resultado.size());
        verify(alumnoGateway).obtenerAlumnos();
    }

    @Test
    void obtenerAlumnos_listaVacia_lanzaListadoVacioExcepcion() {
        when(alumnoGateway.obtenerAlumnos()).thenReturn(List.of());

        assertThrows(ListadoVacioExcepcion.class, () -> alumnoServicio.obtenerAlumnos());
    }

    // ──────────────────────────────────────────────────────────────────────────
    // obtenerAlumnosGrupo
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void obtenerAlumnosGrupo_listaNoVacia_retornaDtosMapeados() {
        // NOTA: AlumnoDto.fabricarDeModelo puede retornar null si el modelo es invalido,
        // y el servicio lo agregaria a la lista sin null-check (comportamiento B).
        // Con modelos validos el mapeo funciona correctamente.
        when(alumnoGateway.obtenerAlumnosGrupo(CATEGORIA, CURSO, ANIO, ITERABLE))
                .thenReturn(List.of(alumnoModelo()));

        List<AlumnoDto> resultado = alumnoServicio.obtenerAlumnosGrupo(CATEGORIA, CURSO, ANIO, ITERABLE);

        assertEquals(1, resultado.size());
        verify(alumnoGateway).obtenerAlumnosGrupo(CATEGORIA, CURSO, ANIO, ITERABLE);
    }

    @Test
    void obtenerAlumnosGrupo_listaVacia_lanzaListadoVacioExcepcion() {
        when(alumnoGateway.obtenerAlumnosGrupo(CATEGORIA, CURSO, ANIO, ITERABLE))
                .thenReturn(List.of());

        assertThrows(ListadoVacioExcepcion.class,
                () -> alumnoServicio.obtenerAlumnosGrupo(CATEGORIA, CURSO, ANIO, ITERABLE));
    }

    // ──────────────────────────────────────────────────────────────────────────
    // insertAlumno
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void insertAlumno_retornaNullSinDelegarAlGateway_comportamientoActual() {
        // COMPORTAMIENTO CUESTIONABLE: insertAlumno no delega al gateway ni lanza
        // excepcion; simplemente retorna null. No hay endpoint REST que lo exponga
        // en AlumnoRest. Codigo muerto identico al patron de insertarInstructor.
        Alumno resultado = alumnoServicio.insertAlumno(new Alumno());

        assertNull(resultado, "insertAlumno siempre retorna null sin llamar al gateway");
        verifyNoInteractions(alumnoGateway);
        verifyNoInteractions(perfilGateway);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // obtenerAlumno
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void obtenerAlumno_existente_retornaAlumno() {
        when(alumnoGateway.obtenerAlumno(ID)).thenReturn(Optional.of(alumnoModelo()));

        Alumno resultado = alumnoServicio.obtenerAlumno(ID);

        assertNotNull(resultado);
        assertEquals("2025-REC-001", resultado.getAlm_codigo());
    }

    @Test
    void obtenerAlumno_noExiste_lanzaNoExisteExcepcion() {
        when(alumnoGateway.obtenerAlumno(ID)).thenReturn(Optional.empty());

        assertThrows(NoExisteExcepcion.class, () -> alumnoServicio.obtenerAlumno(ID));
    }

    // ──────────────────────────────────────────────────────────────────────────
    // actualizarAlumno
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void actualizarAlumno_noExiste_lanzaNoExisteExcepcion() {
        when(alumnoGateway.existeAlumno(ID)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class,
                () -> alumnoServicio.actualizarAlumno(ID, alumnoModelo()));

        verify(alumnoGateway, never()).actualizarAlumno(any(), any());
        verifyNoInteractions(perfilGateway);
    }

    @Test
    void actualizarAlumno_existente_actualizaPerfilYAlumno() {
        // Verifica que el servicio coordina ambos gateways: primero actualiza el perfil
        // (nombre/correo en tbl_perfil via perfilGateway) y luego el alumno (tipo en
        // tbl_alumno via alumnoGateway). Ambas llamadas son requeridas para que SCRUM-66
        // PUT /alumnos/{id} funcione correctamente.
        when(alumnoGateway.existeAlumno(ID)).thenReturn(true);
        when(alumnoGateway.actualizarAlumno(eq(ID), any(Alumno.class))).thenReturn(alumnoModelo());

        Alumno resultado = alumnoServicio.actualizarAlumno(ID, alumnoModelo());

        assertNotNull(resultado);
        verify(perfilGateway).actualizarPerfil(eq(ID), any(Perfil.class));
        verify(alumnoGateway).actualizarAlumno(eq(ID), any(Alumno.class));
    }

    // ──────────────────────────────────────────────────────────────────────────
    // eliminarAlumno
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void eliminarAlumno_noExiste_lanzaNoExisteExcepcion() {
        when(alumnoGateway.existeAlumno(ID)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class, () -> alumnoServicio.eliminarAlumno(ID));

        verify(alumnoGateway, never()).eliminarAlumno(any());
    }

    @Test
    void eliminarAlumno_exitoso_delegaAlGateway() {
        when(alumnoGateway.existeAlumno(ID)).thenReturn(true);
        when(alumnoGateway.eliminarAlumno(ID)).thenReturn(alumnoModelo());

        Alumno resultado = alumnoServicio.eliminarAlumno(ID);

        assertNotNull(resultado);
        verify(alumnoGateway).eliminarAlumno(ID);
    }
}
