package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.primarios.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada.IInstructorServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.InstructorEntidad;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class InstructorRestControlador {
    
    @Autowired
    IInstructorServicio servicio;

    @GetMapping("/instructores")
    public Iterable<InstructorEntidad> obtenerInstructorues() {
        return servicio.obtenerInstructores();
    }
    
}
