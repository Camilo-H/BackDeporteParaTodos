package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.primarios.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IProgramaServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.ProgramaEntidad;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class ProgramaRestControlador {
    
    @Autowired
    private IProgramaServicio servicio;

    @GetMapping("programas")
    public Iterable<ProgramaEntidad> obtenerProgramas() {
        return servicio.obtenerProgramas();
    }
    
}
