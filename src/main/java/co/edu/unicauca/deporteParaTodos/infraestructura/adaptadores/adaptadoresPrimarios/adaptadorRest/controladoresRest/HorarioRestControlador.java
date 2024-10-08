package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.controladoresRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IHorarioServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.HorarioEntidad;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class HorarioRestControlador {
    
    @Autowired
    private IHorarioServicio servicio;

    @GetMapping("/horarios")
    public Iterable<HorarioEntidad> obtenerHorarios() {
        return servicio.obtenerHorarios();
    }
    

}
