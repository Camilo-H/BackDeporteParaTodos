package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICategoriaCursoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaCursoServicioTest {

    @Mock
    private ICategoriaCursoGateway categoriaCursoGateway;

    @InjectMocks
    private CategoriaCursoServicio categoriaCursoServicio;

    private static final String TITULO = "Acuatico";

    private Categoria categoriaModelo() {
        Categoria c = new Categoria();
        c.setTitulo(TITULO);
        c.setDescripcion("Deportes acuaticos");
        c.setImagen(1);
        c.setEliminado(0);
        return c;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // recuperarCategoriasCurso
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void recuperarCategoriasCurso_listaNoVacia_retornaCategorias() {
        when(categoriaCursoGateway.obtenerCategorias()).thenReturn(List.of(categoriaModelo()));

        List<Categoria> resultado = categoriaCursoServicio.recuperarCategoriasCurso();

        assertEquals(1, resultado.size());
        assertEquals(TITULO, resultado.get(0).getTitulo());
        verify(categoriaCursoGateway).obtenerCategorias();
    }

    @Test
    void recuperarCategoriasCurso_listaVacia_retornaListaVaciaSinExcepcion() {
        // DISEÑO INTENCIONAL (C): el servicio documenta explicitamente que no es necesario
        // lanzar excepcion cuando la lista esta vacia.
        when(categoriaCursoGateway.obtenerCategorias()).thenReturn(List.of());

        List<Categoria> resultado = categoriaCursoServicio.recuperarCategoriasCurso();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty(),
                "recuperarCategoriasCurso retorna lista vacia sin excepcion — diseno intencional");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // insertarCategoria
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void insertarCategoria_exitosa_retornaCategoria() {
        when(categoriaCursoGateway.registrarCategoria(any(Categoria.class))).thenReturn(categoriaModelo());

        Categoria resultado = categoriaCursoServicio.insertarCategoria(categoriaModelo());

        assertNotNull(resultado);
        assertEquals(TITULO, resultado.getTitulo());
        verify(categoriaCursoGateway).registrarCategoria(any(Categoria.class));
    }

    @Test
    void insertarCategoria_gatewayRetornaNull_lanzaInsercionFallidaExepcion() {
        when(categoriaCursoGateway.registrarCategoria(any(Categoria.class))).thenReturn(null);

        assertThrows(InsercionFallidaExepcion.class,
                () -> categoriaCursoServicio.insertarCategoria(categoriaModelo()));
    }

    // ──────────────────────────────────────────────────────────────────────────
    // obtenerCategoriaCursoPorId
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void obtenerCategoriaCursoPorId_exitosa_retornaCategoria() {
        when(categoriaCursoGateway.obtenerCategoria(TITULO)).thenReturn(categoriaModelo());

        Categoria resultado = categoriaCursoServicio.obtenerCategoriaCursoPorId(TITULO);

        assertNotNull(resultado);
        assertEquals(TITULO, resultado.getTitulo());
    }

    // ──────────────────────────────────────────────────────────────────────────
    // actualizarCategoria
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void actualizarCategoria_noExiste_lanzaNoExisteExcepcion() {
        when(categoriaCursoGateway.existeCategoria(TITULO)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class,
                () -> categoriaCursoServicio.actualizarCategoria(TITULO, categoriaModelo()));

        verify(categoriaCursoGateway, never()).actualizarCategoria(any(), any());
    }

    @Test
    void actualizarCategoria_gatewayRetornaNull_retornaNullSinExcepcion_comportamientoActual() {
        // COMPORTAMIENTO CUESTIONABLE (B): si el gateway retorna null, el servicio lo
        // retorna directamente sin lanzar excepcion. Inconsistente con insertarCategoria
        // que si guarda contra null. Documentado para que no pase como accidental.
        when(categoriaCursoGateway.existeCategoria(TITULO)).thenReturn(true);
        when(categoriaCursoGateway.actualizarCategoria(eq(TITULO), any(Categoria.class))).thenReturn(null);

        Categoria resultado = categoriaCursoServicio.actualizarCategoria(TITULO, categoriaModelo());

        assertNull(resultado,
                "actualizarCategoria retorna null sin excepcion cuando el gateway retorna null");
    }

    @Test
    void actualizarCategoria_exitosa_retornaCategoria() {
        when(categoriaCursoGateway.existeCategoria(TITULO)).thenReturn(true);
        when(categoriaCursoGateway.actualizarCategoria(eq(TITULO), any(Categoria.class)))
                .thenReturn(categoriaModelo());

        Categoria resultado = categoriaCursoServicio.actualizarCategoria(TITULO, categoriaModelo());

        assertNotNull(resultado);
        assertEquals(TITULO, resultado.getTitulo());
        verify(categoriaCursoGateway).actualizarCategoria(eq(TITULO), any(Categoria.class));
    }

    // ──────────────────────────────────────────────────────────────────────────
    // eliminarCategoria
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void eliminarCategoria_exitosa_retornaCategoriaEliminada() {
        Categoria eliminada = categoriaModelo();
        eliminada.setEliminado(1);
        when(categoriaCursoGateway.eliminarCategoria(TITULO)).thenReturn(eliminada);

        Categoria resultado = categoriaCursoServicio.eliminarCategoria(TITULO);

        assertNotNull(resultado);
        assertEquals(TITULO, resultado.getTitulo());
        assertEquals(1, resultado.getEliminado());
        verify(categoriaCursoGateway).eliminarCategoria(TITULO);
    }

    @Test
    void eliminarCategoria_gatewayLanzaNoExiste_propagaSinInterceptar() {
        // El gateway es la autoridad: lanza NoExisteExcepcion directamente.
        // El servicio no duplica la guarda — deja propagar.
        when(categoriaCursoGateway.eliminarCategoria(TITULO))
                .thenThrow(new NoExisteExcepcion("No existe la categoria " + TITULO));

        assertThrows(NoExisteExcepcion.class,
                () -> categoriaCursoServicio.eliminarCategoria(TITULO));
    }
}
