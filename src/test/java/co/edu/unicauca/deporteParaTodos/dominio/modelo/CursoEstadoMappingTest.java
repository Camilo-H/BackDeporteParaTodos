package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CursoEntidad;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CursoEstadoMappingTest {

    private CursoEntidad entidadBase() {
        CursoEntidad e = new CursoEntidad();
        e.setNombre("Natacion");
        e.setCategoriaCurso("Acuatico");
        e.setDescripcion("Descripcion");
        e.setDeporte("Natacion");
        e.setObjImagen(1);
        return e;
    }

    @Test
    void eliminadoCero_debeMapearseComoActivo() {
        CursoEntidad entidad = entidadBase();
        entidad.setEliminado(0);

        Curso curso = Curso.fabricarDeEntidad(entidad);

        assertEquals(EstadoCurso.ACTIVO, curso.getEstadoCurso());
    }

    @Test
    void eliminadoUno_debeMapearseComoInactivo() {
        CursoEntidad entidad = entidadBase();
        entidad.setEliminado(1);

        Curso curso = Curso.fabricarDeEntidad(entidad);

        assertEquals(EstadoCurso.INACTIVO, curso.getEstadoCurso());
    }

    @Test
    void eliminadoNulo_debeMapearseComoActivo() {
        CursoEntidad entidad = entidadBase();
        entidad.setEliminado(null);

        Curso curso = Curso.fabricarDeEntidad(entidad);

        assertEquals(EstadoCurso.ACTIVO, curso.getEstadoCurso());
    }

    @Test
    void horarioPresenteEnEntidad_debeMapearseAlModelo() {
        CursoEntidad entidad = entidadBase();
        entidad.setEliminado(0);
        entidad.setHorario("Lunes y Miércoles 7:00-9:00");

        Curso curso = Curso.fabricarDeEntidad(entidad);

        assertEquals("Lunes y Miércoles 7:00-9:00", curso.getHorario());
    }

    @Test
    void horarioNuloEnEntidad_debeMapearseComoNuloEnModelo() {
        CursoEntidad entidad = entidadBase();
        entidad.setEliminado(0);
        entidad.setHorario(null);

        Curso curso = Curso.fabricarDeEntidad(entidad);

        assertNull(curso.getHorario());
    }
}
