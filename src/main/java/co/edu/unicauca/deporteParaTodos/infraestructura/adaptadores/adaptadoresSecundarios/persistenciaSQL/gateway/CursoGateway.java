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
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICursoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class CursoGateway implements ICursoGateway{

    @Autowired
    private ICursoRepositorio repoCurso;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    /***
     * verifica la existencia de un curso a partir de su nombre
     */
    @Override
    public boolean existeCurso(String nombreCurso) {
       return repoCurso.existsById(nombreCurso);
    }

    /***
     * retorna toda la lista de cursos sin restricciones
     */
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
    public Optional<Curso> obtenerCurso(String nombreCurso) {
        Optional<CursoEntidad> resultado = repoCurso.findById(nombreCurso);
        //List<GrupoEntidad> grupos = resultado.get().getGrupos();
        System.out.println(" ");
        //System.out.println("GRUPO 1 DEL CURSO "+grupos.get(0).getNombre());
        System.out.println(" ");
        return resultado.map(cursoEntidad -> mapper.map(cursoEntidad, Curso.class));
        
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
        Optional <CursoEntidad> entidadExistente = repoCurso.findById(nombre);
        if (entidadExistente.isPresent()) {
            CursoEntidad entidad = entidadExistente.get();
            repoCurso.delete(entidad);
            return mapper.map(entidad, Curso.class);
        }else{
            throw new NoExisteExcepcion("El curso con el nombre " + nombre + " no existe");
        }
    }

    @Override
    public List<Curso> obtenerCursoDeCategoria(String nombreCategoria) {
        List<CursoEntidad> entidades = repoCurso.findByCategoriaCursoAndEliminado(nombreCategoria, 0);
        List<Curso> cursos = new ArrayList<>();
        entidades.forEach(entidad ->{
            Curso curso = Curso.fabricarDeEntidad(entidad);
            cursos.add(curso);
        });
        return cursos;
    } 
}
