package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.controladoresRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IInstructorServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.InstructorEntidad;

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
