package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.primarios.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IInscripcionServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.InscripcionEntidad;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class InscripcionRestControlador {
    
    @Autowired
    IInscripcionServicio servicio;

    @GetMapping("/inscripciones")
    public Iterable<InscripcionEntidad> obtenerInscripciones() {
        return servicio.obtenerInscripciones();
    }
    
}
