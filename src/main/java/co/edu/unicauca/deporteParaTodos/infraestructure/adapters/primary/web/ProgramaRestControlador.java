package co.edu.unicauca.deporteParaTodos.infraestructure.adapters.primary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.core.domain.models.ProgramaEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound.IProgramaServicio;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class ProgramaRestControlador {
    
    @Autowired
    private IProgramaServicio servicio;

    @GetMapping("programas")
    public Iterable<ProgramaEntidad> obtenerProgramas() {
        return servicio.obtenerProgramas();
    }
    
}
