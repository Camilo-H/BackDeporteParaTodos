package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.controladoresRest;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICursoServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.CursoDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CursoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ImagenEntidad;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("api")
@Validated
public class CursoRestControlador {
    
    @Autowired
    private ICursoServicio servicio;

    @GetMapping("/cursos")
    public Iterable<CursoEntidad> obtenerCursos() {
        return servicio.obtenerCursos();
    }
    
    @PostMapping("/curso")
    public String insertarCurso(@Valid @ModelAttribute CursoDTO datosCurso) {
        //TODO: process POST request
        servicio.insertarCurso(datosCurso);
        return "";
    }

    @GetMapping("/curso")
    public Curso getMethodName(@RequestParam(name="nombre") String nombre) {
        return servicio.obtenerCurso(nombre);
    }
    
    
    
    public String convertirABase64(byte[] datosImagen) {
        return Base64.getEncoder().encodeToString(datosImagen);
    }

    @GetMapping("/cursoss")
    public List<Curso> recuperarCursos(){
        return servicio.recuperarCursos();
    }
    
}