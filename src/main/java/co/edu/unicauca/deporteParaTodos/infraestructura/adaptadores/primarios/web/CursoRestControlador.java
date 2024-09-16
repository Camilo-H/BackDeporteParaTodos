package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.primarios.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada.ICursoServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.CursoEntidad;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class CursoRestControlador {
    
    @Autowired
    private ICursoServicio servicio;

    @GetMapping("/cursos")
    public Iterable<CursoEntidad> obtenerCursos() {
        return servicio.obtenerCursos();
    }
    
}
