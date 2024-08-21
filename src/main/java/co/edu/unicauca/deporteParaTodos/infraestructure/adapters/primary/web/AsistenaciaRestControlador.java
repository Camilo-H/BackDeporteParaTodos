package co.edu.unicauca.deporteParaTodos.infraestructure.adapters.primary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.core.domain.models.AsistenciaEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.ports.inbound.IAsistenciaServicio;

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
