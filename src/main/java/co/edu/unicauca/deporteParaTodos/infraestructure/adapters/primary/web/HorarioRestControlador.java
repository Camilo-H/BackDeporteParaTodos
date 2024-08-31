package co.edu.unicauca.deporteParaTodos.infraestructure.adapters.primary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.core.domain.models.HorarioEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound.IHorarioServicio;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class HorarioRestControlador {
    
    @Autowired
    private IHorarioServicio servicio;

    @GetMapping("/horarios")
    public Iterable<HorarioEntidad> obtenerHorarios() {
        return servicio.obtenerHorarios();
    }
    

}
