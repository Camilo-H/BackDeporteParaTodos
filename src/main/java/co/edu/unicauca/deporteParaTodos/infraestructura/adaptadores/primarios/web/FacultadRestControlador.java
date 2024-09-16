package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.primarios.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IFacultadServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.FacultadEntidad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class FacultadRestControlador {

    @Autowired
    private IFacultadServicio servicio;

    @GetMapping("/facultades")
    public Iterable<FacultadEntidad> obtenerFacultades() {
        return servicio.obtenerFacultades();
    }
    
}
