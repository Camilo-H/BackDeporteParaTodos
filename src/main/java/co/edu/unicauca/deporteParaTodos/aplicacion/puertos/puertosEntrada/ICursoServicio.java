package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.CursoDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CursoEntidad;

public interface ICursoServicio {
    public List<Curso> recuperarCursos();
    public Curso guardarCurso(Curso curso);
    public Iterable<CursoEntidad> obtenerCursos();
    public CursoEntidad insertarCurso(CursoDTO datosCurso);
    public Curso obtenerCurso(String titulo);
}
