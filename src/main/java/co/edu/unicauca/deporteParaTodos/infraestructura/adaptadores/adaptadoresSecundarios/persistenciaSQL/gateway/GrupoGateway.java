package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IGrupoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Grupo;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.GrupoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.CursoId;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.GrupoId;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICategoriaCursoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICursoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IGrupoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IImagenRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IInstructorRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.DependenciaFallida;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.YaExisteElementoExcepcion;

@Service
public class GrupoGateway implements IGrupoGateway {

    @Autowired
    private IGrupoRepositorio repoGrupo;

    @Autowired
    private IImagenRepositorio repoImagen;

    @Autowired
    private ICursoRepositorio repoCurso;

    @Autowired
    private ICategoriaCursoRepositorio repoCategoria;

    @Autowired
    private IInstructorRepositorio repoInstructor;

   
    public boolean existeGrupo(String categoria, String curso, Integer anio, Integer secuencial){
        GrupoId id = new GrupoId(categoria, curso, anio, secuencial);
        return repoGrupo.existsById(id);
    }
    
    public List<Grupo> obtenerTodosGrupos(){
        List<GrupoEntidad> entidades = repoGrupo.findByEliminado(0);
        List<Grupo> modelos = new ArrayList<>();
        entidades.forEach(entidad ->{
            Grupo grupo = Grupo.fabricarDeEntidad(entidad);
            modelos.add(grupo);
        });
        return modelos;
    }

    public List<Grupo> obtenerGruposDisponibles(){
        List<GrupoEntidad> entidades = repoGrupo.findByEliminado(0);
        List<Grupo> modelos = new ArrayList<>();
        entidades.forEach(entidad ->{
            Grupo grupo = Grupo.fabricarDeEntidad(entidad);
            modelos.add(grupo);
        });
        return modelos;
    }

    public List<Grupo> obtenerGruposDeCurso(String categoria, String curso){
        List<GrupoEntidad> entidades = repoGrupo.findByCategoriaAndCursoAndEliminado(categoria, curso, 0);
        List<Grupo> modelos = new ArrayList<>();
        entidades.forEach(entidad ->{
            Grupo grupo = Grupo.fabricarDeEntidad(entidad);
            modelos.add(grupo);
        });
        return modelos;
    }

    public List<Grupo> obtenerGruposInscripcionDisponible(){
        List<GrupoEntidad> entidades = repoGrupo.obtenerGruposConInscripcionDisponibleNativo();
        List<Grupo> modelos = new ArrayList<>();
        entidades.forEach(entidad -> {
            Grupo grupo = Grupo.fabricarDeEntidad(entidad);
            modelos.add(grupo);
        });
        return modelos;
    }

    public List<Grupo> obtenerGruposInstructor(String idInstructor){
        List<GrupoEntidad> entidades = repoGrupo.obtenerGruposPorInstructor(idInstructor);
        List<Grupo> modelos = new ArrayList<>();
        entidades.forEach(entidad -> {
            Grupo grupo = Grupo.fabricarDeEntidad(entidad);
            modelos.add(grupo);
        });
        return modelos;
    }
    
    public Grupo insertarGrupo(Grupo datosGrupo){
        if(!repoCategoria.existsById(datosGrupo.getCategoria())){
            throw new NoExisteExcepcion("la categoria a la que intenta insertar un grupo no existe");
        }
        
        CursoId cursoId = new CursoId(datosGrupo.getCategoria(), datosGrupo.getCurso());
        if(!repoCurso.existsById(cursoId)){
            throw new NoExisteExcepcion("el curso al que intenta insertar un nuevo grupo no existe");
        }
        // fuente única: fechaInscripcionApertura si existe, si no LocalDate.now()
        LocalDate fechaRef = datosGrupo.getFechaInscripcionApertura() != null
            ? datosGrupo.getFechaInscripcionApertura()
            : LocalDate.now();
        Integer anio = fechaRef.getYear();
        datosGrupo.setAnio(anio);
        //obtengo el iterable adecuado para la insercion
        Integer iterable = repoGrupo.countByCategoriaAndCursoAndAnio(datosGrupo.getCategoria(), datosGrupo.getCurso(), anio) + 1;
        datosGrupo.setIterable(iterable);
        GrupoId id = new GrupoId(datosGrupo.getCategoria(), datosGrupo.getCurso(), anio, iterable);
        if(repoGrupo.existsById(id)){
            throw new YaExisteElementoExcepcion("el grupo especificado ya existe en el sistema");
        }
        if(!repoImagen.existsById(datosGrupo.getImagenGrupo())){
            throw new DependenciaFallida("la imagen identificado con "+datosGrupo.getImagenGrupo()+" no existe en el sistema");
        }
        if(datosGrupo.getIdInstructor()!=null){
            if(!repoInstructor.existsById(datosGrupo.getIdInstructor())){
                throw new DependenciaFallida("el instructor identificado con "+datosGrupo.getIdInstructor()+" no existe en el sistema");
            }
        }
        if (datosGrupo.getPeriodo() <= 0) {
            datosGrupo.setPeriodo(fechaRef.getMonthValue() >= 7 ? 2 : 1);
        }
        GrupoEntidad entidad = GrupoEntidad.fabricarDeModelo(datosGrupo);
        if(entidad==null){
            throw new InsercionFallidaExepcion("no fue posible convertir entrada de datos en gateway");
        }
        try{
            entidad.setEliminado(0);
            System.out.println(entidad.getCategoria() +"-"+entidad.getCurso()+"-"+entidad.getAnio()+"-"+entidad.getIterable()+"-"+entidad.getCupos()+"-"+entidad.getIdInstructor()+"-"+entidad.getImagenGrupo()+"-"+entidad.getFechaCreacion()+"-"+entidad.getFechaFinalizacion()+"-"+entidad.getEliminado());
            GrupoEntidad guardado = repoGrupo.save(entidad);
            Grupo respuesta = Grupo.fabricarDeEntidad(guardado);
            return respuesta;
        }catch(Exception e){
            throw new InsercionFallidaExepcion("no se ha logrado insertar en gateway");
        }
    }

