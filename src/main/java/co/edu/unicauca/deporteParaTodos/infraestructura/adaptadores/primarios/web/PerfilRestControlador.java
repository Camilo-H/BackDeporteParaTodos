package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.primarios.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada.IPerfilServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.PerfilEntidad;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class PerfilRestControlador {
    
    @Autowired
    private IPerfilServicio servicio;

    @GetMapping("/perfiles")
    public Iterable<PerfilEntidad> obtenerPerfiles() {
        return servicio.obtenerPerfiles();
    }
    
}
