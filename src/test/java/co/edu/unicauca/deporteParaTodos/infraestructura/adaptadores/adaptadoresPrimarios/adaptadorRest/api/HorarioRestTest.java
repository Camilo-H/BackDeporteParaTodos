package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IHorarioServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Horario;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.HorarioDto;
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
class HorarioRestTest {

    @Mock
    private IHorarioServicio servicio;

    @InjectMocks
    private HorarioRest horarioRest;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(horarioRest)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    private Horario horarioBase() {
        Horario h = new Horario();
        h.setId(1);
        h.setCategoria("Natación");
        h.setCurso("Natación Adultos");
        h.setAnio(2025);
        h.setIterable(1);
        h.setDia("Lunes");
        h.setHoraInicio("08:00");
        h.setHoraFin("10:00");
        h.setEscenario("Piscina");
        h.setEliminado(0);
        return h;
    }

    @Test
    void listarHorariosPorGrupo_retornaListaDto() throws Exception {
        when(servicio.listarHorariosPorGrupo("Natación", "Natación Adultos", 2025, 1))
                .thenReturn(List.of(horarioBase()));

        mockMvc.perform(get("/api/v2/horarios")
                        .param("categoria", "Natación")
                        .param("curso", "Natación Adultos")
                        .param("anio", "2025")
                        .param("iterable", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dia").value("Lunes"))
                .andExpect(jsonPath("$[0].horaInicio").value("08:00"))
                .andExpect(jsonPath("$[0].escenario").value("Piscina"));
    }

    @Test
    void obtenerHorario_retornaDto() throws Exception {
        when(servicio.obtenerHorario(1)).thenReturn(horarioBase());

        mockMvc.perform(get("/api/v2/horario").param("prmId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.dia").value("Lunes"))
                .andExpect(jsonPath("$.horaInicio").value("08:00"));
    }

    @Test
    void postHorario_retornaCreated() throws Exception {
        when(servicio.insertarHorario(any(Horario.class))).thenReturn(horarioBase());

        HorarioDto dto = new HorarioDto();
        dto.setCategoria("Natación");
        dto.setCurso("Natación Adultos");
        dto.setAnio(2025);
        dto.setIterable(1);
        dto.setDia("Lunes");
        dto.setHoraInicio("08:00");
        dto.setHoraFin("10:00");
        dto.setEscenario("Piscina");

        mockMvc.perform(post("/api/v2/horario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dia").value("Lunes"));
    }

    @Test
    void putHorario_retornaOk() throws Exception {
        when(servicio.actualizarHorario(eq(1), any(Horario.class))).thenReturn(horarioBase());

        HorarioDto dto = new HorarioDto();
        dto.setCategoria("Natación");
        dto.setCurso("Natación Adultos");
        dto.setAnio(2025);
        dto.setIterable(1);
        dto.setDia("Martes");
        dto.setHoraInicio("10:00");
        dto.setHoraFin("12:00");
        dto.setEscenario("Piscina");

        mockMvc.perform(put("/api/v2/horario")
                        .param("prmId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dia").value("Lunes"));
    }

    @Test
    void deleteHorario_retornaOk() throws Exception {
        Horario eliminado = horarioBase();
        eliminado.setEliminado(1);
        when(servicio.eliminarHorario(1)).thenReturn(eliminado);

        mockMvc.perform(delete("/api/v2/horario").param("prmId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dia").value("Lunes"));
    }
}
