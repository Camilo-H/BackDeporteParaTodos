package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.primarios.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada.IAlumnoServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.AlumnoEntidad;

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
