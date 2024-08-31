package co.edu.unicauca.deporteParaTodos.infraestructure.adapters.primary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.core.domain.models.CoordinadorEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound.ICoordinadorServicio;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class CoordinadorRestControlador {
    
    @Autowired
    private ICoordinadorServicio servicio;

    @GetMapping("/coordinadores")
    public Iterable<CoordinadorEntidad> obtenerCoordinadores() {
        return servicio.obtenerCoordinadores();
    }
    

}
