package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICursoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.EstadoCurso;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.EstadoInscripciones;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.CursoDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.YaExisteElementoExcepcion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CursoServicioTest {

    @Mock
    private ICursoGateway cursoGateway;

    @InjectMocks
    private CursoServicio cursoServicio;

    private static final String CATEGORIA = "Acuatico";
    private static final String NOMBRE    = "Natacion";

    @Test
    void cambiarEstadoCurso_exitoso_retornaDtoConEstadoActualizado() {
        Curso cursoActualizado = new Curso();
        cursoActualizado.setNombre(NOMBRE);
        cursoActualizado.setCategoriaCurso(CATEGORIA);
        cursoActualizado.setDescripcion("Descripcion");
        cursoActualizado.setDeporte("Natacion");
        cursoActualizado.setImagenId(1);
        cursoActualizado.setEstadoCurso(EstadoCurso.INACTIVO);

        when(cursoGateway.existeCurso(CATEGORIA, NOMBRE)).thenReturn(true);
        when(cursoGateway.cambiarEstadoCurso(CATEGORIA, NOMBRE, EstadoCurso.INACTIVO))
            .thenReturn(cursoActualizado);

        CursoDto resultado = cursoServicio.cambiarEstadoCurso(CATEGORIA, NOMBRE, EstadoCurso.INACTIVO);

        assertNotNull(resultado);
        assertEquals(EstadoCurso.INACTIVO, resultado.getEstadoCurso());
        verify(cursoGateway).cambiarEstadoCurso(CATEGORIA, NOMBRE, EstadoCurso.INACTIVO);
    }

    @Test
    void cambiarEstadoCurso_cursoNoExiste_lanzaNoExisteExcepcion() {
        when(cursoGateway.existeCurso(CATEGORIA, NOMBRE)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class,
            () -> cursoServicio.cambiarEstadoCurso(CATEGORIA, NOMBRE, EstadoCurso.INACTIVO));

        verify(cursoGateway, never()).cambiarEstadoCurso(any(), any(), any());
    }

    // --- eliminarCursoPermanente ---

    @Test
    void eliminarCursoPermanente_exitoso_retornaDtoConEstadoInactivo() {
        Curso cursoActivo = new Curso();
        cursoActivo.setNombre(NOMBRE);
        cursoActivo.setCategoriaCurso(CATEGORIA);
        cursoActivo.setDescripcion("Descripcion");
        cursoActivo.setDeporte("Natacion");
        cursoActivo.setImagenId(1);
        cursoActivo.setEstadoCurso(EstadoCurso.ACTIVO);

        Curso cursoEliminado = new Curso();
        cursoEliminado.setNombre(NOMBRE);
        cursoEliminado.setCategoriaCurso(CATEGORIA);
        cursoEliminado.setDescripcion("Descripcion");
        cursoEliminado.setDeporte("Natacion");
        cursoEliminado.setImagenId(1);
        cursoEliminado.setEstadoCurso(EstadoCurso.INACTIVO);

        when(cursoGateway.existeCurso(CATEGORIA, NOMBRE)).thenReturn(true);
        when(cursoGateway.obtenerCurso(CATEGORIA, NOMBRE)).thenReturn(cursoActivo);
        when(cursoGateway.eliminarCursoPermanente(CATEGORIA, NOMBRE)).thenReturn(cursoEliminado);

        CursoDto resultado = cursoServicio.eliminarCursoPermanente(CATEGORIA, NOMBRE);

        assertNotNull(resultado);
        assertEquals(EstadoCurso.INACTIVO, resultado.getEstadoCurso());
        verify(cursoGateway).eliminarCursoPermanente(CATEGORIA, NOMBRE);
    }

    @Test
    void eliminarCursoPermanente_cursoNoExiste_lanzaNoExisteExcepcion() {
        when(cursoGateway.existeCurso(CATEGORIA, NOMBRE)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class,
            () -> cursoServicio.eliminarCursoPermanente(CATEGORIA, NOMBRE));

        verify(cursoGateway, never()).eliminarCursoPermanente(any(), any());
    }

    @Test
    void eliminarCursoPermanente_yaEstaEliminado_lanzaYaExisteElementoExcepcion() {
        Curso cursoYaInactivo = new Curso();
        cursoYaInactivo.setNombre(NOMBRE);
        cursoYaInactivo.setCategoriaCurso(CATEGORIA);
        cursoYaInactivo.setDescripcion("Descripcion");
        cursoYaInactivo.setDeporte("Natacion");
        cursoYaInactivo.setImagenId(1);
        cursoYaInactivo.setEstadoCurso(EstadoCurso.INACTIVO);

        when(cursoGateway.existeCurso(CATEGORIA, NOMBRE)).thenReturn(true);
        when(cursoGateway.obtenerCurso(CATEGORIA, NOMBRE)).thenReturn(cursoYaInactivo);

        assertThrows(YaExisteElementoExcepcion.class,
            () -> cursoServicio.eliminarCursoPermanente(CATEGORIA, NOMBRE));

        verify(cursoGateway, never()).eliminarCursoPermanente(any(), any());
    }

    // --- cursosDeCategoria (regresión) ---

    @Test
    void cursosDeCategoria_sigueExcluyendoEliminados() {
        Curso cursoActivo = new Curso();
        cursoActivo.setNombre("Natacion");
        cursoActivo.setCategoriaCurso(CATEGORIA);
        cursoActivo.setDescripcion("Desc");
        cursoActivo.setDeporte("Natacion");
        cursoActivo.setImagenId(1);
        cursoActivo.setEstadoCurso(EstadoCurso.ACTIVO);

        when(cursoGateway.obtenerCursosDeCategoria(CATEGORIA))
            .thenReturn(java.util.List.of(cursoActivo));

        java.util.List<CursoDto> resultado = cursoServicio.cursosDeCategoria(CATEGORIA);

        assertEquals(1, resultado.size());
        assertTrue(resultado.stream().allMatch(d -> EstadoCurso.ACTIVO.equals(d.getEstadoCurso())),
            "cursosDeCategoria no debe retornar cursos INACTIVOS");
        verify(cursoGateway).obtenerCursosDeCategoria(CATEGORIA);
        verify(cursoGateway, never()).obtenerTodosCursosDeCategoria(any());
    }

    // --- todosLosCursosDeCategoria ---

    @Test
    void todosLosCursosDeCategoria_incluyeCursosEliminados() {
        Curso cursoActivo = new Curso();
        cursoActivo.setNombre("Natacion");
        cursoActivo.setCategoriaCurso(CATEGORIA);
        cursoActivo.setDescripcion("Desc");
        cursoActivo.setDeporte("Natacion");
        cursoActivo.setImagenId(1);
        cursoActivo.setEstadoCurso(EstadoCurso.ACTIVO);

        Curso cursoInactivo = new Curso();
        cursoInactivo.setNombre("Futbol");
        cursoInactivo.setCategoriaCurso(CATEGORIA);
        cursoInactivo.setDescripcion("Desc");
        cursoInactivo.setDeporte("Futbol");
        cursoInactivo.setImagenId(1);
        cursoInactivo.setEstadoCurso(EstadoCurso.INACTIVO);

        when(cursoGateway.obtenerTodosCursosDeCategoria(CATEGORIA))
            .thenReturn(java.util.List.of(cursoActivo, cursoInactivo));

        java.util.List<CursoDto> resultado = cursoServicio.todosLosCursosDeCategoria(CATEGORIA);

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().anyMatch(d -> EstadoCurso.INACTIVO.equals(d.getEstadoCurso())),
            "Debe incluir al menos un curso con estadoCurso INACTIVO");
        verify(cursoGateway).obtenerTodosCursosDeCategoria(CATEGORIA);
        verify(cursoGateway, never()).obtenerCursosDeCategoria(any());
    }

    // --- cambiarEstadoInscripciones ---

    @Test
    void cambiarEstadoInscripciones_exitoso_retornaDtoConNuevoEstado() {
        Curso cursoCerrado = new Curso();
        cursoCerrado.setNombre(NOMBRE);
        cursoCerrado.setCategoriaCurso(CATEGORIA);
        cursoCerrado.setDescripcion("Descripcion");
        cursoCerrado.setDeporte("Natacion");
        cursoCerrado.setImagenId(1);
        cursoCerrado.setEstadoCurso(EstadoCurso.ACTIVO);
        cursoCerrado.setEstadoInscripciones(EstadoInscripciones.CERRADO);

        when(cursoGateway.existeCurso(CATEGORIA, NOMBRE)).thenReturn(true);
        when(cursoGateway.cambiarEstadoInscripciones(CATEGORIA, NOMBRE, EstadoInscripciones.CERRADO))
            .thenReturn(cursoCerrado);

        CursoDto resultado = cursoServicio.cambiarEstadoInscripciones(CATEGORIA, NOMBRE, EstadoInscripciones.CERRADO);

        assertNotNull(resultado);
        assertEquals(EstadoInscripciones.CERRADO, resultado.getEstadoInscripciones());
        verify(cursoGateway).cambiarEstadoInscripciones(CATEGORIA, NOMBRE, EstadoInscripciones.CERRADO);
    }

    @Test
    void cambiarEstadoInscripciones_cursoNoExiste_lanzaNoExisteExcepcion() {
        when(cursoGateway.existeCurso(CATEGORIA, NOMBRE)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class,
            () -> cursoServicio.cambiarEstadoInscripciones(CATEGORIA, NOMBRE, EstadoInscripciones.CERRADO));

        verify(cursoGateway, never()).cambiarEstadoInscripciones(any(), any(), any());
    }
}
