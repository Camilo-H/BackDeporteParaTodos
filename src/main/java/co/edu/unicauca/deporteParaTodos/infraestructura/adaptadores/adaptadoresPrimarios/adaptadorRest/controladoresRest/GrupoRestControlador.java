package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.controladoresRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IGrupoServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.GrupoEntidad;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api")
public class GrupoRestControlador {
    
    @Autowired
    private IGrupoServicio servicio;

    @GetMapping("/grupos")
    public Iterable<GrupoEntidad> obtenerGrupos() {
        return servicio.obtenerGrupos();
    }
    
}
