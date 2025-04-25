package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.AlumnoDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO.V2AlumnoDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.HorarioEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IAlumnoRepositorio;

@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class AlumnosRest {
    @Autowired
    private IAlumnoRepositorio repositorio;

    @GetMapping("/alumnosGrupo")
    public List<V2AlumnoDTO> getAlumnosGrupo(@RequestParam String categoria, @RequestParam String curso, @RequestParam int anio, @RequestParam int iterable) {
        List<Object[]> objetos = repositorio.buscarAlumnosGrupoRaw(categoria, curso, anio, iterable,0);
        List<V2AlumnoDTO> alumnoDTOs = new ArrayList<>();
        for (Object[] objects : objetos) {
            V2AlumnoDTO alumno = new V2AlumnoDTO();
            alumno = V2AlumnoDTO.fromObjectSQL(objects); //uso de static fabrica
            alumnoDTOs.add(alumno);
        }
        return alumnoDTOs;
    }
}
