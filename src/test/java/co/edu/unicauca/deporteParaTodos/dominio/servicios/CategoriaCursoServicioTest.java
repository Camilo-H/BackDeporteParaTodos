package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICategoriaCursoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.CategoriaDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ErrorInternoException;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoConvertibleException;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
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

    private CategoriaDto categoriaDto() {
        CategoriaDto dto = new CategoriaDto();
        dto.setTitulo(TITULO);
        dto.setDescripcion("Deportes acuaticos");
        dto.setImagenId(1);
        return dto;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // recuperarCategoriasCurso
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void recuperarCategoriasCurso_listaNoVacia_retornaDtosMapeados() {
        when(categoriaCursoGateway.obtenerCategorias()).thenReturn(List.of(categoriaModelo()));

        List<CategoriaDto> resultado = categoriaCursoServicio.recuperarCategoriasCurso();

        assertEquals(1, resultado.size());
        assertEquals(TITULO, resultado.get(0).getTitulo());
        verify(categoriaCursoGateway).obtenerCategorias();
    }

    @Test
    void recuperarCategoriasCurso_listaVacia_retornaListaVaciaSinExcepcion() {
        // DISEÑO INTENCIONAL (C): el servicio documenta explicitamente que no es necesario
        // lanzar excepcion cuando la lista esta vacia. Diferente a obtenerAlumnos,
        // obtenerInstructores, etc., que si lanzan ListadoVacioExcepcion.
        when(categoriaCursoGateway.obtenerCategorias()).thenReturn(List.of());

        List<CategoriaDto> resultado = categoriaCursoServicio.recuperarCategoriasCurso();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty(),
                "recuperarCategoriasCurso retorna lista vacia sin excepcion — diseno intencional");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // insertarCategoria
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void insertarCategoria_exitosa_retornaDtoMapeado() {
        when(categoriaCursoGateway.registrarCategoria(any(Categoria.class))).thenReturn(categoriaModelo());

        CategoriaDto resultado = categoriaCursoServicio.insertarCategoria(categoriaDto());

        assertNotNull(resultado);
        assertEquals(TITULO, resultado.getTitulo());
        verify(categoriaCursoGateway).registrarCategoria(any(Categoria.class));
    }

    @Test
    void insertarCategoria_dtoNulo_lanzaNoConvertibleException() {
        // Categoria.fabricarDeDto(null) lanza NPE internamente, la captura y retorna null.
        // El servicio detecta el null y lanza NoConvertibleException antes de llamar al gateway.
        assertThrows(NoConvertibleException.class,
                () -> categoriaCursoServicio.insertarCategoria(null));

        verifyNoInteractions(categoriaCursoGateway);
    }

    @Test
    void insertarCategoria_gatewayRetornaNull_lanzaInsercionFallidaExepcion() {
        when(categoriaCursoGateway.registrarCategoria(any(Categoria.class))).thenReturn(null);

        assertThrows(InsercionFallidaExepcion.class,
                () -> categoriaCursoServicio.insertarCategoria(categoriaDto()));
    }

    // ──────────────────────────────────────────────────────────────────────────
    // obtenerCategoriaCursoPorId
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void obtenerCategoriaCursoPorId_exitosa_retornaDtoMapeado() {
        when(categoriaCursoGateway.obtenerCategoria(TITULO)).thenReturn(categoriaModelo());

        CategoriaDto resultado = categoriaCursoServicio.obtenerCategoriaCursoPorId(TITULO);

        assertNotNull(resultado);
        assertEquals(TITULO, resultado.getTitulo());
    }

    @Test
    void obtenerCategoriaCursoPorId_gatewayRetornaNull_lanzaErrorInterno_comportamientoActual() {
        // COMPORTAMIENTO CUESTIONABLE (A): si la categoria no existe el gateway retorna null,
        // la conversion a DTO falla silenciosamente, y el servicio lanza ErrorInternoException.
        // Deberia lanzar NoExisteExcepcion (como hace actualizarCategoria con existeCategoria).
        // Riesgo: el cliente recibe 500/error interno en vez de 404 cuando la categoria no existe.
        when(categoriaCursoGateway.obtenerCategoria(TITULO)).thenReturn(null);

        assertThrows(ErrorInternoException.class,
                () -> categoriaCursoServicio.obtenerCategoriaCursoPorId(TITULO),
                "obtenerCategoriaCursoPorId lanza ErrorInternoException cuando la categoria no existe " +
                "en lugar de NoExisteExcepcion");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // actualizarCategoria
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void actualizarCategoria_noExiste_lanzaNoExisteExcepcion() {
        when(categoriaCursoGateway.existeCategoria(TITULO)).thenReturn(false);

        assertThrows(NoExisteExcepcion.class,
                () -> categoriaCursoServicio.actualizarCategoria(TITULO, categoriaDto()));

        verify(categoriaCursoGateway, never()).actualizarCategoria(any(), any());
    }

    @Test
    void actualizarCategoria_datosNulos_lanzaErrorInterno() {
        // Categoria.fabricarDeDto(null) retorna null → el servicio lanza ErrorInternoException.
        when(categoriaCursoGateway.existeCategoria(TITULO)).thenReturn(true);

        assertThrows(ErrorInternoException.class,
                () -> categoriaCursoServicio.actualizarCategoria(TITULO, null));

        verify(categoriaCursoGateway, never()).actualizarCategoria(any(), any());
    }

    @Test
    void actualizarCategoria_gatewayRetornaNull_retornaNullSinExcepcion_comportamientoActual() {
        // COMPORTAMIENTO CUESTIONABLE (B): si el gateway retorna null, CategoriaDto.fabricarDeModelo(null)
        // captura la NPE y retorna null silenciosamente. El servicio retorna null al caller
        // sin lanzar excepcion. Inconsistente con insertarCategoria que si guarda contra null.
        when(categoriaCursoGateway.existeCategoria(TITULO)).thenReturn(true);
        when(categoriaCursoGateway.actualizarCategoria(eq(TITULO), any(Categoria.class))).thenReturn(null);

        CategoriaDto resultado = categoriaCursoServicio.actualizarCategoria(TITULO, categoriaDto());

        assertNull(resultado,
                "actualizarCategoria retorna null sin excepcion cuando el gateway retorna null");
    }

    @Test
    void actualizarCategoria_exitosa_retornaDtoMapeado() {
        when(categoriaCursoGateway.existeCategoria(TITULO)).thenReturn(true);
        when(categoriaCursoGateway.actualizarCategoria(eq(TITULO), any(Categoria.class)))
                .thenReturn(categoriaModelo());

        CategoriaDto resultado = categoriaCursoServicio.actualizarCategoria(TITULO, categoriaDto());

        assertNotNull(resultado);
        assertEquals(TITULO, resultado.getTitulo());
        verify(categoriaCursoGateway).actualizarCategoria(eq(TITULO), any(Categoria.class));
    }

    // ──────────────────────────────────────────────────────────────────────────
    // eliminarCategoria
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    void eliminarCategoria_exitosa_retornaDtoConEliminadoActualizado() {
        Categoria eliminada = categoriaModelo();
        eliminada.setEliminado(1);
        when(categoriaCursoGateway.eliminarCategoria(TITULO)).thenReturn(eliminada);

        CategoriaDto resultado = categoriaCursoServicio.eliminarCategoria(TITULO);

        assertNotNull(resultado);
        assertEquals(TITULO, resultado.getTitulo());
        verify(categoriaCursoGateway).eliminarCategoria(TITULO);
    }

    @Test
    void eliminarCategoria_gatewayLanzaNoExiste_propagaSinInterceptar() {
        // El gateway es la autoridad: lanza NoExisteExcepcion (o YaExisteElementoExcepcion)
        // directamente. El servicio no duplica la guarda con existeCategoria — deja propagar.
        when(categoriaCursoGateway.eliminarCategoria(TITULO))
                .thenThrow(new NoExisteExcepcion("No existe la categoria " + TITULO));

        assertThrows(NoExisteExcepcion.class,
                () -> categoriaCursoServicio.eliminarCategoria(TITULO));
    }

    @Test
    void eliminarCategoria_gatewayRetornaNull_lanzaErrorInterno() {
        // Red de seguridad del servicio: si el gateway retorna null en vez de lanzar
        // excepcion, el if(categoria==null) captura el caso y lanza ErrorInternoException.
        when(categoriaCursoGateway.eliminarCategoria(TITULO)).thenReturn(null);

        assertThrows(ErrorInternoException.class,
                () -> categoriaCursoServicio.eliminarCategoria(TITULO));
    }
}
