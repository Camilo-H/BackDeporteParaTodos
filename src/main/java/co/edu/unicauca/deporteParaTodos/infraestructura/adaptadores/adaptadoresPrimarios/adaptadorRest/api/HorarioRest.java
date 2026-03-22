package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.HorarioEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IHorarioRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.logs.PeticionLogger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class HorarioRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorarioRest.class);

    @Autowired
    IHorarioRepositorio repositorio;

    @GetMapping("/horarios")
    public List<HorarioEntidad> getHorarios(@RequestParam String categoria, @RequestParam String curso, @RequestParam int anio, @RequestParam int iterable) {
        PeticionLogger.log(LOGGER, "GET", "/api/v2/horarios",
                "categoria=" + categoria + ", curso=" + curso + ", anio=" + anio + ", iterable=" + iterable);
        return repositorio.findByCategoriaAndCursoAndAnioAndIterableAndEliminado(categoria, curso, anio, iterable, 0);
    }
}
