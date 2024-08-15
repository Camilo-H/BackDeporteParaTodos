package co.edu.unicauca.deporteParaTodos.infraestructure.adapters.primary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.core.domain.models.PerfilEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound.IPerfilServicio;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class PerfilRestControlador {
    
    @Autowired
    private IPerfilServicio servicio;

    @GetMapping("/perfiles")
    public Iterable<PerfilEntidad> obtenerPerfiles() {
        return servicio.obtenerPerfiles();
    }
    
}
