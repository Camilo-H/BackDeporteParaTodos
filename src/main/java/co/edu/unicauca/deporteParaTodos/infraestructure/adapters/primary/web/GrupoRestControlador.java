package co.edu.unicauca.deporteParaTodos.infraestructure.adapters.primary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.core.domain.models.GrupoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound.IGrupoServicio;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class GrupoRestControlador {
    
    @Autowired
    private IGrupoServicio servicio;

    @GetMapping("/grupos")
    public Iterable<GrupoEntidad> obtenerGrupos() {
        return servicio.obtenerGrupos();
    }
    
}
