package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICategoriaCursoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICategoriaCursoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.CategoriaDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ErrorInternoException;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoConvertibleException;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class CategoriaCursoServicio implements ICategoriaCursoServicio {

    @Autowired
    private ICategoriaCursoGateway categoriaCursoGateway;

    @Transactional(readOnly = true)
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

    @Transactional
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

    @Transactional(readOnly = true)
    @Override
    public CategoriaDto obtenerCategoriaCursoPorId(String tituloCategoria) {
        
        Categoria op = categoriaCursoGateway.obtenerCategoria(tituloCategoria);

        CategoriaDto respuesta = CategoriaDto.fabricarDeModelo(op);

        if(respuesta==null){
            throw new ErrorInternoException();
        }
        return respuesta;
    }

    @Transactional
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

    @Transactional
    @Override
    public CategoriaDto eliminarCategoria(String tituloCategoria) {
        Categoria categoria = categoriaCursoGateway.eliminarCategoria(tituloCategoria);
        if(categoria==null){
            throw new ErrorInternoException();
        }
        CategoriaDto respuesta = CategoriaDto.fabricarDeModelo(categoria);
        return respuesta;
    }

    

}
