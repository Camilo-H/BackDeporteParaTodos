package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IDeporteServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.DeporteDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DeporteRestTest {

    @Mock
    private IDeporteServicio servicioDeporte;

    @InjectMocks
    private DeporteRest deporteRest;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(deporteRest)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    private static final String NOMBRE = "Futbol";

    private DeporteDto dtoBase() {
        DeporteDto dto = new DeporteDto();
        dto.setNombre(NOMBRE);
        return dto;
    }

    @Test
    void obtenerDeportes_retornaOkConListaDeDeportes() throws Exception {
        when(servicioDeporte.listaDeportes()).thenReturn(List.of(dtoBase()));

        mockMvc.perform(get("/api/v2/deportes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value(NOMBRE));
    }

    @Test
    void obtenerDeportes_listaConMultiplesDeportes_retornaOk() throws Exception {
        DeporteDto segundo = new DeporteDto();
        segundo.setNombre("Natacion");
        when(servicioDeporte.listaDeportes()).thenReturn(List.of(dtoBase(), segundo));

        mockMvc.perform(get("/api/v2/deportes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void insertarDeporte_valido_retornaCreated() throws Exception {
        when(servicioDeporte.insertarDeporte(any(DeporteDto.class))).thenReturn(dtoBase());

        mockMvc.perform(post("/api/v2/deportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoBase())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value(NOMBRE));
    }

    @Test
    void insertarDeporte_retornaNombreCorrectoEnRespuesta() throws Exception {
        DeporteDto respuesta = new DeporteDto();
        respuesta.setNombre("Baloncesto");
        when(servicioDeporte.insertarDeporte(any(DeporteDto.class))).thenReturn(respuesta);

        DeporteDto entrada = new DeporteDto();
        entrada.setNombre("Baloncesto");

        mockMvc.perform(post("/api/v2/deportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Baloncesto"));
    }
}
