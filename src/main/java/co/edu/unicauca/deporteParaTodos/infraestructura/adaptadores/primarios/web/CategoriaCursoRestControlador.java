package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.primarios.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada.ICategoriaCursoServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.CategoriaCursoEntidad;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class CategoriaCursoRestControlador {
    
    @Autowired
    private ICategoriaCursoServicio servicio;

    @GetMapping("categorias")
    public Iterable<CategoriaCursoEntidad> obtenerCategoriasCursos() {
        return servicio.obtenerCategoriasCurso();
    }
    
}
