package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.primarios.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada.IDeporteServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.DeporteEntidad;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class DeporteRestControlador {
    
    @Autowired
    private IDeporteServicio servicio;

    @GetMapping("/deportes")
    public Iterable<DeporteEntidad> obtenerDeportes() {
        return servicio.obtenerDeportes();
    }
    
}
