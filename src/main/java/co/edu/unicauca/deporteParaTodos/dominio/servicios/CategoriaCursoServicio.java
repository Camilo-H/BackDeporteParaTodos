package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICategoriaCursoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICategoriaCursoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.CategoriaDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.ImagenDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.peticion.CategoriaInDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.CategoriaDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICategoriaCursoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ErrorInternoException;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoConvertibleException;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.mappers.MapperImagen;

@Service
public class CategoriaCursoServicio implements ICategoriaCursoServicio {

    // @Autowired

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ICategoriaCursoGateway categoriaCursoGateway;

    /**
     * Obtiene retorna las categorias disponibles en el sistema.
     * @return lista de categorias
     */
    @Override
    public List<CategoriaDto> recuperarCategoriasCurso() {
        //no es necesario generar excepcion cuando la lista esta vacia
        List<Categoria> categorias = categoriaCursoGateway.obtenerCategorias();
        List<CategoriaDto> listaDtos = new ArrayList<>();
        //Transformacion de datos
        categorias.forEach(modelo ->{
            CategoriaDto dto = CategoriaDto.fabricarDeModelo(modelo);
            listaDtos.add(dto);
        });
        return listaDtos;
    }

    @Override
    public CategoriaDto insertarCategoria(CategoriaDto dto) {
        //transformacion de datos
        Categoria modelo = Categoria.fabricarDeDto(dto);
        if(modelo==null){
            throw new NoConvertibleException();
        }
        //uso del gate
        Categoria retorno = categoriaCursoGateway.registrarCategoria(modelo);
        if(retorno==null){
            throw new InsercionFallidaExepcion("ha fallado el proceso");
        }
        //transformacion de datos
        CategoriaDto respuesta = CategoriaDto.fabricarDeModelo(retorno);
        //retorno
        return respuesta;
    }

    @Deprecated
    @Override
    public CategoriaDTO registrarCategoria(CategoriaInDTO datos) {
        Categoria categoriaModelo = new Categoria();
        ImagenDTO imagenDTO = new ImagenDTO();
        Imagen imagenModelo;
        ImagenDTO dtoimagenRetorno = null;
      

        categoriaModelo.setTitulo(datos.getTitulo());
        categoriaModelo.setDescripcion(datos.getDescripcion());
        Categoria regitro = categoriaCursoGateway.registrarCategoria(categoriaModelo);

       

        CategoriaDTO catRetorno = new CategoriaDTO();
        catRetorno.setTitulo(regitro.getTitulo());
        catRetorno.setDescripcion(regitro.getDescripcion());
        catRetorno.setImagen(dtoimagenRetorno);
        return catRetorno;
    }

    @Override
    public Categoria obtenerCategoriaCursoPorId(String tituloCategoria) {
        // TODO Auto-generated method stub
        return categoriaCursoGateway.obtenerCategoria(tituloCategoria)
                .orElseThrow(
                        () -> new NoExisteExcepcion("La categoria con el titulo " + tituloCategoria + " no existe"));
    }

    @Override
    public CategoriaDto actualizarCategoria(String titulo, CategoriaDto datosCategoria) {
        if (!categoriaCursoGateway.existeCategoria(titulo)) {
            throw new NoExisteExcepcion("No se encuentra el registro de la categoria ");
        }

        Categoria categoriaModelo = Categoria.fabricarDeDto(datosCategoria);

        if(categoriaModelo == null){
            throw new ErrorInternoException();
        }

        Categoria regitro = categoriaCursoGateway.actualizarCategoria(titulo, categoriaModelo);
        CategoriaDto catRetorno = CategoriaDto.fabricarDeModelo(regitro);
        
        return catRetorno;
    }

    @Override
    public CategoriaDTO eliminarCategoria(String tituloCategoria) {
        if (!categoriaCursoGateway.existeCategoria(tituloCategoria)) {
            throw new NoExisteExcepcion("La categoría con el título " + tituloCategoria + " no existe.");
        }
        return mapper.map(categoriaCursoGateway.eliminarCategoria(tituloCategoria), CategoriaDTO.class);
    }

    

}
