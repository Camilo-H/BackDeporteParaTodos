package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Alumno;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IAlumnoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IPerfilRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlumnoGatewayTest {

    @Mock
    private IAlumnoRepositorio repoAlumno;

    @Mock
    private IPerfilRepositorio repoPerfil;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private AlumnoGateway alumnoGateway;

    // columnas: [0]=eliminadoestado [1]=id [2]=codigo [3]=tipo
    //           [4]=nombre [5]=correo [6]=sexo [7]=tipoid [8]=imagen
    private Object[] filaAlumnoActivo() {
        return new Object[]{ 0, "12345678", "2025-CIEN-001", "Estudiante",
                "Juan Test", "juan@unicauca.edu.co", "M", "CC", 1 };
    }

    // List.of(Object[]) se resuelve como List<Object> por desempaque de varargs;
    // este helper garantiza List<Object[]> sin ambigüedad.
    private static List<Object[]> unaFila(Object[] fila) {
        List<Object[]> lista = new ArrayList<>();
        lista.add(fila);
        return lista;
    }

    @Test
    void obtenerAlumnosGrupo_llamaRepoConEliminadoCero() {
        when(repoAlumno.buscarAlumnosGrupoRaw("Recreativo", "Natacion", 2025, 1, 0))
                .thenReturn(unaFila(filaAlumnoActivo()));

        alumnoGateway.obtenerAlumnosGrupo("Recreativo", "Natacion", 2025, 1);

        // Verifica que el 5º argumento (eliminado) sea siempre 0
        verify(repoAlumno).buscarAlumnosGrupoRaw("Recreativo", "Natacion", 2025, 1, 0);
        verify(repoAlumno, never()).buscarAlumnosGrupoRaw(any(), any(), any(), any(), eq(1));
    }

    @Test
    void obtenerAlumnosGrupo_mapeaRegistroAAlumnoCorrectamente() {
        when(repoAlumno.buscarAlumnosGrupoRaw("Recreativo", "Natacion", 2025, 1, 0))
                .thenReturn(unaFila(filaAlumnoActivo()));

        List<Alumno> resultado = alumnoGateway.obtenerAlumnosGrupo("Recreativo", "Natacion", 2025, 1);

        assertEquals(1, resultado.size());
        Alumno alumno = resultado.get(0);
        assertEquals("12345678", alumno.getPerfil().getId());
        assertEquals("2025-CIEN-001", alumno.getAlm_codigo());
        assertEquals("Estudiante", alumno.getTipoAlumno());
        assertEquals("Juan Test", alumno.getPerfil().getNombre());
        assertEquals("juan@unicauca.edu.co", alumno.getPerfil().getCorreo());
    }

    @Test
    void obtenerAlumnosGrupo_sinResultados_retornaListaVacia() {
        when(repoAlumno.buscarAlumnosGrupoRaw(any(), any(), any(), any(), eq(0)))
                .thenReturn(Collections.emptyList());

        List<Alumno> resultado = alumnoGateway.obtenerAlumnosGrupo("Recreativo", "Natacion", 2025, 1);

        assertTrue(resultado.isEmpty());
    }
}
