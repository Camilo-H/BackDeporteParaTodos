package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICursoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CursoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ImagenEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICursoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IImagenRepositorio;

@Service
public class CursoGateway implements ICursoGateway{

    @Autowired
    private ICursoRepositorio repoCurso;

    @Autowired
    private IImagenRepositorio repoImagen;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @Override
    public boolean existeCurso(String nombreCurso) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existeCurso'");
    }

    //TODO: utilizar mapeo
    @Override
    public List<Curso> obtenerCursos() {
        Iterable<CursoEntidad> respuesta = this.repoCurso.findAll();
        Curso curso = mapper.map(respuesta, Curso.class);
        if(curso==null){
            System.out.println("nullll");
        }
        return null;
        /*Iterable<CursoEntidad> iterable = repoCurso.findAll();
        List<Curso> list = new ArrayList<Curso>();
        Curso curso = new Curso();
        for (CursoEntidad cursoEntidad : iterable) {
            cursoEntidad.getImagen();
            curso= new Curso(
                cursoEntidad.getNombre(), 
                cursoEntidad.getNombreDeporte(), 
                cursoEntidad.getTituloCategoria(), 
                null, 
                cursoEntidad.getDescripcion()
            );
            Optional<ImagenEntidad> imagenOptional = repoImagen.findById(cursoEntidad.getImagen());
            if(imagenOptional.isPresent()){
                Imagen imagen = new Imagen(
                    imagenOptional.get().getId(),
                    imagenOptional.get().getNombre(),
                    imagenOptional.get().getTipoArchivo(),
                    imagenOptional.get().getLongitud(),
                    imagenOptional.get().getDatos()
                );
                curso.setImagen(imagen);
            }
            list.add(curso);
        }
        return list;*/
    }

    //TODO: mapeo no esta dando resultado, corregir
    @Override
    public Curso obtenerCurso(String nombreCurso) {
        System.out.println("nombre: : : "+nombreCurso);
        Optional<CursoEntidad> resultado = repoCurso.findById(nombreCurso);
        if(!resultado.isPresent()){
            System.out.println("no resultado");
            return null;
        }
        Curso respuesta = mapper.map(resultado, Curso.class);
        if(respuesta==null){
            System.out.println("no conversion");
        }
        return respuesta;
    }

    @Override
    public Curso insertarCurso(Curso curso) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertarCurso'");
    }

    @Override
    public Curso actualizarCurso(Curso curso, String nombreCurso) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarCurso'");
    }

    @Override
    public Curso eliminarCurso(String nombre) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarCurso'");
    }
    
}
