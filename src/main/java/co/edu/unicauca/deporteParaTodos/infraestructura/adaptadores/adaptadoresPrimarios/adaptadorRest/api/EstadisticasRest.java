package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO.V2Estadistica;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IAsistenciaRepositorio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class EstadisticasRest {
    @Autowired
    private IAsistenciaRepositorio repositorioAsistencias;

    @GetMapping("estadisticas/categorias")
    public ResponseEntity<List<V2Estadistica>> getEstaditicasCategorias(@RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<Object[]> objetos = repositorioAsistencias.estadisticasCategorias(fechaInicio, fechaFin);
        List<V2Estadistica> estadisticas = new ArrayList<>();
        objetos.forEach((objeto)->{
            V2Estadistica estadistica = V2Estadistica.fromObjectCategorias(objeto);
            estadisticas.add(estadistica);
        });
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }
    
    @GetMapping("estadisticas/cursos")
    public ResponseEntity<List<V2Estadistica>> getEstadisticasCursos(@RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
    @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<Object[]> objetos = repositorioAsistencias.estadisticasCursos(fechaInicio, fechaFin);
        List<V2Estadistica> estadisticas = new ArrayList<>();
        objetos.forEach((objeto)->{
            V2Estadistica estadistica = V2Estadistica.fromObjectCursos(objeto);
            estadisticas.add(estadistica);
        });
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }
    
    @GetMapping("estadisticas/grupos")
    public ResponseEntity<List<V2Estadistica>> getEstadisticasGrupos(@RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
    @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<Object[]> objetos = repositorioAsistencias.estadisticasGrupos(fechaInicio, fechaFin);
        List<V2Estadistica> estadisticas = new ArrayList<>();
        objetos.forEach((objeto)->{
            V2Estadistica estadistica = V2Estadistica.fromObjectGrupos(objeto);
            estadisticas.add(estadistica);
        });
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }
}
