package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Grupo;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.GrupoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.CursoId;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.GrupoId;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICategoriaCursoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICursoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IGrupoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IImagenRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IInstructorRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GrupoGatewayTest {

    @Mock private IGrupoRepositorio repoGrupo;
    @Mock private IImagenRepositorio repoImagen;
    @Mock private ICursoRepositorio repoCurso;
    @Mock private ICategoriaCursoRepositorio repoCategoria;
    @Mock private IInstructorRepositorio repoInstructor;

    @InjectMocks
    private GrupoGateway grupoGateway;

    private static final String CATEGORIA = "Recreativo";
    private static final String CURSO = "Natacion";

    private Grupo grupoBase(LocalDate fechaApertura, int periodo) {
        Grupo g = new Grupo();
        g.setCategoria(CATEGORIA);
        g.setCurso(CURSO);
        g.setImagenGrupo(1);
        g.setCupos(20);
        g.setIdInstructor(null);
        g.setFechaInscripcionApertura(fechaApertura);
        g.setPeriodo(periodo);
        return g;
    }

    private void mockDependencias() {
        when(repoCategoria.existsById(CATEGORIA)).thenReturn(true);
        when(repoCurso.existsById(any(CursoId.class))).thenReturn(true);
        when(repoGrupo.countByCategoriaAndCursoAndAnio(anyString(), anyString(), anyInt())).thenReturn(0);
        when(repoGrupo.existsById(any(GrupoId.class))).thenReturn(false);
        when(repoImagen.existsById(any())).thenReturn(true);
        when(repoGrupo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    void insertarGrupo_calculaPeriodo1_mesesEnero_a_Junio() {
        mockDependencias();
        Grupo grupo = grupoBase(LocalDate.of(2025, 6, 1), 0);

        grupoGateway.insertarGrupo(grupo);

        ArgumentCaptor<GrupoEntidad> captor = ArgumentCaptor.forClass(GrupoEntidad.class);
        verify(repoGrupo).save(captor.capture());
        assertEquals(1, captor.getValue().getPeriodo());
        assertEquals(2025, captor.getValue().getAnio());
    }

    @Test
    void insertarGrupo_calculaPeriodo2_mesesJulio_a_Diciembre() {
        mockDependencias();
        Grupo grupo = grupoBase(LocalDate.of(2025, 7, 15), 0);

        grupoGateway.insertarGrupo(grupo);

        ArgumentCaptor<GrupoEntidad> captor = ArgumentCaptor.forClass(GrupoEntidad.class);
        verify(repoGrupo).save(captor.capture());
        assertEquals(2, captor.getValue().getPeriodo());
        assertEquals(2025, captor.getValue().getAnio());
    }

    @Test
    void insertarGrupo_sinFechaApertura_derivaAnioYPeriodoDeFechaActual() {
        mockDependencias();
        Grupo grupo = grupoBase(null, 0);
        LocalDate hoy = LocalDate.now();
        int periodoEsperado = hoy.getMonthValue() >= 7 ? 2 : 1;

        grupoGateway.insertarGrupo(grupo);

        ArgumentCaptor<GrupoEntidad> captor = ArgumentCaptor.forClass(GrupoEntidad.class);
        verify(repoGrupo).save(captor.capture());
        assertEquals(periodoEsperado, captor.getValue().getPeriodo());
        assertEquals(hoy.getYear(), captor.getValue().getAnio());
    }

    @Test
    void insertarGrupo_periodoExplicito_noSeRecalcula() {
        mockDependencias();
        // mes 3 calcularía periodo 1, pero el DTO trae periodo=2 explícito
        Grupo grupo = grupoBase(LocalDate.of(2025, 3, 1), 2);

        grupoGateway.insertarGrupo(grupo);

        ArgumentCaptor<GrupoEntidad> captor = ArgumentCaptor.forClass(GrupoEntidad.class);
        verify(repoGrupo).save(captor.capture());
        assertEquals(2, captor.getValue().getPeriodo());
    }
}