    public Grupo obtenerGrupoPorId(String categoria, String curso, Integer anio, Integer iterable){
        GrupoId id = new GrupoId(categoria, curso, anio, iterable);
        Optional<GrupoEntidad> op = repoGrupo.findById(id);
        if(op.isEmpty()){
            throw new NoExisteExcepcion("El elemento objetivo no existe en el sistema");
        }
        GrupoEntidad entidad = op.get();
        Grupo grupo = Grupo.fabricarDeEntidad(entidad);
        return grupo;
    }
    
    public Grupo actualizarGrupo(String categoria, String curso, Integer anio, Integer iterable, Grupo datosGrupo){
        GrupoId id = new GrupoId(categoria, curso, anio, iterable);
        if(!repoGrupo.existsById(id)){
            throw new NoExisteExcepcion("el grupo no se encuentra registrado en el sistema");
        }
        if(!repoImagen.existsById(datosGrupo.getImagenGrupo())){
            throw new DependenciaFallida("la imagen identificado con "+datosGrupo.getImagenGrupo()+" no existe en el sistema");
        }
        if(!repoInstructor.existsById(datosGrupo.getIdInstructor())){
            throw new DependenciaFallida("el instructor identificado con "+datosGrupo.getIdInstructor()+" no existe en el sistema");
        }
        GrupoEntidad entidad = repoGrupo.findById(id).get();
        entidad.setCupos(datosGrupo.getCupos());
        entidad.setEliminado(0);
        entidad.setFechaCreacion(datosGrupo.getFechaCreacion());
        entidad.setFechaFinalizacion(datosGrupo.getFechaFinalizacion());
        entidad.setIdInstructor(datosGrupo.getIdInstructor());
        entidad.setImagenGrupo(datosGrupo.getImagenGrupo());
        try{
            GrupoEntidad guardado = repoGrupo.save(entidad);
            Grupo respuesta = Grupo.fabricarDeEntidad(guardado);
            return respuesta;
        }catch(Exception e){
            throw new InsercionFallidaExepcion("No se ha logrado realizar el registro en gateway");
        }
    }

    public Grupo eliminarGrupo(String categoria, String curso, Integer anio, Integer iterable){
        GrupoId id = new GrupoId(categoria, curso, anio, iterable);
        if(!repoGrupo.existsById(id)){
            throw new NoExisteExcepcion("La entidad objetivo no existe");
        }
        GrupoEntidad entidad = repoGrupo.findById(id).get();
        entidad.setEliminado(1);
        GrupoEntidad respuesta = repoGrupo.save(entidad);
        return Grupo.fabricarDeEntidad(respuesta);
    }

    @Override
    public Grupo obtenerGrupo(String categoria, String curso, Integer anio, Integer iterable) {
        GrupoId id = new GrupoId(categoria, curso, anio, iterable);
        if(!repoGrupo.existsById(id)){
            throw new NoExisteExcepcion("el grupo buscado no existe en el sistema");
        }
        GrupoEntidad entidad = repoGrupo.findById(id).get();
        Grupo grupo = Grupo.fabricarDeEntidad(entidad);
        return grupo;
    }



}
