package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.controladoresRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICategoriaCursoServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.CategoriaCursoEntidad;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("api")
@Validated
public class CategoriaCursoRestControlador {
    
    @Autowired
    private ICategoriaCursoServicio servicio;

    @GetMapping("categorias")
    public Iterable<CategoriaCursoEntidad> obtenerCategoriasCursos() {
        return servicio.obtenerCategoriasCurso();
    }
/*
    @PostMapping("categoria")
    public String insertarCategoriaCurso(@ModelAttribute CategoriaCursoDTO datos) {
        //TODO: process POST request
        System.out.println(datos.getTitulo());
        System.out.println(datos.getDescripcion());
        System.out.println(datos.getImagen().getName());
        System.out.println(datos.getImagen().getSize());
        System.out.println(datos.getImagen().getContentType());
        System.out.println(datos.getImagen().getOriginalFilename());
        return "";
    }
     */  
}
