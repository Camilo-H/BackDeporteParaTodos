package co.edu.unicauca.deporteParaTodos.infraestructure.adapters.primary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.core.domain.models.ClaseEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound.IClaseServicio;

@RestController
@RequestMapping("api")
public class ClaseRestControlador {
    
    @Autowired
    private IClaseServicio servicio;

    @GetMapping("/clases")
    public Iterable<ClaseEntidad> obtenerClases(){
        return servicio.obtenerClases();
    }
}
