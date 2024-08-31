package co.edu.unicauca.deporteParaTodos.infraestructure.adapters.primary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.core.domain.models.AlertaEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound.IAlertaServicio;

@RestController
@RequestMapping("api")
public class AlertaRestControlador {
    
    @Autowired
    private IAlertaServicio servicio;

    @GetMapping("/alertas")
    public Iterable<AlertaEntidad> obtenerAlertas(){
        return servicio.obtenerAlertas();
    }
}
