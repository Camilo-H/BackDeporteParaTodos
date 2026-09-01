package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICategoriaCursoServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.CategoriaDto;
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
class CategoriaRestTest {

    @Mock
    private ICategoriaCursoServicio servicioCategoria;

    @InjectMocks
    private CategoriaRest categoriaRest;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoriaRest)
            .setMessageConverters(new MappingJackson2HttpMessageConverter())
            .build();
    }

    private Categoria categoriaBase() {
        Categoria c = new Categoria();
        c.setTitulo("Acuatico");
        c.setDescripcion("Deportes acuaticos");
        c.setImagen(1);
        c.setEliminado(0);
        return c;
    }

    @Test
    void obtenerCategoriasExistentes_retornaListaDto() throws Exception {
        when(servicioCategoria.recuperarCategoriasCurso()).thenReturn(List.of(categoriaBase()));

        mockMvc.perform(get("/api/v2/categorias2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].titulo").value("Acuatico"))
            .andExpect(jsonPath("$[0].descripcion").value("Deportes acuaticos"));
    }

    @Test
    void obtenerCategoria_retornaDto() throws Exception {
        when(servicioCategoria.obtenerCategoriaCursoPorId("Acuatico")).thenReturn(categoriaBase());

        mockMvc.perform(get("/api/v2/categoria").param("titulo", "Acuatico"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.titulo").value("Acuatico"));
    }

    @Test
    void postCategoria_retornaCreated() throws Exception {
        when(servicioCategoria.insertarCategoria(any())).thenReturn(categoriaBase());

        CategoriaDto dto = new CategoriaDto();
        dto.setTitulo("Acuatico");
        dto.setDescripcion("Deportes acuaticos");
        dto.setImagenId(1);

        mockMvc.perform(post("/api/v2/categoria")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.titulo").value("Acuatico"));
    }

    @Test
    void putCategoria_retornaOk() throws Exception {
        when(servicioCategoria.actualizarCategoria(eq("Acuatico"), any())).thenReturn(categoriaBase());

        CategoriaDto dto = new CategoriaDto();
        dto.setTitulo("Acuatico");
        dto.setDescripcion("Deportes acuaticos actualizado");
        dto.setImagenId(1);

        mockMvc.perform(put("/api/v2/categoria")
                .param("titulo", "Acuatico")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.titulo").value("Acuatico"));
    }

    @Test
    void deleteCategoria_retornaOk() throws Exception {
        Categoria eliminada = categoriaBase();
        eliminada.setEliminado(1);
        when(servicioCategoria.eliminarCategoria("Acuatico")).thenReturn(eliminada);

        mockMvc.perform(delete("/api/v2/categoria").param("titulo", "Acuatico"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.titulo").value("Acuatico"));
    }
}
