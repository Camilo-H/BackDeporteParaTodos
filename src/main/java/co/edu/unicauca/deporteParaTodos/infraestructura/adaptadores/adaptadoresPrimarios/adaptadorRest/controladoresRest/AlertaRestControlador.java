package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.controladoresRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IAlertaServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.AlertaEntidad;

@RestController
@CrossOrigin(origins= {"*"}, maxAge = 4200, allowCredentials = "false")
@RequestMapping("api")
@Validated
public class AlertaRestControlador {
    
    @Autowired
    private IAlertaServicio servicio;

    @GetMapping("/alertas")
    public Iterable<AlertaEntidad> obtenerAlertas(){
        return servicio.obtenerAlertas();
    }
}
