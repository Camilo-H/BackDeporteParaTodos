package co.edu.unicauca.deporteParaTodos.others.estudiantes.infraestructura.rest.controller;
// package co.edu.unicauca.proyecto.estudiantes.infraestructura.rest.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import co.edu.unicauca.proyecto.estudiantes.dominio.model.Alumno;
// import co.edu.unicauca.proyecto.estudiantes.dominio.services.AlumnoService;
// import org.springframework.web.bind.annotation.*;
// import java.util.*;


// @RestController
// @RequestMapping("/alumnos")
// public class AlumnoController {
//     private final AlumnoService alumnoService;
//     @Autowired
//     public AlumnoController (AlumnoService prmalumnoService){
//         this.alumnoService = prmalumnoService;
//     }

//     @PostMapping
//     public ResponseEntity<Alumno> crearAlumno(@RequestBody Alumno alumno) {
//         Alumno nuevoAlumno = alumnoService.crearAlumno(alumno);
//         return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAlumno);
//     }

//     @GetMapping("/{codigo}")
//     public ResponseEntity<Alumno> obtenerAlumno(@PathVariable String codigo) {
//         Alumno alumno = alumnoService.obtenerAlumnoPorCodigo(codigo);
//         return ResponseEntity.ok(alumno);
//     }
    
//     @GetMapping
//     public ResponseEntity<List<Alumno>> listarAlumnos() {
//         List<Alumno> alumnos = alumnoService.listarAlumnos();
//         return ResponseEntity.ok(alumnos);
//     }

//     @DeleteMapping("/{codigo}")
//     public ResponseEntity<Void> eliminarAlumno(@PathVariable String codigo) {
//         alumnoService.eliminarAlumno(codigo);
//         return ResponseEntity.noContent().build();
//     }
// }
