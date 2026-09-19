package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ImagenEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IImagenRepositorio;
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
class ImagenGatewayTest {

    @Mock
    private IImagenRepositorio repoImagen;

    @InjectMocks
    private ImagenGateway imagenGateway;

    private static final Integer ID = 1;
    private static final byte[] DATOS = {10, 20, 30};

    private ImagenEntidad entidadBase() {
        ImagenEntidad e = new ImagenEntidad();
        e.setId(ID);
        e.setNombre("foto.jpg");
        e.setTipoArchivo("image/jpeg");
        e.setLongitud((long) DATOS.length);
        e.setDatos(DATOS);
        return e;
    }

    private Imagen modeloBase() {
        return new Imagen(ID, "foto.jpg", "image/jpeg", (long) DATOS.length, DATOS);
    }

    // --- obtenerImagenes ---

    @Test
    void obtenerImagenes_retornaListaMapeada() {
        when(repoImagen.findAll()).thenReturn(List.of(entidadBase()));

        List<Imagen> resultado = imagenGateway.obtenerImagenes();

        assertEquals(1, resultado.size());
        assertEquals("foto.jpg", resultado.get(0).getNombre());
    }

    @Test
    void obtenerImagenes_listaVacia_retornaVacia() {
        when(repoImagen.findAll()).thenReturn(List.of());
        assertTrue(imagenGateway.obtenerImagenes().isEmpty());
    }

    // --- existeImagen ---

    @Test
    void existeImagen_existe_retornaTrue() {
        when(repoImagen.existsById(ID)).thenReturn(true);
        assertTrue(imagenGateway.existeImagen(ID));
    }

    @Test
    void existeImagen_noExiste_retornaFalse() {
        when(repoImagen.existsById(ID)).thenReturn(false);
        assertFalse(imagenGateway.existeImagen(ID));
    }

    // --- insertarImagen ---

    @Test
    void insertarImagen_exitoso_guardaYRetornaMapeado() {
        ImagenEntidad guardada = entidadBase();
        when(repoImagen.save(any(ImagenEntidad.class))).thenReturn(guardada);
        when(repoImagen.findById(ID)).thenReturn(Optional.of(guardada));

        Imagen resultado = imagenGateway.insertarImagen(modeloBase());

        assertNotNull(resultado);
        assertEquals("foto.jpg", resultado.getNombre());
        verify(repoImagen).save(any(ImagenEntidad.class));
    }

    @Test
    void insertarImagen_verificacionFalla_retornaNull() {
        ImagenEntidad guardada = entidadBase();
        when(repoImagen.save(any(ImagenEntidad.class))).thenReturn(guardada);
        when(repoImagen.findById(ID)).thenReturn(Optional.empty());

        assertNull(imagenGateway.insertarImagen(modeloBase()));
    }

    // --- obtenerImagen ---

    @Test
    void obtenerImagen_existe_retornaMapeado() {
        when(repoImagen.findById(ID)).thenReturn(Optional.of(entidadBase()));

        Imagen resultado = imagenGateway.obtenerImagen(ID);

        assertNotNull(resultado);
        assertEquals(ID, resultado.getId());
    }

    @Test
    void obtenerImagen_noExiste_retornaNull() {
        when(repoImagen.findById(ID)).thenReturn(Optional.empty());
        assertNull(imagenGateway.obtenerImagen(ID));
    }

    // --- eliminarImagen ---

    @Test
    void eliminarImagen_existe_eliminaYRetorna() {
        when(repoImagen.findById(ID)).thenReturn(Optional.of(entidadBase()));
        doNothing().when(repoImagen).deleteById(ID);
        when(repoImagen.existsById(ID)).thenReturn(false);

        Imagen resultado = imagenGateway.eliminarImagen(ID);

        assertNotNull(resultado);
        assertEquals("foto.jpg", resultado.getNombre());
        verify(repoImagen).deleteById(ID);
    }

    @Test
    void eliminarImagen_noExiste_retornaNull() {
        when(repoImagen.findById(ID)).thenReturn(Optional.empty());
        assertNull(imagenGateway.eliminarImagen(ID));
    }
}
