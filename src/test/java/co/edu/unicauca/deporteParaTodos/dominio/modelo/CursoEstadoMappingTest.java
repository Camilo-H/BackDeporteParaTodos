package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CursoEntidad;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
