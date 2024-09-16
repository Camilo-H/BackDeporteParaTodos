package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.primarios.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada.IClaseServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.ClaseEntidad;

@RestController
@RequestMapping("api")
public class ClaseRestControlador {
    
    @Autowired
    private IClaseServicio servicio;

    @GetMapping("/clases")
    public Iterable<ClaseEntidad> obtenerClases(){
        return servicio.obtenerClases();
    }
}
