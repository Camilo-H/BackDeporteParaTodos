package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICursoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CursoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.GrupoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ImagenEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.CursoId;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICursoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class CursoGateway implements ICursoGateway{

    @Autowired
    private ICursoRepositorio repoCurso;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @Override
    public boolean existeCurso(String categoria, String nombreCurso) {
        CursoId id = new CursoId(categoria, nombreCurso);
        return repoCurso.existsById(id);
    }

    @Override
    public List<Curso> obtenerCursos() {
        Iterable<CursoEntidad> respuesta = repoCurso.findAll();
        List<Curso> cursos = new ArrayList<>();
        respuesta.forEach(entidad -> {
            Curso curso = Curso.fabricarDeEntidad(entidad);
            cursos.add(curso);
        });
        return cursos;
    }

    @Override
    public Curso obtenerCurso(String categoria, String nombreCurso) {
        CursoId id = new CursoId(categoria,nombreCurso);
        Optional<CursoEntidad> resultado = repoCurso.findById(id);
        if(resultado.isPresent()){
            CursoEntidad entidad = resultado.get();
            Curso curso = Curso.fabricarDeEntidad(entidad);
            return curso;
        }
        return null;
        
    }

    @Override
    public Curso insertarCurso(Curso curso) {
        return null;
        /*CursoEntidad entidad = mapper.map(curso, CursoEntidad.class);
        if (curso.getObjImagen() != null) {
            ImagenEntidad imagenEntidad = mapper.map(curso.getObjImagen(), ImagenEntidad.class);
            //entidad.setObjImagen(imagenEntidad);
        }
        CursoEntidad entidadGuardada = repoCurso.save(entidad);
        return mapper.map(entidadGuardada, Curso.class);*/
    }

    @Override
    public Curso actualizarCurso(Curso curso, String nombreCurso) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarCurso'");
    }

    @Override
    public Curso eliminarCurso(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Curso> obtenerCursosDeCategoria(String nombreCategoria){
        List<CursoEntidad> entidades = repoCurso.findByCategoriaCursoAndEliminado(nombreCategoria, 0);
        List<Curso> cursos = new ArrayList<>();
        entidades.forEach(entidad -> {
            Curso curso = Curso.fabricarDeEntidad(entidad);
            cursos.add(curso);
        });
        return cursos;
    } 
}
