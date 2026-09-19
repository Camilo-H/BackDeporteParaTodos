package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IImagenGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.ImagenDto;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;

import java.util.Base64;

@ExtendWith(MockitoExtension.class)
class ImagenServicioTest {

    @Mock
    private IImagenGateway imagenGateway;

    @InjectMocks
    private ImagenServicio servicio;

    private static final Integer ID = 1;
    private static final byte[] DATOS = {10, 20, 30};

    private Imagen modeloActivo() {
        return new Imagen(ID, "foto.jpg", "image/jpeg", (long) DATOS.length, DATOS);
    }

    private ImagenDto dtoConBase64() {
        ImagenDto dto = new ImagenDto();
        dto.setId(ID);
        dto.setNombre("foto.jpg");
        dto.setTipoArchivo("image/jpeg");
        dto.setDatosBase64(Base64.getEncoder().encodeToString(DATOS));
        return dto;
    }

    // --- obtenerImagenes ---

    @Test
    void obtenerImagenes_listaConElementos_retornaListaDtos() {
        when(imagenGateway.obtenerImagenes()).thenReturn(List.of(modeloActivo()));

        List<ImagenDto> resultado = servicio.obtenerImagenes();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("foto.jpg", resultado.get(0).getNombre());
    }

    @Test
    void obtenerImagenes_listaVacia_lanzaListadoVacioExcepcion() {
        when(imagenGateway.obtenerImagenes()).thenReturn(List.of());
        assertThrows(ListadoVacioExcepcion.class, () -> servicio.obtenerImagenes());
    }

    // --- obtenerImagen ---

    @Test
    void obtenerImagen_noExiste_lanzaNoExisteExcepcion() {
        when(imagenGateway.existeImagen(ID)).thenReturn(false);
        assertThrows(NoExisteExcepcion.class, () -> servicio.obtenerImagen(ID));
    }

    @Test
    void obtenerImagen_gatewayRetornaNulo_lanzaNoExisteExcepcion() {
        when(imagenGateway.existeImagen(ID)).thenReturn(true);
        when(imagenGateway.obtenerImagen(ID)).thenReturn(null);
        assertThrows(NoExisteExcepcion.class, () -> servicio.obtenerImagen(ID));
    }

    @Test
    void obtenerImagen_exitoso_retornaDto() {
        when(imagenGateway.existeImagen(ID)).thenReturn(true);
        when(imagenGateway.obtenerImagen(ID)).thenReturn(modeloActivo());

        ImagenDto resultado = servicio.obtenerImagen(ID);

        assertNotNull(resultado);
        assertEquals("foto.jpg", resultado.getNombre());
        assertNotNull(resultado.getDatosBase64());
    }

    // --- insertarImagen ---

    @Test
    void insertarImagen_exitoso_retornaDto() {
        when(imagenGateway.insertarImagen(any(Imagen.class))).thenReturn(modeloActivo());

        ImagenDto resultado = servicio.insertarImagen(dtoConBase64());

        assertNotNull(resultado);
        assertEquals("foto.jpg", resultado.getNombre());
        verify(imagenGateway).insertarImagen(any(Imagen.class));
    }

    @Test
    void insertarImagen_gatewayRetornaNulo_lanzaInsercionFallidaExepcion() {
        when(imagenGateway.insertarImagen(any(Imagen.class))).thenReturn(null);
        assertThrows(InsercionFallidaExepcion.class, () -> servicio.insertarImagen(dtoConBase64()));
    }

    // --- eliminarImagen ---

    @Test
    void eliminarImagen_noExiste_lanzaNoExisteExcepcion() {
        when(imagenGateway.existeImagen(ID)).thenReturn(false);
        assertThrows(NoExisteExcepcion.class, () -> servicio.eliminarImagen(ID));
    }

    @Test
    void eliminarImagen_gatewayRetornaNulo_lanzaNoExisteExcepcion() {
        when(imagenGateway.existeImagen(ID)).thenReturn(true);
        when(imagenGateway.eliminarImagen(ID)).thenReturn(null);
        assertThrows(NoExisteExcepcion.class, () -> servicio.eliminarImagen(ID));
    }

    @Test
    void eliminarImagen_exitoso_retornaDto() {
        when(imagenGateway.existeImagen(ID)).thenReturn(true);
        when(imagenGateway.eliminarImagen(ID)).thenReturn(modeloActivo());

        ImagenDto resultado = servicio.eliminarImagen(ID);

        assertNotNull(resultado);
        assertEquals("foto.jpg", resultado.getNombre());
        verify(imagenGateway).eliminarImagen(ID);
    }
}
