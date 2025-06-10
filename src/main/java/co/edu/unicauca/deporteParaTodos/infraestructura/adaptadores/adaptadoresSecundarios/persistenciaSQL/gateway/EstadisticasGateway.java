package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IEstadisticasGateway;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.EstadisticaDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.GrupoId;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IAlumnoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IAsistenciaRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICategoriaCursoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICursoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IGrupoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IInstructorRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ErrorInternoException;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class EstadisticasGateway implements IEstadisticasGateway{
    @Autowired
    private IAsistenciaRepositorio repoAsistencia;

    @Autowired
    private ICategoriaCursoRepositorio repoCategoria;

    @Autowired
    private ICursoRepositorio repoCurso;

    @Autowired
    private IGrupoRepositorio repoGrupo;

    @Autowired
    private IAlumnoRepositorio repoAlumno;

    @Autowired 
    private IInstructorRepositorio repoInstructor;

    @Override
    public List<EstadisticaDto> estadisticasCategorias(LocalDate fechaInicio, LocalDate fechaFin, String categoria) {
        if(categoria!=null){
            if(!repoCategoria.existsById(categoria)){
                throw new NoExisteExcepcion("la categoria denotada como "+categoria+" no existe");
            }
        }
        List<Object[]> objetos = repoAsistencia.estadisticasCategorias(fechaInicio, fechaFin, categoria);
        List<EstadisticaDto> estadisticas = new ArrayList<>();
        objetos.forEach((objeto)->{
            EstadisticaDto estadistica = EstadisticaDto.fromObjectCategorias(objeto);
            if(estadistica == null){
                throw new ErrorInternoException();
            }
            estadisticas.add(estadistica);
        });
        return estadisticas;
    }

    @Override
    public List<EstadisticaDto> estadisticasCursos(LocalDate fechaInicio, LocalDate fechaFin, String categoria, String curso) {
        if(categoria!=null){
            if(!repoCategoria.existsById(categoria)){
                throw new NoExisteExcepcion("La categoria denotada como "+categoria+" no existe");
            }
        }
        if(curso!=null){
            if(!repoCurso.existsById(curso)){
                throw new NoExisteExcepcion("El curso denotado como "+curso+" no existe");
            }
        }
        List<Object[]> objetos = repoAsistencia.estadisticasCursos(fechaInicio, fechaFin, categoria, curso);
        List<EstadisticaDto> estadisticas = new ArrayList<>();
        objetos.forEach((objeto)->{
            EstadisticaDto estadistica = EstadisticaDto.fromObjectCursos(objeto);
            if(estadistica == null){
                throw new ErrorInternoException();
            }
            estadisticas.add(estadistica);
        });
        return estadisticas;
    }

    @Override
    public List<EstadisticaDto> estadisticasGrupos(LocalDate fechaInicio, LocalDate fechaFin, String categoria, String curso, Integer anio, Integer iterable) {
        if(categoria!=null){
            if(!repoCategoria.existsById(categoria)){
                throw new NoExisteExcepcion("La categoria denotada como "+categoria+" no existe");
            }
        }
        if(curso!=null){
            if(!repoCurso.existsById(curso)){
                throw new NoExisteExcepcion("El curso denotado como "+curso+" no existe");
            }
        }

        if(anio!=null && iterable!=null){
            GrupoId idGrupo = new GrupoId(categoria, curso, anio, iterable);
            if(!repoGrupo.existsById(idGrupo)){
                throw new NoExisteExcepcion("El grupo denotado como "+categoria+"-"+curso+"-"+anio+"-"+iterable+" no existe");
            }
        }
        
        List<Object[]> objetos = repoAsistencia.estadisticasGrupos(fechaInicio, fechaFin, categoria, curso, anio, iterable);
        List<EstadisticaDto> estadisticas = new ArrayList<>();
        objetos.forEach((objeto)->{
            EstadisticaDto estadistica = EstadisticaDto.fromObjectGrupos(objeto);
            if(estadistica == null){
                throw new ErrorInternoException();
            }
            estadisticas.add(estadistica);
        });
        return estadisticas;
    }

    @Override
    public List<EstadisticaDto> estadisticaAlumno(String alumno, LocalDate fechaInicio, LocalDate fechaFin) {
        if(alumno!=null){
            if(!repoAlumno.existsById(alumno)){
                throw new NoExisteExcepcion("El alumno identificado como: "+alumno+" no existe");
            }
        }
        List<Object[]> objetos = repoAsistencia.estadisticasAlumno(fechaInicio, fechaFin, alumno);
        List<EstadisticaDto> estadisticas = new ArrayList<>();
        objetos.forEach((objeto)->{
            EstadisticaDto estadistica = EstadisticaDto.fromObjectAlumno(objeto);
            if(estadistica == null){
                throw new ErrorInternoException();
            }
            estadisticas.add(estadistica);
        });
        return estadisticas;
    }

    @Override
    public List<EstadisticaDto> estadisticaInstructor(String instructor, LocalDate fechaInicio, LocalDate fechaFin) {
        if(instructor!=null){
            if(!repoInstructor.existsById(instructor)){
                throw new NoExisteExcepcion("El instructor identificado como: "+instructor+" no existe");
            }
        }
        List<Object[]> objetos = repoAsistencia.estadisticasInstructor(fechaInicio, fechaFin, instructor);
        List<EstadisticaDto> estadisticas = new ArrayList<>();
        objetos.forEach((objeto)->{
            EstadisticaDto estadistica = EstadisticaDto.fromObjectInstructor(objeto);
            if(estadistica == null){
                throw new ErrorInternoException();
            }
            estadisticas.add(estadistica);
        });
        return estadisticas;
    }

    @Override
    public EstadisticaDto estadisticaFacultad(String facultad, LocalDate fechaInicio, LocalDate fechaFin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'estadisticaFacultad'");
    }

    @Override
    public List<EstadisticaDto> estadisticasPrograma(String facultad, String programa, LocalDate fechaInicio,
            LocalDate fechaFin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'estadisticasPrograma'");
    }

}
