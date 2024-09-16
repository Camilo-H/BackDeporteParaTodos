package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.baseDatos;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.FacultadEntidad;

public interface IFacultadRepositorio extends CrudRepository<FacultadEntidad,String>{
    
}
