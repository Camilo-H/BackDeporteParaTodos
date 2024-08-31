package co.edu.unicauca.deporteParaTodos.infraestructure.adapters.primary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.core.domain.models.AlumnoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound.IAlumnoServicio;

@RestController
@RequestMapping("api")
public class AlumnoRestControlador {
    
    @Autowired
    private IAlumnoServicio servicio;

    @GetMapping("/alumnos")
    public Iterable<AlumnoEntidad> obtenerAlumnos(){
        return servicio.obtenerAlumnos();
    }
}
