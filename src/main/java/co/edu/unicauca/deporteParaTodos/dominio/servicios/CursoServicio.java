package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICursoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICursoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.CursoDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CursoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ImagenEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICursoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IImagenRepositorio;

@Service
public class CursoServicio implements ICursoServicio{

    @Autowired
    private ICursoRepositorio repoCurso;
    
    @Autowired
    private IImagenRepositorio repoImagen;

    @Autowired
    private ICursoGateway cursoGateway;

    @Override
    public Iterable<CursoEntidad> obtenerCursos() {
        cursoGateway.obtenerCursos();
        return null;
    }

    @Override
    public CursoEntidad insertarCurso(CursoDTO datosCurso) {
        CursoEntidad cursoInsertado = new CursoEntidad();
        if(datosCurso.getImagen()==null){
            System.out.println("datos de imagen vacio");
            return cursoInsertado;
        }
        if(repoImagen.existsById(1)){
            repoImagen.deleteById(1);
        }
        ImagenEntidad imagen = new ImagenEntidad();
        imagen.setId(2);
        imagen.setNombre(datosCurso.getImagen().getOriginalFilename());
        imagen.setTipoArchivo(datosCurso.getImagen().getContentType());
        imagen.setLongitud(datosCurso.getImagen().getSize());
        try{
            imagen.setDatos(datosCurso.getImagen().getBytes());
        }catch(IOException e){
            System.out.println("no se ha obtenido la imagen");
            return cursoInsertado;
        }

        System.out.println(repoImagen.save(imagen));
        //cursoInsertado = new CursoEntidad(datosCurso.getNombre(),datosCurso.getNombreDeporte(), datosCurso.getTituloCategoria(), 1, datosCurso.getDescripcion());
        
        return repoCurso.save(cursoInsertado);
    }

    @Override
    public Curso obtenerCurso(String titulo) {
        return cursoGateway.obtenerCurso(titulo);
    }

    @Override
    public List<Curso> recuperarCursos() {
        return cursoGateway.obtenerCursos();
    }

    @Override
    public Curso guardarCurso(Curso curso) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardarCurso'");
    }

    
    
}
