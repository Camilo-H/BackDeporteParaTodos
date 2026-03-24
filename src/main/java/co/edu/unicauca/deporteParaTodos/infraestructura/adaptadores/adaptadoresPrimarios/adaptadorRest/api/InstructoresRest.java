package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICategoriaCursoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IInstructorServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v2")
@CrossOrigin(origins = { "*" }, maxAge = 4200, allowCredentials = "false")
@Validated
public class InstructoresRest {
    //IMPLEMENTANDO ENDPOINT INSTRUCTORES
    @Autowired
    private IInstructorServicio instructorServicio;


    /*@GetMapping("/instructores")
    public String getMethodName() {
        return null;
    }*/

    // GET api/v2/instructores
    @GetMapping("/instructores")
    public ResponseEntity<List<Instructor>> obtenerInstructores() {
        List<Instructor> instructores = instructorServicio.obtenerInstructores();
        return new ResponseEntity<>(instructores, HttpStatus.OK);
    }
}
