package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IEscenarioServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Escenario;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.EscenarioDto;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EscenarioRestTest {

    @Mock
    private IEscenarioServicio servicio;

    @InjectMocks
    private EscenarioRest escenarioRest;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(escenarioRest)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    private Escenario escenarioBase() {
        Escenario e = new Escenario();
        e.setId(1);
        e.setNombre("Piscina Olímpica");
        e.setDescripcion("Natación competitiva.");
        e.setNumTribunas(4);
        e.setDisponible(true);
        e.setEliminado(0);
        return e;
    }

    @Test
    void listarEscenarios_retornaListaDto() throws Exception {
        when(servicio.listarEscenarios()).thenReturn(List.of(escenarioBase()));

        mockMvc.perform(get("/api/v2/escenarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Piscina Olímpica"))
                .andExpect(jsonPath("$[0].numTribunas").value(4))
                .andExpect(jsonPath("$[0].disponible").value(true));
    }

    @Test
    void obtenerEscenario_retornaDto() throws Exception {
        when(servicio.obtenerEscenario(1)).thenReturn(escenarioBase());

        mockMvc.perform(get("/api/v2/escenario").param("prmId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Piscina Olímpica"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void postEscenario_retornaCreated() throws Exception {
        when(servicio.insertarEscenario(any(Escenario.class))).thenReturn(escenarioBase());

        EscenarioDto dto = new EscenarioDto();
        dto.setNombre("Piscina Olímpica");
        dto.setDescripcion("Natación competitiva.");
        dto.setNumTribunas(4);
        dto.setDisponible(true);

        mockMvc.perform(post("/api/v2/escenario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Piscina Olímpica"));
    }

    @Test
    void putEscenario_retornaOk() throws Exception {
        when(servicio.actualizarEscenario(eq(1), any(Escenario.class))).thenReturn(escenarioBase());

        EscenarioDto dto = new EscenarioDto();
        dto.setNombre("Piscina Olímpica");
        dto.setDescripcion("Actualizado.");
        dto.setNumTribunas(6);
        dto.setDisponible(true);

        mockMvc.perform(put("/api/v2/escenario")
                        .param("prmId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Piscina Olímpica"));
    }

    @Test
    void deleteEscenario_retornaOk() throws Exception {
        Escenario eliminado = escenarioBase();
        eliminado.setEliminado(1);
        when(servicio.eliminarEscenario(1)).thenReturn(eliminado);

        mockMvc.perform(delete("/api/v2/escenario").param("prmId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Piscina Olímpica"));
    }
}
