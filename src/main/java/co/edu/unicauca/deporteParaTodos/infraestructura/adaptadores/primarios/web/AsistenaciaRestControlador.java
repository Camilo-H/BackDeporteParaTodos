package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.primarios.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IAsistenciaServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.AsistenciaEntidad;

@RestController
@RequestMapping("api")
public class AsistenaciaRestControlador {
    
    @Autowired
    private IAsistenciaServicio servicio;

    @GetMapping("/asistencias")
    public Iterable<AsistenciaEntidad> obtenerAsistencias(){
        return servicio.obtenerAsistencias();
    }
}
