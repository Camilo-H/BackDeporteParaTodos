package co.edu.unicauca.deporteParaTodos.infraestructure.adapters.primary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.core.domain.models.InscripcionEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound.IInscripcionServicio;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class InscripcionRestControlador {
    
    @Autowired
    IInscripcionServicio servicio;

    @GetMapping("/inscripciones")
    public Iterable<InscripcionEntidad> obtenerInscripciones() {
        return servicio.obtenerInscripciones();
    }
    
}
