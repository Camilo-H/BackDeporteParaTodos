package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.primarios.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada.IAlertaServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.AlertaEntidad;

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
