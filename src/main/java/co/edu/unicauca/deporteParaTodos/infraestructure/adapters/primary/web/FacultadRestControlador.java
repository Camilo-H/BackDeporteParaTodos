package co.edu.unicauca.deporteParaTodos.infraestructure.adapters.primary.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.core.domain.models.FacultadEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound.IFacultadServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class FacultadRestControlador {

    @Autowired
    private IFacultadServicio servicio;

    @GetMapping("/facultades")
    public Iterable<FacultadEntidad> obtenerFacultades() {
        return servicio.obtenerFacultades();
    }
    
}
