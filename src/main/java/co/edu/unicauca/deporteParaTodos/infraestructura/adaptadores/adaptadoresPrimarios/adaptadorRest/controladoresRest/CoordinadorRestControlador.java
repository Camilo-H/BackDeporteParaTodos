package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.controladoresRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICoordinadorServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CoordinadorEntidad;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class CoordinadorRestControlador {
    
    @Autowired
    private ICoordinadorServicio servicio;

    @GetMapping("/coordinadores")
    public Iterable<CoordinadorEntidad> obtenerCoordinadores() {
        return servicio.obtenerCoordinadores();
    }
    

}
