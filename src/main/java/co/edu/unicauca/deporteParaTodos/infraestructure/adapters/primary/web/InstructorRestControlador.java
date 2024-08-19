package co.edu.unicauca.deporteParaTodos.infraestructure.adapters.primary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.core.domain.models.InstructorEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound.IInstructorServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api")
public class InstructorRestControlador {
    
    @Autowired
    IInstructorServicio servicio;

    @GetMapping("/instructores")
    public Iterable<InstructorEntidad> obtenerInstructorues() {
        return servicio.obtenerInstructores();
    }
    
}
