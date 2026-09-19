package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IAsistenciaServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Asistencia;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.AtencionDto;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AtencionRestTest {

    @Mock
    private IAsistenciaServicio servicioAsistencia;

    @InjectMocks
    private AtencionRest atencionRest;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String PERF_ID = "perf123";
    private static final int CLASE_ID = 10;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(atencionRest)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Test
    void obtenerAtencionesPorClase_retornaOkConLista() throws Exception {
        AtencionDto dto = new AtencionDto(PERF_ID, CLASE_ID, true);
        when(servicioAsistencia.obtenerAtencionesPorClase(CLASE_ID)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v2/atencionesporclase")
                        .param("claseid", String.valueOf(CLASE_ID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPerfil").value(PERF_ID))
                .andExpect(jsonPath("$[0].estaAtendido").value(true));
    }

    @Test
    void obtenerAtencionesPorClase_variosElementos_retornaListaCompleta() throws Exception {
        AtencionDto dto1 = new AtencionDto(PERF_ID, CLASE_ID, true);
        AtencionDto dto2 = new AtencionDto("perf999", CLASE_ID, true);
        when(servicioAsistencia.obtenerAtencionesPorClase(CLASE_ID)).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/api/v2/atencionesporclase")
                        .param("claseid", String.valueOf(CLASE_ID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void registrarAtencionesPorClase_retornaOk() throws Exception {
        List<AtencionDto> atenciones = List.of(new AtencionDto(PERF_ID, CLASE_ID, true));
        doNothing().when(servicioAsistencia).registrarAtencionesPorClase(anyList(), eq(CLASE_ID));

        mockMvc.perform(post("/api/v2/atenciones")
                        .param("idClase", String.valueOf(CLASE_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atenciones)))
                .andExpect(status().isOk());

        verify(servicioAsistencia).registrarAtencionesPorClase(anyList(), eq(CLASE_ID));
    }

    @Test
    void eliminarAsistencia_retornaOkConAtencionDtoCorrect() throws Exception {
        Asistencia asistenciaActiva = new Asistencia(PERF_ID, CLASE_ID, 0);
        when(servicioAsistencia.eliminarAsistencia(PERF_ID, (long) CLASE_ID)).thenReturn(asistenciaActiva);

        mockMvc.perform(delete("/api/v2/asistencia")
                        .param("prmPerfId", PERF_ID)
                        .param("prmClsCodigo", String.valueOf(CLASE_ID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPerfil").value(PERF_ID))
                .andExpect(jsonPath("$.estaAtendido").value(true));
    }
}
