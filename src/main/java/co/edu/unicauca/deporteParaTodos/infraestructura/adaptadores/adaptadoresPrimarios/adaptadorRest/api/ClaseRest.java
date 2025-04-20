package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ClaseEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IClaseRepositorio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class ClaseRest {
    @Autowired
    private IClaseRepositorio repositorio;

    @GetMapping("/clasesGrupo")
    public List<ClaseEntidad> getClasesGrupo(@RequestParam String categoria, @RequestParam String curso, @RequestParam Integer anio, @RequestParam Integer iterable) {
        return repositorio.findByIdGrupoCategoriaAndIdGrupoCursoAndIdGrupoAnioAndIdGrupoIterableAndEliminado(categoria,curso,anio,iterable,0);
    }

    
    
}
