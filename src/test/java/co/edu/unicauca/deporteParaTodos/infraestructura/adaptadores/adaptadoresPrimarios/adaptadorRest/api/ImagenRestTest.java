package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IImagenServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.ImagenDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Base64;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ImagenRestTest {

    @Mock
    private IImagenServicio servicio;

    @InjectMocks
    private ImagenRest imagenRest;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Integer ID = 1;
    private static final byte[] DATOS = {10, 20, 30};

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(imagenRest)
                .setMessageConverters(
                        new ByteArrayHttpMessageConverter(),
                        new MappingJackson2HttpMessageConverter())
                .build();
    }

    private ImagenDto dtoBase() {
        ImagenDto dto = new ImagenDto();
        dto.setId(ID);
        dto.setNombre("foto.jpg");
        dto.setTipoArchivo("image/jpeg");
        dto.setLongitud((long) DATOS.length);
        dto.setDatosBase64(Base64.getEncoder().encodeToString(DATOS));
        return dto;
    }

    @Test
    void postInsertImagen_retornaCreated() throws Exception {
        when(servicio.insertarImagen(any(ImagenDto.class))).thenReturn(dtoBase());

        mockMvc.perform(multipart("/api/v2/imagenMultipart")
                        .param("nombre", "foto.jpg")
                        .param("tipoArchivo", "image/jpeg"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("foto.jpg"));
    }

    @Test
    void getObtenerImagen_retornaOkConDto() throws Exception {
        when(servicio.obtenerImagen(ID)).thenReturn(dtoBase());

        mockMvc.perform(get("/api/v2/imagen").param("idImagen", String.valueOf(ID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.nombre").value("foto.jpg"));
    }

    @Test
    void getImagenStream_retornaOkConBytes() throws Exception {
        when(servicio.obtenerImagen(ID)).thenReturn(dtoBase());

        mockMvc.perform(get("/api/v2/imagenStream").param("idImagen", String.valueOf(ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("image/jpeg"));
    }

    @Test
    void getImagenes_retornaOkConLista() throws Exception {
        when(servicio.obtenerImagenes()).thenReturn(List.of(dtoBase()));

        mockMvc.perform(get("/api/v2/imagenes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("foto.jpg"));
    }
}
