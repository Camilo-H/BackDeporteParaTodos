package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO.V2InstructorDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.InstructorEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.PerfilEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IInstructorRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IPerfilRepositorio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class InstructoresRest {
    @Autowired
    private IInstructorRepositorio repoInstructor;

    @Autowired IPerfilRepositorio repoPerfil;

    @GetMapping("/instructores")
    public List<V2InstructorDTO> getMethodName() {
        Iterable<InstructorEntidad> iterable = repoInstructor.findAll();
        List<V2InstructorDTO> instructores = new ArrayList<>();
        iterable.forEach((InstructorEntidad entidad)->{
            Optional<PerfilEntidad> optional = repoPerfil.findById(entidad.getIdPerfil());
            if(optional.isPresent()){
                V2InstructorDTO dtoInstructor = V2InstructorDTO.factoryFromPerfil(optional.get());
                instructores.add(dtoInstructor);
            }
        });
        return instructores;
    }

    @GetMapping("/instructor")
    public ResponseEntity<V2InstructorDTO> getInstructor(@RequestParam String idInstructor) {
        Optional<PerfilEntidad> response = repoPerfil.findById(idInstructor);
        if(response.isPresent()){
            V2InstructorDTO dtoResponse = V2InstructorDTO.factoryFromPerfil(response.get());
            return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    
}
