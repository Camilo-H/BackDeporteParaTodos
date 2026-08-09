package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CategoriaCursoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICategoriaCursoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IImagenRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.YaExisteElementoExcepcion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaGatewayTest {

    @Mock
    private ICategoriaCursoRepositorio repoCategoria;

    @Mock
    private IImagenRepositorio repoImagen;

    @InjectMocks
    private CategoriaGateway categoriaGateway;

    private static final String TITULO = "Natación";

    private CategoriaCursoEntidad entidadActiva() {
        CategoriaCursoEntidad e = new CategoriaCursoEntidad();
        e.setTitulo(TITULO);
        e.setDescripcion("Deportes acuáticos");
        e.setCat_imagen(1);
        e.setEliminado(0);
        return e;
    }

    private CategoriaCursoEntidad entidadEliminada() {
        CategoriaCursoEntidad e = entidadActiva();
        e.setEliminado(1);
        return e;
    }

    @Test
    void eliminarCategoria_exitoso_marcaEliminadoYNoBorraFisicamente() {
        when(repoCategoria.findById(TITULO)).thenReturn(Optional.of(entidadActiva()));
        when(repoCategoria.marcarComoEliminado(TITULO)).thenReturn(1);

        categoriaGateway.eliminarCategoria(TITULO);

        verify(repoCategoria).marcarComoEliminado(TITULO);
        verify(repoCategoria, never()).deleteById(any());
        verify(repoImagen, never()).deleteById(any());
    }

    @Test
    void eliminarCategoria_noExiste_lanzaNoExisteExcepcion() {
        when(repoCategoria.findById(TITULO)).thenReturn(Optional.empty());

        assertThrows(NoExisteExcepcion.class, () -> categoriaGateway.eliminarCategoria(TITULO));

        verify(repoCategoria, never()).marcarComoEliminado(any());
    }

    @Test
    void eliminarCategoria_yaEliminada_lanzaYaExisteElementoExcepcion() {
        when(repoCategoria.findById(TITULO)).thenReturn(Optional.of(entidadEliminada()));

        assertThrows(YaExisteElementoExcepcion.class, () -> categoriaGateway.eliminarCategoria(TITULO));

        verify(repoCategoria, never()).marcarComoEliminado(any());
    }

    @Test
    void eliminarCategoria_respuestaReflejaEliminadoTrue() {
        when(repoCategoria.findById(TITULO)).thenReturn(Optional.of(entidadActiva()));
        when(repoCategoria.marcarComoEliminado(TITULO)).thenReturn(1);

        Categoria resultado = categoriaGateway.eliminarCategoria(TITULO);

        assertNotNull(resultado);
        assertEquals(1, resultado.getEliminado());
        assertEquals(TITULO, resultado.getTitulo());
    }
}
