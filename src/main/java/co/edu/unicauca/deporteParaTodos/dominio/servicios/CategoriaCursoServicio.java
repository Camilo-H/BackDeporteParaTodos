package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICategoriaCursoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICategoriaCursoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;

@Service
public class CategoriaCursoServicio implements ICategoriaCursoServicio {

    @Autowired
    private ICategoriaCursoGateway categoriaCursoGateway;

    @Transactional(readOnly = true)
    @Override
    public List<Categoria> recuperarCategoriasCurso() {
        return categoriaCursoGateway.obtenerCategorias();
    }

    @Transactional
    @Override
    public Categoria insertarCategoria(Categoria modelo) {
        Categoria retorno = categoriaCursoGateway.registrarCategoria(modelo);
        if (retorno == null) {
            throw new InsercionFallidaExepcion("ha fallado el proceso");
        }
        return retorno;
    }

    @Transactional(readOnly = true)
    @Override
    public Categoria obtenerCategoriaCursoPorId(String tituloCategoria) {
        return categoriaCursoGateway.obtenerCategoria(tituloCategoria);
    }

    @Transactional
    @Override
    public Categoria actualizarCategoria(String titulo, Categoria datosCategoria) {
        if (!categoriaCursoGateway.existeCategoria(titulo)) {
            throw new NoExisteExcepcion("No se encuentra el registro de la categoria ");
        }
        return categoriaCursoGateway.actualizarCategoria(titulo, datosCategoria);
    }

    @Transactional
    @Override
    public Categoria eliminarCategoria(String tituloCategoria) {
        return categoriaCursoGateway.eliminarCategoria(tituloCategoria);
    }
}
