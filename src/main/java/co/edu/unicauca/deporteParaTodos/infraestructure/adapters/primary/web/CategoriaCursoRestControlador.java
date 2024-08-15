package co.edu.unicauca.deporteParaTodos.infraestructure.adapters.primary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.core.domain.models.CategoriaCursoEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound.ICategoriaCursoServicio;
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
