package co.edu.unicauca.deporteParaTodos.infraestructure.adapters.primary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.core.domain.models.CursoEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound.ICursoServicio;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class CursoRestControlador {
    
    @Autowired
    private ICursoServicio servicio;

    @GetMapping("/cursos")
    public Iterable<CursoEntidad> obtenerCursos() {
        return servicio.obtenerCursos();
    }
    
}
