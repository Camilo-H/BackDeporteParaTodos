package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.baseDatos;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.AlumnoEntidad;

public interface IAlumnoRepositorio extends CrudRepository<AlumnoEntidad,String>{
    
}
