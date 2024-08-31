package co.edu.unicauca.deporteParaTodos.infraestructure.adapters.primary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.core.domain.models.DeporteEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound.IDeporteServicio;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class DeporteRestControlador {
    
    @Autowired
    private IDeporteServicio servicio;

    @GetMapping("/deportes")
    public Iterable<DeporteEntidad> obtenerDeportes() {
        return servicio.obtenerDeportes();
    }
    
}
