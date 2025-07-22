package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICursoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICursoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.CursoDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ErrorInternoException;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoProcesableEntidadException;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.YaExisteElementoExcepcion;

@Service
public class CursoServicio implements ICursoServicio {

    @Autowired
    private ICursoGateway cursoGateway;

    /***
     * Retorna todos los cursos del sistema
     */
    @Override
    public List<CursoDto> recuperarCursos() {
        List<Curso> cursos = cursoGateway.obtenerCursos();
        List<CursoDto> dtos = new ArrayList<>();

        cursos.forEach(curso -> {
            CursoDto dto = CursoDto.fabricarDeModelo(curso);
            dtos.add(dto);
        });
        return dtos;
    }

    @Override
    public List<CursoDto> cursosDeCategoria(String categoria) {
        List<Curso> cursos = cursoGateway.obtenerCursosDeCategoria(categoria);
        List<CursoDto> dtos = new ArrayList<>();
        cursos.forEach(curso -> {
            CursoDto dto = CursoDto.fabricarDeModelo(curso);
            dtos.add(dto);
        });
        return dtos;
    }

    @Override
    public CursoDto obtenerCurso(String titulo, String nombre) {
        if(!cursoGateway.existeCurso(titulo, nombre)){
            throw new NoExisteExcepcion();
        }
        Curso respuesta = cursoGateway.obtenerCurso(titulo, nombre);
        if(respuesta==null){
            throw new ErrorInternoException();
        }
        CursoDto dto = CursoDto.fabricarDeModelo(respuesta);
        return dto;
    }

    @Override
    public CursoDto insertarCurso(CursoDto datosCurso) {
        if(cursoGateway.existeCurso(datosCurso.getCategoriaCurso(), datosCurso.getNombre())){
            throw new YaExisteElementoExcepcion("el curso notado con categoria "+datosCurso.getCategoriaCurso()+" y nombre "+datosCurso.getNombre()+" ya se encuentra en el sistema");
        }
        Curso curso = Curso.fabricarDeDto(datosCurso);
        if(curso==null){
            throw new NoProcesableEntidadException("No fue posible convertir de dto a modelo en entrada servicio");
        }
        Curso respuesta = cursoGateway.insertarCurso(curso);
        if(respuesta == null){
            throw new InsercionFallidaExepcion("Error en la insercion o conversion de retorno fallida, se ha respondido con nulo");
        }
        return CursoDto.fabricarDeModelo(respuesta);
    }

    @Override
    public CursoDto actualizarCurso(String categoria, String curso, CursoDto datCurso) {
        if(!cursoGateway.existeCurso(categoria, curso)){
            throw new NoExisteExcepcion("el curso notado no existe en el sistema");
        }
        Curso datos = Curso.fabricarDeDto(datCurso);
        if(curso==null){
            throw new NoProcesableEntidadException("No fue posible convertir de dto a modelo en entrada servicio");
        }
        Curso actualizado = cursoGateway.actualizarCurso(categoria, curso, datos);
        if(actualizado==null){
            throw new InsercionFallidaExepcion("Error en la insercion o conversion de retorno fallida, se ha respondido con nulo");
        }
        return CursoDto.fabricarDeModelo(actualizado);
    }

    @Override
    public CursoDto eliminarCurso(String categoria, String curso) {
        if(!cursoGateway.existeCurso(categoria, curso)){
            throw new NoExisteExcepcion("El curso a eliminar no existe");
        }
        Curso eliminado = cursoGateway.eliminarCurso(categoria, curso);
        CursoDto dto = CursoDto.fabricarDeModelo(eliminado);
        return dto;
    }

    

}
